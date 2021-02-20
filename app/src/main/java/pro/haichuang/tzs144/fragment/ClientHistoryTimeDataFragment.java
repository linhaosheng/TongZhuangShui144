package pro.haichuang.tzs144.fragment;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.SaleOrderDetailActivity;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountHistoryModel;
import pro.haichuang.tzs144.model.ClientTypeSearchModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.TrendModel;
import pro.haichuang.tzs144.presenter.ClientHistoryTimeDatapPresenter;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.ClientFilterDialog;

/**
 * 客户历史数据
 */
public class ClientHistoryTimeDataFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener , ILoadDataView<AccountHistoryModel.DataBean> {


    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.recycle_data_trend)
    RecyclerView recycleDataTrend;
    @BindView(R.id.recycle_data_detail)
    RecyclerView recycleDataDetail;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.bill_order)
    TextView billOrder;

    private OrderPaymentAdapter orderPaymentAdapter;
    private OrderTrendAdapter orderTrendAdapter;


    private View headView;
    private TextView checkOutTime;
    private TextView filter;
    private ClientHistoryTimeDatapPresenter clientHistoryTimeDatapPresenter;
    private List<TrendModel>trendModelList;


    @Override
    public boolean lazyLoader() {
        return true;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_client_real;
    }

    @Override
    protected void setUpView() {
        billOrder.setVisibility(View.GONE);
        refresh.setOnRefreshListener(this);
        orderPaymentAdapter = new OrderPaymentAdapter();
        orderTrendAdapter  = new OrderTrendAdapter();

        headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_check_out_time,null);
        orderTrendAdapter.addHeaderView(headView);
        checkOutTime = headView.findViewById(R.id.check_out_time);
        checkOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        checkOutTime.setText(Utils.formatSelectTime(date));
                    }
                })
                        .build();
                pvTime.show();
            }
        });
        filter = headView.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientFilterDialog clientFilterDialog = new ClientFilterDialog(getActivity(), new ClientFilterDialog.ClientTypeListener() {
                    @Override
                    public void filterSearch(ClientTypeSearchModel clientTypeSearchModel) {

                    }
                });
                clientFilterDialog.show(getChildFragmentManager(),"");

            }
        });

        recycleDataTrend.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleDataTrend.setAdapter(orderTrendAdapter);

        orderTrendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String orderNumId = orderPaymentAdapter.getData().get(position).getId();
                Intent intent = new Intent(getActivity(), SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                startActivity(intent);
            }
        });

        recycleDataDetail.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleDataDetail.setAdapter(orderPaymentAdapter);

        orderPaymentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }

    @Override
    protected void setUpData() {
        clientHistoryTimeDatapPresenter = new ClientHistoryTimeDatapPresenter(this);
        clientHistoryTimeDatapPresenter.countLsOrder(Utils.transformTime(new Date()));
        clientHistoryTimeDatapPresenter.findLsOrders("","","");
    }

    @Override
    public void onRefresh() {
        clientHistoryTimeDatapPresenter.countLsOrder(Utils.transformTime(new Date()));
        clientHistoryTimeDatapPresenter.findLsOrders("","","");
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(AccountHistoryModel.DataBean data) {
        refresh.setRefreshing(false);
        if (trendModelList==null){
            trendModelList = new ArrayList<>();
        }
        trendModelList.clear();

        TrendModel XlTrendModel = new TrendModel("现金",data.getXjVal(),data.getXjDayRatio(),data.getXjWeekRatio());
        trendModelList.add(XlTrendModel);

        TrendModel yxTrendModel = new TrendModel("平台",data.getPtVal(),data.getPtDayRatio(),data.getPtWeekRatio());
        trendModelList.add(yxTrendModel);

        TrendModel ddTrendModel = new TrendModel("月结",data.getYjVal(),data.getYjDayRatio(),data.getYjWeekRatio());
        trendModelList.add(ddTrendModel);

        TrendModel ysTrendModel = new TrendModel("水票",data.getSpVal(),data.getSpDayRatio(),data.getSpWeekRatio());
        trendModelList.add(ysTrendModel);

        TrendModel ssTrendModel = new TrendModel("奖券",data.getJqVal(),data.getJqDayRatio(),data.getJqWeekRatio());
        trendModelList.add(ssTrendModel);

        orderTrendAdapter.setList(trendModelList);
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
    public void onMessageEvent(RealAccountEvent event) {
        if (event!=null){
            if (event.dataBean==null || event.dataBean.getData()==null || event.dataBean.getData().size()==0){
                emptyView.setVisibility(View.VISIBLE);
            }else {
                emptyView.setVisibility(View.GONE);
                orderPaymentAdapter.setList(event.dataBean.getData());
            }
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
