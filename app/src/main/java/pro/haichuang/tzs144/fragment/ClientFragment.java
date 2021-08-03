package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AddClientActivity;
import pro.haichuang.tzs144.activity.AddMainTainRecordActivity;
import pro.haichuang.tzs144.activity.ClientDetailActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.adapter.OrderNumTrendAdapter;
import pro.haichuang.tzs144.adapter.OrderPaymentAdapter;
import pro.haichuang.tzs144.adapter.OrderTrendAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.model.ClientEvent;
import pro.haichuang.tzs144.model.ClientListModel;
import pro.haichuang.tzs144.model.ClientTrendModel;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.model.TrendModel;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.presenter.ClientFragmentPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
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
    private boolean lastPage;
    private View headTimeView;
    private TextView updateTime;
    private String endTime;
    private String startTime = "2019-10-10";
    private String khStatus = "0";
    private String khTypeId = "";
    private String type;


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
        orderPaymentAdapter = new OrderNumTrendAdapter(getActivity());
        orderTrendAdapter  = new OrderTrendAdapter();

        headTimeView = LayoutInflater.from(getActivity()).inflate(R.layout.item_update_time, null);
        orderTrendAdapter.addHeaderView(headTimeView);
        updateTime = headTimeView.findViewById(R.id.update_time);

        recycleTendIncome.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleTendIncome.setAdapter(orderTrendAdapter);

        orderPaymentAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage){
                    currentPage++;
                    clientFragmentPresenter.findKhList(type,"",startTime,endTime,khTypeId,khStatus,currentPage);
                }
            }
        });
        orderPaymentAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        orderPaymentAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);


        orderTrendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        recycleClientList.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleClientList.setAdapter(orderPaymentAdapter);

        orderPaymentAdapter.addChildClickViewIds(R.id.order_state);
        orderPaymentAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                if (view.getId()==R.id.order_state){
                    WaitDialog.show((AppCompatActivity) getActivity(),"加载中....");
                    ClientListModel.DataBean dataBean = orderPaymentAdapter.getData().get(position);
                    String id = dataBean.getId();
                    clientFragmentPresenter.getWhCustomerInfo(id, new HttpRequestResultListener() {
                        @Override
                        public void start() {

                        }

                        @Override
                        public void success(String result) {
                            WaitDialog.dismiss();
                            try {
                                ClientDetailModel clientDetailModel = Utils.gsonInstane().fromJson(result, ClientDetailModel.class);
                                if (clientDetailModel!=null && clientDetailModel.getResult()==1){
                                    Intent intent = new Intent(getActivity(), AddMainTainRecordActivity.class);
                                    ClientDetailModel.DataBean data = clientDetailModel.getData();
                                    String dataJson = Utils.gsonInstane().toJson(data);
                                    intent.putExtra("dataJson",dataJson);
                                    startActivity(intent);
                                }else {
                                    MessageDialog.show((AppCompatActivity) getActivity(), "提示", clientDetailModel.getMessage()).show();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void error(String error) {
                          WaitDialog.dismiss();
                        }
                    });
                }
            }
        });
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
        String clientType = SPUtils.getString(Config.CLIENT_TYPE, "");
        ClientTypeModel clientTypeModel = Utils.gsonInstane().fromJson(clientType, ClientTypeModel.class);
        List<ClientTypeModel.DataBean> data = clientTypeModel.getData();
        StringBuilder stringBuilder = new StringBuilder();
        for (ClientTypeModel.DataBean dataBean : data){
            stringBuilder.append(dataBean.getId()).append(",");
        }
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        khTypeId = substring;

        endTime = Utils.formatSelectTime(new Date());
        clientFragmentPresenter = new ClientFragmentPresenter(this);
        clientFragmentPresenter.countKh(type);
        clientFragmentPresenter.findKhList(type,"",startTime,endTime,khTypeId,khStatus,currentPage);
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
                    currentPage = 1;
                    lastPage = false;
                    clientFragmentPresenter.findKhList(type,searchEdit.getText().toString(),startTime,endTime,khTypeId,khStatus,currentPage);
                }
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(getActivity());
                }
                return false;
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
                    public void filterSearch(List<ClientTypeModel.DataBean> data,String warnType,String selectStartTime,String selectEndTime) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (ClientTypeModel.DataBean dataBean : data){
                            if (dataBean.isTimeType() && dataBean.isCheck()){
                                khStatus = dataBean.getId()+"";
                            }
                            if (!dataBean.isTimeType() && dataBean.isCheck()){
                                stringBuilder.append(dataBean.getId()).append(",");
                            }
                        }
                        if (stringBuilder==null || stringBuilder.length()==0){
                            khTypeId = "";
                        }else {
                            khTypeId = stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
                        }

                        type = warnType;
                        currentPage = 1;
                        lastPage = false;
                        startTime = selectStartTime;
                        endTime = selectEndTime;
                        clientFragmentPresenter.countKh(type);
                        clientFragmentPresenter.findKhList(type,searchEdit.getText().toString(),startTime,endTime,khTypeId,khStatus,currentPage);
                        Log.i(TAG,"khTypeId===="+khTypeId);
                    }
                });
                clientFilterDialog.setStatus(khStatus,khTypeId,type);
                clientFilterDialog.show(getChildFragmentManager(),"");
                break;
            case R.id.cancel:
                searchView.setVisibility(View.GONE);
                headView.setVisibility(View.VISIBLE);
                searchEdit.setText("");
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        lastPage = false;
        clientFragmentPresenter.countKh(type);
        clientFragmentPresenter.findKhList(type,searchEdit.getText().toString(),startTime,endTime,khTypeId,khStatus,currentPage);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(ClientTrendModel data) {
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
        refresh.setRefreshing(false);
        if (event!=null){
             if (event.dataBean==null){
                 Utils.showCenterTomast("获取客户列表失败");
                 emptyView.setVisibility(View.VISIBLE);
             }else {

                 if (event.dataBean.getData()==null || event.dataBean.getData().size()==0){
                     lastPage = true;
                 }
                 if (currentPage==1){
                     if (event.dataBean.getData()!=null && event.dataBean.getData().size()>0){
                         emptyView.setVisibility(View.GONE);
                     }else {
                         emptyView.setVisibility(View.VISIBLE);
                     }
                     orderPaymentAdapter.setList(event.dataBean.getData());
                     if (event.dataBean.getData().size()<10){
                         lastPage = true;
                         orderPaymentAdapter.getLoadMoreModule().loadMoreEnd();
                     }
                 }else {
                     orderPaymentAdapter.addData(event.dataBean.getData());
                     orderPaymentAdapter.getLoadMoreModule().loadMoreComplete();
                 }

                 if (lastPage){
                     orderPaymentAdapter.getLoadMoreModule().loadMoreEnd();
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
