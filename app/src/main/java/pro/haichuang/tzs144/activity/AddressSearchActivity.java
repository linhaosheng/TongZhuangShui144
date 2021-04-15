package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

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

        addressJson = getIntent().getStringExtra("addressJson");
        if (addressJson != null && !"".equals(addressJson)) {
            addressListBean = Utils.gsonInstane().fromJson(addressJson, ClientDetailModel.DataBean.AddressListBean.class);
        }

        poiSearch = PoiSearch.newInstance();
        BitmapDescriptor bitmap = null;
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.address2);

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
        bitmap.recycle();

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
                tips.setVisibility(View.VISIBLE);
                Log.i(TAG,"onItemClick====");
                selectPosition = position;
                AddressBean addressBean = addressSearchAdapter.getData().get(position);

                MyLocationData locData = new MyLocationData.Builder()
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(addressBean.getLatitude())
                        .longitude(addressBean.getLongitude()).build();
                baiduMap.setMyLocationData(locData);

                LatLng ll = new LatLng(addressBean.getLatitude(), addressBean.getLongitude());
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

        LatLng ll;
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
                baiduMap.clear();
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
                String s = Utils.gsonInstane().toJson(poiDetailResult);
                Log.i("onGetSuggestionResult", "data num22222===" + s);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
                String s = Utils.gsonInstane().toJson(poiDetailSearchResult);
                Log.i("onGetSuggestionResult", "data num33333===" + s);
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(onListener);

        mSuggestionSearch = SuggestionSearch.newInstance();
        //编写监听器
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                //处理sug检索结果
                if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                    Log.i("result: ", "没有找到");
                    return;
                } else {
                    //获取在线建议检索结果，并显示到listview中
                    List<SuggestionResult.SuggestionInfo> resl = suggestionResult.getAllSuggestions();
                   // Log.i("onGetSuggestionResult", "data num===" + resl.toString());
                   // baiduMap.clear();
                    addressBeans.clear();
                    for (SuggestionResult.SuggestionInfo suggestionInfo : resl) {
                        //  Log.i("address====", suggestionInfo.key);
                        if (suggestionInfo == null || suggestionInfo.pt == null) {
                            continue;
                        }
                        AddressBean addressBean = new AddressBean();
                        addressBean.setAddress(suggestionInfo.key);
                        StringBuffer sb = new StringBuffer();
                        sb.append(suggestionInfo.city).append(suggestionInfo.district);
                        addressBean.setAddressInfo(sb.toString());
                        addressBean.setLatitude(suggestionInfo.pt.latitude);
                        addressBean.setLongitude(suggestionInfo.pt.longitude);
                        addressBeans.add(addressBean);
                    }
                    addressSearchAdapter.setList(addressBeans);

                }
            }
        };
        initMarkerView();
    }


    private void initMarkerView() {
        MyLocationData locData = null;

        if (addressJson!=null && !"".equals(addressJson)){
            try {
                double latitude = Double.parseDouble(addressListBean.getLatitude());
                double longitude = Double.parseDouble(addressListBean.getLongitude());
                locData = new MyLocationData.Builder()
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(latitude)
                        .longitude(longitude).build();

                Log.i("TAG===","latitude=="+latitude+"=====longitude"+longitude);

                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                        .include(new LatLng(latitude,longitude))
                        .include(new LatLng((latitude+0.01),(longitude+0.01))).build();
                PoiBoundSearchOption boundSearchOption = new PoiBoundSearchOption();
                boundSearchOption.bound(latLngBounds);
                boundSearchOption.keyword(addressListBean.getAddressName());
                poiSearch.searchInBound(boundSearchOption);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            locData = new MyLocationData.Builder()
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(Config.LATITUDE)
                    .longitude(Config.LONGITUDE).build();
        }
        baiduMap.setMyLocationData(locData);
    }

    @OnClick({R.id.back,R.id.tips})
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
        }
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
}
