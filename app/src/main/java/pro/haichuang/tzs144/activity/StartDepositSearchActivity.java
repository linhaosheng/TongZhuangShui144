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
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.StartDepositSearchAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.PageEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.presenter.StartDepositSearchActivityPresenter;
import pro.haichuang.tzs144.util.Config;
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
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(StartDepositSearchActivity.this);
                }
                return false;
            }
        });

        startDepositSearchAdapter.addChildClickViewIds(R.id.voil_txt);
        startDepositSearchAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.voil_txt:
                        WithDrawalOrderModel.DataBean dataBean = startDepositSearchAdapter.getData().get(position);
                        WaitDialog.show(StartDepositSearchActivity.this,"加载中...");
                        startDepositSearchActivityPresenter.returnDeposits(dataBean.getId());
                        break;
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
           if (event.status== Config.LOAD_SUCCESS){
               Utils.showCenterTomast("退押成功");
               startDepositSearchActivityPresenter.findDepositBookList(searchEdit.getText().toString(),1);
           }else {
               Utils.showCenterTomast("退押失败");
           }
        }
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

}
