package pro.haichuang.tzs144.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.CheckoutSummaryActivity;
import pro.haichuang.tzs144.adapter.SaleAdapter;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.IncomeCountEvent;
import pro.haichuang.tzs144.model.RefreshCountEvent;
import pro.haichuang.tzs144.model.SaleCountEvent;
import pro.haichuang.tzs144.model.SaleModel;
import pro.haichuang.tzs144.presenter.SalesPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 销售情况
 */
public class SalesFragment extends BaseFragment{

    @BindView(R.id.recycle_shop_data)
    RecyclerView recycle_shop_data;

    @BindView(R.id.recycle_material_data)
    RecyclerView recycle_material_data;

    private SaleAdapter shopSaleAdapter;
    private SaleAdapter materialSaleAdapter;
    private SalesPresenter salesPresenter;

    public SalesFragment(){
        super();
    }

    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_sale;
    }


    @Override
    protected void setUpView() {
        recycle_shop_data.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recycle_material_data.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        shopSaleAdapter = new SaleAdapter(0);
        recycle_shop_data.setAdapter(shopSaleAdapter);
        materialSaleAdapter = new SaleAdapter(1);
        recycle_material_data.setAdapter(materialSaleAdapter);
    }

    @Override
    protected void setUpData() {
        salesPresenter = new SalesPresenter();
        salesPresenter.findSummarySaleHt(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
        salesPresenter.findSummarySaleQs(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SaleCountEvent event) {
        if (event!=null){
            SaleModel saleModel = event.saleModel;
            if (event.status== Config.LOAD_FAIL){
                Utils.showCenterTomast(saleModel.getMessage());
            }else {
                if (event.type==0){
                    shopSaleAdapter.setList(saleModel.getData().getDataList());
                }else if (event.type==1){
                    materialSaleAdapter.setList(saleModel.getData().getDataList());
                }
            }
        }
    }


    /**
     * 刷新数据
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshCountEvent event) {
        if (event!=null){
            salesPresenter.findSummarySaleHt(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
            salesPresenter.findSummarySaleQs(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
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
