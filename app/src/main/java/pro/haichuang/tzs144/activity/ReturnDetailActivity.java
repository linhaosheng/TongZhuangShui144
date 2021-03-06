package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.Date;
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
import pro.haichuang.tzs144.view.CustomPopup;

/**
 * 取水还桶明细
 */
public class ReturnDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<ReturnDetailModel.DataBean>>, CustomPopup.FilterInterface {


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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private ReturnDetailAdapter returnDetailAdapter;
    private ReturnDetailActivityPresenter returnDetailActivityPresenter;
    private String startTime;
    private String endTime;
    private String id = "1";
    private int currentPage=1;
    private boolean lastPage;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_return_detail;
    }

    @Override
    protected void setUpView() {
        startTime = "2019-06-22";
        endTime = Utils.formatSelectTime(new Date());
        refresh.setOnRefreshListener(this);
        returnDetailAdapter = new ReturnDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(returnDetailAdapter);
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
                returnDetailActivityPresenter.findQsstLogs(startTime,endTime,id,currentPage,searchEdit.getText().toString());
            }
            }
        });

        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(ReturnDetailActivity.this);
                }
                return false;
            }
        });

        returnDetailAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage) {
                    currentPage++;
                    returnDetailActivityPresenter.findQsstLogs(startTime,endTime,id,currentPage,searchEdit.getText().toString());
                }

            }
        });
        returnDetailAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        returnDetailAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }

    @Override
    protected void setUpData() {

        returnDetailActivityPresenter = new ReturnDetailActivityPresenter(this);
        returnDetailActivityPresenter.findQsstLogs(startTime,endTime,"",currentPage,"");
    }


    @OnClick({R.id.back, R.id.filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.filter:
                new XPopup.Builder(this)
                        .atView(searchEdit)
                        .asCustom(new CustomPopup(this,this))
                        .show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        lastPage = false;
        currentPage =1;
        returnDetailActivityPresenter.findQsstLogs(startTime,endTime,id,currentPage,searchEdit.getText().toString());
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<ReturnDetailModel.DataBean> data) {
        refresh.setRefreshing(false);
        if (data == null || data.size() == 0) {
            lastPage = true;
        }

        if (currentPage==1){
            if (data!=null && data.size()>0){
                emptyView.setVisibility(View.GONE);
            }else {
                emptyView.setVisibility(View.VISIBLE);
            }
        }

        if (currentPage == 1) {
            returnDetailAdapter.setList(data);
            if (data.size() < 10) {
                lastPage = true;
                returnDetailAdapter.getLoadMoreModule().loadMoreEnd();
            }
        } else {
            returnDetailAdapter.addData(data);
            returnDetailAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (lastPage) {
            returnDetailAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }

    @Override
    public void filter(String startTime, String endTime, String id) {
         this.startTime = startTime;
         this.endTime = endTime;
         this.id = id;
         currentPage =1;
         lastPage = false;
        returnDetailActivityPresenter.findQsstLogs(startTime,endTime,id,currentPage,searchEdit.getText().toString());
    }
}
