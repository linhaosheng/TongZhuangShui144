package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * 取水还桶明细
 */
public class ReturnDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


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
    LinearLayout emptyView;

    private ReturnDetailAdapter returnDetailAdapter;
    private List<String>listData;

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
        listData = new ArrayList<>();
        listData.add("");
        listData.add("");
        listData.add("");
        returnDetailAdapter.setList(listData);
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
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        },2000);
    }
}
