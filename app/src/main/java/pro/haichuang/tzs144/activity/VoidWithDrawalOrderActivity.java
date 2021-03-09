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

import com.chad.library.adapter.base.listener.OnLoadMoreListener;

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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private VoidWithDrawalOrderAdapter voidWithDrawalOrderAdapter;
    private VoidWithDrawalOrderPresenter voidWithDrawalOrderPresenter;
    private int currentPage = 1;
    private boolean lastPage;


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

        voidWithDrawalOrderAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage){
                    currentPage++;
                    voidWithDrawalOrderPresenter.findCancelList(searchEdit.getText().toString(),currentPage);
                }
            }
        });
        voidWithDrawalOrderAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        voidWithDrawalOrderAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

    }

    @Override
    protected void setUpData() {
        currentPage = 1;
        voidWithDrawalOrderPresenter = new VoidWithDrawalOrderPresenter(this);
        voidWithDrawalOrderPresenter.findCancelList("",currentPage);
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
                    currentPage = 1;
                    lastPage = false;
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

        if (data==null || data.size()<10){
            lastPage = true;
        }
          if (currentPage==1){
              if (data==null || data.size()==0){
                  emptyView.setVisibility(View.VISIBLE);
              }else {
                  emptyView.setVisibility(View.GONE);
              }
              voidWithDrawalOrderAdapter.setList(data);
              if (data.size()<10){
                  voidWithDrawalOrderAdapter.getLoadMoreModule().loadMoreEnd();
              }
          }else {
              voidWithDrawalOrderAdapter.addData(data);
              voidWithDrawalOrderAdapter.getLoadMoreModule().loadMoreComplete();
          }
        if (lastPage){
            voidWithDrawalOrderAdapter.getLoadMoreModule().loadMoreEnd();
        }

    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
