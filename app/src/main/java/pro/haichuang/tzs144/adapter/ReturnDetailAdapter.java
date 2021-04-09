package pro.haichuang.tzs144.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ReturnDetailModel;

/**
 * 需求列表适配器
 */
public class ReturnDetailAdapter extends BaseQuickAdapter<ReturnDetailModel.DataBean, BaseViewHolder> {


    public ReturnDetailAdapter() {
        super(R.layout.item_return_detail);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReturnDetailModel.DataBean dataBean) {

        String oddNumber = "发水收桶单号："+dataBean.getOddNumber();
        String time = "          时间："+dataBean.getCreateTime();
        String name = "        出库人："+dataBean.getCreateName();
        String account = " 当日主账号："+dataBean.getMainUserName();
        String shopName = "           商品："+dataBean.getGoodsName();
        String shopNum = "     发水数量："+dataBean.getOutNum();
        if (dataBean.getMaterialList()!=null && dataBean.getMaterialList().size()>0){
            StringBuilder materBuild = new StringBuilder();
            StringBuilder materNumBuild = new StringBuilder();
            for (int i = 0; i<dataBean.getMaterialList().size() ;i++){
                ReturnDetailModel.DataBean.MaterialListBean materialListBean = dataBean.getMaterialList().get(i);
                if (i!=dataBean.getMaterialList().size()-1) {
                    materNumBuild.append(materialListBean.getNum()).append("\n");
                    materBuild.append(materialListBean.getName()).append("\n");
                }else {
                    materNumBuild.append(materialListBean.getNum());
                    materBuild.append(materialListBean.getName());
                }
            }
            String recycle = materBuild.toString();
            String recycleNum = materNumBuild.toString();

            baseViewHolder.setText(R.id.recycle_data,recycle)
                    .setText(R.id.recycle_num_data,recycleNum);

            baseViewHolder.getView(R.id.recycle_view).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.recycle_num_view).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.recycle_view).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.recycle_num_view).setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.order_num,oddNumber)
                .setText(R.id.time,time)
                .setText(R.id.name,name)
                .setText(R.id.account,account)
                .setText(R.id.shop_name,shopName)
                .setText(R.id.shop_num,shopNum);
    }
}
