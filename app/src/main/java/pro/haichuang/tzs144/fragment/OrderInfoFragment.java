package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.OrderDetailActivity;
import pro.haichuang.tzs144.adapter.OrderInfoAdapter;

/**
 * 订单信息页面
 */
public class OrderInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    private OrderInfoAdapter orderInfoAdapter;
    private List<String> datas;

    private String id;

    public OrderInfoFragment() {
        super();
    }

    public OrderInfoFragment(String mId) {
        this.id = mId;
    }

    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_order_info;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
   //             refresh.setRefreshing(true);
            }
        },100);
        orderInfoAdapter = new OrderInfoAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleData.setAdapter(orderInfoAdapter);

        orderInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void setUpData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        orderInfoAdapter.setList(datas);
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
