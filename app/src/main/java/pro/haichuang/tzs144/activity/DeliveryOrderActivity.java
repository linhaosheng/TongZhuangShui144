package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.BottomMenu;
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
import pro.haichuang.tzs144.adapter.DeliverOrderDetailAdapter;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.AreaEvent;
import pro.haichuang.tzs144.model.AreaModel;
import pro.haichuang.tzs144.model.DespositEvent;
import pro.haichuang.tzs144.model.FileUploadEvent;
import pro.haichuang.tzs144.model.OrderDetailDataModel;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.model.ShopDeleveModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.UpdateOrderEvent;
import pro.haichuang.tzs144.model.UploadFileModel;
import pro.haichuang.tzs144.presenter.OrderDetailActivityPresenter;
import pro.haichuang.tzs144.presenter.OrderDetailPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.AddOrderDepositDialog;
import pro.haichuang.tzs144.view.LSettingItem;
import pro.haichuang.tzs144.view.SelectWaterTicketDialog;

/**
 * 订单配送
 */
public class DeliveryOrderActivity extends BaseActivity implements ILoadDataView<OrderDetailDataModel.DataBean> {



    @BindView(R.id.phone_order_view)
    LinearLayout phone_order_view;
    @BindView(R.id.phone_order_num_data)
    TextView phone_order_num_data;
    @BindView(R.id.phone_order_source)
    TextView phone_order_source;

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
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;

    @BindView(R.id.total_merchandise_num)
    TextView totalMerchandiseNum;
    @BindView(R.id.amount_receivable_num)
    TextView amountReceivableNum;
    @BindView(R.id.actual_amount)
    EditText actualAmount;
    @BindView(R.id.delivery_btn)
    Button deliveryBtn;

    private final static int REQUEST_CODE_CHOOSE_PICTURE_REWARD = 0x1110;
    private final static int REQUEST_CODE_CHOOSE_PICTURE_MONTH = 0x1111;

    private final static int REQUEST_CODE_LIST_CHOOSE_PICTURE_MONTH = 0x1112;
    private final static int REQUEST_CODE_LIST_CHOOSE_PICTURE_REWARD = 0x1113;


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
    private String orderNo;
    private int customerId;
    private AddOrderDepositDialog addOrderDepositDialog;
    private int currentUploadPosition;


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

        deliverOrderDetailAdapter = new DeliverOrderDetailAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycleData.setAdapter(deliverOrderDetailAdapter);

        deliverOrderDetailAdapter.addChildClickViewIds(R.id.shop_add_tong,R.id.reduce_tong,R.id.shop_add_tong2,R.id.reduce_tong2,R.id.shop_add_tong3,R.id.reduce_tong3,R.id.shop_add_tong4,R.id.reduce_tong4,R.id.shop_add_tong5,R.id.reduce_tong5,R.id.water_tickets,R.id.reward_tickets,R.id.monthly,R.id.upload_reward,R.id.upload_month);
        deliverOrderDetailAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = deliverOrderDetailAdapter.getData().get(position);
                int id = view.getId();
                int recycleNum=0;
                switch (id){
                    case R.id.shop_add_tong:
                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList1 = bindMaterList.get(0);
                        recycleNum = bindMaterList1.getNum() +1;
                        bindMaterList1.setNum(recycleNum);
                        bindMaterList.set(0,bindMaterList1);

                        goodsListBean.setBindMaterList(bindMaterList);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);
                    break;
                    case R.id.reduce_tong:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList2 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList3 = bindMaterList2.get(0);
                        recycleNum = bindMaterList3.getNum();
                        if (recycleNum==0){
                            return;
                        }
                        recycleNum = recycleNum - 1;
                        bindMaterList3.setNum(recycleNum);
                        bindMaterList2.set(0,bindMaterList3);
                        goodsListBean.setBindMaterList(bindMaterList2);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);
                        break;

                    case R.id.shop_add_tong2:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList4 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList5 = bindMaterList4.get(1);
                        recycleNum = bindMaterList5.getNum() +1;
                        bindMaterList5.setNum(recycleNum);
                        bindMaterList4.set(1,bindMaterList5);

                        goodsListBean.setBindMaterList(bindMaterList4);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                        break;

                    case R.id.reduce_tong2:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList6 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList7 = bindMaterList6.get(1);
                        recycleNum = bindMaterList7.getNum();
                        if (recycleNum==0){
                            return;
                        }
                        recycleNum = recycleNum - 1;
                        bindMaterList7.setNum(recycleNum);
                        bindMaterList6.set(1,bindMaterList7);
                        goodsListBean.setBindMaterList(bindMaterList6);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);
                        break;

                    case R.id.shop_add_tong3:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList8 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList9 = bindMaterList8.get(2);
                        recycleNum = bindMaterList9.getNum() +1;
                        bindMaterList9.setNum(recycleNum);
                        bindMaterList8.set(2,bindMaterList9);

                        goodsListBean.setBindMaterList(bindMaterList8);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                        break;

                    case R.id.reduce_tong3:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList10 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList11 = bindMaterList10.get(2);
                        recycleNum = bindMaterList11.getNum();
                        if (recycleNum==0){
                            return;
                        }
                        recycleNum = recycleNum - 1;
                        bindMaterList11.setNum(recycleNum);
                        bindMaterList10.set(2,bindMaterList11);
                        goodsListBean.setBindMaterList(bindMaterList10);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                        break;

                    case R.id.shop_add_tong4:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList12 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList13 = bindMaterList12.get(3);
                        recycleNum = bindMaterList13.getNum() +1;
                        bindMaterList13.setNum(recycleNum);
                        bindMaterList12.set(3,bindMaterList13);

                        goodsListBean.setBindMaterList(bindMaterList12);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);
                        break;

                    case R.id.reduce_tong4:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList14 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList15 = bindMaterList14.get(3);
                        recycleNum = bindMaterList15.getNum();
                        if (recycleNum==0){
                            return;
                        }
                        recycleNum = recycleNum - 1;
                        bindMaterList15.setNum(recycleNum);
                        bindMaterList14.set(3,bindMaterList15);
                        goodsListBean.setBindMaterList(bindMaterList14);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                        break;

                    case R.id.shop_add_tong5:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList16 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList17 = bindMaterList16.get(4);
                        recycleNum = bindMaterList17.getNum() +1;
                        bindMaterList17.setNum(recycleNum);
                        bindMaterList16.set(4,bindMaterList17);

                        goodsListBean.setBindMaterList(bindMaterList16);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);
                        break;

                    case R.id.reduce_tong5:

                        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList18 = goodsListBean.getBindMaterList();
                        OrderDetailModel.DataBean.BindMaterList bindMaterList19 = bindMaterList18.get(4);
                        recycleNum = bindMaterList19.getNum();
                        if (recycleNum==0){
                            return;
                        }
                        recycleNum = recycleNum - 1;
                        bindMaterList19.setNum(recycleNum);
                        bindMaterList18.set(4,bindMaterList19);
                        goodsListBean.setBindMaterList(bindMaterList18);
                        deliverOrderDetailAdapter.setData(position,goodsListBean);

                        break;

                    case R.id.reward_tickets:
                        OrderDetailModel.DataBean.GoodsListBean goodsListBean3 = deliverOrderDetailAdapter.getData().get(position);
                        if (goodsListBean3.isShowReward()){
                            goodsListBean3.setCouponDeductNum(0);
                            goodsListBean3.setCouponImg(null);
                            goodsListBean3.setShowReward(false);
                        }else {
                            goodsListBean3.setShowReward(true);
                        }
                        deliverOrderDetailAdapter.setData(position,goodsListBean3);
                        caculateData();
                        break;

                    case R.id.monthly:
                        OrderDetailModel.DataBean.GoodsListBean goodsListBean2 = deliverOrderDetailAdapter.getData().get(position);
                        if (goodsListBean2.isShowMonth()){
                            goodsListBean2.setMonthDeductNum(0);
                            goodsListBean2.setMonthImg(null);
                            goodsListBean2.setShowMonth(false);
                        }else {
                            goodsListBean2.setShowMonth(true);
                        }
                        deliverOrderDetailAdapter.setData(position,goodsListBean2);
                        caculateData();
                        break;
                    case R.id.water_tickets:
                        OrderDetailModel.DataBean.GoodsListBean goodsListBean1 = deliverOrderDetailAdapter.getData().get(position);
                        if (goodsListBean1.isShowWater()){
                            goodsListBean1.setWaterNum(0);
                            goodsListBean1.setWaterDeductNum(0);
                            goodsListBean1.setWaterName(null);
                            goodsListBean1.setShowWater(false);
                        }else {
                            goodsListBean1.setShowWater(true);
                        }
                        deliverOrderDetailAdapter.setData(position,goodsListBean1);
                        caculateData();
                        break;
                    case R.id.upload_reward:
                        currentUploadPosition = position;
                        selectPicture(REQUEST_CODE_LIST_CHOOSE_PICTURE_REWARD);
                        break;
                    case R.id.upload_month:
                        currentUploadPosition = position;
                        selectPicture(REQUEST_CODE_LIST_CHOOSE_PICTURE_MONTH);
                        break;
                }
            }
        });

        deliverOrderDetailAdapter.setSelectWaterListener(new DeliverOrderDetailAdapter.SelectWaterListener() {
            @Override
            public void selectClick(int position) {
                SelectWaterTicketDialog selectWaterTicketDialog = new SelectWaterTicketDialog(DeliveryOrderActivity.this, new SelectWaterTicketDialog.SelectShopListener() {
                    @Override
                    public void selectShop(ShopModel.DataBean dataBean) {
                        OrderDetailModel.DataBean.GoodsListBean goodsListBean = deliverOrderDetailAdapter.getData().get(position);
                        goodsListBean.setWaterGoodsId(dataBean.getId());
                        goodsListBean.setWaterName(dataBean.getName()+"   "+dataBean.getSpecs());
                        goodsListBean.setWaterDeductNum(dataBean.getWaterNum());
                        goodsListBean.setWaterNum(dataBean.getWaterNum());
                        deliverOrderDetailAdapter.setData(position,goodsListBean);
                    }
                },customerId);
                selectWaterTicketDialog.show(getSupportFragmentManager(),"");
            }
        });

        deliverOrderDetailAdapter.setCaculateMoneyListener(new DeliverOrderDetailAdapter.CaculateMoneyListener() {
            @Override
            public void caculate() {
                caculateData();
            }
        });
        caculateData();
    }

    private void caculateData(){
        try {
            amount_receivable = 0;
            actual_amount = 0;

            totalPrice =  Float.parseFloat(totalMerchandiseNum.getText().toString());

            List<OrderDetailModel.DataBean.GoodsListBean> data = deliverOrderDetailAdapter.getData();
            for (OrderDetailModel.DataBean.GoodsListBean goodsListBean : data){
                float waterDeductNum = 0;
                float monthDeductNum = 0;
                float couponDeductNum = 0;

                if (goodsListBean.getWaterName()!=null && !goodsListBean.getWaterName().equals("")){
                    waterDeductNum = goodsListBean.getWaterDeductNum();
                }

                if (goodsListBean.getMonthDeductNum()>0){
                    monthDeductNum = goodsListBean.getMonthDeductNum();
                }
                if (goodsListBean.getCouponDeductNum()>0) {
                    couponDeductNum = goodsListBean.getCouponDeductNum();
                }
                amount_receivable += (waterDeductNum + couponDeductNum)*goodsListBean.getGoodsPrice();  //应收金额
                actual_amount += monthDeductNum * goodsListBean.getGoodsPrice();   //实收金额
            }

            amountReceivableNum.setText((totalPrice - amount_receivable) +"");
            actualAmount.setText(((totalPrice - amount_receivable) - actual_amount)+"");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back,R.id.receive_payment,R.id.void_sale_btn,R.id.delivery_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.void_sale_btn:
                WaitDialog.show(this, "提交中...");
                orderDetailPresenter.directSelling(id);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.delivery_btn:
            case R.id.receive_payment:
                caculateData();

                List<OrderDetailModel.DataBean.GoodsListBean> data = deliverOrderDetailAdapter.getData();

                List<ShopDeleveModel.GoodsListBean> goodsListBeanList = new ArrayList<>();

                int shopNum = 0;
                int materialNum = 0;
                boolean haveBindMaterList = false;
                for (OrderDetailModel.DataBean.GoodsListBean goodsListBean : data){

                    shopNum += goodsListBean.getGoodsNum();
                    if (goodsListBean.getBindMaterList()!=null && goodsListBean.getBindMaterList().size()>0){
                        haveBindMaterList = true;
                    }else {
                        haveBindMaterList = false;
                    }
                    for (OrderDetailModel.DataBean.BindMaterList bindMaterList : goodsListBean.getBindMaterList()){
                        materialNum += bindMaterList.getNum();
                    }

                    ShopDeleveModel.GoodsListBean goodsListBean1 = new ShopDeleveModel.GoodsListBean();
                    goodsListBean1.setOrderGoodsId(goodsListBean.getOrderGoodsId());

                    if (goodsListBean.getWaterGoodsId()!=0){
                        ShopDeleveModel.GoodsListBean.DeductWaterBean deductWaterBean = new ShopDeleveModel.GoodsListBean.DeductWaterBean();
                        deductWaterBean.setWaterGoodsId(goodsListBean.getWaterGoodsId()+"");
                        deductWaterBean.setNum(goodsListBean.getWaterNum()+"");
                        deductWaterBean.setDeductNum(goodsListBean.getWaterDeductNum()+"");
                        goodsListBean1.setDeductWater(deductWaterBean);
                    }

                    if (goodsListBean.getMonthDeductNum()>0){
                        ShopDeleveModel.GoodsListBean.DeductMonthBean monthBean = new ShopDeleveModel.GoodsListBean.DeductMonthBean();
                        monthBean.setMonthImg(goodsListBean.getMonthImg());
                        monthBean.setDeductNum(goodsListBean.getMonthDeductNum()+"");
                        goodsListBean1.setDeductMonth(monthBean);
                    }

                    if (goodsListBean.getCouponDeductNum()>0){
                        ShopDeleveModel.GoodsListBean.DeductCouponBean couponBean = new ShopDeleveModel.GoodsListBean.DeductCouponBean();
                        couponBean.setCouponImg(goodsListBean.getCouponImg());
                        couponBean.setDeductNum(goodsListBean.getCouponDeductNum()+"");
                        goodsListBean1.setDeductCoupon(couponBean);
                    }

                    List<OrderDetailModel.DataBean.BindMaterList> bindMaterList = goodsListBean.getBindMaterList();
                    if (bindMaterList!=null && bindMaterList.size()>0){

                        List<ShopDeleveModel.GoodsListBean.MaterialsBean> materials  = new ArrayList<>();
                        for (OrderDetailModel.DataBean.BindMaterList bindMaterList1 : bindMaterList){
                            ShopDeleveModel.GoodsListBean.MaterialsBean materialsBean = new ShopDeleveModel.GoodsListBean.MaterialsBean();
                            materialsBean.setMaterialId(bindMaterList1.getId()+"");
                            materialsBean.setNum(bindMaterList1.getNum()+"");
                            materials.add(materialsBean);
                        }
                        goodsListBean1.setMaterials(materials);
                    }
                    goodsListBeanList.add(goodsListBean1);
                }

                if (priceView.getVisibility()==View.VISIBLE){
                    totalMerchandiseNum.setText(tatalPrice.getText().toString().replace("¥",""));
                    amountReceivableNum.setText(needPrice.getText().toString().replace("¥",""));
                    actualAmount.setText(actualPrice.getText().toString().replace("¥",""));
                }
                if (shopNum > materialNum && haveBindMaterList){
                    String content = "商品数量大于空桶数量，是否填写开押单？";
                    MessageDialog.show(this, "提示", content, "确定","取消")
                            .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                                @Override
                                public boolean onClick(BaseDialog baseDialog, View v) {
                                    addOrderDepositDialog = new AddOrderDepositDialog(DeliveryOrderActivity.this, id, customerId, new AddOrderDepositDialog.StartDespositListener() {
                                       @Override
                                       public void despositResult(boolean success) {
                                           if (success){
                                               orderDetailPresenter.deliveryOrder(id,totalMerchandiseNum.getText().toString(),amountReceivableNum.getText().toString(),actualAmount.getText().toString(),goodsListBeanList);
                                           }
                                       }
                                   });
                                    addOrderDepositDialog.show(getSupportFragmentManager(),"");
                                    return false;
                                }
                            }).setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v) {

                            List<OrderDetailModel.DataBean.GoodsListBean> tempList = new ArrayList<>();

                            for (int i = 0;i<data.size();i++){

                                OrderDetailModel.DataBean.GoodsListBean goodsListBean1 = data.get(i);
                                float goodsNum = goodsListBean1.getGoodsNum();

                                List<OrderDetailModel.DataBean.BindMaterList>tempBindMaterList = new ArrayList<>();
                                List<OrderDetailModel.DataBean.BindMaterList> bindMaterList = goodsListBean1.getBindMaterList();
                                if (bindMaterList!=null){
                                    for (int j= 0;j<bindMaterList.size();j++){
                                        OrderDetailModel.DataBean.BindMaterList bindMaterList1 = bindMaterList.get(j);
                                        if (j==0){
                                            bindMaterList1.setNum((int) goodsNum);
                                        }else {
                                            bindMaterList1.setNum(0);
                                        }
                                        tempBindMaterList.add(bindMaterList1);
                                    }
                                    goodsListBean1.setBindMaterList(tempBindMaterList);
                                    tempList.add(goodsListBean1);
                                }
                            }
                            deliverOrderDetailAdapter.setList(tempList);
                            caculateData();
                            return false;
                        }
                    });
                }else if (materialNum>shopNum){
                    String tip = "多了"+(materialNum-shopNum)+"个回收材料，请单独退押";
                   MessageDialog.show(DeliveryOrderActivity.this,"提示",tip,"确定","取消")
                   .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                       @Override
                       public boolean onClick(BaseDialog baseDialog, View v) {

                           List<ShopDeleveModel.GoodsListBean> tempList = new ArrayList<>();

                           for (int i = 0;i<data.size();i++){
                               float goodsNum = data.get(i).getGoodsNum();

                               ShopDeleveModel.GoodsListBean goodsListBean = goodsListBeanList.get(i);
                               List<ShopDeleveModel.GoodsListBean.MaterialsBean> materials = goodsListBean.getMaterials();
                               List<ShopDeleveModel.GoodsListBean.MaterialsBean> tempMaterials = new ArrayList<>();

                               for (ShopDeleveModel.GoodsListBean.MaterialsBean materialsBean  : materials){
                                   materialsBean.setNum("0");
                                   tempMaterials.add(materialsBean);
                               }

                               ShopDeleveModel.GoodsListBean.MaterialsBean materialsBean = tempMaterials.get(0);
                               materialsBean.setNum(goodsNum+"");
                               tempMaterials.set(0,materialsBean);
                               goodsListBean.setMaterials(tempMaterials);
                               tempList.add(goodsListBean);
                           }
                            orderDetailPresenter.deliveryOrder(id,totalMerchandiseNum.getText().toString(),amountReceivableNum.getText().toString(),actualAmount.getText().toString(),tempList);
                           return false;
                       }
                   });
                }else {
                    orderDetailPresenter.deliveryOrder(id,totalMerchandiseNum.getText().toString(),amountReceivableNum.getText().toString(),actualAmount.getText().toString(),goodsListBeanList);
                }
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
        if (data.getCustomerTypeName()==null || data.getCustomerTypeName().equals("")){
            type.setVisibility(View.GONE);
        }
        type.setText(data.getCustomerTypeName());
        address.setText(data.getAddressName());
        addressDetail.setText(data.getAddress());
        timeSend.setText(data.getTimeRange());
        timeOut.setText(data.getTimeStatus());


        orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.nedd_delivery));

        tatalPrice.setText("¥" + data.getTotalPrice());
        needPrice.setText("¥" + data.getReceivablePrice());
        orderNumData.setText("订单编号：" + data.getOrderNo());
        orderNo = data.getOrderNo();



        //data.setOrderType("微商城");
        //data.setPayMode("水票支付");
        /**
         *微商城  : 微信支付 (完成配送)  水票支付(作废转直销 完成配送)
         *
         *外卖订单: (完成配送)
         *
         * 电话订单 : (作废转直销)  (收款)
         */

        if ("微商城".equals(data.getOrderType())){
            voidDeliveryView.setVisibility(View.GONE);
            receivePayment.setVisibility(View.VISIBLE);

            if ("微信支付".equals(data.getPayMode())){
                receivePayment.setVisibility(View.VISIBLE);
            }else if ("水票支付".equals(data.getPayMode())){
                voidDeliveryView.setVisibility(View.VISIBLE);
                shopAmountView.setVisibility(View.GONE);
                receivePayment.setVisibility(View.GONE);
                deliverOrderDetailAdapter.setShowWater_type(true);
            }
        }

        if ("外卖订单".equals(data.getOrderType())){
            receivePayment.setVisibility(View.VISIBLE);
        }

        if ("电话订单".equals(data.getOrderType())){
            priceView.setVisibility(View.GONE);
            orderView.setVisibility(View.GONE);
            shopAmountView.setVisibility(View.VISIBLE);
            deliverOrderDetailAdapter.setShowTicket_type(true);

            receivePayment.setVisibility(View.GONE);
            voidDeliveryView.setVisibility(View.VISIBLE);
            deliveryBtn.setText("收款");
            phone_order_view.setVisibility(View.VISIBLE);
            phone_order_num_data.setText("订单编号：" + data.getOrderNo());
            phone_order_source.setText("订单来源: "+data.getOrderType());
        }

        orderSource.setText("订单来源: "+data.getOrderType());
        payWay.setText("支付方式：" + data.getPayMode());
        actualPrice.setText("¥"+data.getRealPrice());

        deliverOrderDetailAdapter.setList(data.getGoodsList());
        totalMerchandiseNum.setText((float)data.getReceivablePrice()+"");
        customerId = data.getCustomerId();

        caculateData();

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

                }else if (requestCode==REQUEST_CODE_CHOOSE_PICTURE_MONTH){
                    String path = medias.get(0).getPath();
                    orderDetailPresenter.uploadFile("month",path);
                }else if (requestCode==REQUEST_CODE_LIST_CHOOSE_PICTURE_MONTH){
                    String path = medias.get(0).getPath();
                    orderDetailPresenter.uploadFile("list_month",path);
                }else if (requestCode==REQUEST_CODE_LIST_CHOOSE_PICTURE_REWARD){
                    String path = medias.get(0).getPath();
                    orderDetailPresenter.uploadFile("list_reward",path);
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DespositEvent event) {
        if (event!=null){
            Log.i(TAG,"event==="+event.despositNum);
            if (addOrderDepositDialog!=null){
                addOrderDepositDialog.setYJnum(event.despositNum);
            }
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
                    String key = event.key;
                    if (key.equals("reward")){
                        rewardUrl = event.path;
                    }else if(key.equals("month")){
                        monthUrl = event.path;
                    }else if (key.equals("list_month")){
                        OrderDetailModel.DataBean.GoodsListBean goodsListBean = deliverOrderDetailAdapter.getData().get(currentUploadPosition);
                        goodsListBean.setMonthImg(event.path);
                        deliverOrderDetailAdapter.setData(currentUploadPosition,goodsListBean);
                    }else if (key.equals("list_reward")){
                        OrderDetailModel.DataBean.GoodsListBean goodsListBean = deliverOrderDetailAdapter.getData().get(currentUploadPosition);
                        goodsListBean.setCouponImg(event.path);
                        deliverOrderDetailAdapter.setData(currentUploadPosition,goodsListBean);
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.type==3){
                if (event.status==Config.LOAD_SUCCESS){
                    Utils.showCenterTomast("配送成功");
                    finish();
                }else {
                    Utils.showCenterTomast("配送失败 : "+ event.result);
                }
            }else if (event.type==2){
                if (event.status == Config.LOAD_SUCCESS) {
                    Utils.showCenterTomast("订单作废成功");
                    finish();
                } else {
                    Utils.showCenterTomast("订单作废失败 : "+ event.result);
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
        EventBus.getDefault().post(new UpdateOrderEvent(typeId+""));
        EventBus.getDefault().unregister(this);
    }
}
