package pro.haichuang.tzs144.activity;


import android.os.Bundle;
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
import pro.haichuang.tzs144.adapter.ClientAddressAdapter;

/**
 * 选择地址页面
 */
public class SelectAddressActivity extends BaseActivity {


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
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;

    private ClientAddressAdapter clientAddressAdapter;
    private List<String> listData;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_select_adddress;
    }

    @Override
    protected void setUpView() {
      title.setText("张三");
        clientAddressAdapter = new ClientAddressAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(clientAddressAdapter);
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0; i<3;i++){
            listData.add("");
        }
        clientAddressAdapter.setList(listData);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
