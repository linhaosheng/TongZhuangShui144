package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddressListAdapter;
import pro.haichuang.tzs144.adapter.MainTainRecordAdapter;

/**
 * 客户详情
 */
public class ClientDetailActivity extends BaseActivity {


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
    @BindView(R.id.client_name)
    TextView clientName;
    @BindView(R.id.client_type)
    TextView clientType;
    @BindView(R.id.client_persion)
    TextView clientPersion;
    @BindView(R.id.client_phone)
    TextView clientPhone;
    @BindView(R.id.client_location)
    TextView clientLocation;
    @BindView(R.id.develop_persion)
    TextView developPersion;
    @BindView(R.id.business_persion)
    TextView businessPersion;
    @BindView(R.id.addree_recycle)
    RecyclerView addreeRecycle;
    @BindView(R.id.add_maintain_record)
    TextView addMaintainRecord;
    @BindView(R.id.maintain_record)
    RecyclerView maintainRecordRecycle;
    @BindView(R.id.update_address)
    TextView updateAddress;

    private AddressListAdapter addressListAdapter;
    private MainTainRecordAdapter mainTainRecordAdapter;

    private List<String>addressList;
    private List<String>recordList;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_client_detail;
    }

    @Override
    protected void setUpView() {
        title.setText("客户详情");
        tips.setVisibility(View.VISIBLE);
        tips.setTextSize(12);
        tips.setText("订单记录");

        addressListAdapter = new AddressListAdapter();
        addreeRecycle.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        addreeRecycle.setAdapter(addressListAdapter);

        mainTainRecordAdapter = new MainTainRecordAdapter();
        maintainRecordRecycle.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        maintainRecordRecycle.setAdapter(mainTainRecordAdapter);


    }

    @Override
    protected void setUpData() {
        addressList = new ArrayList<>();
        addressList.add("");
        addressList.add("");
        addressList.add("");
        addressListAdapter.setList(addressList);

        recordList = new ArrayList<>();
        recordList.add("");
        recordList.add("");
        recordList.add("");
        mainTainRecordAdapter.setList(recordList);

    }


    @OnClick({R.id.back, R.id.tips,R.id.add_maintain_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                Intent intent2 = new Intent(this,OrderRecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.add_maintain_record:
                Intent intent = new Intent(this,AddMainTainRecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
