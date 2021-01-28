package pro.haichuang.tzs144.activity;

import android.content.Intent;
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

import androidx.core.content.ContextCompat;

import com.kongzue.dialog.v2.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private LoginPresenter loginPresenter;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private List<SubjectModel.DataBean>dataBeanList;
    private boolean checked = true;
    private int selectPosition;
    private boolean todayLogin;
    private boolean loadSubject;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {
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
                if (!loadSubject && !TextUtils.isEmpty(account.getText())) {
                    Log.i(TAG, "afterTextChanged==");
                    loginPresenter.loadSubjectList(account.getText().toString());
                    loadSubject = true;
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        loginPresenter = new LoginPresenter(this);
        account.setText("10008");
        password.setText("123456");

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

    @OnClick({R.id.login_btn, R.id.check_state})
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
        SPUtils.putString(account.getText().toString(), dataBeanList.get(selectPosition).getId());
        Log.i(TAG, "登录....");

        loginPresenter.loginServer(account.getText().toString(), password.getText().toString(), dataBeanList.get(selectPosition).getId());
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this, "登录中....");
    }

    @Override
    public void successLoad(String data) {
        SPUtils.putBoolean(Config.IS_LOGIN, true);
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
        if (event!=null){
            dataBeanList = event.subjectModel.getData();

            for (SubjectModel.DataBean dataBean : event.subjectModel.getData()){
                data_list.add(dataBean.getName());
            }
            arr_adapter.addAll();
            arr_adapter.notifyDataSetChanged();

        }
        Log.i(TAG,"onMessageEvent===");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
