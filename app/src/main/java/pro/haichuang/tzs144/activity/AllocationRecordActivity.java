package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AllocationRecordAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AllocationRecordModel;
import pro.haichuang.tzs144.presenter.AllocationRecordActivityPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 调拨记录
 */
public class AllocationRecordActivity extends BaseActivity implements ILoadDataView<List<AllocationRecordModel.DataBean>> {


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
    private AllocationRecordAdapter allocationRecordAdapter;
    private AllocationRecordActivityPresenter allocationRecordActivityPresenter;
    private int currentPage = 1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_allocation_record;
    }

    @Override
    protected void setUpView() {
        title.setText("调拨记录");
        allocationRecordAdapter = new AllocationRecordAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(allocationRecordAdapter);
    }

    @Override
    protected void setUpData() {
        allocationRecordActivityPresenter = new AllocationRecordActivityPresenter(this);
        allocationRecordActivityPresenter.findAllotGoodsLog(currentPage);
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
    public void successLoad(List<AllocationRecordModel.DataBean> data) {
        WaitDialog.dismiss();
        if (data!=null && data.size()>0){
            emptyView.setVisibility(View.GONE);
            allocationRecordAdapter.setList(data);
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
