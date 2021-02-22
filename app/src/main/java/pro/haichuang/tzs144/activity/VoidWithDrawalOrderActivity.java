package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import pro.haichuang.tzs144.adapter.VoidWithDrawalOrderAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.presenter.VoidWithDrawalOrderPresenter;
import pro.haichuang.tzs144.util.Utils;


/**
 * 作废退押记录
 */
public class VoidWithDrawalOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<WithDrawalOrderModel.DataBean>> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private VoidWithDrawalOrderAdapter voidWithDrawalOrderAdapter;
    private VoidWithDrawalOrderPresenter voidWithDrawalOrderPresenter;
    private int currentPage = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_voil_with_drawal_order;
    }

    @Override
    protected void setUpView() {
        voidWithDrawalOrderAdapter = new VoidWithDrawalOrderAdapter(this);
        refresh.setOnRefreshListener(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(voidWithDrawalOrderAdapter);
    }

    @Override
    protected void setUpData() {
        voidWithDrawalOrderPresenter = new VoidWithDrawalOrderPresenter(this);
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
                    voidWithDrawalOrderPresenter.findCancelList(searchEdit.getText().toString(),currentPage);
                }
            }
        });
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

    @Override
    public void startLoad() {
       refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<WithDrawalOrderModel.DataBean> data) {
        refresh.setRefreshing(false);
          if (data==null || data.size()==0){
              emptyView.setVisibility(View.VISIBLE);
          }else {
              emptyView.setVisibility(View.GONE);
          }
        voidWithDrawalOrderAdapter.setList(data);
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
