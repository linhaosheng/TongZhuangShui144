package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.shinichi.library.ImagePreview;
import pro.bilibili.boxing.Boxing;
import pro.bilibili.boxing.model.config.BoxingConfig;
import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.bilibili.boxing_impl.ui.BoxingActivity;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddOrderAdapter;
import pro.haichuang.tzs144.adapter.MaterialListAdapter;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.AddOrderStatusEvent;
import pro.haichuang.tzs144.model.DespositEvent;
import pro.haichuang.tzs144.model.MaterialModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.model.ShopDeleveModel;
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
    EditText shopNum;
    @BindView(R.id.shop_add)
    TextView shopAdd;
    @BindView(R.id.shop_capacity)
    TextView shopCapacity;
    @BindView(R.id.shop_unit)
    TextView shopUnit;
    @BindView(R.id.shop_price)
    EditText shopPrice;
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
    @BindView(R.id.give_away_nunm)
    LSettingItem giveAwayNunm;
    @BindView(R.id.give_away_money)
    LSettingItem giveAwayMoney;
    @BindView(R.id.give_away)
    TextView giveAway;
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
    EditText actualAmount;
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
    @BindView(R.id.recycle_material)
    RecyclerView recycleMaterial;
    private AddOrderAdapter addOrderAdapter;
    private MaterialListAdapter materialListAdapter;

    private boolean selectWater;
    private boolean selectReward;
    private boolean selectMonth;
    private boolean selectGiveAway;

    private final static int REQUEST_CODE_CHOOSE_PICTURE_REWARD = 0x1110;
    private final static int REQUEST_CODE_CHOOSE_PICTURE_MONTH = 0x1111;
    private EnterOrderActivityPresenter enterOrderActivityPresenter;
    private UploadOrderModel uploadOrderModel;
    private String rewardUrl = "";
    private String monthUrl = "";
    private int shopId;
    private int waterId = -1;
    private ShopModel.DataBean mDataBea;
    private ShopModel.DataBean shopDataBean;
    public final static int SELECT_ADDRESS_INFO = 0x1110;
    private SaleDataModel.DataBean dataBean;
    private float totalPrice;
    private float amount_receivable;
    private float actual_amount;
    private int orderType;
    private AddOrderDepositDialog addOrderDepositDialog;
    private int customeId;

    private boolean saveAdd = true;

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
//                        if (dataBean.getWaterNum()>0){
//                            selectWaterNum.setEditinput(String.valueOf(dataBean.getWaterNum()));
//                            selectDeductionNunm.setEditinput(String.valueOf(dataBean.getWaterNum()));
//                        }else {
//                            selectWaterNum.setEditinput("");
//                            selectDeductionNunm.setEditinput("");
//                        }
                    }
                },dataBean.getId());
                selectWaterTicketDialog.show(getSupportFragmentManager(),"");
            }
        });
        addOrderAdapter = new AddOrderAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(addOrderAdapter);
        addOrderAdapter.addChildClickViewIds(R.id.delete,R.id.edit,R.id.reward_img,R.id.month_img);
        addOrderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.delete:
                        List<AddOrderModel.GoodsListBean> data = addOrderAdapter.getData();
                        data.remove(position);
                        addOrderAdapter.setList(data);
                        caculateShopMount();
                        if (data.size()==0){
                            shop_amount_view.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.edit:

                        List<AddOrderModel.GoodsListBean> data1 = addOrderAdapter.getData();
                        AddOrderModel.GoodsListBean goodsListBean = data1.get(position);

                        if (goodsListBean.getDeductWater()!=null){
                            Log.i("TAG====","getDeductMonth======");
                            waterId = Integer.parseInt(goodsListBean.getDeductWater().getWaterGoodsId());
                            selectWaterNum.setRightText(goodsListBean.getDeductWater().getNum());
                            selectDeductionNunm.setRightText(goodsListBean.getDeductWater().getDeductNum());
                        }

                        if (goodsListBean.getDeductMonth()!=null){
                            monthly.setBackground(ContextCompat.getDrawable(EnterOrderActivity.this,R.drawable.set_bg_btn17));
                            upload_month_view.setVisibility(View.VISIBLE);
                            monthDeductionNunm.setVisibility(View.VISIBLE);
                            Utils.showImage(uploadMonth,goodsListBean.getDeductMonth().getMonthImg());
                            monthDeductionNunm.setRightText(goodsListBean.getDeductMonth().getDeductNum());
                        }else {
                            monthly.setBackground(ContextCompat.getDrawable(EnterOrderActivity.this,R.drawable.set_bg_btn33));
                            upload_month_view.setVisibility(View.GONE);
                            monthDeductionNunm.setVisibility(View.GONE);
                        }

                        if (goodsListBean.getDeductCoupon()!=null){
                            rewardTickets.setBackground(ContextCompat.getDrawable(EnterOrderActivity.this,R.drawable.set_bg_btn17));
                            upload_reward_view.setVisibility(View.VISIBLE);
                            rewardDeductionNunm.setVisibility(View.VISIBLE);
                            Utils.showImage(uploadReward,goodsListBean.getDeductCoupon().getCouponImg());
                            rewardDeductionNunm.setRightText(goodsListBean.getDeductCoupon().getDeductNum());
                        }else {
                            rewardTickets.setBackground(ContextCompat.getDrawable(EnterOrderActivity.this,R.drawable.set_bg_btn33));
                            upload_reward_view.setVisibility(View.GONE);
                            rewardDeductionNunm.setVisibility(View.GONE);
                        }
                        materialListAdapter.setList(goodsListBean.getMaterials());

                        shopDetail.setVisibility(View.VISIBLE);
                        shop_amount_view.setVisibility(View.VISIBLE);

                        shopName.setText(goodsListBean.getGoodName());
                        shopCapacity.setText(goodsListBean.getSpecs());
                        shopPrice.setText(goodsListBean.getGoodsPrice()+"");

                        shopId = Integer.parseInt(goodsListBean.getGoodsId());

                        data1.remove(position);
                        addOrderAdapter.setList(data1);
                        caculateShopMount();
                        break;
                    case R.id.reward_img:
                        AddOrderModel.GoodsListBean goodsListBean1 = addOrderAdapter.getData().get(position);
                        String couponImg = goodsListBean1.getDeductCoupon().getCouponImg();
                        showImage(couponImg);
                        break;
                    case R.id.month_img:
                        AddOrderModel.GoodsListBean goodsListBean2 = addOrderAdapter.getData().get(position);
                        String monthImg = goodsListBean2.getDeductMonth().getMonthImg();
                        showImage(monthImg);
                        break;
                }
            }
        });
        initAddressInfo(getIntent());
        recycleMaterial.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        materialListAdapter = new MaterialListAdapter();
        recycleMaterial.setAdapter(materialListAdapter);

        materialListAdapter.addChildClickViewIds(R.id.reduce_tong,R.id.shop_add_tong);
        materialListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                MaterialModel.DataBean dataBean = materialListAdapter.getData().get(position);
                switch (view.getId()){
                    case R.id.reduce_tong:
                        if (dataBean.getNum()==0){
                            return;
                        }else {
                            int num = dataBean.getNum() -1;
                            dataBean.setNum(num);
                            materialListAdapter.setData(position,dataBean);
                        }
                        break;
                    case R.id.shop_add_tong:
                        int num = dataBean.getNum() + 1;
                        dataBean.setNum(num);
                        materialListAdapter.setData(position,dataBean);
                        break;
                }
            }
        });

        selectWaterNum.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                selectDeductionNunm.setEditinput(text);
            }
        });

        shopNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (selectGiveAway){
                    if (shopDataBean!=null){
                        try {
                            double shopNumData = Double.parseDouble(shopNum.getText().toString());
                            giveAwayMoney.setEditinput(String.valueOf(shopDataBean.getPrice() * shopNumData));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        shopPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (selectGiveAway){
                    if (shopDataBean!=null){
                        try {
                            double shopNumData = Double.parseDouble(shopNum.getText().toString());
                            double shopPriceData = Double.parseDouble(shopPrice.getText().toString());
                            giveAwayMoney.setEditinput(String.valueOf(shopPriceData* shopNumData));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 显示大图
     * @param path
     */
    private void showImage(String path){
        ImagePreview.getInstance()
                .setContext(EnterOrderActivity.this)
                .setShowDownButton(false)
                .setImage(path).start();
    }

    private void initAddressInfo(Intent intent) {
        String dataBeanJson = intent.getStringExtra(Config.PERSION_INFO);
        if (dataBeanJson!=null){
            dataBean = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);
            name.setText(dataBean.getName());
            phone.setText(dataBean.getPhone());
            addressName.setText(dataBean.getAddressName());
            addressDetail.setText(dataBean.getAddress());
            type.setText(dataBean.getType());
            customeId = dataBean.getId();
        }
    }

    @Override
    protected void setUpData() {
        orderType = getIntent().getIntExtra("order_type",0);
        uploadOrderModel = new UploadOrderModel();
       enterOrderActivityPresenter = new EnterOrderActivityPresenter(this);
    }


    @OnClick({R.id.back, R.id.upload_reward, R.id.upload_month, R.id.receive_payment,R.id.tip_img,R.id.address_detail,R.id.add_shop_btn,R.id.water_tickets,R.id.reward_tickets,R.id.monthly,R.id.give_away,R.id.upload_month_view,R.id.confirm_add_shop,R.id.select_client,R.id.reduce,R.id.shop_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reduce:
                try {
                    int shopNumData = Integer.parseInt(shopNum.getText().toString());
                    if (shopNumData==0){
                        return;
                    }else {
                        shopNum.setText(String.valueOf(shopNumData-1));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.shop_add:
                try {
                    int shopNumData = Integer.parseInt(shopNum.getText().toString());
                    shopNum.setText(String.valueOf(shopNumData+1));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
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
                if (addOrderAdapter.getData()==null || addOrderAdapter.getData().size()==0){
                    Utils.showCenterTomast("请添加商品");
                    return;
                }
                if (shopDetail.getVisibility()==View.VISIBLE){
                    Utils.showCenterTomast("请点击确认添加按钮");
                    return;
                }
                boolean despositTips = tipOpenDesposit();
                if (despositTips){
                    return;
                }
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
                if (dataBean!=null){
                    intent1.putExtra("customerId",dataBean.getId());
                }
                startActivityForResult(intent1,1000);
                break;
            case R.id.add_shop_btn:
                if (!saveAdd){
                    Utils.showCenterTomast("请先点击确认添加");
                    return;
                }
                AddShopDialog addShopDialog = new AddShopDialog(this, new AddShopDialog.SelectShopListener() {
                    @Override
                    public void selectShop(ShopModel.DataBean dataBean, List<MaterialModel.DataBean>dataBeanList) {
                        saveAdd = false;
                        shopDataBean = dataBean;
                        monthly.setBackground(ContextCompat.getDrawable(EnterOrderActivity.this,R.drawable.set_bg_btn33));
                        rewardTickets.setBackground(ContextCompat.getDrawable(EnterOrderActivity.this,R.drawable.set_bg_btn33));
                        shopDetail.setVisibility(View.VISIBLE);
                        shop_amount_view.setVisibility(View.VISIBLE);
                        shopName.setText(dataBean.getName());
                        shopCapacity.setText(dataBean.getSpecs());
                        shopPrice.setText(dataBean.getPrice()+"");
                        shopId = dataBean.getId();
                        materialListAdapter.setList(dataBeanList);

                        enterOrderActivityPresenter.getCustomerPrice(customeId,dataBean.getId());
                    }
                });
                addShopDialog.show(getSupportFragmentManager(),"");
                break;
            case R.id.water_tickets:
                if (selectWater){
                    selectTicket.setLeftText("水票");
                    selectWaterNum.setEditinput("0");
                    selectDeductionNunm.setEditinput("0");
                    waterId = -1;
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
                    uploadReward.setImageDrawable(ContextCompat.getDrawable(EnterOrderActivity.this,R.mipmap.upload));
                    rewardDeductionNunm.setEditinput("");

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
                    uploadMonth.setImageDrawable(ContextCompat.getDrawable(EnterOrderActivity.this,R.mipmap.upload));
                    monthDeductionNunm.setEditinput("");

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
            case R.id.give_away:

                if (selectGiveAway){
                    giveAwayMoney.setEditinput("");
                    giveAwayNunm.setEditinput("");
                    giveAway.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn33));
                    giveAwayNunm.setVisibility(View.GONE);
                    giveAwayMoney.setVisibility(View.GONE);
                }else {
                    giveAway.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn17));
                    giveAwayNunm.setVisibility(View.VISIBLE);
                    giveAwayMoney.setVisibility(View.VISIBLE);
                    if (shopDataBean!=null){
                        try {
                            double shopNumData = Double.parseDouble(shopNum.getText().toString());
                            giveAwayMoney.setEditinput(String.valueOf(shopDataBean.getPrice() * shopNumData));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                selectGiveAway=!selectGiveAway;
                break;

            case R.id.confirm_add_shop:

                if (mDataBea==null){
                 //   Utils.showCenterTomast("请选择水票类型");
                    //return;
                }
                saveAdd = true;

                Log.i("TAG==","PRICE==="+shopPrice.getText().toString());
                AddOrderModel.GoodsListBean goodsListBean = new AddOrderModel.GoodsListBean();
                goodsListBean.setGoodName(shopName.getText().toString());
                goodsListBean.setGoodsId(String.valueOf(shopId));
                goodsListBean.setNum(shopNum.getText().toString());
                goodsListBean.setGoodsPrice(shopPrice.getText().toString());
                goodsListBean.setSpecs(shopCapacity.getText().toString());

                List<MaterialModel.DataBean>tempData = new ArrayList<>();
                for (MaterialModel.DataBean dataBean : materialListAdapter.getData()){
                    dataBean.setMaterialId(dataBean.getId());
                    tempData.add(dataBean);
                }
                goodsListBean.setMaterials(tempData);

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

                try {
                    float num = Float.parseFloat(rewardDeductionNunm.getEditText());
                    if (num>0){
                        AddOrderModel.GoodsListBean.DeductCouponBean deductCouponBean = new AddOrderModel.GoodsListBean.DeductCouponBean();
                        deductCouponBean.setDeductNum(rewardDeductionNunm.getEditText());
                        deductCouponBean.setCouponImg(rewardUrl);
                        goodsListBean.setDeductCoupon(deductCouponBean);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    float num = Float.parseFloat(monthDeductionNunm.getEditText());
                    if (num>0){
                        AddOrderModel.GoodsListBean.DeductMonthBean deductMonthBean = new AddOrderModel.GoodsListBean.DeductMonthBean();
                        deductMonthBean.setMonthImg(monthUrl);
                        deductMonthBean.setDeductNum(monthDeductionNunm.getEditText());
                        goodsListBean.setDeductMonth(deductMonthBean);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    int num = Integer.parseInt(giveAwayNunm.getEditText());
                    if (num>0){
                        goodsListBean.setSendNum(num);
                        goodsListBean.setSendPrice(Float.parseFloat(giveAwayMoney.getEditText()));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                String json = Utils.gsonInstane().toJson(goodsListBean);
                Log.i(TAG,"json====="+json);
                List<AddOrderModel.GoodsListBean> data = addOrderAdapter.getData();
                if (data==null){
                    data = new ArrayList<>();
                }
                data.add(0,goodsListBean);
                addOrderAdapter.setList(data);

                shopDetail.setVisibility(View.GONE);
                caculateShopMount();

                shop_amount_view.setVisibility(View.VISIBLE);
                initViewData();

                selectTicket.setLeftText("水票");
                selectWaterNum.setEditinput("");
                selectDeductionNunm.setEditinput("");
                rewardDeductionNunm.setEditinput("");
                monthDeductionNunm.setEditinput("");
                uploadReward.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.upload));
                uploadMonth.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.upload));

                break;
            case R.id.select_client:
                Intent intent2 = new Intent(this,SaleSearchActivity.class);
                startActivityForResult(intent2,SELECT_ADDRESS_INFO);
                break;
        }
    }


    private void caculateShopMount() {

        totalPrice = 0;
        amount_receivable = 0;
        actual_amount = 0;
        float month_mount = 0;
        float sendMoney = 0;

        try {
            List<AddOrderModel.GoodsListBean> data = addOrderAdapter.getData();
            for (AddOrderModel.GoodsListBean goodsListBean1 : data){
                if (goodsListBean1.getDeductWater()==null){
                    // Utils.showCenterTomast("请选择水票");
                    // break;
                }

                float shopPrice =  Float.parseFloat(goodsListBean1.getGoodsPrice());
                //总价格 : 所有商品金额之和
                float currentAmount = Float.parseFloat(goodsListBean1.getNum()) * shopPrice;

                totalPrice += currentAmount;
                if (goodsListBean1.getDeductWater()!=null){
                    float deductWaterNum = Float.parseFloat(goodsListBean1.getDeductWater().getDeductNum());
                    if (goodsListBean1.getDeductCoupon()!=null){
                        //应收价格  商品总额-（水票+奖券抵扣金额）
                        float deductCouponNum = Float.parseFloat(goodsListBean1.getDeductCoupon().getDeductNum());
                        amount_receivable += currentAmount - (deductWaterNum + deductCouponNum) * shopPrice;
                    }else {
                        amount_receivable += currentAmount - (deductWaterNum * shopPrice);
                    }
                }else {
                    if (goodsListBean1.getDeductCoupon()!=null){
                        float deductCouponNum = Float.parseFloat(goodsListBean1.getDeductCoupon().getDeductNum());
                        amount_receivable += currentAmount - (deductCouponNum * shopPrice);
                    }else {
                        amount_receivable +=  currentAmount;
                    }
                }

                if (goodsListBean1.getDeductMonth()!=null){
                    //实收价格  【单个商品总价-（单个商品抵扣数量*单价）】之和；
                    float deductMonthNum = Float.parseFloat(goodsListBean1.getDeductMonth().getDeductNum());
                    month_mount += deductMonthNum * shopPrice;
                }
                if (goodsListBean1.getSendNum()>0){
                    sendMoney += goodsListBean1.getSendPrice() * goodsListBean1.getSendNum();
                }
            }
            totalMerchandiseNum.setText(totalPrice +"");
            amountReceivableNum.setText(amount_receivable +"");
            actualAmount.setText((amount_receivable - month_mount - sendMoney)+"");
        }catch (Exception e){
            e.printStackTrace();
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
        addOrderModel.setGoodsList(addOrderAdapter.getData());

        enterOrderActivityPresenter.enterOrder(addOrderModel, totalMerchandiseNum.getText().toString(),amountReceivableNum.getText().toString(),actualAmount.getText().toString());
    }

    /**
     * 计算当前距离是否合法
     * @param distance
     */
    private final void caculateDistance(int distance){
        String content ="";
        if (distance>1000){
            content = "您当前位置与客户相差"+(float)distance/1000+"KM，确定继续收款?";
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
            }else if (requestCode==1000){
                Log.i(TAG,"initAddressInfo");
                String dataBeanJson = data.getStringExtra(Config.PERSION_INFO);
                SaleDataModel.DataBean dataBean1 = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);

                addressName.setText(dataBean1.getAddressName());
                addressDetail.setText(dataBean1.getAddress());
                dataBean.setAddress(dataBean1.getAddress());
                dataBean.setAddressName(dataBean1.getAddressName());
                dataBean.setId(dataBean1.getId());
                dataBean.setLatitude(dataBean1.getLatitude()+"");
                dataBean.setLongitude(dataBean1.getLongitude()+"");
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
    public void onMessageEvent(AddOrderStatusEvent event) {
        if (event!=null){
            if (event.status==Config.LOAD_SUCCESS){
                WaitDialog.dismiss();
                Utils.showCenterTomast("提交成功");
                if (!addDesposit(event.id)){
                    finish();
                }
            }
        }
    }

    public boolean tipOpenDesposit(){

        int materialNum = 0;
        float shopNum = 0;
        try {
            for (AddOrderModel.GoodsListBean goodsListBean : addOrderAdapter.getData()){
                List<MaterialModel.DataBean> materials = goodsListBean.getMaterials();
                for (MaterialModel.DataBean dataBean : materials){
                    materialNum +=dataBean.getNum();
                }
                shopNum+=Integer.parseInt(goodsListBean.getNum());
            }
            if (shopNum<materialNum){
                // String content = "回收材料多了"+(materialNum - shopNum) +"，请单独退押";
                String content = "多了"+(materialNum - shopNum)+"个回收材料，请单独退押";
                MessageDialog.show(this, "提示", content, "确定")
                        .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                try {
                                    List<AddOrderModel.GoodsListBean> data = addOrderAdapter.getData();
                                    List<AddOrderModel.GoodsListBean>tempData = new ArrayList<>();

                                    for (int i = 0;i<data.size();i++){
                                        int goodsNum =  Integer.parseInt(data.get(i).getNum());
                                        AddOrderModel.GoodsListBean goodsListBean = data.get(i);
                                        List<MaterialModel.DataBean> materials = goodsListBean.getMaterials();
                                        List<MaterialModel.DataBean> tempMaterials = new ArrayList<>();
                                        for (MaterialModel.DataBean dataBean : materials){
                                            dataBean.setNum(0);
                                            tempMaterials.add(dataBean);
                                        }

                                        MaterialModel.DataBean dataBean = tempMaterials.get(0);
                                        dataBean.setNum(goodsNum);
                                        tempMaterials.set(0,dataBean);
                                        goodsListBean.setMaterials(tempMaterials);
                                        tempData.add(goodsListBean);
                                    }
                                    addOrderAdapter.setList(tempData);
                                    caculateShopMount();

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                return false;
                            }
                        });
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否需要添加开押单
     * @return
     */
    public boolean addDesposit(int orderId){
        int materialNum = 0;
        int shopNum = 0;
        try {
            boolean haveMaterials = false;
            for (AddOrderModel.GoodsListBean goodsListBean : addOrderAdapter.getData()){
                List<MaterialModel.DataBean> materials = goodsListBean.getMaterials();
                if (goodsListBean.getMaterials()!=null && goodsListBean.getMaterials().size()>0){
                    haveMaterials = true;
                }
                for (MaterialModel.DataBean dataBean : materials){
                    materialNum +=dataBean.getNum();
                }
                shopNum+=Integer.parseInt(goodsListBean.getNum());
            }
            if (shopNum>materialNum && haveMaterials){
                // String content = "回收材料多了"+(materialNum - shopNum) +"，请单独退押";
                String content = "商品数量大于空桶数量，是否填写开押单？";
                MessageDialog.show(this, "提示", content, "确定","取消")
                        .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                addOrderDepositDialog = new AddOrderDepositDialog(EnterOrderActivity.this, String.valueOf(orderId), dataBean.getId(), new AddOrderDepositDialog.StartDespositListener() {
                                    @Override
                                    public void despositResult(boolean success) {
                                        if (success){
                                            finish();
                                        }
                                    }
                                });
                                addOrderDepositDialog.show(getSupportFragmentManager(),"");
                                return false;
                            }
                        }).setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        finish();
                        return false;
                    }
                });
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
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
    public void onMessageEvent(EnterOrderActivityPresenter.CustomerPriceModel model) {
        if (model!=null){
            try {
                if (model.getResult()==1){
                    EnterOrderActivityPresenter.CustomerPriceModel.DataBean data = model.getData();
                    shopPrice.setText(data.getPrice()+"");
                    shopDataBean.setPrice(Double.parseDouble(data.getPrice()));
                    shopPrice.setFocusable(data.isEdit());
                    shopPrice.setFocusableInTouchMode(data.isEdit());
                }else{
                    shopPrice.setFocusable(true);
                    shopPrice.setFocusableInTouchMode(true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        MessageDialog.show(this, "提示", error, "确定")
                .show();
    }
}
