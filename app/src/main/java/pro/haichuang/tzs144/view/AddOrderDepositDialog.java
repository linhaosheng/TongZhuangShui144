package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.JsonObject;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.DepositManagementSearchActivity;
import pro.haichuang.tzs144.activity.FindDespositActivity;
import pro.haichuang.tzs144.activity.StartDepositActivity;
import pro.haichuang.tzs144.model.DespositEvent;
import pro.haichuang.tzs144.model.GoodsShopEvent;
import pro.haichuang.tzs144.model.GoodsShopModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class AddOrderDepositDialog extends DialogFragment {


    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.guarantee_persion_phone)
    LSettingItem guaranteePersionPhone;
    @BindView(R.id.deposit_items)
    LSettingItem depositItems;
    @BindView(R.id.mortgage_price)
    LSettingItem mortgagePrice;
    @BindView(R.id.mortgage_num)
    LSettingItem mortgageNum;
    @BindView(R.id.total_pr)
    LSettingItem totalPr;
    @BindView(R.id.mortgage_type)
    LSettingItem mortgageType;
    @BindView(R.id.finish_btn)
    Button finishBtn;
    private View view;

    private AppCompatActivity context;
    private List<CharSequence> depositTypeList;
    private List<CharSequence>depositGoodShopList;
    private GoodsShopModel goodsShopModel;
    private String orderId;
    private int customerId;
    private String priceData;
    private int goodsId;
    private int type;
    private StartDespositListener startDespositListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public AddOrderDepositDialog(AppCompatActivity mContext,String orderId,int customerId,StartDespositListener startDespositListener) {
        this.context = mContext;
        this.orderId = orderId;
        this.customerId = customerId;
        this.startDespositListener = startDespositListener;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        //window.setWindowAnimations(R.style.BottomDialog_Animation);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // getDialog().getWindow().setLayout(dm.widthPixels, (int) (dm.heightPixels * 0.28));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.dialog_add_order_deposit, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        return view;
    }

    private void initView() {
        findDepositGoods();
        depositGoodShopList = new ArrayList<>();
        depositTypeList = new ArrayList<>();
        depositTypeList.add("押金");
        depositTypeList.add("借条");
        depositTypeList.add("暂欠");

        guaranteePersionPhone.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
             Intent intent = new Intent(context, FindDespositActivity.class);
             startActivity(intent);
            }
        });

        depositItems.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(context, depositGoodShopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        depositItems.setRightText(text);
                        goodsId = goodsShopModel.getData().get(index).getId();
                    }
                });
            }
        });

        mortgageType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(context, depositTypeList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        mortgageType.setRightText(text);
                        type = index;
                    }
                });
            }
        });

        totalPr.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                try {
                    float goodPrice = Float.parseFloat(mortgagePrice.getEditText());
                    int goodNum = Integer.parseInt(mortgageNum.getEditText());
                    float total = goodPrice * goodNum;
                    totalPr.setRightText(total+"元");
                    priceData = String.valueOf(total);

                }catch (Exception e){
                    e.printStackTrace();
                    Utils.showCenterTomast("请输入正确的数量和金额");
                }
            }
        });

        mortgagePrice.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                try {
                    float goodPrice = Float.parseFloat(text);
                    int goodNum = Integer.parseInt(mortgageNum.getEditText());
                    float total = goodPrice * goodNum;
                    totalPr.setRightText(total+"元");
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
                    totalPr.setRightText(total+"元");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DespositEvent event) {
        if (event!=null){
            guaranteePersionPhone.setRightText(event.despositNum);
        }
    }

    public void setYJnum(String yJnum){
        guaranteePersionPhone.setRightText(yJnum);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    /**
     * 添加押金本
     *
     * @param
     * @param
     * @param
     */
    public void addDepositBook(String orderId,String no,int customerId,int goodsId,String price,String num,int type) {

        Map<String, Object> params = new ArrayMap<>();
        params.put("orderId", orderId);
        params.put("no", no);

        params.put("customerId", customerId);
        params.put("goodsId", goodsId);

        params.put("price", price);
        params.put("num", num);

        params.put("type", type);

        HttpRequestEngine.postRequest(ConfigUrl.ADD_DEPOSIT_INFO, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        Utils.showCenterTomast(jsonObject.getString("message"));
                        dismiss();
                        if (startDespositListener!=null){
                            startDespositListener.despositResult(true);
                        }

                    }else {
                        Utils.showCenterTomast(jsonObject.getString("message"));
                        if (startDespositListener!=null){
                            startDespositListener.despositResult(false);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {

            }
        });

    }

    @OnClick({R.id.delete, R.id.finish_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete:
                dismiss();
                break;
            case R.id.finish_btn:

                if (guaranteePersionPhone.getRightText().contains("请选择")){
                    Utils.showCenterTomast("请选择押金编号");
                    return;
                }
                if (depositItems.getRightText().contains("请选择")){
                    Utils.showCenterTomast("请选择押金物品");
                    return;
                }
                if (TextUtils.isEmpty(mortgagePrice.getEditText())){
                    Utils.showCenterTomast("请输入单价");
                    return;
                }

                if (TextUtils.isEmpty(mortgageNum.getEditText())){
                    Utils.showCenterTomast("请输入数量");
                    return;
                }

                if (mortgageType.getRightText().contains("请选择")){
                    Utils.showCenterTomast("请选择开押类型");
                    return;
                }
                addDepositBook(orderId,guaranteePersionPhone.getRightText(),customerId,goodsId,mortgagePrice.getEditText(),mortgageNum.getEditText(),type);

                break;
        }
    }

    /**
     * [押金]查询开押商品
     */
    public final void findDepositGoods(){
        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_GOODS, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                 goodsShopModel = Utils.gsonInstane().fromJson(result, GoodsShopModel.class);
                for (GoodsShopModel.DataBean dataBean : goodsShopModel.getData()){
                    depositGoodShopList.add(dataBean.getName());
                }
            }

            @Override
            public void error(String error) {

            }
        });
    }

    public static interface StartDespositListener{
        void despositResult(boolean success);
    }
}
