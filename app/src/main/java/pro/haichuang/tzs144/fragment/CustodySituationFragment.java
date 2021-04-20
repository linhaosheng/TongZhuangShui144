package pro.haichuang.tzs144.fragment;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.CheckoutSummaryActivity;
import pro.haichuang.tzs144.adapter.CustodySituationAdapter;
import pro.haichuang.tzs144.adapter.DepositSituationAdapter;
import pro.haichuang.tzs144.model.CustodyEvent;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.DespositEvent;
import pro.haichuang.tzs144.model.RefreshCountEvent;
import pro.haichuang.tzs144.presenter.CustodySituationPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 开押情况
 */
public class CustodySituationFragment extends BaseFragment{

    @BindView(R.id.recycle_data)
    RecyclerView recycle_data;
    private int type;

    private DepositSituationAdapter depositSituationAdapter;

    private CustodySituationPresenter custodySituationPresenter;


    public CustodySituationFragment(int type){
        super();
        this.type = type;
    }

    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custody_situation;
    }

    @Override
    protected void setUpView() {
        recycle_data.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        depositSituationAdapter = new DepositSituationAdapter(type);
        recycle_data.setAdapter(depositSituationAdapter);
    }

    @Override
    protected void setUpData() {
       custodySituationPresenter = new CustodySituationPresenter();
       custodySituationPresenter.findSummaryKy(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CustodyEvent event) {
        if (event!=null){
            CustodyModel custodyModel = event.custodyModel;
            if (event.status== Config.LOAD_FAIL){
               Utils.showCenterTomast(custodyModel.getMessage());
           }else {
                depositSituationAdapter.setList(custodyModel.getData().getDataList());
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
            custodySituationPresenter.findSummaryKy(CheckoutSummaryActivity.scMainId,CheckoutSummaryActivity.type,CheckoutSummaryActivity.startTime,CheckoutSummaryActivity.endTime,CheckoutSummaryActivity.categoryId);
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
