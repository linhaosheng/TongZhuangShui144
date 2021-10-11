package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddressSearchAdapter;
import pro.haichuang.tzs144.model.AddressBean;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class AddressSearchActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.tip_img)
    ImageView tipImg;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_view)
    LinearLayout searchView;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.current_address)
    ImageView currentAddress;
    private PoiSearch poiSearch;
    private SuggestionSearch mSuggestionSearch;
    private BaiduMap baiduMap = null;
    private List<AddressBean> addressBeans;
    private AddressSearchAdapter addressSearchAdapter;
    private int selectPosition = -1;
    private String addressJson = null;
    private  ClientDetailModel.DataBean.AddressListBean addressListBean;
    private  double latitude;
    private double longitude;
    private BitmapDescriptor bitmap = null;
    private boolean isNeedUpdate = false;
    private LatLng ll;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_address_search;
    }

    @Override
    protected void setUpView() {
        title.setText("地址搜索");
        tips.setText("确定");
        tips.setTextSize(15);
        tips.setTextColor(Color.parseColor("#1AAD19"));
        baiduMap = map.getMap();
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setOnMapStatusChangeListener(listener);

        addressJson = getIntent().getStringExtra("addressJson");
        if (addressJson != null && !"".equals(addressJson)) {
            addressListBean = Utils.gsonInstane().fromJson(addressJson, ClientDetailModel.DataBean.AddressListBean.class);
        }

        poiSearch = PoiSearch.newInstance();
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.address_icon2);

        LatLng point;
        if (addressListBean!=null){
            latitude = Double.parseDouble(addressListBean.getLatitude());
            longitude = Double.parseDouble(addressListBean.getLongitude());
            point = new LatLng(latitude, longitude);
        }else {
            point = new LatLng(Config.LATITUDE, Config.LONGITUDE);
        }
        Bundle bundle = new Bundle();
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .clickable(true)
                .extraInfo(bundle)
                .icon(bitmap);
        baiduMap.addOverlay(option);
       // bitmap.recycle();

        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(AddressSearchActivity.this);
                }
                return false;
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
              if (searchEdit.getText()!=null && !searchEdit.getText().equals("")){
//                  mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
//                          .keyword("" + searchEdit.getText().toString())
//                          .city("广东")
//                          .citylimit(false));

                  try {
                      poiSearch.searchInCity(new PoiCitySearchOption()
                              .city(Config.CITY) //必填
                              .keyword(searchEdit.getText().toString()) //必填
                              .cityLimit(false)
                              .pageCapacity(30));
                  }catch (Exception e){
                      e.printStackTrace();
                  }
              }
            }
        });

        addressSearchAdapter = new AddressSearchAdapter(this,baiduMap);
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(addressSearchAdapter);

        addressSearchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                isNeedUpdate = false;
                tips.setVisibility(View.VISIBLE);
                selectPosition = position;
                baiduMap.clear();
                AddressBean addressBean = addressSearchAdapter.getData().get(position);

                MyLocationData locData = new MyLocationData.Builder()
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(addressBean.getLatitude())
                        .longitude(addressBean.getLongitude()).build();
                baiduMap.setMyLocationData(locData);

                LatLng ll = new LatLng(addressBean.getLatitude(), addressBean.getLongitude());
                OverlayOptions option = new MarkerOptions()
                        .position(ll)
                        .icon(bitmap);
                baiduMap.addOverlay(option);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(15.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                List<AddressBean> data = addressSearchAdapter.getData();
                List<AddressBean> tempData = new ArrayList<>();
                for (int i= 0;i<data.size();i++){
                    AddressBean addressBean1 = data.get(i);
                    if (i==position){
                        addressBean1.setSelect(true);
                    }else {
                        addressBean1.setSelect(false);
                    }
                    tempData.add(addressBean1);
                }
                addressSearchAdapter.setList(tempData);
            }
        });

        if (addressListBean!=null){
            ll = new LatLng(latitude, longitude);
        }else {
          ll = new LatLng(Config.LATITUDE, Config.LONGITUDE);
        }
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(12.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }


    @Override
    protected void setUpData() {
        addressBeans = new ArrayList<>();

        OnGetPoiSearchResultListener onListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                WaitDialog.dismiss();
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    addressSearchAdapter.setList(null);
                    return;
                }
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    addressBeans.clear();
                    List<PoiInfo> allPoi = poiResult.getAllPoi();

                    for (PoiInfo poiInfo : allPoi) {
                        if (poiInfo == null || poiInfo.getLocation() == null) {
                            continue;
                        }
                        AddressBean addressBean = new AddressBean();
                        addressBean.setAddress(poiInfo.getAddress());
                        StringBuffer sb = new StringBuffer();
                        sb.append(poiInfo.getCity()).append(poiInfo.getArea()).append(poiInfo.getName());
                        addressBean.setAddressInfo(sb.toString());
                        addressBean.setLatitude(poiInfo.getLocation().latitude);
                        addressBean.setLongitude(poiInfo.getLocation().longitude);
                        addressBeans.add(addressBean);
                    }
                    addressSearchAdapter.setList(addressBeans);

                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(onListener);

        mSuggestionSearch = SuggestionSearch.newInstance();
        //编写监听器
        initMarkerView();
    }


    private void initMarkerView() {
        MyLocationData locData = null;

        WaitDialog.show(this,"加载中...");
        if (addressJson!=null && !"".equals(addressJson)){
            try {
                double latitude = Double.parseDouble(addressListBean.getLatitude());
                double longitude = Double.parseDouble(addressListBean.getLongitude());
                locData = new MyLocationData.Builder()
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(latitude)
                        .longitude(longitude).build();
                
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        poiSearch.searchNearby(new PoiNearbySearchOption()
                                //搜索结果排序规则，PoiSortType.comprehensive->距离排序
                                .radius(10000)//检索半径范围，单位：米 100000
                                .keyword("小区&商铺&办公楼&广场")
                                .pageCapacity(30)
                                .location(new LatLng(latitude,longitude)));
                    }
                },1000);


            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            locData = new MyLocationData.Builder()
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(Config.LATITUDE)
                    .longitude(Config.LONGITUDE).build();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    poiSearch.searchNearby(new PoiNearbySearchOption()
                            //搜索结果排序规则，PoiSortType.comprehensive->距离排序
                            .radius(100000)//检索半径范围，单位：米
                            .keyword("小区&商铺&办公楼&广场")
                            .pageCapacity(30)
                            .location(new LatLng(Config.LATITUDE,Config.LONGITUDE)));
                }
            },1000);
        }
        baiduMap.setMyLocationData(locData);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };

    @OnClick({R.id.back,R.id.tips,R.id.current_address})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                AddressBean addressBean = addressSearchAdapter.getData().get(selectPosition);
                String addressJson = Utils.gsonInstane().toJson(addressBean);
                Intent intent = new Intent();
                intent.putExtra(Config.ADDRESS_JSON,addressJson);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.current_address:
                //定位到当前位置
                showCurrentPosition();
                break;
        }
    }

    /**
     * 定位到刚才进来的位置
     */
    private final void showCurrentPosition(){
        //定位到当前位置
        if (ll==null){
            return;
        }
        baiduMap.clear();
        OverlayOptions option = new MarkerOptions()
                .position(ll)
                .icon(bitmap);
        baiduMap.addOverlay(option);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                WaitDialog.show(AddressSearchActivity.this,"加载中...");
                poiSearch.searchNearby(new PoiNearbySearchOption()
                        //搜索结果排序规则，PoiSortType.comprehensive->距离排序
                        .radius(100000)//检索半径范围，单位：米
                        .keyword("小区&商铺&办公楼&广场")
                        .pageCapacity(30)
                        .location(ll));
            }
        },300);
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时必须调用mMapView. onResume ()
        map.onResume();
        super.onResume();
    }
    @Override
    protected void onPause() {
        //在activity执行onPause时必须调用mMapView. onPause ()
        map.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        if (bitmap!=null){
            bitmap.recycle();
            bitmap = null;
        }
        map.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        map = null;
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
        }
        if (poiSearch!=null){
            poiSearch.destroy();
        }
        super.onDestroy();
    }

    BaiduMap.OnMapStatusChangeListener listener = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {

            if (mapStatus == null && mapStatus.target==null){
                return;
            }
            if (!isNeedUpdate){
                isNeedUpdate = true;
                return;
            }
            baiduMap.clear();
            OverlayOptions option = new MarkerOptions()
                    .position(mapStatus.target)
                    .icon(bitmap);
            baiduMap.addOverlay(option);

            double latitude = mapStatus.target.latitude;
            double longitude = mapStatus.target.longitude;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WaitDialog.show(AddressSearchActivity.this,"加载中...");
                    poiSearch.searchNearby(new PoiNearbySearchOption()
                            //搜索结果排序规则，PoiSortType.comprehensive->距离排序
                            .radius(10000)//检索半径范围，单位：米
                            .keyword("小区&商铺&办公楼&广场")
                            .pageCapacity(30)
                            .location(new LatLng(latitude,longitude)));
                }
            },300);
        }
    };
}
