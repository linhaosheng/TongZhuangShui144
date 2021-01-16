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

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.inventory_subject)
    Spinner inventorySubject;
    private LoginPresenter loginPresenter;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {
        back.setVisibility(View.GONE);
        title.setText("登录");
    }

    @Override
    protected void setUpData() {
      //  account.setText("17360155213");  //17360155214   //  15165011853
     //   password.setText("123456");
        //数据
        data_list = new ArrayList<String>();
        data_list.add("北京");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        inventorySubject.setAdapter(arr_adapter);

        inventorySubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {

        if (TextUtils.isEmpty(account.getText())){
            Utils.showCenterTomast("请输入正确账号");
            return;
        }
        if (TextUtils.isEmpty(password.getText())){
            Utils.showCenterTomast("请输入正确密码");
            return;
        }
        Log.i(TAG,"登录....");
        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

     //   loginPresenter.login(account.getText().toString(),password.getText().toString());
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"登录中....");
    }

    @Override
    public void successLoad(String data) {
        SPUtils.putBoolean(Config.IS_LOGIN,true);
        WaitDialog.dismiss();
        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast("登录失败，请检查账户和密码是否正确");
        WaitDialog.dismiss();
    }
}
