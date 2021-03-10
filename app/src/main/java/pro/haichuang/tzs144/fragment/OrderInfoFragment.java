package pro.haichuang.tzs144.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.DeliveryOrderActivity;
import pro.haichuang.tzs144.activity.OrderDetailActivity;
import pro.haichuang.tzs144.adapter.OrderInfoAdapter;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderInfoModel;
import pro.haichuang.tzs144.model.PageEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.StatusUpdateEvent;
import pro.haichuang.tzs144.model.UpdateOrderEvent;
import pro.haichuang.tzs144.presenter.OrderInfoFragmentPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.ShopDetailDialog;
import pro.haichuang.tzs144.view.ShowMoreOrderInfoDialog;

/**
 * 订单信息页面
 */
public class OrderInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<OrderInfoModel.DataBean>> {


    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.empty_data_view)
    RelativeLayout emptyDataView;
    TextView lastTime;
    TextView selectTime;
    @BindView(R.id.map)
    MapView mapView;

    private OrderInfoAdapter orderInfoAdapter;
    private View headTimeView;
    private OrderInfoFragmentPresenter orderInfoFragmentPresenter;
    private BaiduMap baiduMap = null;

    private int id;

    private boolean lastPage;
    private int currentPage = 1;
    private boolean lastOrder = true;

    public OrderInfoFragment() {
        super();
    }

    public OrderInfoFragment(int mId) {
        this.id = mId;
        Log.i("TAG==","id===="+mId);
    }

    @Override
    public boolean lazyLoader() {
//        if (id!=0){
//            return true;
//        }else {
//            return false;
//        }
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_order_info;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);

        orderInfoAdapter = new OrderInfoAdapter(getActivity(), id);
        recycleData.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recycleData.setAdapter(orderInfoAdapter);

        orderInfoAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage) {
                    currentPage++;
                    orderInfoFragmentPresenter.loadOrderByStatus(id, Utils.formatSelectTime(new Date()), currentPage);
                }

            }
        });
        orderInfoAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        orderInfoAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        orderInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //配送订单
                OrderInfoModel.DataBean dataBean = orderInfoAdapter.getData().get(position);
                String orderNumId = dataBean.getId();

                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("id", orderNumId);
                intent.putExtra("typeId", id);
                intent.putExtra("orderStatus", dataBean.getOrderStatus());
                startActivity(intent);
            }
        });
        orderInfoAdapter.addChildClickViewIds(R.id.call_phone, R.id.order_detail_info, R.id.order_detail_info, R.id.take_orders);
        orderInfoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int viewId = view.getId();
                if (viewId == R.id.order_detail_info) {
                    ShopDetailDialog shopDetailDialog = new ShopDetailDialog(getActivity(), orderInfoAdapter.getData().get(position));
                    shopDetailDialog.show(getChildFragmentManager(), "");
                } else if (viewId == R.id.call_phone) {
                    String customerPhone = orderInfoAdapter.getData().get(position).getCustomerPhone();
                    //Utils.callPhone(customerPhone);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + customerPhone);
                    intent.setData(data);
                    getActivity().startActivity(intent);
                } else if (viewId == R.id.take_orders) {
                    OrderInfoModel.DataBean dataBean = orderInfoAdapter.getData().get(position);
                    String orderNumId = dataBean.getId();
                    Log.i(TAG,"==id==="+id);
                    if (id == 1) {
                        Intent intent = new Intent(getActivity(), DeliveryOrderActivity.class);
                        intent.putExtra("id", orderNumId);
                        intent.putExtra("typeId", id);
                        intent.putExtra("orderStatus", dataBean.getOrderStatus());
                        startActivity(intent);
                    }else if (id==0){
                       // Utils.showCenterTomast();
//                        WaitDialog.show(get,"提交中....");
                       orderInfoFragmentPresenter.takeOrder(orderNumId,id);
                    }else if (id==3){
                        orderInfoFragmentPresenter.setIsKnow(orderNumId,id);
                    }
                }
            }
        });

        if (id == 4) {
            headTimeView = LayoutInflater.from(getActivity()).inflate(R.layout.item_head_view, null);
            orderInfoAdapter.addHeaderView(headTimeView);
            lastTime = headTimeView.findViewById(R.id.last_time);
            selectTime = headTimeView.findViewById(R.id.select_time);
            selectTimeData();
            selectTime.setText(Utils.formatSelectTime(new Date()));
        }
        Log.i(TAG, "----id" + id);
        if (id == 1) {
            mapView.setVisibility(View.VISIBLE);
            baiduMap = mapView.getMap();
            baiduMap.setMyLocationEnabled(true);
            //显示卫星图层
            baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

            LatLng ll = new LatLng(Config.LATITUDE, Config.LONGITUDE);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(12.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //送达时间，添加点击事件
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String time = "送达时间: " + marker.getExtraInfo().getString("time");
                    Utils.showCenterTomast(time);
                    return true;
                }
            });
        }else {
            mapView.setVisibility(View.GONE);
        }
    }

    /**
     * 选择时间控件
     */
    private void selectTimeData() {

        lastTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastTime.setTextColor(Color.parseColor("#32C5FF"));
                lastTime.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.set_bg_btn24));
                selectTime.setTextColor(Color.parseColor("#333333"));
                selectTime.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.set_bg_btn50));
                currentPage = 1;
                lastPage = false;
                lastOrder = true;
                orderInfoFragmentPresenter.loadOrderByStatus(id, null, currentPage);
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastOrder = false;
                selectTime.setTextColor(Color.parseColor("#32C5FF"));
                lastTime.setTextColor(Color.parseColor("#333333"));
                selectTime.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.set_bg_btn24));
                lastTime.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.set_bg_btn50));

                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        selectTime.setText(Utils.formatSelectTime(date));
                        currentPage = 1;
                        lastPage = false;
                        orderInfoFragmentPresenter.loadOrderByStatus(id, selectTime.getText().toString(), currentPage);
                    }
                })
                        .build();
                pvTime.show();

//                Calendar ca = Calendar.getInstance();
//                int mYear = ca.get(Calendar.YEAR);
//                int mMonth = ca.get(Calendar.MONTH);
//                int mDay = ca.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        selectTime.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth);
//                        orderInfoFragmentPresenter.loadOrderByStatus(id, selectTime.getText().toString(), 1);
//                    }
//                }, mYear, mMonth, mDay);
//                datePickerDialog.show();
            }
        });
    }

    @Override
    protected void setUpData() {
        orderInfoFragmentPresenter = new OrderInfoFragmentPresenter(this);
        orderInfoFragmentPresenter.loadOrderByStatus(id, null, currentPage);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        lastPage = false;
        if (lastOrder){
            orderInfoFragmentPresenter.loadOrderByStatus(id, null, currentPage);
        }else {
            orderInfoFragmentPresenter.loadOrderByStatus(id, Utils.formatSelectTime(new Date()), currentPage);
        }
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<OrderInfoModel.DataBean> data) {
        refresh.setRefreshing(false);
        if (data == null || data.size() == 0) {
            lastPage = true;
        }
        if (currentPage == 1) {
            if (data != null && data.size() > 0) {
                if (id==1){
                    emptyDataView.setVisibility(View.GONE);
                }else {
                    emptyView.setVisibility(View.GONE);
                }
            } else {
                if (id==1){
                    emptyDataView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
            orderInfoAdapter.setList(data);
            if (data.size() < 10) {
                lastPage = true;
                orderInfoAdapter.getLoadMoreModule().loadMoreEnd();
            }
        } else {
            orderInfoAdapter.addData(data);
            orderInfoAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (lastPage) {
            orderInfoAdapter.getLoadMoreModule().loadMoreEnd();
        }

        if (id == 1) {
            List<OrderInfoModel.DataBean> data1 = orderInfoAdapter.getData();
            baiduMap.clear();
            showAddressIcon();
            List<InfoWindow>list = new ArrayList<>();

            for (OrderInfoModel.DataBean dataBean : data1) {
                //Log.i(TAG,"data==="+dataBean.getLatitude()+"======long===="+dataBean.getLatitude());
                LatLng point = new LatLng(dataBean.getLatitude(), dataBean.getLongitude());
                Button button = new Button(getActivity());
                button.setBackground(ContextCompat.getDrawable(getActivity(),R.mipmap.time_bg));
                button.setText(dataBean.getTimeRange());
                InfoWindow  infoWindow = new InfoWindow(button,point,0);
                list.add(infoWindow);
            }
            baiduMap.showInfoWindows(list);
        }
    }

    private void showAddressIcon() {
        BitmapDescriptor bitmap = null;
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.address2);

        LatLng point = new LatLng(Config.LATITUDE, Config.LONGITUDE);

        Bundle bundle = new Bundle();
        // bundle.putString(Config.CHARGE_SERIAL_NUMBER,chargeData.getS());
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .clickable(true)
                .extraInfo(bundle)
                .icon(bitmap);
        baiduMap.addOverlay(option);
        bitmap.recycle();
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdateOrderEvent event) {
        if (event != null) {
            try {
                int currentId = Integer.parseInt(event.id);
                if (currentId == id) {
                    orderInfoFragmentPresenter.loadOrderByStatus(currentId, Utils.formatSelectTime(new Date()), 1);
                }
                Log.i(TAG, "onMessageEvent==id=" + event.id + " === id===" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        if (event != null) {
            try {
                if (event.type==id){
                    if (event.status==Config.LOAD_SUCCESS){
                        Utils.showCenterTomast("接单成功...");
                        orderInfoFragmentPresenter.loadOrderByStatus(event.type, Utils.formatSelectTime(new Date()), 1);
                    }else {
                        Utils.showCenterTomast("接单失败...");
                    }
                }
                Log.i(TAG, "onMessageEvent==id=" + event.type + " === id===" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusUpdateEvent event) {
        if (event != null) {
            try {
                if (event.type==id){
                    if (event.status==Config.LOAD_SUCCESS){
                        orderInfoFragmentPresenter.loadOrderByStatus(event.type, Utils.formatSelectTime(new Date()), 1);
                          EventBus.getDefault().post(new PageEvent(event.type));
                    }else {
                    }
                }
                Log.i(TAG, "onMessageEvent==id=" + event.type + " === id===" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
