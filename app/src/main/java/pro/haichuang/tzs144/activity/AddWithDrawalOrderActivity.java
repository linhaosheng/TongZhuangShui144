package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
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
import pro.haichuang.tzs144.view.WithDrawalDepositDialog;

/**
 * 新增退押记录
 */
public class AddWithDrawalOrderActivity extends BaseActivity implements ILoadDataView<List<WithDrawalOrderModel.DataBean>> ,SwipeRefreshLayout.OnRefreshListener{


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
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private AddWithDrawalOrderAdapter addWithDrawalOrderAdapter;
    private AddWithDrawalOrderActivityPresenter addWithDrawalOrderActivityPresenter;
    private SaleDataModel.DataBean dataBean;
    private int currentPage = 1;
    private boolean lastPage;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_with_drawal_order;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
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
        addWithDrawalOrderAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage){
                    currentPage++;
                    addWithDrawalOrderActivityPresenter.findByKhReturnDeposits(searchEdit.getText().toString(), currentPage);
                }
            }
        });
        addWithDrawalOrderAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        addWithDrawalOrderAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }

    @Override
    protected void setUpData() {
        addWithDrawalOrderActivityPresenter = new AddWithDrawalOrderActivityPresenter(this);
//        String dataBeanJson = getIntent().getStringExtra(Config.PERSION_INFO);
//        if (dataBeanJson!=null){
//            dataBean = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);
//            name.setText(dataBean.getName());
//            phone.setText(dataBean.getPhone());
//            address.setText(dataBean.getAddressName());
//            addressDetail.setText(dataBean.getAddress());
//            addWithDrawalOrderActivityPresenter.findByKhReturnDeposits(dataBean.getId()+"");
//        }
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
                   currentPage = 1;
                   lastPage = false;
                   addWithDrawalOrderActivityPresenter.findByKhReturnDeposits(searchEdit.getText().toString(),currentPage);
               }
            }
        });
    }


    @OnClick({R.id.back,R.id.with_drawal_btn,R.id.address_detail})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.with_drawal_btn:
                StringBuilder idBuilder = new StringBuilder();
                StringBuilder content = new StringBuilder();

                for (WithDrawalOrderModel.DataBean dataBean : addWithDrawalOrderAdapter.getData()){
                    if (dataBean.isChecked()){
                        idBuilder.append(dataBean.getId()).append(",");
                        content.append("开押数量 : "+dataBean.getNum()).append("\n")
                                .append("开押金额 : "+dataBean.getTotalPrice()).append("\n\n");

                    }
                }

                if (idBuilder.length()<=0){
                    Utils.showCenterTomast("请选择退押选项");
                    return;
                }
                WithDrawalDepositDialog  withDrawalDepositDialog = new WithDrawalDepositDialog(AddWithDrawalOrderActivity.this, new WithDrawalDepositDialog.AddDepositListener() {
                    @Override
                    public void addDespositStatus(String ids,String returnCount,String returnPrice) {
                        Log.i("TAG===","ids==="+ids+"====returnCount=="+returnCount+"-=====returnPrice=="+returnPrice);
                        addWithDrawalOrderActivityPresenter.returnDeposits(ids,returnCount,returnPrice);
                    }
                });
                withDrawalDepositDialog.setData(addWithDrawalOrderAdapter.getData());
                withDrawalDepositDialog.show(getSupportFragmentManager(),"");

                break;
            case R.id.address_detail:
                Intent intent1 = new Intent(this,SelectAddressActivity.class);
                if (dataBean!=null){
                    intent1.putExtra("customerId",dataBean.getId());
                }
                startActivityForResult(intent1,1000);
                break;
        }
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
      //  WaitDialog.show(this,"加载中...");
    }

    @Override
    public void successLoad(List<WithDrawalOrderModel.DataBean> data) {
        refresh.setRefreshing(false);

        if (data==null || data.size()==0){
            lastPage = true;
        }
        if (currentPage==1){
            if (data==null || data.size()==0){
                emptyView.setVisibility(View.VISIBLE);
                withDrawalBtn.setVisibility(View.GONE);
            }else {
                emptyView.setVisibility(View.GONE);
                withDrawalBtn.setVisibility(View.VISIBLE);
            }
            addWithDrawalOrderAdapter.setList(data);
            if (data.size()<10){
                lastPage = true;
                addWithDrawalOrderAdapter.getLoadMoreModule().loadMoreEnd();
            }

        }else {
            addWithDrawalOrderAdapter.addData(data);
            addWithDrawalOrderAdapter.getLoadMoreModule().loadMoreComplete();
        }

        if (lastPage){
            addWithDrawalOrderAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
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
                Utils.showCenterTomast("新增失败 : "+event.result);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (resultCode==1000){

                Log.i(TAG,"initAddressInfo");
                String dataBeanJson = data.getStringExtra(Config.PERSION_INFO);
                SaleDataModel.DataBean dataBean1 = Utils.gsonInstane().fromJson(dataBeanJson, SaleDataModel.DataBean.class);

                address.setText(dataBean1.getAddressName());
                addressDetail.setText(dataBean1.getAddress());

                dataBean.setAddress(dataBean1.getAddress());
                dataBean.setAddressName(dataBean1.getAddressName());
                dataBean.setId(dataBean1.getId());
                dataBean.setLatitude(dataBean1.getLatitude()+"");
                dataBean.setLongitude(dataBean1.getLongitude()+"");
            }
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        lastPage = false;
        addWithDrawalOrderActivityPresenter.findByKhReturnDeposits(searchEdit.getText().toString(),currentPage);
    }


}
