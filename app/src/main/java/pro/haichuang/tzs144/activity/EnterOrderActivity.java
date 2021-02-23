package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import pro.haichuang.tzs144.adapter.AddOrderAdapter;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.UploadFileModel;
import pro.haichuang.tzs144.model.UploadOrderModel;
import pro.haichuang.tzs144.presenter.EnterOrderActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.AddOrderDepositDialog;
import pro.haichuang.tzs144.view.AddShopDialog;
import pro.haichuang.tzs144.view.LSettingItem;
import pro.haichuang.tzs144.view.SelectWaterTicketDialog;

/**
 * 录入订单   直接销售
 */
public class EnterOrderActivity extends BaseActivity implements IUpLoadFileView<UploadFileModel.DataBean> {


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
    @BindView(R.id.actual_amount)
    TextView actualAmount;
    @BindView(R.id.receive_payment)
    Button receivePayment;
    @BindView(R.id.shop_amount_view)
    LinearLayout shop_amount_view;
    @BindView(R.id.upload_reward_view)
    LinearLayout upload_reward_view;
    @BindView(R.id.upload_month_view)
    LinearLayout upload_month_view;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.select_client)
    LinearLayout selectClient;
    private AddOrderAdapter addOrderAdapter;

    private boolean selectReward;
    private boolean selectMonth;

    private final static int REQUEST_CODE_CHOOSE_PICTURE_REWARD = 0x1110;
    private final static int REQUEST_CODE_CHOOSE_PICTURE_MONTH = 0x1111;
    private EnterOrderActivityPresenter enterOrderActivityPresenter;
    private UploadOrderModel uploadOrderModel;
    private String rewardUrl = "";
    private String monthUrl = "";
    private List<AddOrderModel.GoodsListBean> goodsListBeans;
    private int shopId;
    private int waterId = -1;
    private ShopModel.DataBean mDataBea;
    public final static int SELECT_ADDRESS_INFO = 0x1110;
    private SaleDataModel.DataBean dataBean;
    private float totalPrice;
    private float amount_receivable;
    private float actual_amount;
    private int orderType;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_enter_order;
    }

    @Override
    protected void setUpView() {
      title.setText("直接销售");
      tipImg.setVisibility(View.VISIBLE);
      tipImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.search));
        selectTicket.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                SelectWaterTicketDialog selectWaterTicketDialog = new SelectWaterTicketDialog(EnterOrderActivity.this, new SelectWaterTicketDialog.SelectShopListener() {
                    @Override
                    public void selectShop(ShopModel.DataBean dataBean) {
                        mDataBea = dataBean;
                        selectTicket.setLeftText(dataBean.getName()+"   "+dataBean.getSpecs());
                        waterId = dataBean.getId();
                    }
                });
                selectWaterTicketDialog.show(getSupportFragmentManager(),"");
            }
        });
        addOrderAdapter = new AddOrderAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(addOrderAdapter);

    }

    @Override
    protected void setUpData() {
        orderType = getIntent().getIntExtra("order_type",0);
        uploadOrderModel = new UploadOrderModel();
       enterOrderActivityPresenter = new EnterOrderActivityPresenter(this);
    }


    @OnClick({R.id.back, R.id.upload_reward, R.id.upload_month, R.id.receive_payment,R.id.tip_img,R.id.address_detail,R.id.add_shop_btn,R.id.reward_tickets,R.id.monthly,R.id.upload_month_view,R.id.confirm_add_shop,R.id.select_client})
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
                int distance = (int)Utils.GetDistance(Config.LONGITUDE, Config.LATITUDE, dataBean.getLongitude(), dataBean.getLatitude());
                if (distance>400){
                    caculateDistance(distance);
                }else {
                    addOrderClick();
                }
                break;
            case R.id.tip_img:
                Intent intent = new Intent(this,SaleSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.address_detail:
                Intent intent1 = new Intent(this,SelectAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_shop_btn:
                AddShopDialog addShopDialog = new AddShopDialog(this, new AddShopDialog.SelectShopListener() {
                    @Override
                    public void selectShop(ShopModel.DataBean dataBean) {
                        shopDetail.setVisibility(View.VISIBLE);
                        shop_amount_view.setVisibility(View.VISIBLE);
                        shopName.setText(dataBean.getName());
                        shopCapacity.setText(dataBean.getSpecs());
                        shopPrice.setText(dataBean.getPrice()+"");
                        shopId = dataBean.getId();
                    }
                });
                addShopDialog.show(getSupportFragmentManager(),"");
                break;
            case R.id.reward_tickets:
                if (selectReward){
                    upload_reward_view.setVisibility(View.GONE);
                    rewardDeductionNunm.setVisibility(View.GONE);
                }else {
                    upload_reward_view.setVisibility(View.VISIBLE);
                    rewardDeductionNunm.setVisibility(View.VISIBLE);
                }
                selectReward =!selectReward;
                break;
            case R.id.monthly:
                if (selectMonth){
                    upload_month_view.setVisibility(View.GONE);
                    monthDeductionNunm.setVisibility(View.GONE);
                }else {
                    upload_month_view.setVisibility(View.VISIBLE);
                    monthDeductionNunm.setVisibility(View.VISIBLE);
                }
                selectMonth =!selectMonth;
                break;
            case R.id.confirm_add_shop:
                if (goodsListBeans==null){
                    goodsListBeans = new ArrayList<>();
                }
                if (mDataBea==null){
                    Utils.showCenterTomast("请选择水票类型");
                    return;
                }
                AddOrderModel.GoodsListBean goodsListBean = new AddOrderModel.GoodsListBean();
                goodsListBean.setGoodName(mDataBea.getName()+mDataBea.getSpecs());
                goodsListBean.setGoodsId(String.valueOf(shopId));
                goodsListBean.setNum(shopNum.getText().toString());
                goodsListBean.setGoodsPrice(shopPrice.getText().toString());
//                AddOrderModel.GoodsListBean.MaterialsBean materialsBean = new AddOrderModel.GoodsListBean.MaterialsBean();
//                materialsBean.setMaterialId("00");
//                materialsBean.setNum("10");
//                materialsBean.setMaterialName("桶装水");
//                List<AddOrderModel.GoodsListBean.MaterialsBean>materialsBeanList = new ArrayList<>();
//                materialsBeanList.add(materialsBean);
//                goodsListBean.setMaterials(materialsBeanList);
                if (selectWaterNum.getEditText().equals("")){
                    Utils.showCenterTomast("请输入水票数量");
                    return;
                }

                if (selectDeductionNunm.getEditText().equals("")){
                    Utils.showCenterTomast("请输入抵扣数量");
                    return;
                }

                if (waterId!=-1){
                    AddOrderModel.GoodsListBean.DeductWaterBean deductWaterBean = new AddOrderModel.GoodsListBean.DeductWaterBean();
                    deductWaterBean.setWaterGoodsId(String.valueOf(waterId));
                    deductWaterBean.setNum(selectWaterNum.getEditText());
                    deductWaterBean.setDeductNum(selectDeductionNunm.getEditText());
                    goodsListBean.setDeductWater(deductWaterBean);
                    waterId = -1;
                    selectWaterNum.setRightText("");
                    selectDeductionNunm.setRightText("");
                }

                if (!rewardUrl.equals("")){

                    AddOrderModel.GoodsListBean.DeductCouponBean deductCouponBean = new AddOrderModel.GoodsListBean.DeductCouponBean();
                    deductCouponBean.setDeductNum(rewardDeductionNunm.getEditText());
                    deductCouponBean.setCouponImg(rewardUrl);
                    goodsListBean.setDeductCoupon(deductCouponBean);
                    rewardUrl = "";
                }
                if (!monthUrl.equals("")){
                    AddOrderModel.GoodsListBean.DeductMonthBean deductMonthBean = new AddOrderModel.GoodsListBean.DeductMonthBean();
                    deductMonthBean.setMonthImg(monthUrl);
                    deductMonthBean.setDeductNum(monthDeductionNunm.getEditText());
                    goodsListBean.setDeductMonth(deductMonthBean);
                    monthUrl = "";
                }

                goodsListBeans.add(goodsListBean);
                addOrderAdapter.setList(goodsListBeans);

                shopDetail.setVisibility(View.GONE);

                for (AddOrderModel.GoodsListBean goodsListBean1 : goodsListBeans){
                    totalPrice += Integer.parseInt(goodsListBean1.getNum()) * Float.parseFloat(goodsListBean1.getGoodsPrice());
                    if (goodsListBean1.getDeductCoupon()!=null){
                        amount_receivable += totalPrice - (Integer.parseInt(goodsListBean1.getDeductWater().getNum()) + Integer.parseInt(goodsListBean1.getDeductCoupon().getDeductNum()));
                    }else {
                        amount_receivable += totalPrice - (Integer.parseInt(goodsListBean1.getDeductWater().getNum()));
                    }

                    if (goodsListBean1.getDeductMonth()!=null){
                        actual_amount += totalPrice - (amount_receivable + Integer.parseInt(goodsListBean1.getDeductMonth().getDeductNum()));
                    }else {
                        actual_amount += totalPrice - amount_receivable;
                    }

                }
                totalMerchandiseNum.setText(totalPrice +"");
                amountReceivableNum.setText(amount_receivable +"");
                actualAmount.setText(actual_amount+"");

                shop_amount_view.setVisibility(View.VISIBLE);
                initViewData();
                break;
            case R.id.select_client:
                Intent intent2 = new Intent(this,SaleSearchActivity.class);
                startActivityForResult(intent2,SELECT_ADDRESS_INFO);
                break;
        }
    }

    private void initViewData(){
        uploadReward.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.upload));
        uploadMonth.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.upload));
        selectReward = false;
        selectMonth = false;
        upload_reward_view.setVisibility(View.GONE);
        rewardDeductionNunm.setVisibility(View.GONE);
        selectWaterNum.setRightText("");
        selectDeductionNunm.setRightText("");
    }

    private void addOrderClick() {

        AddOrderModel addOrderModel = new AddOrderModel();
        addOrderModel.setOrderType(String.valueOf(orderType));
        addOrderModel.setCustomerId(dataBean.getId()+"");
        addOrderModel.setAddressId(dataBean.getAddressId()+"");
        addOrderModel.setGoodsList(goodsListBeans);

        enterOrderActivityPresenter.enterOrder(addOrderModel);
    }

    /**
     * 计算当前距离是否合法
     * @param distance
     */
    private final void caculateDistance(int distance){
        String content ="";
        if (distance>1000){
            content = "您当前位置与客户相差"+(float)distance/1000+"M，确定继续收款?";
        }else {
            content = "您当前位置与客户相差"+distance+"M，确定继续收款?";
        }

        MessageDialog.
                show(this, "提示", content, "确定", "取消")
                .setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
            @Override
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        })
        .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
            @Override
            public boolean onClick(BaseDialog baseDialog, View v) {
                addOrderClick();
                return false;
            }
        });
    }

    /**
     * 提示是否要显示开押弹框
     */
    private final void showOrderDepositDialog(){
        AddOrderDepositDialog depositDialog = new AddOrderDepositDialog(this);
        depositDialog.show(getSupportFragmentManager(),"");
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
                    String path = medias.get(0).getPath();
                    enterOrderActivityPresenter.uploadFile("reward",path);
                    Utils.showImage(uploadReward,medias.get(0).getPath());

                }else if (requestCode==REQUEST_CODE_CHOOSE_PICTURE_MONTH){
                    String path = medias.get(0).getPath();
                    enterOrderActivityPresenter.uploadFile("month",path);
                    Utils.showImage(uploadMonth,medias.get(0).getPath());
                }
            }else if (requestCode==SELECT_ADDRESS_INFO){
                dataBean = Utils.gsonInstane().fromJson(data.getStringExtra(Config.PERSION_INFO),SaleDataModel.DataBean.class);
                name.setText(dataBean.getName());
                phone.setText(dataBean.getPhone());
                addressName.setText(dataBean.getAddressName());
                addressDetail.setText(dataBean.getAddress());
                type.setText(dataBean.getType());
            }
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"提交中...");
    }

    @Override
    public void successLoad(UploadFileModel.DataBean data) {
        WaitDialog.dismiss();
        if (data.getKey().equals("reward")){
            rewardUrl = data.getFileUrl();
        }else {
           monthUrl = data.getFileUrl();
        }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        if (event!=null){
            if (event.status==Config.LOAD_SUCCESS){
                Utils.showCenterTomast("提交成功");
                finish();
            }
        }
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
