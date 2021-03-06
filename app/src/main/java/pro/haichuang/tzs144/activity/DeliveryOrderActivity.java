package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import pro.bilibili.boxing.Boxing;
import pro.bilibili.boxing.model.config.BoxingConfig;
import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.bilibili.boxing_impl.ui.BoxingActivity;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DeliverOrderDetailAdapter;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.AreaEvent;
import pro.haichuang.tzs144.model.AreaModel;
import pro.haichuang.tzs144.model.FileUploadEvent;
import pro.haichuang.tzs144.model.OrderDetailDataModel;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.model.ShopDeleveModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.UploadFileModel;
import pro.haichuang.tzs144.presenter.OrderDetailActivityPresenter;
import pro.haichuang.tzs144.presenter.OrderDetailPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;
import pro.haichuang.tzs144.view.SelectWaterTicketDialog;

/**
 * 订单配送
 */
public class DeliveryOrderActivity extends BaseActivity implements ILoadDataView<OrderDetailDataModel.DataBean> {



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
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.order_state_img)
    ImageView orderStateImg;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.time_out)
    TextView timeOut;
    @BindView(R.id.name_view)
    RelativeLayout nameView;

    @BindView(R.id.time_send)
    TextView timeSend;
    @BindView(R.id.tatal_price)
    TextView tatalPrice;
    @BindView(R.id.need_price)
    TextView needPrice;
    @BindView(R.id.actual_price)
    TextView actualPrice;
    @BindView(R.id.price_view)
    RelativeLayout priceView;
    @BindView(R.id.order_num_data)
    TextView orderNumData;
    @BindView(R.id.order_source)
    TextView orderSource;
    @BindView(R.id.pay_way)
    TextView payWay;
    @BindView(R.id.order_view)
    LinearLayout orderView;

    @BindView(R.id.void_delivery_view)
    LinearLayout voidDeliveryView;

    @BindView(R.id.receive_payment)
    Button receivePayment;
    @BindView(R.id.shop_amount_view)
    LinearLayout shopAmountView;
    @BindView(R.id.shop_detail)
    RelativeLayout shopDetail;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
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
    @BindView(R.id.upload_reward_view)
    LinearLayout upload_reward_view;
    @BindView(R.id.reward_deduction_nunm)
    LSettingItem rewardDeductionNunm;
    @BindView(R.id.upload_month_view)
    LinearLayout upload_month_view;
    @BindView(R.id.month_deduction_nunm)
    LSettingItem monthDeductionNunm;

    @BindView(R.id.upload_reward)
    ImageView uploadReward;
    @BindView(R.id.upload_month)
    ImageView uploadMonth;

    @BindView(R.id.total_merchandise_num)
    TextView totalMerchandiseNum;
    @BindView(R.id.amount_receivable_num)
    TextView amountReceivableNum;
    @BindView(R.id.actual_amount)
    TextView actualAmount;

    private boolean selectWater;
    private boolean selectReward;
    private boolean selectMonth;

    private final static int REQUEST_CODE_CHOOSE_PICTURE_REWARD = 0x1110;
    private final static int REQUEST_CODE_CHOOSE_PICTURE_MONTH = 0x1111;


    private  String id;
    private int typeId;
    private int orderStatus;
    private OrderDetailActivityPresenter orderDetailPresenter;
    private DeliverOrderDetailAdapter deliverOrderDetailAdapter;
    private String rewardUrl = "";
    private String monthUrl = "";
    private ShopModel.DataBean mDataBea;
    private int waterId = -1;
    private float totalPrice;
    private float amount_receivable;
    private float actual_amount;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_deliver_order;
    }

    @Override
    protected void setUpView() {
        title.setText("订单详情");

    }

    @Override
    protected void setUpData() {
        orderStatus = getIntent().getIntExtra("orderStatus",0);
        id = getIntent().getStringExtra("id");
        typeId = getIntent().getIntExtra("typeId",0);
        orderDetailPresenter = new OrderDetailActivityPresenter(this);
        orderDetailPresenter.getHomeOrderInfo(id);

        deliverOrderDetailAdapter = new DeliverOrderDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycleData.setAdapter(deliverOrderDetailAdapter);

        deliverOrderDetailAdapter.addChildClickViewIds(R.id.shop_add_tong,R.id.reduce_tong);
        deliverOrderDetailAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = deliverOrderDetailAdapter.getData().get(position);
                int recycleNum = goodsListBean.getRecycleNum();
                int id = view.getId();
                switch (id){
                    case R.id.shop_add_tong:
                        recycleNum = recycleNum + 1;
                        goodsListBean.setRecycleNum(recycleNum);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                    break;
                    case R.id.reduce_tong:
                        if (recycleNum==0){
                            return;
                        }
                        recycleNum = recycleNum - 1;
                        goodsListBean.setRecycleNum(recycleNum);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                        break;

                }
            }
        });

        selectTicket.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                SelectWaterTicketDialog selectWaterTicketDialog = new SelectWaterTicketDialog(DeliveryOrderActivity.this, new SelectWaterTicketDialog.SelectShopListener() {
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

        selectWaterNum.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                caculateShopMount();
            }
        });
        rewardDeductionNunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                caculateShopMount();
            }
        });
        monthDeductionNunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                caculateShopMount();
            }
        });
    }


    @OnClick({R.id.back,R.id.receive_payment,R.id.water_tickets,R.id.reward_tickets,R.id.monthly,R.id.upload_reward,R.id.upload_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.receive_payment:

                ShopDeleveModel shopDeleveModel = new ShopDeleveModel();
                ShopDeleveModel.GoodsListBean goodsListBean = new ShopDeleveModel.GoodsListBean();
                if (selectWater){
                    ShopDeleveModel.GoodsListBean.DeductWaterBean deductWaterBean = new ShopDeleveModel.GoodsListBean.DeductWaterBean();
                    deductWaterBean.setWaterGoodsId(mDataBea.getId()+"");
                    deductWaterBean.setDeductNum(selectDeductionNunm.getEditText());
                    deductWaterBean.setNum(selectWaterNum.getEditText());
                    goodsListBean.setDeductWater(deductWaterBean);
                }
                if (selectReward){
                    ShopDeleveModel.GoodsListBean.DeductCouponBean deductCouponBean = new ShopDeleveModel.GoodsListBean.DeductCouponBean();
                    deductCouponBean.setCouponImg(rewardUrl);
                    deductCouponBean.setDeductNum(rewardDeductionNunm.getEditText());
                    goodsListBean.setDeductCoupon(deductCouponBean);
                }
                if (selectMonth){
                    ShopDeleveModel.GoodsListBean.DeductMonthBean deductMonthBean = new ShopDeleveModel.GoodsListBean.DeductMonthBean();
                    deductMonthBean.setMonthImg(monthUrl);
                    deductMonthBean.setDeductNum(monthDeductionNunm.getEditText());
                    goodsListBean.setDeductMonth(deductMonthBean);
                }
                List<ShopDeleveModel.GoodsListBean.MaterialsBean> materialsBeanList = new ArrayList<>();

                List<OrderDetailModel.DataBean.GoodsListBean> data = deliverOrderDetailAdapter.getData();
                for (OrderDetailModel.DataBean.GoodsListBean goodsListBean1 : data){
                    OrderDetailModel.DataBean.BindMaterList bindMaterList = goodsListBean1.getBindMaterList().get(0);

                    ShopDeleveModel.GoodsListBean.MaterialsBean materialsBean = new ShopDeleveModel.GoodsListBean.MaterialsBean();
                    materialsBean.setMaterialId(bindMaterList.getId()+"");
                    materialsBean.setNum(goodsListBean1.getRecycleNum()+"");
                    materialsBeanList.add(materialsBean);
                }
                goodsListBean.setMaterials(materialsBeanList);

                List<ShopDeleveModel.GoodsListBean> goodsListBeanList = new ArrayList<>();
                goodsListBeanList.add(goodsListBean);
                shopDeleveModel.setGoodsList(goodsListBeanList);



                break;
            case R.id.water_tickets:
                if (selectWater){
                    waterTickets.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn33));
                    selectTicket.setVisibility(View.GONE);
                    selectWaterNum.setVisibility(View.GONE);
                    selectDeductionNunm.setVisibility(View.GONE);
                }else {
                    waterTickets.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn17));
                    selectTicket.setVisibility(View.VISIBLE);
                    selectWaterNum.setVisibility(View.VISIBLE);
                    selectDeductionNunm.setVisibility(View.VISIBLE);
                }
                selectWater = !selectWater;
                break;
            case R.id.reward_tickets:
                if (selectReward){
                    rewardTickets.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn33));
                    upload_reward_view.setVisibility(View.GONE);
                    rewardDeductionNunm.setVisibility(View.GONE);
                }else {
                    rewardTickets.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn17));
                    upload_reward_view.setVisibility(View.VISIBLE);
                    rewardDeductionNunm.setVisibility(View.VISIBLE);
                }
                selectReward =!selectReward;
                break;
            case R.id.monthly:
                if (selectMonth){
                    monthly.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn33));
                    upload_month_view.setVisibility(View.GONE);
                    monthDeductionNunm.setVisibility(View.GONE);
                }else {
                    monthly.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn17));
                    upload_month_view.setVisibility(View.VISIBLE);
                    monthDeductionNunm.setVisibility(View.VISIBLE);
                }
                selectMonth =!selectMonth;
                break;
            case R.id.upload_reward:
                selectPicture(REQUEST_CODE_CHOOSE_PICTURE_REWARD);
                break;
            case R.id.upload_month:
                selectPicture(REQUEST_CODE_CHOOSE_PICTURE_MONTH);
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
    public void startLoad() {
        WaitDialog.show(this, "加载中...");
    }


    @Override
    public void successLoad(OrderDetailDataModel.DataBean data) {
        WaitDialog.dismiss();

        name.setText(data.getCustomerName());
        orderNum.setText(data.getCustomerPhone());
        type.setText(data.getCustomerTypeName());
        address.setText(data.getAddressName());
        addressDetail.setText(data.getAddress());
        timeSend.setText(data.getTimeRange());
        timeOut.setText(data.getTimeStatus());

//        if (data.getDeliveryStatus()==0){
//            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.pend_order));
//        }else if (data.getDeliveryStatus()==1){
//            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.nedd_delivery));
//        }else if (data.getDeliveryStatus()==2){
//            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.have_finish));
//        }else if (data.getDeliveryStatus()==3){
//            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.have_cancel));
//        }
        orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.nedd_delivery));

        tatalPrice.setText("¥" + data.getTotalPrice());
        needPrice.setText("¥" + data.getReceivablePrice());
        orderNumData.setText("订单编号：" + data.getOrderNo());

        if ("电话订单".equals(data.getOrderType())){
            priceView.setVisibility(View.GONE);
            orderView.setVisibility(View.GONE);
            shopAmountView.setVisibility(View.VISIBLE);
            shopDetail.setVisibility(View.VISIBLE);
        }
        if ("外卖订单".equals(data.getOrderType())){
            receivePayment.setVisibility(View.VISIBLE);
            ticketType.setVisibility(View.GONE);
        }


        orderSource.setText("订单来源: "+data.getOrderType());
        payWay.setText("支付方式：" + data.getPayMode());
        actualPrice.setText("¥"+data.getRealPrice());

        if ("微信支付".equals(data.getPayMode())){
            receivePayment.setVisibility(View.VISIBLE);
            ticketType.setVisibility(View.GONE);
        }else if ("水票支付".equals(data.getPayMode())){
            voidDeliveryView.setVisibility(View.VISIBLE);
            receivePayment.setVisibility(View.GONE);
            shopDetail.setVisibility(View.VISIBLE);
            monthly.setVisibility(View.GONE);
            rewardTickets.setVisibility(View.GONE);
            shopAmountView.setVisibility(View.GONE);
             selectTicket.setVisibility(View.VISIBLE);
             selectWaterNum.setVisibility(View.VISIBLE);
             selectDeductionNunm.setVisibility(View.VISIBLE);
            receivePayment.setText("收款");
        }
        deliverOrderDetailAdapter.setList(data.getGoodsList());

        totalMerchandiseNum.setText((float)data.getReceivablePrice()+"");

    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias!=null && medias.size()>0){
                if (requestCode==REQUEST_CODE_CHOOSE_PICTURE_REWARD){
                    String path = medias.get(0).getPath();
                    orderDetailPresenter.uploadFile("reward",path);
                    Utils.showImage(uploadReward,medias.get(0).getPath());

                }else if (requestCode==REQUEST_CODE_CHOOSE_PICTURE_MONTH){
                    String path = medias.get(0).getPath();
                    orderDetailPresenter.uploadFile("month",path);
                    Utils.showImage(uploadMonth,medias.get(0).getPath());
                }
            }
        }
    }


    /**
     * 计算商品总额
     */
    private void caculateShopMount() {
        try {

            totalPrice =  Float.parseFloat(totalMerchandiseNum.getText().toString());
            amount_receivable = 0;
            actual_amount = 0;
            int waterNum = 0;
            int rewardNum = 0;
            int monthNum = 0;

            if (selectWater){
                waterNum = Integer.parseInt(selectWaterNum.getEditText());
            }
            if (selectReward){
                rewardNum = Integer.parseInt(rewardDeductionNunm.getEditText());
            }
            if (selectMonth){
                monthNum = Integer.parseInt(monthDeductionNunm.getEditText());
            }

            amountReceivableNum.setText((totalPrice - waterNum - rewardNum) +"");
            actualAmount.setText((totalPrice - waterNum - rewardNum - monthNum)+"");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FileUploadEvent event) {
        if (event != null) {
            if (event.status==0){
                WaitDialog.show(this,"上传中...");
            }else {
                WaitDialog.dismiss();
                if (event.status==1){
                    Utils.showCenterTomast("上传失败");
                }else {
                    Utils.showCenterTomast("上传成功");
                    if (event.key.equals("reward")){
                        rewardUrl = event.path;
                    }else {
                        monthUrl = event.path;
                    }
                }
            }
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
}
