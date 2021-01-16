package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.SaleListItemAdapter;

/**
 * 直接销售列表
 */
public class SalesListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tip_img)
    ImageView tipImg;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    private SaleListItemAdapter saleListItemAdapter;
    private List<String> datas;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sale_list;
    }

    @Override
    protected void setUpView() {
        title.setText("直接销售列表");
        title.setTextColor(ContextCompat.getColor(this,R.color.blank2));

        tipImg.setVisibility(View.VISIBLE);
        tipImg.setBackgroundResource(R.mipmap.tab);
        refresh.setOnRefreshListener(this);

        saleListItemAdapter = new SaleListItemAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(saleListItemAdapter);

    }

    @Override
    protected void setUpData() {
        datas = new ArrayList<>();
        for (int i = 0;i<5; i++){
            datas.add("");
        }
        saleListItemAdapter.setList(datas);

    }


    @OnClick({R.id.back, R.id.tip_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tip_img:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
