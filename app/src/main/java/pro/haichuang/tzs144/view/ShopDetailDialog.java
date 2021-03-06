package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.ShopDetailAdapter;
import pro.haichuang.tzs144.model.OrderInfoModel;
import pro.haichuang.tzs144.model.ShopDetailModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.util.Utils;


/**
 * 添加商品
 */
public class ShopDetailDialog extends DialogFragment {

    @BindView(R.id.shop_num)
    TextView shopNum;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.pay_way)
    TextView payWay;

    private View view;
    private Context context;
    private ShopDetailAdapter shopDetailAdapter;
    private OrderInfoModel.DataBean dataBeans;
    private List<ShopDetailModel>shopDetailModelList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public ShopDetailDialog(Context mContext, OrderInfoModel.DataBean dataBeanList) {
        this.context = mContext;
        this.dataBeans = dataBeanList;
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
            view = inflater.inflate(R.layout.dialog_shop_detail, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        return view;
    }

    private void initView() {

        shopDetailAdapter = new ShopDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(shopDetailAdapter);

        shopDetailModelList = new ArrayList<>();
        for (int j = 0; j<dataBeans.getGoodsList().size();j++){
            OrderInfoModel.DataBean.GoodsListBean goodsListBean = dataBeans.getGoodsList().get(j);
            for (int i = 0; i<goodsListBean.getList().size();i++){
               ShopDetailModel shopDetailModel = new ShopDetailModel();
               OrderInfoModel.DataBean.GoodsListBean.ListBean listBean = goodsListBean.getList().get(i);
               if (i==0){
                   shopDetailModel.setTitle(dataBeans.getGoodsList().get(j).getCategory());
               }
                shopDetailModel.setImage_url(listBean.getGoodsImg());
                shopDetailModel.setPrice("¥"+listBean.getPrice());
                shopDetailModel.setName(listBean.getGoodsName());
                shopDetailModel.setUnit(listBean.getUnit());
                shopDetailModelList.add(shopDetailModel);
           }
        }

        shopDetailAdapter.setList(shopDetailModelList);
        shopNum.setText(dataBeans.getGoodsList().size()+"件商品");
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

    @OnClick(R.id.delete)
    public void onViewClicked() {
        dismiss();
    }


    public interface SelectShopListener {
        void selectShop(ShopModel.DataBean dataBean);
    }

}
