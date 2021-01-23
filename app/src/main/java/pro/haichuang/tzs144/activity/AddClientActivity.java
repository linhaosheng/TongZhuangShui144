package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 新增客户
 */
public class AddClientActivity extends BaseActivity {


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
    @BindView(R.id.client_type)
    LSettingItem clientType;
    @BindView(R.id.location_type)
    LSettingItem locationType;
    @BindView(R.id.clinet_name)
    LSettingItem clinetName;
    @BindView(R.id.phone)
    LSettingItem phone;
    @BindView(R.id.monopoly)
    LSettingItem monopoly;
    @BindView(R.id.develop_persion)
    LSettingItem developPersion;
    @BindView(R.id.business)
    LSettingItem business;
    @BindView(R.id.address)
    LSettingItem address;
    @BindView(R.id.address_name)
    LSettingItem addressName;
    @BindView(R.id.detail_address)
    LSettingItem detailAddress;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_client;
    }

    @Override
    protected void setUpView() {
      title.setText("新增客户");
    }

    @Override
    protected void setUpData() {

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
