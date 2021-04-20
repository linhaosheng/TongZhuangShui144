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
import pro.haichuang.tzs144.model.CustodyEvent;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.IncomeCountEvent;
import pro.haichuang.tzs144.model.RefreshCountEvent;
import pro.haichuang.tzs144.presenter.IncomeCountPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 收入情况
 */
public class IncomeCountFragment extends BaseFragment{

    @BindView(R.id.recycle_data)
    RecyclerView recycle_data;

    private CustodySituationAdapter custodySituationAdapter;

    private IncomeCountPresenter incomeCountPresenter;

    public IncomeCountFragment(){
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
        custodySituationAdapter = new CustodySituationAdapter(-1);
        recycle_data.setAdapter(custodySituationAdapter);

    }

    @Override
    protected void setUpData() {
        incomeCountPresenter = new IncomeCountPresenter();
        incomeCountPresenter.findSummarySr(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IncomeCountEvent event) {
        if (event!=null){
            CustodyModel custodyModel = event.custodyModel;
            if (event.status== Config.LOAD_FAIL){
                Utils.showCenterTomast(custodyModel.getMessage());
            }else {
                custodySituationAdapter.setList(custodyModel.getData().getDataList());
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
            incomeCountPresenter.findSummarySr(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
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
