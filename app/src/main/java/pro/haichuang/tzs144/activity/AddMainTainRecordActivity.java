package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;

/**
 * 新增维护记录
 */
public class AddMainTainRecordActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.tip_img)
    ImageView tipImg;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.client_name)
    TextView clientName;
    @BindView(R.id.client_type)
    TextView clientType;
    @BindView(R.id.client_persion)
    TextView clientPersion;
    @BindView(R.id.client_phone)
    TextView clientPhone;
    @BindView(R.id.main_tain_time)
    TextView mainTainTime;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.client_attr)
    TextView clientAttr;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_main_tain_record;
    }

    @Override
    protected void setUpView() {
        title.setText("新增维护记录");

    }

    @Override
    protected void setUpData() {

    }


    @OnClick({R.id.back, R.id.input_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.input_btn:
                break;
        }
    }
}
