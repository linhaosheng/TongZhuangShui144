package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import pro.haichuang.tzs144.adapter.AddWithDrawalOrderAdapter;

/**
 * 新增退押记录
 */
public class AddWithDrawalOrderActivity extends BaseActivity {


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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.with_drawal_btn)
    Button withDrawalBtn;

    private AddWithDrawalOrderAdapter addWithDrawalOrderAdapter;
    private List<String> listData;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_with_drawal_order;
    }

    @Override
    protected void setUpView() {
        addWithDrawalOrderAdapter = new AddWithDrawalOrderAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(addWithDrawalOrderAdapter);
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0;i<6;i++){
            listData.add("");
        }
        addWithDrawalOrderAdapter.setList(listData);
    }


    @OnClick({R.id.back,R.id.with_drawal_btn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.with_drawal_btn:
                break;
        }
    }
}
