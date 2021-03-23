package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kongzue.dialog.v3.WaitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DepositDetailAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.DeposiDetailModel;
import pro.haichuang.tzs144.presenter.DepositManagementDetailPresenter;
import pro.haichuang.tzs144.util.Utils;

public class DepositManagementDetailActivity extends BaseActivity implements ILoadDataView<DeposiDetailModel>,SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.deposit_info)
    TextView depositInfo;
    @BindView(R.id.deposit_view)
    LinearLayout depositView;
    @BindView(R.id.qiantiao_info)
    TextView qiantiaoInfo;
    @BindView(R.id.qiantiao_view)
    LinearLayout qiantiaoView;
    @BindView(R.id.owe_info)
    TextView oweInfo;
    @BindView(R.id.owe_view)
    LinearLayout oweView;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.empty_info)
    TextView emptyInfo;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private DepositDetailAdapter depositDetailAdapter;
    private DepositManagementDetailPresenter depositManagementDetailPresenter;
    private String id;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_deposit_detail;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        depositDetailAdapter = new DepositDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(depositDetailAdapter);
    }

    @Override
    protected void setUpData() {
        id = getIntent().getStringExtra("id");
        depositManagementDetailPresenter = new DepositManagementDetailPresenter(this);
        depositManagementDetailPresenter.getDepositBookInfo(id);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (searchEdit.getText()!=null){
                  depositManagementDetailPresenter.getDepositBookInfo(searchEdit.getText().toString());
              }
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(DepositManagementDetailActivity.this);
                }
                return false;
            }
        });
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
       // WaitDialog.show(this, "加载中");
    }

    @Override
    public void successLoad(DeposiDetailModel data) {
        refresh.setRefreshing(false);
      //  WaitDialog.dismiss();
        if (data!=null && data.getData()!=null){
            DeposiDetailModel.DataBean data1 = data.getData();

            String deposit_info = "开押数量："+data1.getYjKyNum()
                    +"       商品数量："+ data1.getYjGoodsNum()+"       收款金额："
                    +data1.getYjPrice()+"元  \n退押数量： "
                    +data1.getYjTyNum()+"      退押金额："+data1.getYtTyPrice()+"元";

            String qiantiao_info =  "开押数量："+data1.getJtKyNum()
                    +"       商品数量："+ data1.getJtGoodsNum()+"       收款金额："
                    +data1.getJtPrice()+"元  \n退押数量： "
                    +data1.getJtTyNum()+"      退押金额："+data1.getJtTyPrice()+"元";

            String owe_info = "开押数量："+data1.getJtKyNum()
                    +"       商品数量："+ data1.getJtGoodsNum()+"       收款金额："
                    +data1.getJtPrice()+"元  \n退押数量： "
                    +data1.getJtTyNum()+"      退押金额："+data1.getJtTyPrice()+"元";

            qiantiaoInfo.setText(qiantiao_info);
            depositInfo.setText(deposit_info);
            oweInfo.setText(owe_info);

           depositDetailAdapter.setList(data1.getList());
        }

    }

    @Override
    public void errorLoad(String error) {
       // WaitDialog.dismiss();
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);

    }

    @Override
    public void onRefresh() {
        if (TextUtils.isEmpty(searchEdit.getText())){
            depositManagementDetailPresenter.getDepositBookInfo(id.toString());
        }else {
            depositManagementDetailPresenter.getDepositBookInfo(searchEdit.getText().toString());
        }
    }
}
