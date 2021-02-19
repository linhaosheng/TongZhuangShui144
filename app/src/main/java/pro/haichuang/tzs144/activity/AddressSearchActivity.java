package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddressSearchAdapter;
import pro.haichuang.tzs144.model.AddressBean;
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

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_address_search;
    }

    @Override
    protected void setUpView() {
        title.setText("地址搜索");
        baiduMap = map.getMap();
        //显示卫星图层
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
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


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        map.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        map.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        map.onDestroy();
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
        }
    }
}
