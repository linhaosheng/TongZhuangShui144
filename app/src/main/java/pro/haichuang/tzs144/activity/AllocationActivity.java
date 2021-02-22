package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import pro.haichuang.tzs144.adapter.DemandListAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AllocationShopEvent;
import pro.haichuang.tzs144.model.AllocationShopModel;
import pro.haichuang.tzs144.model.GoodBeanModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.SubjectModel;
import pro.haichuang.tzs144.presenter.AllocationActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 调拨
 */
public class AllocationActivity extends BaseActivity implements ILoadDataView<String> {


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
    @BindView(R.id.subject)
    LSettingItem subject;
    @BindView(R.id.add_shop)
    Button addShop;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;

    private DemandListAdapter demandListAdapter;
    private List<CharSequence> subjectList;
    private String subjectId;
    private SubjectModel subjectModel;
    private AllocationActivityPresenter allocationActivityPresenter;
    private AllocationShopModel allocationShopModel;
    private List<GoodBeanModel>goodBeanModelList;
    private List<CharSequence>goodList;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_allocation;
    }

    @Override
    protected void setUpView() {
        title.setText("调拨");
        tips.setText("调拨记录");
        tips.setVisibility(View.VISIBLE);
        tips.setTextSize(12);

        demandListAdapter = new DemandListAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(demandListAdapter);
        demandListAdapter.addChildClickViewIds(R.id.delete);
        demandListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        // listData.remove(0);
                        // demandListAdapter.setList(listData);
                        break;
                }
            }
        });

        String subjectListJson = SPUtils.getString(Config.SUBJECT_LIST, "");
        if (!subjectListJson.equals("")) {
            subjectList = new ArrayList<>();
            subjectModel = Utils.gsonInstane().fromJson(subjectListJson, SubjectModel.class);
            for (SubjectModel.DataBean dataBean : subjectModel.getData()) {
                subjectList.add(dataBean.getName());
            }
        }

        goodBeanModelList = new ArrayList<>();
        subject.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                if (subjectList != null) {
                    BottomMenu.show(AllocationActivity.this, subjectList, new OnMenuItemClickListener() {
                        @Override
                        public void onClick(String text, int index) {
                            subjectId = subjectModel.getData().get(index).getId();
                            subject.setRightText(text);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void setUpData() {

        allocationActivityPresenter = new AllocationActivityPresenter(this);
        allocationActivityPresenter.findAllotGoods("");

    }


    @OnClick({R.id.back, R.id.tips, R.id.add_shop, R.id.input_btn, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                Intent intent = new Intent(this, AllocationRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.add_shop:
                BottomMenu.show(this, goodList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        AllocationShopModel.DataBean dataBean = allocationShopModel.getData().get(index);
                        goodBeanModelList.add(new GoodBeanModel(dataBean.getId(),0));
                        demandListAdapter.setList(goodBeanModelList);

                    }
                });
                //listData.add("");
                //demandListAdapter.setList(listData);
                break;
            case R.id.input_btn:

                if (subjectId==null){
                    Utils.showCenterTomast("请选择库存主体");
                    return;
                }
                if (goodBeanModelList==null || goodBeanModelList.size()==0){
                    Utils.showCenterTomast("请添加商品");
                    return;
                }
                allocationActivityPresenter.allotGoods(subjectId,goodBeanModelList);
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AllocationShopEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            allocationShopModel = event.allocationShopModel;
            goodList = new ArrayList<>();
            if (allocationShopModel.getData()!=null){
                for (AllocationShopModel.DataBean dataBean : allocationShopModel.getData()){
                    goodList.add(dataBean.getGoodsName() + "   "+dataBean.getSpecsName());
                }
            }
        }
        Log.i(TAG, "onMessageEvent===");
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
        WaitDialog.show(this,"提交中...");
    }

    @Override
    public void successLoad(String data) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(data);
        finish();
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
