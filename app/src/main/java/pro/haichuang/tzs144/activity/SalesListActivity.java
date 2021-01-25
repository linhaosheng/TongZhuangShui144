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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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

/**
 * 直接销售列表
 */
public class SalesListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


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
    @BindView(R.id.order_state)
    TextView orderState;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    TextView timeSort;

    View addHeadView;

    private SaleListItemAdapter saleListItemAdapter;
    private List<String> datas;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sale_list;
    }

    @Override
    protected void setUpView() {
        title.setText("直接销售列表");
        title.setTextColor(ContextCompat.getColor(this,R.color.blank2));

        tipImg.setVisibility(View.VISIBLE);
        tipImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.add_order));
        refresh.setOnRefreshListener(this);

        saleListItemAdapter = new SaleListItemAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(saleListItemAdapter);
        addHeadView =  LayoutInflater.from(this).inflate(R.layout.item_head_sale_list, null);
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
                })
                        .build();
                pvTime.show();
            }
        });

    }

    @Override
    protected void setUpData() {
        datas = new ArrayList<>();
        for (int i = 0;i<5; i++){
            datas.add("");
        }
        saleListItemAdapter.setList(datas);

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
                                        if (position==0){
                                          intent.setClass(SalesListActivity.this,EnterOrderActivity.class);
                                        }else if (position==1){
                                            intent.setClass(SalesListActivity.this,EnterOrderActivity.class);
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
      refresh.postDelayed(new Runnable() {
          @Override
          public void run() {
              refresh.setRefreshing(false);
          }
      },2000);
    }
}
