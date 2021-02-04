package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v2.WaitDialog;

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
import pro.haichuang.tzs144.presenter.AccountingListPresenter;
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
        title.setText("账务列表");
        endTime.setText(Utils.formatSelectTime(new Date()));

        accountingListAdapter = new AccountingListAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(accountingListAdapter);
        accountingListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(AccountingListActivity.this,AccountingListDetailActivity.class);
                intent.putExtra("id","");
                startActivity(intent);
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
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
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
            accountingListAdapter.setList(data);
        }
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }
}
