package pro.haichuang.tzs144.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 客户历史数据
 */
public class ClientHistoryTimeDataFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.recycle_data_trend)
    RecyclerView recycleDataTrend;
    @BindView(R.id.recycle_data_detail)
    RecyclerView recycleDataDetail;

    private OrderPaymentAdapter orderPaymentAdapter;
    private OrderTrendAdapter orderTrendAdapter;

    private List<String> trendList;
    private List<String> orderPayList;
    private View headView;
    private TextView checkOutTime;
    private TextView filter;


    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_client_real;
    }

    @Override
    protected void setUpView() {

        refresh.setOnRefreshListener(this);
        orderPaymentAdapter = new OrderPaymentAdapter();
        orderTrendAdapter  = new OrderTrendAdapter();

        headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_check_out_time,null);
        orderTrendAdapter.addHeaderView(headView);
        checkOutTime = headView.findViewById(R.id.check_out_time);
        checkOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        checkOutTime.setText(Utils.formatSelectTime(date));
                    }
                })
                        .build();
                pvTime.show();
            }
        });
        filter = headView.findViewById(R.id.filter);

        recycleDataTrend.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleDataTrend.setAdapter(orderTrendAdapter);

        orderTrendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        recycleDataDetail.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleDataDetail.setAdapter(orderPaymentAdapter);

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
//        orderTrendAdapter.setList(trendList);
        orderPaymentAdapter.setList(orderPayList);
    }

    @Override
    public void onRefresh() {

    }
}
