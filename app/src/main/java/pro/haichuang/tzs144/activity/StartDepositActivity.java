package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import pro.haichuang.tzs144.presenter.StartDepositActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 开押页面
 */
public class StartDepositActivity extends BaseActivity implements ILoadDataView<String> {


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
    @BindView(R.id.save_deposit_btn)
    Button saveDepositBtn;

    private String customerId;
    private List<CharSequence> depositTypeList;
    private List<CharSequence>depositGoodShopList;
    private  GoodsShopModel goodsShopModel;
    private int type;
    private String goodsId;
    private StartDepositActivityPresenter startDepositActivityPresenter;
    private String priceData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_start_deposit;
    }

    @Override
    protected void setUpView() {
       title.setText("开押");
        depositType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(StartDepositActivity.this, depositTypeList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                       type = index;
                        depositType.setRightText(text);
                    }
                });
            }
        });

        depositGoods.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(StartDepositActivity.this, depositGoodShopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        depositGoods.setRightText(text);
                        goodsId = goodsShopModel.getData().get(index).getId() +"";
                    }
                });
            }
        });

        price.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                try {
                    float goodPrice = Float.parseFloat(text);
                    int goodNum = Integer.parseInt(num.getEditText());
                    float total = goodPrice * goodNum;
                    money.setRightText(total+"元");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        num.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                try {
                    float goodPrice = Float.parseFloat(price.getEditText());
                    int goodNum = Integer.parseInt(text);
                    float total = goodPrice * goodNum;
                    money.setRightText(total+"元");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        money.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
               try {
                   float goodPrice = Float.parseFloat(price.getEditText());
                   int goodNum = Integer.parseInt(num.getEditText());
                   float total = goodPrice * goodNum;
                   money.setRightText(total+"元");
                   priceData = String.valueOf(total);

               }catch (Exception e){
                   e.printStackTrace();
                   Utils.showCenterTomast("请输入正确的数量和金额");
               }
            }
        });
    }

    @Override
    protected void setUpData() {
        startDepositActivityPresenter = new StartDepositActivityPresenter(this);
        startDepositActivityPresenter.findDepositGoods();
        String dataBeanJson = getIntent().getStringExtra(Config.PERSION_INFO);
        if (dataBeanJson!=null){
            SaleDataModel.DataBean dataBean = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);
            name.setText(dataBean.getName());
            phone.setText(dataBean.getPhone());
            address.setText(dataBean.getAddressName());
            addressDetail.setText(dataBean.getAddress());
            customerId = dataBean.getId()+"";
        }

        depositTypeList = new ArrayList<>();
        depositTypeList.add("押金");
        depositTypeList.add("借条");
        depositTypeList.add("暂欠");
        depositGoodShopList = new ArrayList<>();
    }


    @OnClick({R.id.back,R.id.save_deposit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.save_deposit_btn:
                String no = depositNum.getEditText();
                if (no==null || no.equals("")){
                    Utils.showCenterTomast("请输入押金编号");
                    return;
                }

                if (depositGoods.getRightText().contains("请选择")){
                    Utils.showCenterTomast("请输入开押类型");
                    return;
                }

                String bookNo = price.getEditText();
                if (bookNo==null || bookNo.equals("")){
                    Utils.showCenterTomast("请输入单价");
                    return;
                }
                String numData = num.getEditText();
                if (numData==null || numData.equals("")){
                    Utils.showCenterTomast("请输入数量");
                    return;
                }

                if (depositType.getRightText().contains("请选择")){
                    Utils.showCenterTomast("请输入开押类型");
                    return;
                }

                startDepositActivityPresenter.addDepositInfo(no,customerId,goodsId,priceData,numData,type+"");
                break;
        }
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
        Utils.showCenterTomast("提交失败");
    }
}
