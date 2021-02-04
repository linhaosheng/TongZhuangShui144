package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongzue.dialog.v2.WaitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.presenter.HistoryWithDrawalOrderActivityPresenter;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 历史退押记录
 */
public class HistoryWithDrawalOrderActivity extends BaseActivity implements ILoadDataView<String> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
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
    @BindView(R.id.client_info)
    RelativeLayout clientInfo;
    @BindView(R.id.guarantee_persion_phone)
    LSettingItem guaranteePersionPhone;
    @BindView(R.id.deposit_number)
    LSettingItem depositNumber;
    @BindView(R.id.client_name)
    LSettingItem clientName;
    @BindView(R.id.mortgage_price)
    LSettingItem mortgagePrice;
    @BindView(R.id.mortgage_num)
    LSettingItem mortgageNum;
    @BindView(R.id.mortgage_type)
    LSettingItem mortgageType;
    @BindView(R.id.with_drawal_btn)
    Button withDrawalBtn;
    @BindView(R.id.deposit_money)
    EditText depositMoney;

    private HistoryWithDrawalOrderActivityPresenter historyWithDrawalOrderActivityPresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_history_with_drawal;
    }

    @Override
    protected void setUpView() {
       title.setText("退押");
    }

    @Override
    protected void setUpData() {
        historyWithDrawalOrderActivityPresenter = new HistoryWithDrawalOrderActivityPresenter(this);

    }



    @OnClick({R.id.back, R.id.with_drawal_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.with_drawal_btn:
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
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }
}
