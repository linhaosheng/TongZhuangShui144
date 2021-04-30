package pro.haichuang.tzs144.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPluginPlatformInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.jpush.ExampleUtil;
import pro.haichuang.tzs144.model.ExitModel;
import pro.haichuang.tzs144.model.LoginModel;
import pro.haichuang.tzs144.model.MessageEvent;
import pro.haichuang.tzs144.model.SubjectModel;
import pro.haichuang.tzs144.presenter.LoginPresenter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.util.Config;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
@RuntimePermissions
public class LoginActivity extends BaseActivity implements ILoadDataView<String> {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.inventory_subject)
    Spinner inventorySubject;
    @BindView(R.id.check_state)
    ImageView checkState;
    @BindView(R.id.test_push)
    Button test_push;
    @BindView(R.id.server_tvt)
    TextView serverTvt;
    private LoginPresenter loginPresenter;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private List<SubjectModel.DataBean>dataBeanList;
    private boolean checked = true;
    private int selectPosition;
    private boolean todayLogin;
    private boolean loadSubject;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private boolean showDialog = true;



    /**消息Id**/
    private static final String KEY_MSGID = "msg_id";
    /**该通知的下发通道**/
    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
    /**通知标题**/
    private static final String KEY_TITLE = "n_title";
    /**通知内容**/
    private static final String KEY_CONTENT = "n_content";
    /**通知附加字段**/
    private static final String KEY_EXTRAS = "n_extras";

    private JPluginPlatformInterface jPluginPlatformInterface;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }


    private void initView(){

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        //数据
        data_list = new ArrayList<String>();

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        inventorySubject.setAdapter(arr_adapter);

        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadSubject = false;
                showDialog = false;
                loginPresenter.loadSubjectList(account.getText().toString(),showDialog);
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!loadSubject && !TextUtils.isEmpty(account.getText())) {
                    Log.i(TAG, "afterTextChanged==");
                    loginPresenter.loadSubjectList(account.getText().toString(),showDialog);
                    loadSubject = true;
                }
            }
        });

        /**
         * 检查通知权限是否打开
         */
        if (!Utils.isNotificationEnabled(this)){
            Utils.goToNotificationSetting(this);
        }
        handlerPushClick();
    }

    /**
     * 处理极光推送
     */
    private void handlerPushClick(){
        String data = null;
        //获取华为平台附带的jpush信息
        if (getIntent().getData() != null) {
            data = getIntent().getData().toString();
        }

        //获取fcm、oppo、vivo、华硕、小米平台附带的jpush信息
        if (TextUtils.isEmpty(data) && getIntent().getExtras() != null) {
            data = getIntent().getExtras().getString("JMessageExtra");
        }

        Log.w(TAG, "msg content is " + String.valueOf(data));
        if (TextUtils.isEmpty(data)) return;

        try {
            JSONObject jsonObject = new JSONObject(data);
            String msgId = jsonObject.optString(KEY_MSGID);
            byte whichPushSDK = (byte) jsonObject.optInt(KEY_WHICH_PUSH_SDK);
            String title = jsonObject.optString(KEY_TITLE);
            String content = jsonObject.optString(KEY_CONTENT);
            String extras = jsonObject.optString(KEY_EXTRAS);
            StringBuilder sb = new StringBuilder();
            sb.append("msgId:");
            sb.append(String.valueOf(msgId));
            sb.append("\n");
            sb.append("title:");
            sb.append(String.valueOf(title));
            sb.append("\n");
            sb.append("content:");
            sb.append(String.valueOf(content));
            sb.append("\n");
            sb.append("extras:");
            sb.append(String.valueOf(extras));
            sb.append("\n");
            sb.append("platform:");
            sb.append(getPushSDKName(whichPushSDK));

            //上报点击事件
            JPushInterface.reportNotificationOpened(this, msgId, whichPushSDK, data);
        } catch (JSONException e) {
            Log.w(TAG, "parse notification error");
        }

    }

    private String getPushSDKName(byte whichPushSDK) {
        String name;
        switch (whichPushSDK) {
            case 0:
                name = "jpush";
                break;
            case 1:
                name = "xiaomi";
                break;
            case 2:
                name = "huawei";
                break;
            case 3:
                name = "meizu";
                break;
            case 4:
                name = "oppo";
                break;
            case 5:
                name = "vivo";
                break;
            case 6:
                name = "asus";
                break;
            case 8:
                name = "fcm";
                break;
            default:
                name = "jpush";
        }
        return name;
    }

    @Override
    protected void setUpView() {
        jPluginPlatformInterface = new JPluginPlatformInterface(this);
        LoginActivityPermissionsDispatcher.allplyPermissionWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION})
    public void allplyPermission() {
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 申请权限被拒绝时
     */
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION})
    void onWriteAndReadDenied() {
        Utils.showCenterTomast("获取位置权限被拒，有可能导致无法使用");
    }

    /**
     * 申请权限被拒绝并勾选不再提醒时
     */
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION})
    void onInstallNeverAskAgain() {
        Utils.showCenterTomast("获取位置权限被拒，有可能导致无法使用");
    }


    @Override
    protected void setUpData() {
        loginPresenter = new LoginPresenter(this);
        String accountInfo = SPUtils.getString(Config.ACCOUNT_INFO, "");
        if (!accountInfo.equals("")){
            showDialog = false;
            int i = accountInfo.indexOf("-");
            String accountTxt = accountInfo.substring(0,i);
            String passwordTxt = accountInfo.substring(i+1);
            account.setText(accountTxt);
            password.setText(passwordTxt);

            Log.i(TAG,"accountTxt==="+accountTxt+"===passwordTxt=="+passwordTxt);

        }

        //account.setText("10008");//"1001"
        //password.setText("123456");

        String login_time = SPUtils.getString(Config.LOGIN_TIME, "");
        if (!login_time.equals("")) {
            String currentTime = Utils.transformTime(new Date());
            if (currentTime.contains(login_time)) {
                todayLogin = true;
            }
        }

        if (!todayLogin) {
            inventorySubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectPosition = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        /**
         * 设置默认显示上一次的经销商
         */
        String account = SPUtils.getString(Config.ACCOUNT, "");
        if (!account.equals("")) {
            String inventoryStr = SPUtils.getString(account, "");
            if (!inventoryStr.equals("")) {
                int index = data_list.indexOf(inventoryStr);
                inventorySubject.setSelection(index, true);
            }
        }

    }

    @OnClick({R.id.login_btn, R.id.check_state,R.id.test_push,R.id.server_tvt})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.check_state:
                if (checked) {
                    checkState.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.check));
                } else {
                    checkState.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.check_box));
                }
                checked = !checked;
                break;
            case R.id.test_push:
                ExampleUtil.buildLocalNotification(this,"测试推送","这是一条测试的推送");
                break;
            case R.id.server_tvt:
               startActivity(new Intent(this,ServerConfigActivity.class));
                break;
        }
    }

    private void login() {

        if (TextUtils.isEmpty(account.getText())) {
            Utils.showCenterTomast("请输入正确账号");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            Utils.showCenterTomast("请输入正确密码");
            return;
        }
        SPUtils.putString(Config.ACCOUNT, account.getText().toString());
        if (dataBeanList!=null && dataBeanList.size()>0){
            SPUtils.putString(account.getText().toString(), dataBeanList.get(selectPosition).getId());
        }else {
            Utils.showCenterTomast("请检查账户是否绑定库存主体");
            return;
        }

       if (!Utils.isOPen(this)){
           MessageDialog.show(this,"提示","请打开GPS开关，否则应用无法正常使用");
           return;
       }
        if (checked){
            String info = account.getText().toString() +"-"+password.getText().toString();
            SPUtils.putString(Config.ACCOUNT_INFO,info);
        }else {
            SPUtils.putString(Config.ACCOUNT_INFO,"");
        }
        Log.i(TAG, "登录....");

        if (dataBeanList!=null){
            if (dataBeanList.size()<=0){
                Utils.showCenterTomast("请检查账户是否绑定库存主体");
                return;
            }
            Config.CURRENT_MAIN_ID = dataBeanList.get(selectPosition).getId();
            Config.CURRENT_MAIN_NAME =  dataBeanList.get(selectPosition).getName();

            loginPresenter.loginServer(account.getText().toString(), password.getText().toString(), dataBeanList.get(selectPosition).getId());
        }else {
            Utils.showCenterTomast("获取数据超时，请退出，重新登录");
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this, "加载中....");
    }

    @Override
    public void successLoad(String data) {

        /**
         * 极光推送，添加tag
         */
        Set<String> tags = new HashSet<>();
        tags.add(Config.CURRENT_MAIN_ID);

        JPushInterface.setTags(this, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i(TAG,"i===="+i);
            }
        });

        Config.IS_LOGIN = true;
        WaitDialog.dismiss();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast("登录失败，请检查账户和密码是否正确");
        WaitDialog.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        WaitDialog.dismiss();
        if (event!=null){
            arr_adapter.clear();
            if (event.subjectModel==null){
                return;
            }
            dataBeanList = event.subjectModel.getData();

            for (SubjectModel.DataBean dataBean : event.subjectModel.getData()){
                data_list.add(dataBean.getName());
            }
            arr_adapter.addAll();
            arr_adapter.notifyDataSetChanged();
        }else {
            loadSubject = false;
        }
        Log.i(TAG,"onMessageEvent===");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ExitModel event) {
       finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        if (jPluginPlatformInterface!=null){
            jPluginPlatformInterface.onStart(this);
        }
    }

    protected void onStop() {
        super.onStop();
        jPluginPlatformInterface.onStop(this);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mLocationClient!=null){
            mLocationClient.stop();
        }
        super.onDestroy();
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            Config.LATITUDE = location.getLatitude();    //获取纬度信息
            Config.LONGITUDE = location.getLongitude();    //获取经度信息
            Config.CITY = location.getCity();    //获取城市
            //Log.i("TAG","==="+Config.CITY);
        }
    }
}
