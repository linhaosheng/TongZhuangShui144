package pro.haichuang.tzs144.fragment;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.SaleOrderDetailActivity;
import pro.haichuang.tzs144.activity.SalesListActivity;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountHistoryModel;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.model.ClientTypeSearchModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.TrendModel;
import pro.haichuang.tzs144.presenter.ClientHistoryTimeDatapPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.ClientFilterDialog;
import pro.haichuang.tzs144.view.TimeDialog;

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
    @BindView(R.id.filter_view)
    LinearLayout filterView;

    private OrderPaymentAdapter orderPaymentAdapter;
    private OrderTrendAdapter orderTrendAdapter;

    private View headView;
    private TextView checkOutTime;
    private TextView filter;
    private String startTime;
    private String endTime;
    private String pickTime;
    private ClientHistoryTimeDatapPresenter clientHistoryTimeDatapPresenter;
    private List<TrendModel>trendModelList;

    private boolean lastPage;
    private int currentPage = 1;


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
        filterView.setVisibility(View.GONE);
        startTime = "2019-10-22";
        endTime = Utils.formatSelectTime(new Date());

        billOrder.setVisibility(View.GONE);
        refresh.setOnRefreshListener(this);
        orderPaymentAdapter = new OrderPaymentAdapter(0);
        orderTrendAdapter  = new OrderTrendAdapter();

        headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_check_out_time,null);
        orderTrendAdapter.addHeaderView(headView);

        orderPaymentAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        orderPaymentAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        orderPaymentAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage) {
                    currentPage++;
                    clientHistoryTimeDatapPresenter.findLsOrders(pickTime,startTime,endTime,currentPage);
                }
            }
        });

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                TimeDialog timeDialog = new TimeDialog(getActivity(), new TimeDialog.SelectTimeListener() {
                    @Override
                    public void selectTime(String mStartTime, String mEndTime) {
                        lastPage = false;
                        currentPage  =1;
                        startTime = mStartTime;
                        endTime = mEndTime;
                        pickTime = null;
                        clientHistoryTimeDatapPresenter.findLsOrders(pickTime,startTime,endTime,currentPage);
                        clientHistoryTimeDatapPresenter.countLsOrder(null,startTime,endTime);
                    }
                });
                timeDialog.show(getChildFragmentManager(), "");
            }
        });
        filter = headView.findViewById(R.id.filter);
        checkOutTime = headView.findViewById(R.id.check_out_time);
        checkOutTime.setText(Utils.formatSelectTime(new Date()));
        checkOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        lastPage = false;
                        currentPage=1;
                        checkOutTime.setText(Utils.formatSelectTime(date));
                        pickTime = checkOutTime.getText().toString();
                        clientHistoryTimeDatapPresenter.countLsOrder(pickTime,null,null);
                        startTime = null;
                        endTime = null;
                        clientHistoryTimeDatapPresenter.findLsOrders(pickTime,startTime,endTime,currentPage);
                    }
                })
                        .build();
                pvTime.show();
            }
        });


        recycleDataTrend.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleDataTrend.setAdapter(orderTrendAdapter);

        orderTrendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        recycleDataDetail.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleDataDetail.setAdapter(orderPaymentAdapter);

        orderPaymentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String orderNumId = orderPaymentAdapter.getData().get(position).getId() +"";
                Intent intent = new Intent(getActivity(), SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpData() {
        clientHistoryTimeDatapPresenter = new ClientHistoryTimeDatapPresenter(this);
        pickTime = checkOutTime.getText().toString();
        clientHistoryTimeDatapPresenter.countLsOrder(pickTime,null,null);
        clientHistoryTimeDatapPresenter.findLsOrders(pickTime,startTime,endTime,currentPage);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        lastPage = false;
        clientHistoryTimeDatapPresenter.countLsOrder(pickTime,null,null);
        clientHistoryTimeDatapPresenter.findLsOrders(pickTime,startTime,endTime,currentPage);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(AccountHistoryModel.DataBean data) {
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

        TrendModel coupondModel = new TrendModel("优惠券",data.getCouponPrice(),data.getCouponDayRatio(),data.getCouponWeekRatio());
        trendModelList.add(coupondModel);

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

        if (event==null && event.type!=2){
            return;
        }
        refresh.setRefreshing(false);

        if (event.dataBean == null || event.dataBean.getData() == null || event.dataBean.getData().size() == 0) {
            lastPage = true;
            if (currentPage == 1) {
                emptyView.setVisibility(View.VISIBLE);
            }else {
                emptyView.setVisibility(View.GONE);
            }
        }

        if (currentPage==1){
            if (event.dataBean!=null && event.dataBean.getData().size()<10){
                lastPage = true;
                orderPaymentAdapter.getLoadMoreModule().loadMoreEnd();
            }
            orderPaymentAdapter.setList(event.dataBean.getData());
        }else {
            orderPaymentAdapter.addData(event.dataBean.getData());
            orderPaymentAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (lastPage) {
            orderPaymentAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        currentPage=1;
        lastPage = false;
        if (event != null) {
            if (event.type==4){
                if (event.status== Config.LOAD_SUCCESS){
                    clientHistoryTimeDatapPresenter.countLsOrder(pickTime,null,null);
                    clientHistoryTimeDatapPresenter.findLsOrders(pickTime,startTime,endTime,currentPage);
                }
            }
        }
    }


    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
