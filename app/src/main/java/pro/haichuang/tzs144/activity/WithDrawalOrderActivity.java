package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DepositManagementSearchAdapter;
import pro.haichuang.tzs144.adapter.WithDrawalOrderAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.PageEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.presenter.WithDrawalOrderPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 退押订单列表
 */
public class WithDrawalOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener , ILoadDataView<List<WithDrawalOrderModel.DataBean>> {


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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private WithDrawalOrderAdapter withDrawalOrderAdapter;
    private WithDrawalOrderPresenter withDrawalOrderPresenter;
    private int page = 1;
    private boolean lastPage;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_with_drawal_order;
    }

    @Override
    protected void setUpView() {
        withDrawalOrderAdapter = new WithDrawalOrderAdapter(this);
        refresh.setOnRefreshListener(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(withDrawalOrderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        lastPage = false;
        withDrawalOrderPresenter.findReturnDeposits(searchEdit.getText().toString(),page);
    }

    @Override
    protected void setUpData() {
        withDrawalOrderPresenter = new WithDrawalOrderPresenter(this);
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
                 page=1;
                 lastPage = false;
                 withDrawalOrderPresenter.findReturnDeposits(searchEdit.getText().toString(),page);
             }
            }
        });

        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(WithDrawalOrderActivity.this);
                }
                return false;
            }
        });

        withDrawalOrderAdapter.addChildClickViewIds(R.id.voil_txt);
        withDrawalOrderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.voil_txt:
                        WaitDialog.show(WithDrawalOrderActivity.this,"加载中");
                        WithDrawalOrderModel.DataBean dataBean = withDrawalOrderAdapter.getData().get(position);
                        withDrawalOrderPresenter.cancelReturnDeposit(dataBean.getId()+"");
                        break;
                }
            }
        });

        withDrawalOrderAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage){
                    page++;
                    withDrawalOrderPresenter.findReturnDeposits(searchEdit.getText().toString(),page);
                }
            }
        });
        withDrawalOrderAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        withDrawalOrderAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }


    @OnClick({R.id.back, R.id.add_with_drawal, R.id.historical_deposit, R.id.detention_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_with_drawal:
                Intent intent = new Intent(this,AddWithDrawalOrderActivity.class);
               // intent.putExtra("type","add_with_drawal");
                startActivity(intent);
                break;
            case R.id.historical_deposit:
                Intent intent2 = new Intent(this,SaleSearchActivity.class);
                intent2.putExtra("type","historical_deposit");
                startActivity(intent2);

                break;
            case R.id.detention_record:
                Intent intent3 = new Intent(this,VoidWithDrawalOrderActivity.class);
                startActivity(intent3);
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

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<WithDrawalOrderModel.DataBean> data) {
        refresh.setRefreshing(false);
        if (data==null || data.size()==0){
            lastPage = true;
        }

        if (page==1){
            if (data != null && data.size() > 0) {
                emptyView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
            withDrawalOrderAdapter.setList(data);
            if (data.size()<10){
                lastPage = true;
                withDrawalOrderAdapter.getLoadMoreModule().loadMoreEnd();
            }
        }else {
            withDrawalOrderAdapter.addData(data);
            withDrawalOrderAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (lastPage){
            withDrawalOrderAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.status==Config.LOAD_SUCCESS && event.type==20){

                Utils.showCenterTomast("作废成功");
                page = 1;
                lastPage = false;
                withDrawalOrderPresenter.findReturnDeposits(searchEdit.getText().toString(),page);

            }else if (event.status==Config.LOAD_FAIL && event.type==20){
                Utils.showCenterTomast("作废失败 : "+event.result);
            }
        }
    }
}
