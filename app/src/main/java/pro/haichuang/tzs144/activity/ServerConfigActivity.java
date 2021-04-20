package pro.haichuang.tzs144.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ExitModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;

public class ServerConfigActivity extends BaseActivity{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.server_edit)
    EditText serverEdit;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_server_config;
    }

    @Override
    protected void setUpView() {
        title.setText("服务器配置");

    }

    @Override
    protected void setUpData() {
        serverEdit.setText(SPUtils.getString(Config.SERVER_URL, ConfigUrl.DEFAULT_SERVER_URL));
    }

    @OnClick({R.id.back, R.id.save_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back:
                finish();
                break;
            case R.id.save_btn:
                saveServerConfig();
                break;
        }
    }

    private final void saveServerConfig(){
        String serverUrl = serverEdit.getText().toString();
        SPUtils.putString(Config.SERVER_URL,serverUrl);
        MessageDialog.show(this,"提示","服务器配置保存成功,请重启")
                .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        ConfigUrl.BASE_URL = SPUtils.getString(Config.SERVER_URL,ConfigUrl.DEFAULT_SERVER_URL);
                        EventBus.getDefault().post(new ExitModel(0));
                        finish();
                        return false;
                    }
                });
    }
}
