package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.bilibili.boxing.Boxing;
import pro.bilibili.boxing.model.config.BoxingConfig;
import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.bilibili.boxing_impl.ui.BoxingActivity;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 录入订单   直接销售
 */
public class EnterOrderActivity extends BaseActivity {


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
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.add_shop)
    TextView addShop;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.reduce)
    TextView reduce;
    @BindView(R.id.shop_num)
    TextView shopNum;
    @BindView(R.id.shop_add)
    TextView shopAdd;
    @BindView(R.id.shop_capacity)
    TextView shopCapacity;
    @BindView(R.id.shop_unit)
    TextView shopUnit;
    @BindView(R.id.shop_price)
    TextView shopPrice;
    @BindView(R.id.view2)
    TextView view2;
    @BindView(R.id.reduce_tong)
    TextView reduceTong;
    @BindView(R.id.shop_num_tong)
    TextView shopNumTong;
    @BindView(R.id.shop_add_tong)
    TextView shopAddTong;
    @BindView(R.id.shop_info)
    RelativeLayout shopInfo;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.water_tickets)
    TextView waterTickets;
    @BindView(R.id.reward_tickets)
    TextView rewardTickets;
    @BindView(R.id.monthly)
    TextView monthly;
    @BindView(R.id.ticket_type)
    LinearLayout ticketType;
    @BindView(R.id.select_ticket)
    LSettingItem selectTicket;
    @BindView(R.id.select_water_num)
    LSettingItem selectWaterNum;
    @BindView(R.id.select_deduction_nunm)
    LSettingItem selectDeductionNunm;
    @BindView(R.id.upload_reward)
    ImageView uploadReward;
    @BindView(R.id.reward_deduction_nunm)
    LSettingItem rewardDeductionNunm;
    @BindView(R.id.upload_month)
    ImageView uploadMonth;
    @BindView(R.id.month_deduction_nunm)
    LSettingItem monthDeductionNunm;
    @BindView(R.id.confirm_add_shop)
    Button confirmAddShop;
    @BindView(R.id.shop_detail)
    RelativeLayout shopDetail;
    @BindView(R.id.total_merchandise)
    TextView totalMerchandise;
    @BindView(R.id.price_unit)
    TextView priceUnit;
    @BindView(R.id.total_merchandise_num)
    TextView totalMerchandiseNum;
    @BindView(R.id.amount_receivable)
    TextView amountReceivable;
    @BindView(R.id.price_unit1)
    TextView priceUnit1;
    @BindView(R.id.amount_receivable_num)
    TextView amountReceivableNum;
    @BindView(R.id.actual_merchandise)
    TextView actualMerchandise;
    @BindView(R.id.price_unit2)
    TextView priceUnit2;
    @BindView(R.id.receive_payment)
    Button receivePayment;

    private final static int REQUEST_CODE_CHOOSE_PICTURE_REWARD = 0x1110;
    private final static int REQUEST_CODE_CHOOSE_PICTURE_MONTH = 0x1111;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_enter_order;
    }

    @Override
    protected void setUpView() {
      title.setText("直接销售");
        tipImg.setVisibility(View.VISIBLE);
      tipImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.search));
    }

    @Override
    protected void setUpData() {

    }


    @OnClick({R.id.back, R.id.upload_reward, R.id.upload_month, R.id.receive_payment,R.id.tip_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.upload_reward:
                selectPicture(REQUEST_CODE_CHOOSE_PICTURE_REWARD);
                break;
            case R.id.upload_month:
                selectPicture(REQUEST_CODE_CHOOSE_PICTURE_MONTH);
                break;
            case R.id.receive_payment:
                break;
            case R.id.tip_img:
                Intent intent = new Intent(this,SaleSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 打开相册选择图片
     */
    private void selectPicture(int type){
        BoxingConfig config;
        config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        config.needCamera(R.mipmap.ic_boxing_camera_white).needGif();
            List<BaseMedia> data =null;
            config.withMaxCount(1);
            Boxing.of(config).withIntent(MyApplication.getApplication(), BoxingActivity.class, (ArrayList) data).start(this, type);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias!=null && medias.size()>0){
                if (requestCode==REQUEST_CODE_CHOOSE_PICTURE_REWARD){
                    Utils.showImage(uploadReward,medias.get(0).getPath());
                }else if (requestCode==REQUEST_CODE_CHOOSE_PICTURE_MONTH){
                    Utils.showImage(uploadMonth,medias.get(0).getPath());
                }
            }
        }
    }
}
