package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DepositManagementSearchAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.DepositManagerModel;
import pro.haichuang.tzs144.presenter.DepositManagementSearchActivityPresenter;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.AddDepositDialog;

/**
 * 押金本搜索管理  押金本管理
 */
public class DepositManagementSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<DepositManagerModel.DataBean>> {


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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;
    private int currentPage = 1;

    private DepositManagementSearchAdapter depositManagementSearchAdapter;
    private DepositManagementSearchActivityPresenter depositManagementSearchActivityPresenter;

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
        endTime.setText(Utils.formatSelectTime(new Date()));

        depositManagementSearchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String id = depositManagementSearchAdapter.getData().get(position).getId() +"";
                Intent intent = new Intent(DepositManagementSearchActivity.this,DepositManagementDetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpData() {
        depositManagementSearchActivityPresenter = new DepositManagementSearchActivityPresenter(this);
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
                  depositManagementSearchActivityPresenter.findDepositBookList(searchEdit.getText().toString(),currentPage,startTime.getText().toString(),endTime.getText().toString());
              }
            }
        });
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
                //开押管理
                Intent intent = new Intent(this,StartDepositSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.refund_orders:
                //退押定单列表
                Intent intent1 = new Intent(this,WithDrawalOrderActivity.class);
                startActivity(intent1);
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
        depositManagementSearchActivityPresenter.findDepositBookList(searchEdit.getText().toString(),currentPage,startTime.getText().toString(),endTime.getText().toString());
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

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<DepositManagerModel.DataBean> data) {
        refresh.setRefreshing(false);
        depositManagementSearchAdapter.setList(data);
        if (data==null || data.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
       Utils.showCenterTomast(error);
    }
}
