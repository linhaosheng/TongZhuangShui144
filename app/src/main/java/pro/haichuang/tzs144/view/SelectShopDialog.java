package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddShopDialogAdapter;
import pro.haichuang.tzs144.adapter.ShopDialogAdapter;
import pro.haichuang.tzs144.model.ShopListModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;


/**
 * 添加押金本
 */
public class SelectShopDialog extends DialogFragment {


    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    private ShopDialogAdapter shopDialogAdapter;
    private View view;
    private Context context;
    private SelectShopListener selectShopListener;
    private int selectShopPosition = -1;
    private String defaultCategory = "桶装水,瓶装水";
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public SelectShopDialog(Context mContext,int type, SelectShopListener selectShopListener) {
        this.context = mContext;
        this.selectShopListener = selectShopListener;
        this.type = type;
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
            view = inflater.inflate(R.layout.dialog_add_shop, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        return view;
    }

    private void initView() {
        shopDialogAdapter = new ShopDialogAdapter(context);
        recycleData.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(shopDialogAdapter);
        findGoodsList(Config.CURRENT_MAIN_ID,defaultCategory);

        shopDialogAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ShopListModel.DataBean.DataListBean dataListBean = shopDialogAdapter.getData().get(position);
                List<ShopListModel.DataBean.DataListBean> data = shopDialogAdapter.getData();

                if (dataListBean.isCheck()){
                    dataListBean.setCheck(false);
                    shopDialogAdapter.setData(position,dataListBean);
                    selectShopPosition = -1;
                }else {
                    List<ShopListModel.DataBean.DataListBean> tempData = new ArrayList<>();
                    for (ShopListModel.DataBean.DataListBean dataBean1 : data){
                        dataBean1.setCheck(false);
                        tempData.add(dataBean1);
                    }
                    dataListBean.setCheck(true);
                    tempData.set(position,dataListBean);
                    selectShopPosition = position;
                    shopDialogAdapter.setList(tempData);
                }
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             if (searchEdit.getText()!=null){
                 if ("".equals(searchEdit.getText().toString().trim())){
                     findGoodsList(Config.CURRENT_MAIN_ID,defaultCategory);
                 }else {
                     findGoodsList(Config.CURRENT_MAIN_ID,searchEdit.getText().toString());
                 }
             }
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    @OnClick({R.id.cancel_btn, R.id.input_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.input_btn:
                if (selectShopPosition==-1){
                    Utils.showCenterTomast("请选择商品");
                    return;
                }
                if (selectShopListener!=null){
                    selectShopListener.selectShop(shopDialogAdapter.getData().get(selectShopPosition));
                }
                dismiss();
                break;
        }
    }

    /**
     * [实时库存]获取商品品类
     */
    public void findGoodsList(String mainId,String categoryNames){

        Map<String,Object>params = new ArrayMap<>();
        params.put("mainId",mainId);
        params.put("isGoods","1");
        params.put("page","0");
        params.put("limit","10");
        params.put("categoryNames",categoryNames);
        if (type==0){
            params.put("unintNames","箱,包,件");
        }else {
            params.put("unintNames","瓶");
        }

        HttpRequestEngine.postRequest(ConfigUrl.FIND_GOODS_LIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    ShopListModel shopListModel = Utils.gsonInstane().fromJson(result, ShopListModel.class);
                    if (shopListModel.getResult()==1){
                        List<ShopListModel.DataBean.DataListBean> dataList = shopListModel.getData().getDataList();
                        List<ShopListModel.DataBean.DataListBean> tempDataList = new ArrayList<>();
                        for (ShopListModel.DataBean.DataListBean dataListBean : dataList){
                            if ("1".equals(dataListBean.getGoodsStatus())){
                                tempDataList.add(dataListBean);
                            }
                        }
                       // shopListModel.getData().setDataList(tempDataList);
                        shopDialogAdapter.setList(tempDataList);

                      //  SPUtils.putString(Config.FIND_GOODS_LIST,Utils.gsonInstane().toJson(shopListModel));
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

    public interface  SelectShopListener{
        void selectShop(ShopListModel.DataBean.DataListBean dataBean);
    }

}
