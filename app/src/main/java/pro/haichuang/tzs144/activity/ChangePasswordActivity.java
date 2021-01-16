package pro.haichuang.tzs144.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kongzue.dialog.v2.WaitDialog;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.presenter.ChangePasswordPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;

/**
 *  修改密码页面
 */
public class ChangePasswordActivity extends BaseActivity implements ILoadDataView<String> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.edit_new_password_cconfirm)
    EditText editNewPasswordCconfirm;
    @BindView(R.id.confirn_change_btn)
    Button confirnChangeBtn;

    private ChangePasswordPresenter changePasswordPresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void setUpView() {

        title.setText("修改密码");
    }

    @Override
    protected void setUpData() {
        changePasswordPresenter = new ChangePasswordPresenter();
    }

    @OnClick({R.id.back, R.id.confirn_change_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirn_change_btn:



                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"修改中....");
    }

    @Override
    public void successLoad(String data) {
        WaitDialog.dismiss();
        Utils.showCenterTomast("修改成功");
        finish();
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast("修改失败，请重新输入密码修改");
    }
}
