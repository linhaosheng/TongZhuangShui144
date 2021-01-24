package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AllocationRecordAdapter;
import pro.haichuang.tzs144.adapter.DemandRecordAdapter;

/**
 * 需求记录
 */
public class DemandRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


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
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    private DemandRecordAdapter demandRecordAdapter;
    private List<String> listData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_demand_record;
    }

    @Override
    protected void setUpView() {
      refresh.setOnRefreshListener(this);
        title.setText("需求记录");
        demandRecordAdapter = new DemandRecordAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(demandRecordAdapter);
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        listData.add("");
        listData.add("");
        listData.add("");
        demandRecordAdapter.setList(listData);
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        },2000);
    }
}
