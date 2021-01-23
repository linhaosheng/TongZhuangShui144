package pro.haichuang.tzs144.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.OrderDetailActivity;
import pro.haichuang.tzs144.adapter.OrderInfoAdapter;
import pro.haichuang.tzs144.view.ShowMoreOrderInfoDialog;

/**
 * 订单信息页面
 */
public class OrderInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    TextView lastTime;
    TextView selectTime;


    private OrderInfoAdapter orderInfoAdapter;
    private List<String> datas;
    private View headTimeView;

    private int id;

    public OrderInfoFragment() {
        super();
    }

    public OrderInfoFragment(int mId) {
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
        }, 100);
        orderInfoAdapter = new OrderInfoAdapter(getActivity(), id);
        recycleData.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recycleData.setAdapter(orderInfoAdapter);
        orderInfoAdapter.addChildClickViewIds(R.id.order_detail_info);
        orderInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                startActivity(intent);
            }
        });
        orderInfoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int id = view.getId();
                if (id==R.id.order_detail_info){
                    ShowMoreOrderInfoDialog showMoreOrderInfoDialog = new ShowMoreOrderInfoDialog(getActivity());
                    showMoreOrderInfoDialog.show(getChildFragmentManager(),"");
                }
            }
        });

        if (id == 3) {
            headTimeView = LayoutInflater.from(getActivity()).inflate(R.layout.item_head_view, null);
            orderInfoAdapter.addHeaderView(headTimeView);
            lastTime = headTimeView.findViewById(R.id.last_time);
            selectTime = headTimeView.findViewById(R.id.select_time);
            selectTimeData();
        }

    }

    /**
     * 选择时间控件
     */
    private void selectTimeData(){
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ca = Calendar.getInstance();
                int  mYear = ca.get(Calendar.YEAR);
                int  mMonth = ca.get(Calendar.MONTH);
                int  mDay = ca.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectTime.setText(""+year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    protected void setUpData() {
        datas = new ArrayList<>();
        datas.add("" + 0);
        datas.add("" + 1);
        datas.add("" + 2);
        datas.add("" + 0);
        datas.add("" + 1);
        datas.add("" + 2);
        orderInfoAdapter.setList(datas);
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        }, 2000);
    }

}