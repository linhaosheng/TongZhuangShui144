package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
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
    private SuggestionSearch mSuggestionSearch;
    private BaiduMap baiduMap = null;
    private List<AddressBean> addressBeans;
    private AddressSearchAdapter addressSearchAdapter;
    private int selectPosition = -1;

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

        //显示卫星图层
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.address_icon);
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker
        ));

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
                  mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                          .keyword("" + searchEdit.getText().toString())
                          .city("广东")
                          .citylimit(false));
              }
            }
        });

        addressSearchAdapter = new AddressSearchAdapter(this);
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

        MyLocationData locData = new MyLocationData.Builder()
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(Config.LATITUDE)
                .longitude(Config.LONGITUDE).build();
        baiduMap.setMyLocationData(locData);

        LatLng ll = new LatLng(Config.LATITUDE, Config.LONGITUDE);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void setUpData() {
        addressBeans = new ArrayList<>();
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
        if (mSuggestionSearch != null) {
            mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        }
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
        super.onDestroy();
    }
}
