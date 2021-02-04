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
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientTrendModel;
import pro.haichuang.tzs144.model.ClientTypeSearchModel;
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
    @BindView(R.id.filter)
    ImageView filter;

    private OrderNumTrendAdapter orderPaymentAdapter;
    private OrderTrendAdapter orderTrendAdapter;

    private ClientFragmentPresenter clientFragmentPresenter;

    private List<TrendModel> trendList;
    private List<String> orderPayList;
    private int currentPage = 1;


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
        clientFragmentPresenter = new ClientFragmentPresenter(this);
        clientFragmentPresenter.countKh();
        clientFragmentPresenter.findKhList("","2019-10-10","2021-01-20","","0",currentPage);


        trendList = new ArrayList<>();
        orderPayList = new ArrayList<>();

        for (int i = 0;i<6;i++){

            orderPayList.add("");
        }
        orderPaymentAdapter.setList(orderPayList);
    }

    @OnClick({R.id.left_text, R.id.tip_img,R.id.filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                Intent intent = new Intent(getActivity(), AddClientActivity.class);
                startActivity(intent);
                break;
            case R.id.tip_img:
                break;
            case R.id.filter:
                ClientFilterDialog clientFilterDialog = new ClientFilterDialog(getActivity(), new ClientFilterDialog.ClientTypeListener() {
                    @Override
                    public void filterSearch(ClientTypeSearchModel clientTypeSearchModel) {

                    }
                });
                clientFilterDialog.show(getChildFragmentManager(),"");
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(ClientTrendModel data) {
        refresh.setRefreshing(false);
        if (data!=null){
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

            orderTrendAdapter.setList(trendList);
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
