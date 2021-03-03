package pro.haichuang.tzs144.activity;


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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddWithDrawalOrderAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.presenter.AddWithDrawalOrderActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 新增退押记录
 */
public class AddWithDrawalOrderActivity extends BaseActivity implements ILoadDataView<List<WithDrawalOrderModel.DataBean>> {


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
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private AddWithDrawalOrderAdapter addWithDrawalOrderAdapter;
    private AddWithDrawalOrderActivityPresenter addWithDrawalOrderActivityPresenter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_with_drawal_order;
    }

    @Override
    protected void setUpView() {
        title.setText("退押");
        addWithDrawalOrderAdapter = new AddWithDrawalOrderAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(addWithDrawalOrderAdapter);
        addWithDrawalOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                List<WithDrawalOrderModel.DataBean> data = addWithDrawalOrderAdapter.getData();
                WithDrawalOrderModel.DataBean dataBean = data.get(position);
                if (dataBean.isChecked()){
                    dataBean.setChecked(false);
                }else {
                    dataBean.setChecked(true);
                }
                data.set(position,dataBean);
                addWithDrawalOrderAdapter.setList(data);
            }
        });
    }

    @Override
    protected void setUpData() {
        addWithDrawalOrderActivityPresenter = new AddWithDrawalOrderActivityPresenter(this);
        String dataBeanJson = getIntent().getStringExtra(Config.PERSION_INFO);
        if (dataBeanJson!=null){
            SaleDataModel.DataBean dataBean = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);
            name.setText(dataBean.getName());
            phone.setText(dataBean.getPhone());
            address.setText(dataBean.getAddressName());
            addressDetail.setText(dataBean.getAddress());
            addWithDrawalOrderActivityPresenter.findByKhReturnDeposits(dataBean.getId()+"");
        }
    }


    @OnClick({R.id.back,R.id.with_drawal_btn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.with_drawal_btn:
                StringBuilder idBuilder = new StringBuilder();
                for (WithDrawalOrderModel.DataBean dataBean : addWithDrawalOrderAdapter.getData()){
                    idBuilder.append(dataBean.getId()).append(",");
                }
                //去除多余的逗号
                String ids = idBuilder.substring(0, idBuilder.toString().length() - 2);
                addWithDrawalOrderActivityPresenter.returnDeposits(ids);
                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"加载中...");
    }

    @Override
    public void successLoad(List<WithDrawalOrderModel.DataBean> data) {
        WaitDialog.dismiss();
        addWithDrawalOrderAdapter.setList(data);
        if (data==null || data.size()==0){
            emptyView.setVisibility(View.VISIBLE);
            withDrawalBtn.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            withDrawalBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("新增退押记录成功");
                finish();
            } else {
                Utils.showCenterTomast("新增失败");
            }
        }

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
