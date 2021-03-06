package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import pro.haichuang.tzs144.adapter.AddressListAdapter;
import pro.haichuang.tzs144.adapter.MainTainRecordAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.ClientDetailActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 客户详情
 */
public class ClientDetailActivity extends BaseActivity implements ILoadDataView<ClientDetailModel.DataBean> {


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
    @BindView(R.id.add_address)
    TextView addAddress;

    private AddressListAdapter addressListAdapter;
    private MainTainRecordAdapter mainTainRecordAdapter;
    private ClientDetailActivityPresenter clientDetailActivityPresenter;

    private String customerId;
    private ClientDetailModel.DataBean dataBean;
    private int deletePosition = -1;

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

        addressListAdapter.addChildClickViewIds(R.id.edit_txt,R.id.delete_txt);

        addressListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
               switch (view.getId()){
                   case R.id.edit_txt:

                       break;
                   case R.id.delete_txt:
                       deletePosition = position;
                        ClientDetailModel.DataBean.AddressListBean addressListBean = addressListAdapter.getData().get(position);
                        WaitDialog.show(ClientDetailActivity.this,"删除中....");
                        clientDetailActivityPresenter.delAddress(addressListBean.getId()+"");
                       break;
               }
            }
        });

    }

    @Override
    protected void setUpData() {
        customerId = getIntent().getStringExtra("id");
        if (customerId==null){
            finish();
        }
        clientDetailActivityPresenter = new ClientDetailActivityPresenter(this);
        clientDetailActivityPresenter.getCustomerInfo(customerId);

    }


    @OnClick({R.id.back, R.id.tips,R.id.add_maintain_record,R.id.update_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                Intent intent2 = new Intent(this,OrderRecordActivity.class);
                intent2.putExtra("id",customerId);
                startActivity(intent2);
                break;
            case R.id.add_maintain_record:
                if (dataBean!=null){
                    String dataJson = Utils.gsonInstane().toJson(dataBean);
                    Intent intent = new Intent(this,AddMainTainRecordActivity.class);
                    intent.putExtra("dataJson",dataJson);
                    startActivity(intent);
                }else {
                    Utils.showCenterTomast("获取数据错误");
                }
                break;
            case R.id.update_address:
                updateAddress.setVisibility(View.GONE);
                addAddress.setVisibility(View.VISIBLE);

                List<ClientDetailModel.DataBean.AddressListBean> data = addressListAdapter.getData();
                List<ClientDetailModel.DataBean.AddressListBean> tempData = new ArrayList<>();
                for (ClientDetailModel.DataBean.AddressListBean addressListBean : data){
                    addressListBean.setUpadteAddress(true);
                    tempData.add(addressListBean);
                }
                addressListAdapter.setList(tempData);

                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"加载中...");
    }

    @Override
    public void successLoad(ClientDetailModel.DataBean dataBean) {
        WaitDialog.dismiss();
        if (dataBean!=null){
            this.dataBean = dataBean;
            addressListAdapter.setList(dataBean.getAddressList());
            mainTainRecordAdapter.setList(dataBean.getMaintainList());

            clientName.setText("客户类型："+dataBean.getCustomerType());
            clientPersion.setText("   联系人："+dataBean.getContacts());
            clientPhone.setText("联系电话："+dataBean.getPhone());
            clientLocation.setText("所在片区："+dataBean.getArea());
            if (dataBean.getInviter()!=null){
                developPersion.setText("发展人员："+dataBean.getInviter());
            }else {
                developPersion.setText("发展人员：无");
            }
            if (dataBean.getBusiness()!=null){
                businessPersion.setText("业务人员："+dataBean.getBusiness());
            }else {
                businessPersion.setText("业务人员：无");
            }

            if (dataBean.getCustomerType().contains("经销商") ||dataBean.getCustomerType().contains("协议客户")){
                addMaintainRecord.setVisibility(View.VISIBLE);
            }else {
                addMaintainRecord.setVisibility(View.GONE);
            }

            if (dataBean.getCustomerType().contains("经销商")){
                updateAddress.setVisibility(View.VISIBLE);
            }else {
                updateAddress.setVisibility(View.GONE);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event!=null){
            //删除地址
            if (event.type==0){
                if (event.type==Config.LOAD_SUCCESS){
                    Utils.showCenterTomast("删除成功");
                    if (deletePosition!=-1){
                        List<ClientDetailModel.DataBean.AddressListBean> data = addressListAdapter.getData();
                        data.remove(deletePosition);
                        addressListAdapter.setList(data);
                    }
                }else {
                    Utils.showCenterTomast("删除失败");
                }
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
