package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import pro.haichuang.tzs144.adapter.DepositManagementSearchAdapter;
import pro.haichuang.tzs144.adapter.WithDrawalOrderAdapter;

/**
 * 退押订单列表
 */
public class WithDrawalOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.add_with_drawal)
    Button addWithDrawal;
    @BindView(R.id.historical_deposit)
    Button historicalDeposit;
    @BindView(R.id.detention_record)
    Button detentionRecord;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private WithDrawalOrderAdapter withDrawalOrderAdapter;
    private List<String> listData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_with_drawal_order;
    }

    @Override
    protected void setUpView() {
        withDrawalOrderAdapter = new WithDrawalOrderAdapter();
        refresh.setOnRefreshListener(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(withDrawalOrderAdapter);
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0;i<6;i++){
            listData.add("");
        }
        withDrawalOrderAdapter.setList(listData);
    }


    @OnClick({R.id.back, R.id.add_with_drawal, R.id.historical_deposit, R.id.detention_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_with_drawal:
                Intent intent = new Intent(this,AddWithDrawalOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.historical_deposit:
                Intent intent1 = new Intent(this,HistoryWithDrawalOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.detention_record:
                Intent intent2 = new Intent(this,VoidWithDrawalOrderActivity.class);
                startActivity(intent2);
                break;
        }
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
