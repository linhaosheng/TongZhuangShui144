package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 新增客户
 */
public class AddClientActivity extends BaseActivity {


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
    @BindView(R.id.address_name)
    LSettingItem addressName;
    @BindView(R.id.detail_address)
    LSettingItem detailAddress;

    private ClientTypeModel clientTypeModel;
    private List<CharSequence> clientlist;
    private List<CharSequence>monopolyList;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_client;
    }

    @Override
    protected void setUpView() {
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

                        }
                    });
            }
        });
    }

    @Override
    protected void setUpData() {

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
