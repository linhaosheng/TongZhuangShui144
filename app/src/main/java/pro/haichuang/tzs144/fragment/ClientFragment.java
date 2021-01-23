package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AddClientActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.adapter.OrderNumTrendAdapter;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;

/**
 * 客户
 */
public class ClientFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.tip_img)
    ImageView tipImg;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.recycle_tend_income)
    RecyclerView recycleTendIncome;
    @BindView(R.id.recycle_client_list)
    RecyclerView recycleClientList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.left_text)
    TextView leftText;

    private OrderNumTrendAdapter orderPaymentAdapter;
    private OrderTrendAdapter orderTrendAdapter;

    private List<String> trendList;
    private List<String> orderPayList;


    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_client;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        back.setVisibility(View.GONE);
        leftText.setVisibility(View.VISIBLE);
        title.setText("客户列表");
        tipImg.setVisibility(View.VISIBLE);
        tipImg.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.mipmap.search));

        refresh.setOnRefreshListener(this);
        orderPaymentAdapter = new OrderNumTrendAdapter();
        orderTrendAdapter  = new OrderTrendAdapter();

        recycleTendIncome.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleTendIncome.setAdapter(orderTrendAdapter);

        orderTrendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        recycleClientList.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleClientList.setAdapter(orderPaymentAdapter);

        orderPaymentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }


    @Override
    protected void setUpData() {
        trendList = new ArrayList<>();
        orderPayList = new ArrayList<>();

        for (int i = 0;i<6;i++){
            trendList.add("");
            orderPayList.add("");
        }
        orderTrendAdapter.setList(trendList);
        orderPaymentAdapter.setList(orderPayList);
    }

    @OnClick({R.id.left_text, R.id.tip_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                Intent intent = new Intent(getActivity(), AddClientActivity.class);
                startActivity(intent);
                break;
            case R.id.tip_img:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
