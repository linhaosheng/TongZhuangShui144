package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AddClientActivity;
import pro.haichuang.tzs144.activity.ClientDetailActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.adapter.OrderNumTrendAdapter;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientEvent;
import pro.haichuang.tzs144.model.ClientTrendModel;
import pro.haichuang.tzs144.model.ClientTypeSearchModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.model.TrendModel;
import pro.haichuang.tzs144.presenter.ClientFragmentPresenter;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.ClientFilterDialog;

/**
 * 客户
 */
public class ClientFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<ClientTrendModel> {


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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.search_view)
    LinearLayout searchView;
    @BindView(R.id.search_edit)
    EditText searchEdit;

    private OrderNumTrendAdapter orderPaymentAdapter;
    private OrderTrendAdapter orderTrendAdapter;

    private ClientFragmentPresenter clientFragmentPresenter;

    private List<TrendModel> trendList;
    private int currentPage = 1;
    private View headTimeView;
    private TextView updateTime;
    private String endTime;


    @Override
    public boolean lazyLoader() {
        return true;
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

        headTimeView = LayoutInflater.from(getActivity()).inflate(R.layout.item_update_time, null);
        orderTrendAdapter.addHeaderView(headTimeView);
        updateTime = headTimeView.findViewById(R.id.update_time);

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
                 String clientID = orderPaymentAdapter.getData().get(position).getId();
                 Intent intent = new Intent(getActivity(), ClientDetailActivity.class);
                 intent.putExtra("id",clientID);
                 startActivity(intent);
            }
        });
    }


    @Override
    protected void setUpData() {
        endTime = Utils.formatSelectTime(new Date());
        clientFragmentPresenter = new ClientFragmentPresenter(this);
        clientFragmentPresenter.countKh();
        clientFragmentPresenter.findKhList("","2019-10-10",endTime,"","0",currentPage);
        trendList = new ArrayList<>();
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
                    clientFragmentPresenter.findKhList(searchEdit.getText().toString(),"2019-10-10","2021-01-20","","0",1);
                }
            }
        });

    }

    @OnClick({R.id.left_text, R.id.tip_img,R.id.filter,R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                Intent intent = new Intent(getActivity(), AddClientActivity.class);
                startActivity(intent);
                break;
            case R.id.tip_img:
                searchView.setVisibility(View.VISIBLE);
                headView.setVisibility(View.GONE);
                break;
            case R.id.filter:
                ClientFilterDialog clientFilterDialog = new ClientFilterDialog(getActivity(), new ClientFilterDialog.ClientTypeListener() {
                    @Override
                    public void filterSearch(ClientTypeSearchModel clientTypeSearchModel) {

                    }
                });
                clientFilterDialog.show(getChildFragmentManager(),"");
                break;
            case R.id.cancel:
                searchView.setVisibility(View.GONE);
                headView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onRefresh() {
        clientFragmentPresenter.countKh();
        clientFragmentPresenter.findKhList("测试  ","2019-10-10","2021-01-20","","0",1);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(ClientTrendModel data) {
        refresh.setRefreshing(false);
        if (data!=null){
            trendList.clear();
            ClientTrendModel.DataBean data1 = data.getData();
            TrendModel countTrendModel = new TrendModel("总客户",String.valueOf(data1.getKhCount()),data1.getKhDayCount(),data1.getKhWeekCount());
            trendList.add(countTrendModel);

            TrendModel yxTrendModel = new TrendModel("有效户",String.valueOf(data1.getYxCount()),data1.getYxDayCount(),data1.getYxWeekCount());
            trendList.add(yxTrendModel);

            TrendModel whTrendModel = new TrendModel("维护户数",String.valueOf(data1.getWhCount()),data1.getWhDayCount(),data1.getWhWeekCount());
            trendList.add(whTrendModel);

            TrendModel jxsTrendModel = new TrendModel("经销商",String.valueOf(data1.getJxsCount()),data1.getJxsDayCount(),data1.getJxsWeekCount());
            trendList.add(jxsTrendModel);

            TrendModel xyTrendModel = new TrendModel("协议客户",String.valueOf(data1.getXyCount()),data1.getXyDayCount(),data1.getXyWeekCount());
            trendList.add(xyTrendModel);

            TrendModel zdTrendModel = new TrendModel("终端客户",String.valueOf(data1.getZdCount()),data1.getZdDayCount(),data1.getZdWeekCount());
            trendList.add(zdTrendModel);

            if (data.getTime() == null) {
                updateTime.setText("指标更新于" + Utils.transformTime(new Date()));
            } else {
                updateTime.setText("指标更新于" + data.getTime());
            }

            orderTrendAdapter.setList(trendList);
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ClientEvent event) {
        if (event!=null){
             if (event.dataBean==null){
                 Utils.showCenterTomast("获取客户列表失败");
                 emptyView.setVisibility(View.VISIBLE);
             }else {
                 if (event.dataBean.getData()!=null && event.dataBean.getData().size()>0){
                     emptyView.setVisibility(View.GONE);
                     orderPaymentAdapter.setList(event.dataBean.getData());
                 }else {
                     emptyView.setVisibility(View.VISIBLE);
                 }
             }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
