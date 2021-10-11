package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.WaitDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.SaleListItemAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.model.RefreshEvent;
import pro.haichuang.tzs144.model.SaleListModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.SalesListActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.TimeDialog;

/**
 * 直接销售列表
 */
public class SalesListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<SaleListModel.DataBean>> {


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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private TextView client_type;
    TextView timeSort;
    View addHeadView;

    private SaleListItemAdapter saleListItemAdapter;
    private SalesListActivityPresenter salesListActivityPresenter;

    private ClientTypeModel clientTypeModel;
    private List<CharSequence> clientlist;
    private String clientTypeJson;
    private String cutomerType;
    private String startTime;
    private String endTime;
    private int page = 1;
    private boolean lastPage;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sale_list;
    }

    @Override
    protected void setUpView() {
        title.setText("直接销售列表");
        title.setTextColor(ContextCompat.getColor(this, R.color.blank2));

        tipImg.setVisibility(View.VISIBLE);
        tipImg.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.add_order));
        refresh.setOnRefreshListener(this);

        saleListItemAdapter = new SaleListItemAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(saleListItemAdapter);
        addHeadView = LayoutInflater.from(this).inflate(R.layout.item_head_sale_list, null);
        client_type = addHeadView.findViewById(R.id.client_type);
        saleListItemAdapter.addHeaderView(addHeadView);
        timeSort = addHeadView.findViewById(R.id.time_sort);
        timeSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                TimeDialog timeDialog = new TimeDialog(SalesListActivity.this, new TimeDialog.SelectTimeListener() {
                    @Override
                    public void selectTime(String mStartTime, String mEndTime) {
                        startTime = mStartTime;
                        endTime = mEndTime;
                        salesListActivityPresenter.findDirectSales(cutomerType, mStartTime, mEndTime, 1);
                    }
                });
                timeDialog.show(getSupportFragmentManager(), "");
            }
        });

        client_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clientTypeJson.equals("")) {
                    BottomMenu.show(SalesListActivity.this, clientlist, new OnMenuItemClickListener() {
                        @Override
                        public void onClick(String text, int index) {
                            page = 1;
                            lastPage = false;
                            if (text.equals("全部客户")){
                                cutomerType = "";
                            }else {
                                cutomerType = text;
                            }
                            salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, page);
                        }
                    });
                }
            }
        });
        saleListItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SaleListModel.DataBean dataBean = saleListItemAdapter.getData().get(position);
                String orderNumId = dataBean.getId();

                Intent intent = new Intent(SalesListActivity.this,SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                intent.putExtra("settleStatus",dataBean.getSettleStatus());
                startActivity(intent);
            }
        });

        saleListItemAdapter.addChildClickViewIds(R.id.order_state);
        saleListItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId()==R.id.order_state){
                    String orderNumId = saleListItemAdapter.getData().get(position).getId();
                    salesListActivityPresenter.directSelling(orderNumId);
                }
            }
        });

        saleListItemAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage){
                    page++;
                    salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, page);
                }
            }
        });
        saleListItemAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        saleListItemAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

    }

    @Override
    protected void setUpData() {
        salesListActivityPresenter = new SalesListActivityPresenter(this);

        clientTypeJson = SPUtils.getString(Config.CLIENT_TYPE, "");
        clientTypeModel = Utils.gsonInstane().fromJson(clientTypeJson, ClientTypeModel.class);
        clientlist = new ArrayList<>();
        for (ClientTypeModel.DataBean dataBean : clientTypeModel.getData()) {
            clientlist.add(dataBean.getName());
        }
        clientlist.add("全部客户");
        cutomerType = "";
        startTime = "2020-10-10";
        endTime = Utils.formatSelectTime(new Date());
        salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, page);
    }


    @OnClick({R.id.back, R.id.tip_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tip_img:

                String [] list = null;
                if (Config.AUTHORITY.contains("5")){
                    list = new String[]{"直接销售", "补录订单"};
                }else {
                    list = new String[]{"直接销售"};
                }

                new XPopup.Builder(this)
                        .atView(tipImg)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(list,
                                new int[]{},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Intent intent = new Intent();
                                        if (position == 0) {
                                            intent.putExtra("order_type",0);
                                            intent.putExtra("type","add_order");
                                            intent.setClass(SalesListActivity.this, SaleSearchActivity.class);

                                        } else if (position == 1) {
                                            intent.putExtra("order_type",1);
                                            intent.putExtra("type","add_order");
                                            intent.setClass(SalesListActivity.this, SaleSearchActivity.class);
                                        }
                                        startActivity(intent);
                                    }
                                })
                        .show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        lastPage = false;
        page=1;
        salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, page);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<SaleListModel.DataBean> data) {
        refresh.setRefreshing(false);
        if (data==null || data.size()==0){
            lastPage = true;
        }
        if (page==1){
            if (data != null && data.size() > 0) {
                emptyView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
            saleListItemAdapter.setList(data);
            if (data.size()<10){
                lastPage = true;
                saleListItemAdapter.getLoadMoreModule().loadMoreEnd();
            }
        }else {
            saleListItemAdapter.addData(data);
            saleListItemAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (lastPage){
            saleListItemAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("订单作废成功");
                page=1;
                lastPage=false;
                salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, 1);
            } else {
                Utils.showCenterTomast("订单作废失败: " + event.result);
            }
        }
        Log.i(TAG, "onMessageEvent===");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
           if ("refresh".equals(event.status)){
               page=1;
               lastPage=false;
               salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, 1);
           }
        }
        Log.i(TAG, "onMessageEvent===");
    }
}

