package pro.haichuang.tzs144.activity;

import android.os.Bundle;
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
import pro.haichuang.tzs144.adapter.VoidWithDrawalOrderAdapter;


/**
 * 作废退押记录
 */
public class VoidWithDrawalOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


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
    LinearLayout emptyView;

    private VoidWithDrawalOrderAdapter voidWithDrawalOrderAdapter;
    private List<String>listData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_voil_with_drawal_order;
    }

    @Override
    protected void setUpView() {

        voidWithDrawalOrderAdapter = new VoidWithDrawalOrderAdapter();
        refresh.setOnRefreshListener(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(voidWithDrawalOrderAdapter);
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0; i<6;i++){
            listData.add("");
        }
        voidWithDrawalOrderAdapter.setList(listData);
    }



    @OnClick(R.id.back)
    public void onViewClicked() {
    }

    @Override
    public void onRefresh() {

    }
}
