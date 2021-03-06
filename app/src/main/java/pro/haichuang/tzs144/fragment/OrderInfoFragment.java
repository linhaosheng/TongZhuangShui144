package pro.haichuang.tzs144.fragment;

import android.app.Activity;
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
import androidx.appcompat.app.AppCompatActivity;
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
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
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
import pro.haichuang.tzs144.activity.FindGoodsActivity;
import pro.haichuang.tzs144.activity.OrderDetailActivity;
import pro.haichuang.tzs144.activity.SaleOrderDetailActivity;
import pro.haichuang.tzs144.activity.SaleSummaryActivity;
import pro.haichuang.tzs144.adapter.OrderInfoAdapter;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderInfoModel;
import pro.haichuang.tzs144.model.PageEvent;
import pro.haichuang.tzs144.model.ShopListModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.StatusUpdateEvent;
import pro.haichuang.tzs144.model.TypeListModel;
import pro.haichuang.tzs144.model.UpdateOrderEvent;
import pro.haichuang.tzs144.presenter.OrderInfoFragmentPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.MyMapView;
import pro.haichuang.tzs144.view.SelectShopDialog;
import pro.haichuang.tzs144.view.ShopDetailDialog;
import pro.haichuang.tzs144.view.ShowMoreOrderInfoDialog;

/**
 * ??????????????????
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
    TextView selectShop;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.myMapView)
    MyMapView myMapView;

    private OrderInfoAdapter orderInfoAdapter;
    private View headTimeView;
    private OrderInfoFragmentPresenter orderInfoFragmentPresenter;
    private BaiduMap baiduMap = null;

    private int id;

    private boolean lastPage;
    private int currentPage = 1;
    private boolean visibleToUser;

    private String startTime;
    private String endTime;
    private List<CharSequence> shopList;
    private TypeListModel typeListModel;
    private String goodsId;

    public OrderInfoFragment() {
        super();
    }

    public OrderInfoFragment(int mId) {
        this.id = mId;
    }

    @Override
    public boolean lazyLoader() {
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
                    if (id==4){
                        orderInfoFragmentPresenter.loadOrderByStatus(id, startTime,endTime, currentPage,goodsId);
                    }else {
                        orderInfoFragmentPresenter.loadOrderByStatus(id, null, currentPage,null);
                    }
                }

            }
        });
        orderInfoAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //??????????????????????????????????????????????????????????????????????????????????????????(?????????true)
        orderInfoAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        orderInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //????????????
                OrderInfoModel.DataBean dataBean = orderInfoAdapter.getData().get(position);

                /**
                 * 0-???????????? 1-???????????? 2-???????????? 3-???????????? 4-????????????
                 */
                if (dataBean.getOrderType()==0 || dataBean.getOrderType()==1){
                    Intent intent = new Intent(getActivity(), SaleOrderDetailActivity.class);
                    intent.putExtra("id",dataBean.getId());
                    startActivity(intent);
                    return;
                }

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
//                        WaitDialog.show(get,"?????????....");
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
            selectShop = headTimeView.findViewById(R.id.select_shop);
            selectTimeData();
            selectTime.setText(Utils.formatSelectTime(new Date()));

            /**
             * ????????????
             */
            String categoryListJson = SPUtils.getString(Config.GOODS_CATEGORY_LIST, "");
            if (!categoryListJson.equals("")) {
                shopList = new ArrayList<>();
                typeListModel = Utils.gsonInstane().fromJson(categoryListJson, TypeListModel.class);
                for (TypeListModel.DataBean dataBean : typeListModel.getData()) {
                    shopList.add(dataBean.getName());
                }
            }

        }
        Log.i(TAG, "----id" + id);
        if (id == 1) {
            myMapView.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.VISIBLE);
            baiduMap = mapView.getMap();
            baiduMap.setMyLocationEnabled(true);
            //??????????????????
            baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

            LatLng ll = new LatLng(Config.LATITUDE, Config.LONGITUDE);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(12.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //?????????????????????????????????
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String time = "????????????: " + marker.getExtraInfo().getString("time");
                    Utils.showCenterTomast(time);
                    return true;
                }
            });
        }else {
            myMapView.setVisibility(View.GONE);
            mapView.setVisibility(View.GONE);
        }
        initData();
    }

    private void initData(){
   //     startTime =  Utils.getPastDate(7,new Date());
   //     endTime = Utils.formatSelectTime(new Date());
        if (orderInfoFragmentPresenter==null){
            orderInfoFragmentPresenter = new OrderInfoFragmentPresenter(this);
        }
    }

    /**
     * ??????????????????
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
                startTime =  Utils.getPastDate(7,new Date());
                endTime = Utils.formatSelectTime(new Date());
                orderInfoFragmentPresenter.loadOrderByStatus(id, startTime,endTime, currentPage,goodsId);
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        startTime = selectTime.getText().toString();
                        endTime = startTime;
                        orderInfoFragmentPresenter.loadOrderByStatus(id, endTime, currentPage,goodsId);
                    }
                })
                        .build();
                pvTime.show();
            }
        });
        selectShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomMenu.show((AppCompatActivity) getActivity(), shopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        String categoryId = String.valueOf(typeListModel.getData().get(index).getId());
                        Intent intent = new Intent(getActivity(), FindGoodsActivity.class);
                        intent.putExtra("categoryId",categoryId);
                        startActivityForResult(intent,20000);
                    }
                });
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && refresh!=null){
            onRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        visibleToUser = true;
        onRefresh();
    }

    @Override
    protected void setUpData() {
        if (id!=4){
            orderInfoFragmentPresenter.loadOrderByStatus(id, null, currentPage,null);
        }else {
            orderInfoFragmentPresenter.loadOrderByStatus(id, endTime, currentPage,goodsId);
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        lastPage = false;
        if (id==4){
            orderInfoFragmentPresenter.loadOrderByStatus(id, startTime,endTime, currentPage,goodsId);
        }else {
            orderInfoFragmentPresenter.loadOrderByStatus(id, null, currentPage,null);
        }
    }

    @Override
    public void startLoad() {
        refresh.postDelayed(()->{
            refresh.setRefreshing(true);
        },200);
    }

    @Override
    public void successLoad(List<OrderInfoModel.DataBean> data) {
        refresh.postDelayed(()->{
            refresh.setRefreshing(false);
        },200);
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
                    onRefresh();
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
                        Utils.showCenterTomast("????????????...");
                        orderInfoFragmentPresenter.loadOrderByStatus(event.type, Utils.formatSelectTime(new Date()), 1,null);
                    }else {
                        Utils.showCenterTomast("????????????: "+event.result);
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
                        orderInfoFragmentPresenter.loadOrderByStatus(event.type, Utils.formatSelectTime(new Date()), 1,null);
                          EventBus.getDefault().post(new PageEvent(event.type));
                    }else {
                        Utils.showCenterTomast("????????????");
                        refresh.setRefreshing(false);
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

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && visibleToUser){
//            onRefresh();
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==20000 && resultCode== Activity.RESULT_OK){
            try {
                goodsId = String.valueOf(data.getIntExtra("goodsId",0));
                String goodName = data.getStringExtra("goodsName");
                selectShop.setText(goodName);
                orderInfoFragmentPresenter.loadOrderByStatus(id, startTime,endTime, currentPage,goodsId);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        visibleToUser = false;
    }
}
