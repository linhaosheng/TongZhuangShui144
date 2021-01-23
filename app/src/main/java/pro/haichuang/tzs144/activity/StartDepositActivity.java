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
 * 开押页面
 */
public class StartDepositActivity extends BaseActivity {


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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.client_info)
    RelativeLayout clientInfo;
    @BindView(R.id.deposit_num)
    LSettingItem depositNum;
    @BindView(R.id.deposit_goods)
    LSettingItem depositGoods;
    @BindView(R.id.price)
    LSettingItem price;
    @BindView(R.id.num)
    LSettingItem num;
    @BindView(R.id.money)
    LSettingItem money;
    @BindView(R.id.deposit_type)
    LSettingItem depositType;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_start_deposit;
    }

    @Override
    protected void setUpView() {
       title.setText("开押");
    }

    @Override
    protected void setUpData() {

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
