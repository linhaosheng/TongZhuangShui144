package pro.haichuang.tzs144.fragment;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.MainActivity;
import pro.haichuang.tzs144.activity.SaleOrderDetailActivity;
import pro.haichuang.tzs144.activity.SalesListActivity;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountRealTimeModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.TrendModel;
import pro.haichuang.tzs144.presenter.ClientRealTimeDatapPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 客户实时数据
 */
public class ClientRealTimeDataFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<AccountRealTimeModel.DataBean> {


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
    private TextView updateTime;
    private ClientRealTimeDatapPresenter clientRealTimeDatapPresenter;
    private List<TrendModel> trendModelList;


    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_client_real;
    }

    @Override
    protected void setUpView() {

        refresh.setOnRefreshListener(this);
        orderPaymentAdapter = new OrderPaymentAdapter();
        orderTrendAdapter = new OrderTrendAdapter();

        headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_update_time, null);
        orderTrendAdapter.addHeaderView(headView);
        updateTime = headView.findViewById(R.id.update_time);


        recycleDataTrend.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycleDataTrend.setAdapter(orderTrendAdapter);

        orderTrendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        recycleDataDetail.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recycleDataDetail.setAdapter(orderPaymentAdapter);

        orderPaymentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                String orderNumId = orderPaymentAdapter.getData().get(position).getId();
                Intent intent = new Intent(getActivity(), SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpData() {
        clientRealTimeDatapPresenter = new ClientRealTimeDatapPresenter(this);
        clientRealTimeDatapPresenter.ssManagerCount();
        clientRealTimeDatapPresenter.findSsOrders("2020-01-10", Utils.formatSelectTime(new Date()));
    }

    @Override
    public void onRefresh() {
        clientRealTimeDatapPresenter.ssManagerCount();
        clientRealTimeDatapPresenter.findSsOrders("2020-01-10", Utils.formatSelectTime(new Date()));
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
        if (event != null) {
            if (event.dataBean == null || event.dataBean.getData() == null || event.dataBean.getData().size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
                orderPaymentAdapter.setList(event.dataBean.getData());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        if (event != null) {
            if (event.type==4){
                if (event.status== Config.LOAD_SUCCESS){
                    Utils.showCenterTomast("结账成功");
                    billOrder.setVisibility(View.GONE);
                }else {
                    Utils.showCenterTomast("结账失败");
                }
            }else if (event.type==5){
                if (event.status== Config.LOAD_SUCCESS){
                    Utils.showCenterTomast("订单作废成功");
                }else {
                    Utils.showCenterTomast("订单作废失败");
                }
            }
        }
    }



    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(AccountRealTimeModel.DataBean data) {
        refresh.setRefreshing(false);
        if (trendModelList == null) {
            trendModelList = new ArrayList<>();
        }
        trendModelList.clear();
        TrendModel XlTrendModel = new TrendModel("销量", data.getXlVal(), data.getXlDayRatio(), data.getXlWeekRatio());
        trendModelList.add(XlTrendModel);

        TrendModel yxTrendModel = new TrendModel("有效订单", data.getYxddVal(), data.getYxddDayRatio(), data.getYxddWeekRatio());
        trendModelList.add(yxTrendModel);

        TrendModel ddTrendModel = new TrendModel("订单客户数", data.getDdKhDayVal(), data.getDdKhDayRatio(), data.getDdKhWeekRatio());
        trendModelList.add(ddTrendModel);

        TrendModel ysTrendModel = new TrendModel("应收收入", data.getYsVal(), data.getYsDayRatio(), data.getYsWeekRatio());
        trendModelList.add(ysTrendModel);

        TrendModel ssTrendModel = new TrendModel("实际收入", data.getSsVal(), data.getSsDayRatio(), data.getSsWeekRatio());
        trendModelList.add(ssTrendModel);

        TrendModel djTrendModel = new TrendModel("待结收入", data.getDjPriceVal(), data.getDjDayRatio(), data.getDjWeekRatio());
        trendModelList.add(djTrendModel);

        if (data.getTime() == null) {
            updateTime.setText("指标更新于" + Utils.transformTime(new Date()));
        } else {
            updateTime.setText("指标更新于" + data.getTime());
        }
        orderTrendAdapter.setList(trendModelList);

    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
        refresh.setRefreshing(false);
    }

    @OnClick({R.id.bill_order})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bill_order:
                Utils.showCenterTomast("正在结账");
                clientRealTimeDatapPresenter.settle();
                break;
        }
    }
}
