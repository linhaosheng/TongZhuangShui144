package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongzue.dialog.v3.WaitDialog;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.presenter.AddMainTainRecordActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 新增维护记录
 */
public class AddMainTainRecordActivity extends BaseActivity implements ILoadDataView<String> {


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
    @BindView(R.id.main_tain_time)
    TextView mainTainTime;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.client_attr)
    TextView clientAttr;

    private ClientDetailModel.DataBean dataBean;
    private AddMainTainRecordActivityPresenter addMainTainRecordActivityPresenter;
    private long distanceData;
    private String customerId;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_main_tain_record;
    }

    @Override
    protected void setUpView() {
        title.setText("新增维护记录");

    }

    @Override
    protected void setUpData() {
        addMainTainRecordActivityPresenter = new AddMainTainRecordActivityPresenter(this);

        String dataJson = getIntent().getStringExtra("dataJson");
        if (dataJson!=null){
            dataBean = Utils.gsonInstane().fromJson(dataJson,ClientDetailModel.DataBean.class);
            clientName.setText("客户名称："+dataBean.getCustomerName());
            clientType.setText("客户类型："+dataBean.getCustomerType());
            clientPersion.setText("   联系人："+dataBean.getContacts());
            clientPhone.setText("联系电话："+dataBean.getPhone());
            mainTainTime.setText("维护时间："+Utils.transformTime(new Date()));

            customerId = dataBean.getId() +"";

            List<ClientDetailModel.DataBean.AddressListBean> addressList = dataBean.getAddressList();
            if (addressList!=null){
                ClientDetailModel.DataBean.AddressListBean addressListBean = addressList.get(0);
                try {
                    double longitude = Double.parseDouble(addressListBean.getLongitude());
                    double latitude = Double.parseDouble(addressListBean.getLatitude());
                    distanceData = (int)Utils.GetDistance(Config.LONGITUDE, Config.LATITUDE, longitude, latitude);

                    if (distanceData<=1000){
                        distance.setText("   距客户："+distanceData+"米");
                    }else {
                        distance.setText("   距客户："+(float)distanceData/1000+"千米");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }


    @OnClick({R.id.back, R.id.input_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.input_btn:

                if (TextUtils.isEmpty(edit.getText())){
                    Utils.showCenterTomast("请输入维护记录");
                    return;
                }
                addMainTainRecordActivityPresenter.saveMaintainLog("100",customerId,edit.getText().toString(),String.valueOf(distanceData),Utils.transformTime(new Date()));

                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"提交中...");
    }

    @Override
    public void successLoad(String data) {
        WaitDialog.dismiss();
        Utils.showCenterTomast("提交成功");
        finish();
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast("提交失败");
    }
}
