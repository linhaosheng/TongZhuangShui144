package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DemandListAdapter;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 填写需求单
 */
public class DemandListActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.start_time)
    LSettingItem startTime;
    @BindView(R.id.end_time)
    LSettingItem endTime;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.add_shop)
    Button addShop;
    private DemandListAdapter demandListAdapter;
    private List<String> listData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_demand_list;
    }

    @Override
    protected void setUpView() {
      title.setText("填写需求单");
        tips.setVisibility(View.VISIBLE);
        tips.setText("需求记录");
        tips.setTextSize(12);

        demandListAdapter = new DemandListAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(demandListAdapter);
        demandListAdapter.addChildClickViewIds(R.id.delete);
        demandListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.delete:
                        listData.remove(0);
                        demandListAdapter.setList(listData);
                        break;
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        listData.add("");
        demandListAdapter.setList(listData);
    }


    @OnClick({R.id.back, R.id.tips, R.id.input_btn, R.id.cancel_btn,R.id.add_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                Intent intent = new Intent(this,DemandRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.input_btn:
                break;
            case R.id.cancel_btn:
                break;
            case R.id.add_shop:
                listData.add("");
                demandListAdapter.setList(listData);
                break;
        }
    }
}
