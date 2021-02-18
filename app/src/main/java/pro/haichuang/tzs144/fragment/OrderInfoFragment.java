package pro.haichuang.tzs144.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.DeliveryOrderActivity;
import pro.haichuang.tzs144.activity.OrderDetailActivity;
import pro.haichuang.tzs144.adapter.OrderInfoAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderInfoModel;
import pro.haichuang.tzs144.presenter.OrderInfoFragmentPresenter;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.ShowMoreOrderInfoDialog;

/**
 * 订单信息页面
 */
public class OrderInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<OrderInfoModel.DataBean>> {


    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    TextView lastTime;
    TextView selectTime;

    private OrderInfoAdapter orderInfoAdapter;
    private View headTimeView;
    private OrderInfoFragmentPresenter orderInfoFragmentPresenter;

    private int id;
    private int currentPage = 1;

    public OrderInfoFragment() {
        super();
    }

    public OrderInfoFragment(int mId) {
        this.id = mId;
    }

    @Override
    public boolean lazyLoader() {
        if (id!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_order_info;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);

        orderInfoAdapter = new OrderInfoAdapter(getActivity(), id);
        recycleData.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recycleData.setAdapter(orderInfoAdapter);
        orderInfoAdapter.addChildClickViewIds(R.id.order_detail_info);
        orderInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //配送订单
                String orderNumId = orderInfoAdapter.getData().get(position).getId();
                if (id==1){
                    Intent intent = new Intent(getActivity(), DeliveryOrderActivity.class);
                    intent.putExtra("id",orderNumId);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("id",orderNumId);
                    startActivity(intent);
                }
            }
        });
        orderInfoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int id = view.getId();
                if (id==R.id.order_detail_info){
                    ShowMoreOrderInfoDialog showMoreOrderInfoDialog = new ShowMoreOrderInfoDialog(getActivity());
                    showMoreOrderInfoDialog.show(getChildFragmentManager(),"");
                }else if (id==R.id.call_phone){
                    String customerPhone = orderInfoAdapter.getData().get(position).getCustomerPhone();
                    Utils.callPhone(customerPhone);
                }
            }
        });

        if (id == 4) {
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
        orderInfoFragmentPresenter = new OrderInfoFragmentPresenter(this);
        orderInfoFragmentPresenter.loadOrderByStatus(id, Utils.formatSelectTime(new Date()),currentPage);
    }

    @Override
    public void onRefresh() {
        orderInfoFragmentPresenter.loadOrderByStatus(id, Utils.formatSelectTime(new Date()),currentPage);
    }

    @Override
    public void startLoad() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
            }
        },50);
//       refresh.postDelayed(new Runnable() {
//           @Override
//           public void run() {
//               refresh.setRefreshing(true);
//           }
//       },100);
    }

    @Override
    public void successLoad(List<OrderInfoModel.DataBean> data) {
        refresh.setRefreshing(false);
        if (data!=null && data.size()>0){
            orderInfoAdapter.setList(data);
            emptyView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);

    }
}
