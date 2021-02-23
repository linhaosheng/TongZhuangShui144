package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

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
import pro.haichuang.tzs144.model.SaleListModel;
import pro.haichuang.tzs144.presenter.SalesListActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;

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
                TimePickerView pvTime = new TimePickerBuilder(SalesListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                    }
                }).build();
                pvTime.show();
            }
        });

        client_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clientTypeJson.equals("")) {
                    BottomMenu.show(SalesListActivity.this, clientlist, new OnMenuItemClickListener() {
                        @Override
                        public void onClick(String text, int index) {
                            cutomerType = text;
                            salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, page);
                        }
                    });
                }
            }
        });
        saleListItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String orderNumId = saleListItemAdapter.getData().get(position).getId();
                Intent intent = new Intent(SalesListActivity.this,SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                startActivity(intent);
            }
        });
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
        cutomerType = clientlist.get(0).toString();
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
                new XPopup.Builder(this)
                        .atView(tipImg)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"直接销售", "补录订单"},
                                new int[]{},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Intent intent = new Intent();
                                        if (position == 0) {
                                            intent.putExtra("order_type",0);
                                            intent.setClass(SalesListActivity.this, EnterOrderActivity.class);

                                        } else if (position == 1) {
                                            intent.putExtra("order_type",1);
                                            intent.setClass(SalesListActivity.this, EnterOrderActivity.class);
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
        salesListActivityPresenter.findDirectSales(cutomerType, startTime, endTime, page);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<SaleListModel.DataBean> data) {
        refresh.setRefreshing(false);
        saleListItemAdapter.setList(data);
        if (data != null && data.size() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }

}
