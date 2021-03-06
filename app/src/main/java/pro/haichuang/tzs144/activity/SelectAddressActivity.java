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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.ClientAddressAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.presenter.SelectAddressActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 选择地址页面
 */
public class SelectAddressActivity extends BaseActivity implements ILoadDataView<List<SaleDataModel.DataBean>> {


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
    private SelectAddressActivityPresenter selectAddressActivityPresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_select_adddress;
    }

    @Override
    protected void setUpView() {
        title.setText("张三");
        clientAddressAdapter = new ClientAddressAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(clientAddressAdapter);
    }

    @Override
    protected void setUpData() {
        String customerId = getIntent().getIntExtra("customerId",0)+"";
        selectAddressActivityPresenter = new SelectAddressActivityPresenter(this);
        selectAddressActivityPresenter.findAddress(customerId);

        clientAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SaleDataModel.DataBean dataBean = clientAddressAdapter.getData().get(position);
                String dataStr = Utils.gsonInstane().toJson(dataBean);
                Intent intent = new Intent();
                intent.putExtra(Config.PERSION_INFO,dataStr);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"加载中");
    }

    @Override
    public void successLoad(List<SaleDataModel.DataBean> data) {
        WaitDialog.dismiss();
        clientAddressAdapter.setList(data);
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
        WaitDialog.dismiss();
    }
}
