package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DemandRecordAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.DemandRecordModel;
import pro.haichuang.tzs144.presenter.DemandRecordActivityPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 需求记录
 */
public class DemandRecordActivity extends BaseActivity implements ILoadDataView<List<DemandRecordModel.DataBean>> {


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
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.empty_info)
    TextView emptyInfo;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    private DemandRecordAdapter demandRecordAdapter;
    private int currentPage = 1;
    private DemandRecordActivityPresenter demandRecordActivityPresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_demand_record;
    }

    @Override
    protected void setUpView() {

        title.setText("需求记录");
        demandRecordAdapter = new DemandRecordAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(demandRecordAdapter);
    }

    @Override
    protected void setUpData() {
        demandRecordActivityPresenter = new DemandRecordActivityPresenter(this);
        demandRecordActivityPresenter.findDemands(currentPage);
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"加载中...");
    }

    @Override
    public void successLoad(List<DemandRecordModel.DataBean> data) {
        WaitDialog.dismiss();
        if (data!=null && data.size()>0){
            emptyView.setVisibility(View.GONE);
            List<DemandRecordModel.DataBean> dataBeans = new ArrayList<>();
            for (DemandRecordModel.DataBean dataBean : data){
                dataBeans.add(dataBean);
            }
            demandRecordAdapter.setList(data);
        }else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
