package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.RefreshCountEvent;
import pro.haichuang.tzs144.model.RefreshInventoryEvent;
import pro.haichuang.tzs144.model.ShopListModel;
import pro.haichuang.tzs144.model.StockMainModel;
import pro.haichuang.tzs144.model.TypeListModel;
import pro.haichuang.tzs144.presenter.UnpackPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;
import pro.haichuang.tzs144.view.SelectShopDialog;

/**
 * 拆包
 */
public class UnpackActivity extends BaseActivity implements ILoadDataView<String> {

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
    @BindView(R.id.inventory_subject)
    LSettingItem inventorySubject;
    @BindView(R.id.shop_type)
    LSettingItem shopType;
    @BindView(R.id.shop_num)
    LSettingItem shopNum;
    @BindView(R.id.unpack_shop_type)
    LSettingItem unpackShopType;
    @BindView(R.id.unpack_shop_num)
    LSettingItem unpackShopNum;
    @BindView(R.id.save_deposit_btn)
    Button saveDepositBtn;

    /**
     * 库存主体
     */
    private List<CharSequence> subjectList;
    private StockMainModel stockMainModel;
    private String unpackGoodsId;
    private String goodsId;
    private String mainId; //库存主体

    /**
     * 商品
     */
    private List<CharSequence> shopList;
    private ShopListModel.DataBean.DataListBean dataListBean;


    private UnpackPresenter unpackPresenter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_unpack;
    }

    @Override
    protected void setUpView() {
       title.setText("拆包");

        inventorySubject.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(UnpackActivity.this, subjectList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        inventorySubject.setRightText(text);
                        mainId = String.valueOf(stockMainModel.getData().get(index));
                    }
                });
            }
        });

        /**
         * 商品类型
         */
        shopType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                SelectShopDialog selectShopDialog  = new SelectShopDialog(UnpackActivity.this, 0,new SelectShopDialog.SelectShopListener() {
                    @Override
                    public void selectShop(ShopListModel.DataBean.DataListBean dataBean) {
                        dataListBean = dataBean;
                        shopType.setRightText(dataBean.getGoodsName());
                        unpackGoodsId = dataBean.getId();
                    }
                });
                selectShopDialog.show(getSupportFragmentManager(),"");
//                BottomMenu.show(UnpackActivity.this, shopList, new OnMenuItemClickListener() {
//                    @Override
//                    public void onClick(String text, int index) {
//                        shopType.setRightText(text);
//                        unpackGoodsId = String.valueOf(shopListModel.getData().getDataList().get(index).getId());
//                    }
//                });
            }
        });

        /**
         * 拆解后的商品
         */
        unpackShopType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                SelectShopDialog selectShopDialog  = new SelectShopDialog(UnpackActivity.this, 1,new SelectShopDialog.SelectShopListener() {
                    @Override
                    public void selectShop(ShopListModel.DataBean.DataListBean dataBean) {
                        unpackShopType.setRightText(dataBean.getGoodsName());
                        goodsId = dataBean.getId();
                    }
                });
                selectShopDialog.show(getSupportFragmentManager(),"");
            }
        });
    }

    @Override
    protected void setUpData() {
        unpackPresenter = new UnpackPresenter(this);
      inventorySubject.setRightText(Config.CURRENT_MAIN_NAME);
       mainId = Config.CURRENT_MAIN_ID;
        String subjectListJson = SPUtils.getString(Config.STOCK_MAIN_LIST, "");
        if (!subjectListJson.equals("")) {
            subjectList = new ArrayList<>();
            stockMainModel = Utils.gsonInstane().fromJson(subjectListJson, StockMainModel.class);
            for (StockMainModel.DataBean dataBean : stockMainModel.getData()) {
                subjectList.add(dataBean.getStockName());
            }
        }
        shopList = new ArrayList<>();

        /**
         * 商品品类
         */
//        String categoryListJson = SPUtils.getString(Config.FIND_GOODS_LIST, "");
//        if (!categoryListJson.equals("")) {
//            shopList = new ArrayList<>();
//            //   shopList.add("全部");
//            shopListModel = Utils.gsonInstane().fromJson(categoryListJson, ShopListModel.class);
//            if (shopListModel.getData().getDataList()!=null){
//                for (ShopListModel.DataBean.DataListBean dataBean : shopListModel.getData().getDataList()) {
//                    shopList.add(dataBean.getGoodsName());
//                }
//            }
//        }
    }


    @OnClick({R.id.back, R.id.save_deposit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save_deposit_btn:
                saveUnpack();
                break;
        }
    }

    private final void saveUnpack(){

        if (mainId==null || mainId.equals("") || mainId.contains("请输入")){
            Utils.showCenterTomast("请选择库存主体");
            return;
        }
            if (unpackGoodsId==null || unpackGoodsId.equals("") || unpackGoodsId.contains("请输入")){
                Utils.showCenterTomast("请选择商品");
                return;
            }

        if (goodsId==null || goodsId.equals("") || goodsId.contains("请输入")){
            Utils.showCenterTomast("请选择拆解后商品");
            return;
        }

        try {
            String unpackNum = shopNum.getEditText();
            double inputNum = Double.parseDouble(unpackNum);
            if (inputNum<=0){
                Utils.showCenterTomast("请输入商品数量");
                return;
            }

            if (dataListBean!=null){
                int stockNum = Integer.parseInt(dataListBean.getStockNum());
                if (stockNum < inputNum){
                    Utils.showCenterTomast("库存不足");
                    return;
                }

            }

            String num = unpackShopNum.getEditText();
            inputNum = Double.parseDouble(num);

            if (inputNum<=0){
                Utils.showCenterTomast("请输入拆解后的商品数量");
                return;
            }

            unpackPresenter.unpack(mainId,unpackGoodsId,unpackNum,goodsId,num);
        }catch (Exception e){
            e.printStackTrace();
            Utils.showCenterTomast("请输入正确的数量");
        }

    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"提交中...");
    }

    @Override
    public void successLoad(String data) {
        WaitDialog.dismiss();
        MessageDialog.show(this,"提示",data,"确定")
                .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        EventBus.getDefault().post(new RefreshInventoryEvent(0));
                        finish();
                        return false;
                    }
                });
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
