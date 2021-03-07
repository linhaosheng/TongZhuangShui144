package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import pro.haichuang.tzs144.adapter.AddressListAdapter;
import pro.haichuang.tzs144.adapter.MainTainRecordAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AddressBean;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.ClientDetailActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

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
    @BindView(R.id.order_record)
    TextView orderRecord;

    private AddressListAdapter addressListAdapter;
    private MainTainRecordAdapter mainTainRecordAdapter;
    private ClientDetailActivityPresenter clientDetailActivityPresenter;

    private String customerId;
    private ClientDetailModel.DataBean dataBean;
    private int deletePosition = -1;
    private int editType;
    private List<ClientDetailModel.DataBean.MaintainListBean> data;
    private int editRecordId;
    private String maintainInfo = "";
    private double longitude;
    private double latitude;
    private String addressName;
    private int addressId;
    private AddressBean addressBean;


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

        addressListAdapter = new AddressListAdapter(this, new AddressListAdapter.AddressNameListener() {
            @Override
            public void addressName(int position,String name) {
                addressName = name;
            }
        });
        addreeRecycle.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        addreeRecycle.setAdapter(addressListAdapter);

        mainTainRecordAdapter = new MainTainRecordAdapter(new MainTainRecordAdapter.EditContentListener() {
            @Override
            public void editContent(String content,int position) {
                maintainInfo = content;
                data = mainTainRecordAdapter.getData();
                ClientDetailModel.DataBean.MaintainListBean maintainListBean = data.get(position);
                maintainListBean.setEdit(false);
                maintainListBean.setMaintainInfo(content);
                editRecordId = maintainListBean.getId();
            }
        });
        maintainRecordRecycle.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        maintainRecordRecycle.setAdapter(mainTainRecordAdapter);

        addressListAdapter.addChildClickViewIds(R.id.edit_txt,R.id.delete_txt);
        addressListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
               switch (view.getId()){
                   case R.id.edit_txt:
                       tips.setText("保存");
                       editType = 1;
                       ClientDetailModel.DataBean.AddressListBean addressListBean1 = addressListAdapter.getData().get(position);
                       addressListBean1.setEdit(true);
                       addressId = addressListBean1.getId();
                       Log.i(TAG,"addressId===="+addressId);
                       addressListAdapter.setData(position,addressListBean1);

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

        mainTainRecordAdapter.addChildClickViewIds(R.id.edit,R.id.delete);
        mainTainRecordAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.edit:
                        editType = 2;
                        tips.setText("保存");
                        orderRecord.setVisibility(View.VISIBLE);
                        ClientDetailModel.DataBean.MaintainListBean maintainListBean1 = mainTainRecordAdapter.getData().get(position);
                        maintainListBean1.setEdit(true);
                        mainTainRecordAdapter.setData(position,maintainListBean1);

                        break;
                    case R.id.delete:
                        deletePosition = position;
                        ClientDetailModel.DataBean.MaintainListBean maintainListBean = mainTainRecordAdapter.getData().get(position);
                        int id = maintainListBean.getId();
                        WaitDialog.show(ClientDetailActivity.this,"删除中....");
                        clientDetailActivityPresenter.delMaintainLog(id+"");
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tips.getText().toString().contains("订单记录") && addAddress.getVisibility()==View.GONE){
            clientDetailActivityPresenter.getCustomerInfo(customerId);
        }
    }

    @OnClick({R.id.back, R.id.tips,R.id.add_maintain_record,R.id.update_address,R.id.order_record,R.id.add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                if (tips.getText().toString().contains("订单记录")){
                    Intent intent2 = new Intent(this,OrderRecordActivity.class);
                    intent2.putExtra("id",customerId);
                    startActivity(intent2);
                }else {
                    //地址编辑
                    if (editType==1){
                        clientDetailActivityPresenter.updateAddress(addressId,customerId,addressName,addressBean.getAddress(),addressBean.getLongitude(),addressBean.getLatitude());

                    }else if (editType==2){  //维修记录
                     double distanceData = (int)Utils.GetDistance(Config.LONGITUDE, Config.LATITUDE, longitude, latitude);
                     clientDetailActivityPresenter.saveMaintainLog(editRecordId,customerId,maintainInfo,distanceData,Utils.formatSelectTime(new Date()));
                    }
                }
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

            case R.id.order_record:
                Intent intent3 = new Intent(this,OrderRecordActivity.class);
                intent3.putExtra("id",customerId);
                startActivity(intent3);
                break;
            case R.id.add_address:
                tips.setText("保存");
                editType = 1;
                addressId = 0;
                ClientDetailModel.DataBean.AddressListBean addressListBean = new ClientDetailModel.DataBean.AddressListBean();
                addressListBean.setEdit(true);
                addressListBean.setAddAddress(true);
                addressListAdapter.addData(addressListBean);
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

            List<ClientDetailModel.DataBean.AddressListBean> addressList = dataBean.getAddressList();
            if (addressList!=null){
                ClientDetailModel.DataBean.AddressListBean addressListBean = addressList.get(0);
                try {
                     longitude = Double.parseDouble(addressListBean.getLongitude());
                     latitude = Double.parseDouble(addressListBean.getLatitude());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event!=null){
            //删除地址
            if (event.type==0){
                if (event.status==Config.LOAD_SUCCESS){
                    updateAddress.setVisibility(View.VISIBLE);
                    Utils.showCenterTomast("删除成功");
                    if (deletePosition!=-1){
                        List<ClientDetailModel.DataBean.AddressListBean> data = addressListAdapter.getData();
                        data.remove(deletePosition);
                        addressListAdapter.setList(data);
                    }
                }else {
                    Utils.showCenterTomast("删除失败");
                }
            }else if (event.type==1){
                if (event.status==Config.LOAD_SUCCESS){
                    Utils.showCenterTomast("删除成功");
                    if (deletePosition!=-1){
                        List<ClientDetailModel.DataBean.MaintainListBean> data = mainTainRecordAdapter.getData();
                        data.remove(deletePosition);
                        mainTainRecordAdapter.setList(data);
                    }
                }else {
                    Utils.showCenterTomast("删除失败");
                }
            }else if (event.type==2){
                if (event.status==Config.LOAD_SUCCESS){
                    tips.setText("订单记录");
                    orderRecord.setVisibility(View.GONE);
                    Utils.showCenterTomast("编辑成功");
                    mainTainRecordAdapter.setList(data);
                    updateAddress.setVisibility(View.VISIBLE);
                }else {
                    Utils.showCenterTomast("编辑失败");
                }
            }else if (event.type==3){
                if (event.status==Config.LOAD_SUCCESS){
                    tips.setText("订单记录");
                    addAddress.setVisibility(View.GONE);
                    orderRecord.setVisibility(View.GONE);
                    Utils.showCenterTomast("地址修改成功");
                    updateAddress.setVisibility(View.VISIBLE);
                    clientDetailActivityPresenter.getCustomerInfo(customerId);
                }else {
                    Utils.showCenterTomast("编辑失败");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.i(TAG, "onActivityResult===="+requestCode);
            String addressJson = data.getStringExtra(Config.ADDRESS_JSON);
            addressBean = Utils.gsonInstane().fromJson(addressJson, AddressBean.class);

            ClientDetailModel.DataBean.AddressListBean addressListBean = addressListAdapter.getData().get(requestCode);
            addressListBean.setEdit(true);
            addressListBean.setNewAddress(addressBean.getAddress());
            addressListBean.setNewAddressName(addressName);
            addressListAdapter.setData(requestCode,addressListBean);
        }
    }
}
