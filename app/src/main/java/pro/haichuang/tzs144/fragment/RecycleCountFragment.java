package pro.haichuang.tzs144.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.CheckoutSummaryActivity;
import pro.haichuang.tzs144.adapter.CustodySituationAdapter;
import pro.haichuang.tzs144.adapter.RecycleCountAdapter;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.IncomeCountEvent;
import pro.haichuang.tzs144.model.RecycleCountEvent;
import pro.haichuang.tzs144.model.RecycleCountModel;
import pro.haichuang.tzs144.model.RefreshCountEvent;
import pro.haichuang.tzs144.presenter.IncomeCountPresenter;
import pro.haichuang.tzs144.presenter.RecycleCountPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 收入情况
 */
public class RecycleCountFragment extends BaseFragment{

    @BindView(R.id.recycle_data)
    RecyclerView recycle_data;

    private RecycleCountAdapter recycleCountAdapter;

    private RecycleCountPresenter recycleCountPresenter;

    public RecycleCountFragment(){
        super();
    }

    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_income_count;
    }

    @Override
    protected void setUpView() {
        recycle_data.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recycleCountAdapter = new RecycleCountAdapter(-1);
        recycle_data.setAdapter(recycleCountAdapter);

    }

    @Override
    protected void setUpData() {
        recycleCountPresenter = new RecycleCountPresenter();
        recycleCountPresenter.findSummaryFSSThsLb(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RecycleCountEvent event) {
        if (event!=null){
            RecycleCountModel recycleCountModel = event.recycleCountModel;
            if (event.status== Config.LOAD_FAIL){
                Utils.showCenterTomast(recycleCountModel.getMessage());
            }else {
                recycleCountAdapter.setList(recycleCountModel.getData().getDataList());
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
            recycleCountPresenter.findSummaryFSSThsLb(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
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
