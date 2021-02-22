package pro.haichuang.tzs144.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.DemandListAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AllocationShopModel;
import pro.haichuang.tzs144.model.GoodBeanModel;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.ShopModelEvent;
import pro.haichuang.tzs144.presenter.DemandListActivityPresenter;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 填写需求单
 */
public class DemandListActivity extends BaseActivity implements ILoadDataView<String> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.start_time)
    LSettingItem startTime;
    @BindView(R.id.end_time)
    LSettingItem endTime;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.add_shop)
    Button addShop;
    private DemandListAdapter demandListAdapter;
    private List<String> listData;
    private Date startDate;
    private Date endDate;
    private DemandListActivityPresenter demandListActivityPresenter;

    private List<GoodBeanModel>goodBeanModelList;
    private List<CharSequence>goodList;
    private ShopModel shopModel;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_demand_list;
    }

    @Override
    protected void setUpView() {
      title.setText("填写需求单");
        tips.setVisibility(View.VISIBLE);
        tips.setText("需求记录");
        tips.setTextSize(12);

        demandListAdapter = new DemandListAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(demandListAdapter);
        demandListAdapter.addChildClickViewIds(R.id.delete);
        demandListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.delete:
                        listData.remove(0);
                      //  demandListAdapter.setList(listData);
                        break;
                }
            }
        });
        startTime.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                TimePickerView pvTime = new TimePickerBuilder(DemandListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startDate = date;
                        startTime.setRightText(Utils.transformTime2(date));
                    }
                }) .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                        .isCyclic(true)//是否循环滚动
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                        .build();
                pvTime.show();

            }
        });
        endTime.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                TimePickerView pvTime = new TimePickerBuilder(DemandListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endDate = date;
                        endTime.setRightText(Utils.transformTime2(date));
                    }
                }) .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                        .isCyclic(true)//是否循环滚动
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                        .build();
                pvTime.show();
            }
        });
    }

    @Override
    protected void setUpData() {
        demandListActivityPresenter = new DemandListActivityPresenter(this);
        demandListActivityPresenter.findDemandGoods("");
        goodList = new ArrayList<>();
        goodBeanModelList = new ArrayList<>();
    }


    @OnClick({R.id.back, R.id.tips, R.id.input_btn, R.id.cancel_btn,R.id.add_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                Intent intent = new Intent(this,DemandRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.input_btn:
                if (startDate==null){
                    Utils.showCenterTomast("请选择预计送达开始时间");
                    return;
                }
                if (endDate==null){
                    Utils.showCenterTomast("请选择预计送达结束时间");
                    return;
                }
                if (endDate.getTime() - startDate.getTime() <=0){
                    Utils.showCenterTomast("预计送达结束时间不能小于开始时间");
                    return;
                }
                demandListActivityPresenter.demand(startTime.getRightText(),endTime.getRightText(),goodBeanModelList);
                break;
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.add_shop:
                BottomMenu.show(this, goodList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        ShopModel.DataBean dataBean = shopModel.getData().get(index);
                        goodBeanModelList.add(new GoodBeanModel(dataBean.getId()+"",0));
                        demandListAdapter.setList(goodBeanModelList);
                    }
                });
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShopModelEvent event) {
        if (event != null) {
             shopModel = event.shopModel;
            if (shopModel!=null &&  shopModel.getData()!=null){
               for (ShopModel.DataBean dataBean : shopModel.getData()){
                   goodList.add(dataBean.getName() + "   "+ dataBean.getSpecs());
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
