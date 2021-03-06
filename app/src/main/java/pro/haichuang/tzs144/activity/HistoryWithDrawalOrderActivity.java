package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.GoodsShopEvent;
import pro.haichuang.tzs144.model.GoodsShopModel;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.presenter.HistoryWithDrawalOrderActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 历史退押记录
 */
public class HistoryWithDrawalOrderActivity extends BaseActivity implements ILoadDataView<String> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
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
    @BindView(R.id.guarantee_persion_phone)
    LSettingItem guaranteePersionPhone;
    @BindView(R.id.deposit_number)
    LSettingItem depositNumber;
    @BindView(R.id.client_name)
    LSettingItem clientName;
    @BindView(R.id.mortgage_price)
    LSettingItem mortgagePrice;
    @BindView(R.id.mortgage_num)
    LSettingItem mortgageNum;
    @BindView(R.id.mortgage_type)
    LSettingItem mortgageType;
    @BindView(R.id.mortgage_goods)
    LSettingItem mortgageGoods;
    @BindView(R.id.with_drawal_btn)
    Button withDrawalBtn;
    @BindView(R.id.deposit_money)
    EditText depositMoney;

    private HistoryWithDrawalOrderActivityPresenter historyWithDrawalOrderActivityPresenter;
    private List<CharSequence>depositType;
    private List<CharSequence>depositGoodShopList;
    private  GoodsShopModel goodsShopModel;
    private String goodsId;
    private String customerId;
    private  String type;
    private SaleDataModel.DataBean dataBean;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_history_with_drawal;
    }

    @Override
    protected void setUpView() {
       title.setText("退押");
        mortgageGoods.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(HistoryWithDrawalOrderActivity.this, depositGoodShopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        mortgageGoods.setRightText(text);
                        goodsId = goodsShopModel.getData().get(index).getId() +"";
                    }
                });
            }
        });
        mortgagePrice.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                try {
                    float goodPrice = Float.parseFloat(text);
                    int goodNum = Integer.parseInt(mortgageNum.getEditText());
                    float total = goodPrice * goodNum;
                    depositMoney.setText(total+"¥");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mortgageNum.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                try {
                    float goodPrice = Float.parseFloat(mortgagePrice.getEditText());
                    int goodNum = Integer.parseInt(text);
                    float total = goodPrice * goodNum;
                    depositMoney.setText(total+"¥");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void setUpData() {
        historyWithDrawalOrderActivityPresenter = new HistoryWithDrawalOrderActivityPresenter(this);
        historyWithDrawalOrderActivityPresenter.findDepositGoods();
        String dataBeanJson = getIntent().getStringExtra(Config.PERSION_INFO);
        if (dataBeanJson!=null){
            dataBean = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);
            name.setText(dataBean.getName());
            phone.setText(dataBean.getPhone());
            address.setText(dataBean.getAddressName());
            addressDetail.setText(dataBean.getAddress());
            customerId = dataBean.getId()+"";
        }
        depositType = new ArrayList<>();
        depositType.add("押金");
        depositType.add("借条");
        depositType.add("暂欠");
        mortgageType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(HistoryWithDrawalOrderActivity.this, depositType, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        mortgageType.setRightText(text);
                        type = index+"";
                    }
                });
            }
        });
        depositGoodShopList = new ArrayList<>();
    }


    @OnClick({R.id.back, R.id.with_drawal_btn,R.id.address_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.with_drawal_btn:
                String no = guaranteePersionPhone.getEditText();
                if (no==null || no.equals("")){
                    Utils.showCenterTomast("请输入押金编号");
                    return;
                }
                String bookNo = depositNumber.getEditText();
                if (bookNo==null || bookNo.equals("")){
                    Utils.showCenterTomast("请输入押金本编号");
                    return;
                }
                String num = mortgageNum.getEditText();
                if (num==null || num.equals("")){
                    Utils.showCenterTomast("请输入数量");
                    return;
                }
                String price = mortgagePrice.getEditText();
                if (price==null || price.equals("")){
                    Utils.showCenterTomast("请输入价格");
                    return;
                }

                if (mortgageType.getRightText().contains("请选择")){
                    Utils.showCenterTomast("请输入开押类型");
                    return;
                }
                historyWithDrawalOrderActivityPresenter.saveHistory(no,bookNo,customerId,goodsId,price,num,type);
                break;
            case R.id.address_detail:
                Intent intent1 = new Intent(this,SelectAddressActivity.class);
                if (dataBean!=null){
                    intent1.putExtra("customerId",dataBean.getId());
                }
                startActivityForResult(intent1,1000);
                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"提交中...");
    }

    @Override
    public void successLoad(String data) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(data);
        finish();
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsShopEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            goodsShopModel = event.goodsShopModel;
            if (goodsShopModel.getData()!=null){
                for (GoodsShopModel.DataBean dataBean : goodsShopModel.getData()){
                    depositGoodShopList.add(dataBean.getName());
                }
            }
        }
        Log.i(TAG, "onMessageEvent===");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (resultCode==1000){

                Log.i(TAG,"initAddressInfo");
                String dataBeanJson = data.getStringExtra(Config.PERSION_INFO);
                SaleDataModel.DataBean dataBean1 = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);

                address.setText(dataBean1.getAddressName());
                addressDetail.setText(dataBean1.getAddress());

                dataBean.setAddress(dataBean1.getAddress());
                dataBean.setAddressName(dataBean1.getAddressName());
                dataBean.setId(dataBean1.getId());
                dataBean.setLatitude(dataBean1.getLatitude());
                dataBean.setLongitude(dataBean1.getLongitude());
            }
        }
    }

}
