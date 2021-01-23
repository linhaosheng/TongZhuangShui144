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
import pro.haichuang.tzs144.adapter.SaleSearcAdapter;

/**
 * 客户销售搜索页面
 */
public class SaleSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private SaleSearcAdapter saleSearcAdapter;
    private List<String> listData;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sales_search;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        saleSearcAdapter = new SaleSearcAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(saleSearcAdapter);

    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0; i< 6; i++){
            listData.add("");
        }
        saleSearcAdapter.setList(listData);
    }



    @OnClick({R.id.back, R.id.tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.tips:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
