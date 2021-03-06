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
 * ????????????
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


    /**??????Id**/
    private static final String KEY_MSGID = "msg_id";
    /**????????????????????????**/
    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
    /**????????????**/
    private static final String KEY_TITLE = "n_title";
    /**????????????**/
    private static final String KEY_CONTENT = "n_content";
    /**??????????????????**/
    private static final String KEY_EXTRAS = "n_extras";

    private JPluginPlatformInterface jPluginPlatformInterface;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }


    private void initView(){

        mLocationClient = new LocationClient(getApplicationContext());
        //??????LocationClient???
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

        //??????
        data_list = new ArrayList<String>();

        //?????????
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //????????????
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //???????????????
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
         * ??????????????????????????????
         */
        if (!Utils.isNotificationEnabled(this)){
            Utils.goToNotificationSetting(this);
        }
        handlerPushClick();
    }

    /**
     * ??????????????????
     */
    private void handlerPushClick(){
        String data = null;
        //???????????????????????????jpush??????
        if (getIntent().getData() != null) {
            data = getIntent().getData().toString();
        }

        //??????fcm???oppo???vivo?????????????????????????????????jpush??????
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

            //??????????????????
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
     * ????????????????????????
     */
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION})
    void onWriteAndReadDenied() {
        Utils.showCenterTomast("??????????????????????????????????????????????????????");
    }

    /**
     * ?????????????????????????????????????????????
     */
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION})
    void onInstallNeverAskAgain() {
        Utils.showCenterTomast("??????????????????????????????????????????????????????");
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
         * ???????????????????????????????????????
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
                ExampleUtil.buildLocalNotification(this,"????????????","???????????????????????????");
                break;
            case R.id.server_tvt:
               startActivity(new Intent(this,ServerConfigActivity.class));
                break;
        }
    }

    private void login() {

        if (TextUtils.isEmpty(account.getText())) {
            Utils.showCenterTomast("?????????????????????");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            Utils.showCenterTomast("?????????????????????");
            return;
        }
        SPUtils.putString(Config.ACCOUNT, account.getText().toString());
        if (dataBeanList!=null && dataBeanList.size()>0){
            SPUtils.putString(account.getText().toString(), dataBeanList.get(selectPosition).getId());
        }else {
            Utils.showCenterTomast("???????????????????????????????????????");
            return;
        }

       if (!Utils.isOPen(this)){
           MessageDialog.show(this,"??????","?????????GPS???????????????????????????????????????");
           return;
       }
        if (checked){
            String info = account.getText().toString() +"-"+password.getText().toString();
            SPUtils.putString(Config.ACCOUNT_INFO,info);
        }else {
            SPUtils.putString(Config.ACCOUNT_INFO,"");
        }
        Log.i(TAG, "??????....");

        if (dataBeanList!=null){
            if (dataBeanList.size()<=0){
                Utils.showCenterTomast("???????????????????????????????????????");
                return;
            }
            Config.CURRENT_MAIN_ID = dataBeanList.get(selectPosition).getId();
            Config.CURRENT_MAIN_NAME =  dataBeanList.get(selectPosition).getName();

            loginPresenter.loginServer(account.getText().toString(), password.getText().toString(), dataBeanList.get(selectPosition).getId());
        }else {
            Utils.showCenterTomast("?????????????????????????????????????????????");
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this, "?????????....");
    }

    @Override
    public void successLoad(String id) {

        /**
         * ?????????????????????tag
         */
        Set<String> tags = new HashSet<>();
        tags.add(Config.CURRENT_MAIN_ID);
        tags.add(id);

        JPushInterface.setTags(this, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i(TAG,"i===="+i);
            }
        });

        JPushInterface.setAlias(this, id, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i(TAG,"setAlias===="+i+"======s===="+s);
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
        Utils.showCenterTomast(error);
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
            //?????????BDLocation?????????????????????????????????????????????get??????????????????????????????????????????
            //?????????????????????????????????????????????????????????????????????
            //??????????????????????????????????????????????????????BDLocation???????????????
            Config.LATITUDE = location.getLatitude();    //??????????????????
            Config.LONGITUDE = location.getLongitude();    //??????????????????
            Config.CITY = location.getCity();    //????????????
          //  Log.i("TAG","==="+Config.CITY);
        }
    }
}
