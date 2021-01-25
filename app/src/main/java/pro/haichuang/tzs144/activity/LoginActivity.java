package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

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
    private boolean checked = true;
    private int selectPosition;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        account.setText("17360155213");  //17360155214   //  15165011853
        password.setText("123456");
        //数据
        data_list = new ArrayList<String>();
        data_list.add("经销商A");
        data_list.add("经销商B");
        data_list.add("经销商C");
        data_list.add("经销商D");

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        inventorySubject.setAdapter(arr_adapter);

        inventorySubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * 设置默认显示上一次的经销商
         */
        String account = SPUtils.getString(Config.ACCOUNT, "");
        if (!account.equals("")){
            String inventoryStr = SPUtils.getString(account, "");
            if (!inventoryStr.equals("")){
                int index = data_list.indexOf(inventoryStr);
                inventorySubject.setSelection( index , true );
            }
        }

    }

    @OnClick({R.id.login_btn,R.id.check_state})
    public void onViewClicked(View view) {

        switch (view.getId()){
            case R.id.login_btn:
               login();
                break;
            case R.id.check_state:
                if (checked){
                    checkState.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.check));
                }else {
                    checkState.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.check_box));
                }
                checked =!checked;
                break;
        }

        //
    }

    private void login(){
        if (TextUtils.isEmpty(account.getText())) {
            Utils.showCenterTomast("请输入正确账号");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            Utils.showCenterTomast("请输入正确密码");
            return;
        }
        SPUtils.putString(Config.ACCOUNT,account.getText().toString());
        SPUtils.putString(account.getText().toString(),data_list.get(selectPosition));

        Log.i(TAG, "登录....");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
     //   loginPresenter.login(account.getText().toString(),password.getText().toString());
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
}
