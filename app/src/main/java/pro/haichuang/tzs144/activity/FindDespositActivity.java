package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.FindDespositAdapter;
import pro.haichuang.tzs144.model.DepositModel;
import pro.haichuang.tzs144.model.DespositEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

/**
 * 查询押金编号
 */
public class FindDespositActivity extends BaseActivity implements OnRefreshListener{


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
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.empty_info)
    TextView emptyInfo;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private FindDespositAdapter findDespositAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_find_desposit;
    }

    @Override
    protected void setUpView() {
        title.setText("押金编号");
        refresh.setOnRefreshListener(this);
        findDespositAdapter = new FindDespositAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(findDespositAdapter);

        findDespositAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DepositModel.DataBean dataBean = findDespositAdapter.getData().get(position);
                EventBus.getDefault().post(new DespositEvent(dataBean.getYjNo()));
                finish();
            }
        });
    }

    @Override
    protected void setUpData() {

        searchEdit.setText("");
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (searchEdit.getText()!=null){
                   searchDesposit(searchEdit.getText().toString());
               }
            }
        });
    }


    private final void searchDesposit(String query){

        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEPOSIT_YJMOLIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                refresh.setRefreshing(true);
            }

            @Override
            public void success(String result) {
                refresh.setRefreshing(false);
                DepositModel depositModel = Utils.gsonInstane().fromJson(result, DepositModel.class);
                findDespositAdapter.setList(depositModel.getData());
                if (depositModel.getData()==null || depositModel.getData().size()==0){
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                }

            }

            @Override
            public void error(String error) {
                refresh.setRefreshing(false);
            }
        });

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        searchDesposit(searchEdit.getText().toString());
    }
}
