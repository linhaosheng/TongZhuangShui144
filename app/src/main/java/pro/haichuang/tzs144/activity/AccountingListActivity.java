package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

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
import pro.haichuang.tzs144.adapter.AccountingListAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.AccountingListPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 账务列表 页面
 */
public class AccountingListActivity extends BaseActivity implements ILoadDataView<List<AccountListModel.DataBean>> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.tip_img)
    ImageView tipImg;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private AccountingListAdapter accountingListAdapter;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;
    private AccountingListPresenter accountingListPresenter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_account_list;
    }

    @Override
    protected void setUpView() {
        title.setText("账目列表");
        endTime.setText(Utils.formatSelectTime(new Date()));

        accountingListAdapter = new AccountingListAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(accountingListAdapter);
        accountingListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AccountListModel.DataBean dataBean = accountingListAdapter.getData().get(position);
                String id = dataBean.getId();
                Intent intent = new Intent(AccountingListActivity.this,AccountingListDetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("settleStatus",dataBean.getSettleStatus());
                startActivity(intent);
            }
        });
        accountingListAdapter.addChildClickViewIds(R.id.write_off);
        accountingListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId()==R.id.write_off){
                    String id = accountingListAdapter.getData().get(position).getId();
                    WaitDialog.show(AccountingListActivity.this,"加载中...");
                    accountingListPresenter.cancelAccount(id);
                }
            }
        });

    }

    @Override
    protected void setUpData() {
        accountingListPresenter = new AccountingListPresenter(this);
        accountingListPresenter.findOrderAccounts(startTime.getText().toString(),endTime.getText().toString());
    }


    @OnClick({R.id.back, R.id.start_time, R.id.end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.start_time:
                endTime.setTextColor(Color.parseColor("#6D7278"));
                startTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn36));
                endTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn37));
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
                startTime.setTextColor(Color.parseColor("#6D7278"));
                endTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn37));
                endTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn36));
                selectTime(SELECT_END_TIME);
                break;
        }
    }

    private void selectTime(int type){

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (type==SELECT_START_TIME){
                    startTime.setText(Utils.formatSelectTime(date));
                }else {
                    endTime.setText(Utils.formatSelectTime(date));
                    accountingListPresenter.findOrderAccounts(startTime.getText().toString(),endTime.getText().toString());
                }
            }
        })
                .build();
        pvTime.show();
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"获取中...");
    }

    @Override
    public void successLoad(List<AccountListModel.DataBean> data) {
        WaitDialog.dismiss();
        if (data==null || data.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
        }
        accountingListAdapter.setList(data);
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("销账成功");
                accountingListPresenter.findOrderAccounts(startTime.getText().toString(),endTime.getText().toString());
            } else {
                Utils.showCenterTomast("销账失败 : "+event.result);
            }
        }
        Log.i(TAG, "onMessageEvent===");
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
