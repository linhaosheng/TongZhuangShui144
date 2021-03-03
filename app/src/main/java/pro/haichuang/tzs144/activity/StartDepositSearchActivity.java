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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.StartDepositSearchAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.presenter.StartDepositSearchActivityPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 开押管理
 */
public class StartDepositSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<WithDrawalOrderModel.DataBean>> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText searchEdit;
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

    private StartDepositSearchActivityPresenter startDepositSearchActivityPresenter;
    private StartDepositSearchAdapter  startDepositSearchAdapter;
    private int currentPage = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_start_deposite_search;
    }

    @Override
    protected void setUpView() {
        startDepositSearchAdapter = new StartDepositSearchAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(startDepositSearchAdapter);
        refresh.setOnRefreshListener(this);
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
                    startDepositSearchActivityPresenter.findDepositBookList(searchEdit.getText().toString(),currentPage);
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        startDepositSearchActivityPresenter = new StartDepositSearchActivityPresenter(this);
        startDepositSearchActivityPresenter.findDepositBookList("",currentPage);
    }


    @OnClick({R.id.back, R.id.open_deposit, R.id.refund_orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.open_deposit:
                Intent intent = new Intent(this,SaleSearchActivity.class) ;
                intent.putExtra("type","open_deposit");
                startActivity(intent);
                break;
            case R.id.refund_orders:
                Intent intent2 = new Intent(this,SaleSearchActivity.class);
                intent2.putExtra("type","add_with_drawal");
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onRefresh() {
        startDepositSearchActivityPresenter.findDepositBookList(searchEdit.getText().toString(),currentPage);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<WithDrawalOrderModel.DataBean> dataBeans) {
        refresh.setRefreshing(false);
        if (dataBeans==null || dataBeans.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
        }
        startDepositSearchAdapter.setList(dataBeans);
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
        refresh.setRefreshing(false);
    }

}
