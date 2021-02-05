package pro.haichuang.tzs144.activity;


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

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.presenter.StartDepositSearchActivityPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 开押管理
 */
public class StartDepositSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<String> {


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
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private StartDepositSearchActivityPresenter startDepositSearchActivityPresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_start_deposite_search;
    }

    @Override
    protected void setUpView() {
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
                startDepositSearchActivityPresenter.findDepositBookList(searchEdit.getText().toString());
            }
        });
    }

    @Override
    protected void setUpData() {
        startDepositSearchActivityPresenter = new StartDepositSearchActivityPresenter(this);
    }


    @OnClick({R.id.back, R.id.open_deposit, R.id.refund_orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.open_deposit:
                break;
            case R.id.refund_orders:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(String data) {
        refresh.setRefreshing(false);
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
        refresh.setRefreshing(false);
    }

}
