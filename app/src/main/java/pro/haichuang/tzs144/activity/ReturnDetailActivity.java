package pro.haichuang.tzs144.activity;

import android.os.Bundle;
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
import pro.haichuang.tzs144.adapter.ReturnDetailAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ReturnDetailModel;
import pro.haichuang.tzs144.presenter.ReturnDetailActivityPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 取水还桶明细
 */
public class ReturnDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<ReturnDetailModel.DataBean>> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.filter)
    TextView filter;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private ReturnDetailAdapter returnDetailAdapter;
    private ReturnDetailActivityPresenter returnDetailActivityPresenter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_return_detail;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        returnDetailAdapter = new ReturnDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(returnDetailAdapter);
    }

    @Override
    protected void setUpData() {
        returnDetailActivityPresenter = new ReturnDetailActivityPresenter(this);
        //returnDetailActivityPresenter.findQsstLogs();
    }



    @OnClick({R.id.back, R.id.filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.filter:

                break;
        }
    }

    @Override
    public void onRefresh() {
       // returnDetailActivityPresenter.findQsstLogs();
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<ReturnDetailModel.DataBean> data) {
        refresh.setRefreshing(false);
        returnDetailAdapter.setList(data);
        if (data!=null && data.size()>0){
            emptyView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
