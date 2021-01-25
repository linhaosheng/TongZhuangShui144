package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DepositManagementSearchAdapter;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.AddDepositDialog;

/**
 * 押金本搜索管理  押金本管理
 */
public class DepositManagementSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.open_deposit)
    Button openDeposit;
    @BindView(R.id.refund_orders)
    Button refundOrders;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;

    private DepositManagementSearchAdapter depositManagementSearchAdapter;
    private List<String> listData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_deposit_management;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        depositManagementSearchAdapter = new DepositManagementSearchAdapter();

        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(depositManagementSearchAdapter);
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listData.add("");
        }
        depositManagementSearchAdapter.setList(listData);
    }


    @OnClick({R.id.back, R.id.add_img, R.id.open_deposit, R.id.refund_orders,R.id.start_time, R.id.end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_img:
                AddDepositDialog addDepositDialog = new AddDepositDialog(this);
                addDepositDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.open_deposit:
                break;
            case R.id.refund_orders:
                break;
            case R.id.start_time:
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
                selectTime(SELECT_END_TIME);
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
       },1500);
    }

    private void selectTime(int type){

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (type==SELECT_START_TIME){
                    startTime.setText(Utils.formatSelectTime(date));
                }else {
                    endTime.setText(Utils.formatSelectTime(date));
                }
            }
        })
                .build();
        pvTime.show();
    }
}
