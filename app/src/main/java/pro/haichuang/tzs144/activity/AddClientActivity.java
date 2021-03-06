package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
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
import pro.haichuang.tzs144.adapter.AddClientAddressAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AddClientModel;
import pro.haichuang.tzs144.model.AddressBean;
import pro.haichuang.tzs144.model.AreaEvent;
import pro.haichuang.tzs144.model.AreaModel;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.model.StaffModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.AddClientActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;
import rxhttp.wrapper.param.IFile;

/**
 * 新增客户
 */
public class AddClientActivity extends BaseActivity implements ILoadDataView<String> {


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
    @BindView(R.id.client_type)
    LSettingItem clientType;
    @BindView(R.id.location_type)
    LSettingItem locationType;
    @BindView(R.id.clinet_name)
    LSettingItem clinetName;
    @BindView(R.id.clinet_contact)
    LSettingItem clinetContact;
    @BindView(R.id.phone)
    LSettingItem phone;
    @BindView(R.id.monopoly)
    LSettingItem monopoly;
    @BindView(R.id.develop_persion)
    LSettingItem developPersion;
    @BindView(R.id.business)
    LSettingItem business;
    @BindView(R.id.address)
    LSettingItem address;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    private AddClientAddressAdapter addClientAddressAdapter;

//    @BindView(R.id.address_name)
//    LSettingItem addressName;
//    @BindView(R.id.detail_address)
//    LSettingItem detailAddress;

    private int customerTypeId;
    private int honeycombId;
    private int honeycombGridId;
    private int inviterId;
    private int businessId;

    private ClientTypeModel clientTypeModel;
    private List<CharSequence> clientlist;
    private List<CharSequence> monopolyList;
    private List<CharSequence> businessList;
    private List<CharSequence> areaList;
    private List<CharSequence> subAreaList;
    private List<AddressBean> addressBeanList;
    private AddClientActivityPresenter addClientActivityPresenter;
    private AreaModel areaModel;

    private String areaStr;
    private String subAreaStr;
    private int selectPosition;
    private StaffModel staffModel = null;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_client;
    }

    @Override
    protected void setUpView() {
        addressBeanList = new ArrayList<>();
        addressBeanList.add(new AddressBean());
        addClientAddressAdapter = new AddClientAddressAdapter(new AddClientAddressAdapter.LSettingItemClickListner() {
            @Override
            public void itemClick(int position) {
                selectPosition = position;
                Intent intent = new Intent(AddClientActivity.this, AddressSearchActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(addClientAddressAdapter);
        addClientAddressAdapter.setList(addressBeanList);

        String clientTypeJson = SPUtils.getString(Config.CLIENT_TYPE, "");

        if (!clientTypeJson.equals("")) {
            clientTypeModel = Utils.gsonInstane().fromJson(clientTypeJson, ClientTypeModel.class);
            clientlist = new ArrayList<>();
            for (ClientTypeModel.DataBean dataBean : clientTypeModel.getData()) {
                clientlist.add(dataBean.getName());
            }
        }
        title.setText("新增客户");
        clientType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                if (!clientTypeJson.equals("")) {
                    BottomMenu.show(AddClientActivity.this, clientlist, new OnMenuItemClickListener() {
                        @Override
                        public void onClick(String text, int index) {
                            clientType.setRightText(text);
                            customerTypeId = clientTypeModel.getData().get(index).getId();
                            if (text.contains("终端")){
                                developPersion.setVisibility(View.GONE);
                                business.setVisibility(View.GONE);
                            }else {
                                developPersion.setVisibility(View.VISIBLE);
                                business.setVisibility(View.VISIBLE);
                            }
                            if (text.contains("经销商")){
                                monopoly.setVisibility(View.VISIBLE);
                            }else {
                                monopoly.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        monopolyList = new ArrayList<>();
        monopolyList.add("是");
        monopolyList.add("否");
        monopoly.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                BottomMenu.show(AddClientActivity.this, monopolyList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        monopoly.setRightText(text);
                    }
                });
            }
        });

        businessList = new ArrayList<>();
        String findStaffJson = SPUtils.getString(Config.FIND_STAFFS, "");

        if (!findStaffJson.equals("")) {
            staffModel = Utils.gsonInstane().fromJson(findStaffJson, StaffModel.class);
            for (StaffModel.DataBean dataBean : staffModel.getData()) {
                businessList.add(dataBean.getName());
            }
        }

        /**
         * 业务人员
         */
        business.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(AddClientActivity.this, businessList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        business.setRightText(text);
                        businessId = staffModel.getData().get(index).getId();
                    }
                });
            }
        });

        /**
         * 发展人员
         */
        developPersion.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(AddClientActivity.this, businessList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        developPersion.setRightText(text);
                        inviterId = staffModel.getData().get(index).getId();
                    }
                });
            }
        });

        addClientAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(AddClientActivity.this, AddressSearchActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        /**
         * 所在片区
         */
        subAreaList = new ArrayList<>();
        areaList = new ArrayList<>();
        String areaJson = SPUtils.getString(Config.FIND_AREA, "");
        if (!areaJson.equals("")) {
            areaModel = Utils.gsonInstane().fromJson(areaJson, AreaModel.class);
            for (AreaModel.DataBean dataBean : areaModel.getData()) {
                areaList.add(dataBean.getName());
            }
        }
        locationType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(AddClientActivity.this, areaList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        honeycombId = areaModel.getData().get(index).getId();
                        areaStr = text;
                        addClientActivityPresenter.findAreas(String.valueOf(areaModel.getData().get(index).getId()));
                    }
                });
            }
        });

        address.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                if (addressBeanList.size() > 0) {
                    AddressBean addressBean = addressBeanList.get(addressBeanList.size() - 1);
                    if (addressBean.getAddress() == null) {
                        Utils.showCenterTomast("请输入地址名称和详细地址");
                        return;
                    } else {
                        if (clientType.getRightText().contains("终端")){
                            if (addressBeanList.size()>=5){
                                Utils.showCenterTomast("最多只能设置5个地址");
                                return;
                            }
                        }
                        addressBeanList.add(new AddressBean());
                        addClientAddressAdapter.setList(addressBeanList);
                    }
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        addClientActivityPresenter = new AddClientActivityPresenter(this);
    }


    @OnClick({R.id.back, R.id.add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_btn:
                if (clientType.getRightText().contains("请选择")) {
                    Utils.showCenterTomast("请选择客户类型");
                    return;
                }
                if (locationType.getRightText().contains("请选择")) {
                    Utils.showCenterTomast("请选择所在片区");
                    return;
                }
                if (TextUtils.isEmpty(clinetName.getEditText())) {
                    Utils.showCenterTomast("请输入客户名称");
                    return;
                }
                if (TextUtils.isEmpty(clinetContact.getEditText())) {
                    Utils.showCenterTomast("请输入联系人");
                    return;
                }
                if (TextUtils.isEmpty(phone.getEditText())) {
                    Utils.showCenterTomast("请输入联系电话");
                    return;
                }
                if (monopoly.getRightText().contains("请选择")) {
                    Utils.showCenterTomast("请选择是否专卖");
                    return;
                }
                for (AddressBean addressBean : addressBeanList) {
                    if (addressBean.getAddressName() == null) {
                        Utils.showCenterTomast("请填写地址");
                        return;
                    }
                }

                AddClientModel addClientModel = new AddClientModel();
                addClientModel.setCustomerTypeId(customerTypeId);
                addClientModel.setHoneycombId(honeycombId);
                addClientModel.setHoneycombGridId(honeycombGridId);
                addClientModel.setCustomerName(clinetName.getEditText());
                addClientModel.setContacts(clinetContact.getEditText());
                addClientModel.setPhone(phone.getEditText());
                if (monopoly.getRightText().equals("是")) {
                    addClientModel.setIsMonopoly(1);
                } else {
                    addClientModel.setIsMonopoly(0);
                }
                addClientModel.setInviterId(inviterId);
                addClientModel.setBusinessId(businessId);

                List<AddClientModel.AddressListBean> listBeans = new ArrayList<>();

                for (AddressBean addressBean : addressBeanList) {
                    AddClientModel.AddressListBean addressListBean = new AddClientModel.AddressListBean();
                    addressListBean.setAddressName(addressBean.getAddressName());
                    addressListBean.setAddress(addressBean.getAddressInfo() + " " + addressBean.getAddress());
                    addressListBean.setLatitude(addressBean.getLatitude() + "");
                    addressListBean.setLongitude(addressBean.getLongitude() + "");
                    listBeans.add(addressListBean);
                }
                addClientModel.setAddressList(listBeans);
                addClientActivityPresenter.addKh(addClientModel);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Log.i(TAG, "onActivityResult====");
            String addressJson = data.getStringExtra(Config.ADDRESS_JSON);
            AddressBean addressBean = Utils.gsonInstane().fromJson(addressJson, AddressBean.class);
            LSettingItem addressName = (LSettingItem) addClientAddressAdapter.getViewByPosition(selectPosition, R.id.address_name);
            if (!TextUtils.isEmpty(addressName.getEditText())) {
                addressBean.setAddressName(addressName.getEditText());
            }
            addressBeanList.set(selectPosition, addressBean);
            addClientAddressAdapter.setList(addressBeanList);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AreaEvent event) {
        if (event != null) {
            List<AreaModel.DataBean> data = event.dataBean.getData();
            subAreaList.clear();
            for (AreaModel.DataBean dataBean : data) {
                subAreaList.add(dataBean.getName());
            }
            BottomMenu.show(AddClientActivity.this, subAreaList, new OnMenuItemClickListener() {
                @Override
                public void onClick(String text, int index) {
                    honeycombGridId = data.get(index).getId();
                    subAreaStr = text;
                    locationType.setRightText(areaStr + "/" + subAreaStr);
                }
            });
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

    @Override
    public void startLoad() {
        WaitDialog.show(this, "添加中...");
    }

    @Override
    public void successLoad(String data) {
        WaitDialog.dismiss();
        Utils.showCenterTomast("新增成功");
        finish();
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
