package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.SaleSummaryAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleSummaryModel;
import pro.haichuang.tzs144.model.SummaryModel;
import pro.haichuang.tzs144.model.TypeListModel;
import pro.haichuang.tzs144.presenter.SaleSummaryPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 销售汇总
 */
public class SaleSummaryActivity extends BaseActivity implements ILoadDataView<List<SaleSummaryModel.DataBean>> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.start_time)
    LSettingItem start_time;
    @BindView(R.id.end_time)
    LSettingItem end_time;
    @BindView(R.id.shop_type)
    LSettingItem shop_type;
    @BindView(R.id.shop_id)
    LSettingItem shop_id;
    @BindView(R.id.recycle_data)
    RecyclerView recycle_data;


    private List<CharSequence> shopList;
    private TypeListModel typeListModel;
    private SaleSummaryPresenter saleSummaryPresenter;
    public static String startTime;
    public static String endTime;
    public static String categoryId = "";
    public static String goodsId;
    private SaleSummaryAdapter saleSummaryAdapter;

    private int currentPage = 1;
    private boolean lastPage;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sale_summary;
    }

    @Override
    protected void setUpView() {
        title.setText("销售汇总");
        /**
         * 开始时间
         */
        start_time.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                TimePickerView pvTime = new TimePickerBuilder(SaleSummaryActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startTime = Utils.transformTime3(date);
                        start_time.setRightText(Utils.formatSelectTime(date));
                        currentPage = 1;
                        lastPage = false;
                        if (!TextUtils.isEmpty(endTime)){
                            saleSummaryPresenter.findOrderSumList(startTime,endTime,categoryId,goodsId,currentPage);
                        }
                    }
                })
                        .build();
                pvTime.show();
            }
        });

        /**
         * 结束时间
         */
        end_time.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                TimePickerView pvTime = new TimePickerBuilder(SaleSummaryActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        currentPage = 1;
                        lastPage = false;
                        endTime = Utils.transformTime3(date);
                        end_time.setRightText(Utils.formatSelectTime(date));
                        saleSummaryPresenter.findOrderSumList(startTime,endTime,categoryId,goodsId,currentPage);
                    }
                })
                        .build();
                pvTime.show();
            }
        });


        /**
         * 商品品类
         */
        shop_type.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(SaleSummaryActivity.this, shopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        currentPage = 1;
                        lastPage = false;
                        shop_type.setRightText(text);
                        categoryId = typeListModel.getData().get(index).getId() +"";
                        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                            saleSummaryPresenter.findOrderSumList(startTime,endTime,categoryId,goodsId,currentPage);
                        }
                    }
                });
            }
        });

        /**
         * 商品ID
         */
        shop_id.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                Intent intent = new Intent(SaleSummaryActivity.this,FindGoodsActivity.class);
                intent.putExtra("categoryId",categoryId);
                startActivityForResult(intent,10000);
            }
        });

        saleSummaryAdapter = new SaleSummaryAdapter();
        recycle_data.setAdapter(saleSummaryAdapter);
        recycle_data.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        saleSummaryAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        saleSummaryAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        saleSummaryAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!lastPage) {
                    currentPage++;
                    saleSummaryPresenter.findOrderSumList(startTime,endTime,categoryId,goodsId,currentPage);
                }
            }
        });

    }

    @Override
    protected void setUpData() {

        saleSummaryPresenter =  new SaleSummaryPresenter(this);

        shopList = new ArrayList<>();

        /**
         * 商品品类
         */
        String categoryListJson = SPUtils.getString(Config.GOODS_CATEGORY_LIST, "");
        if (!categoryListJson.equals("")) {
            shopList = new ArrayList<>();
            //   shopList.add("全部");
            typeListModel = Utils.gsonInstane().fromJson(categoryListJson, TypeListModel.class);
            for (TypeListModel.DataBean dataBean : typeListModel.getData()) {
                shopList.add(dataBean.getName());
            }
        }
    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this, "加载中...");
    }

    @Override
    public void successLoad(List<SaleSummaryModel.DataBean> datas) {
        WaitDialog.dismiss();
        if (datas == null || datas.size() == 0) {
            lastPage = true;
        }
        if (datas.size() < 10) {
            lastPage = true;
            saleSummaryAdapter.getLoadMoreModule().loadMoreEnd();
        }
        if (currentPage==1){
            saleSummaryAdapter.setList(datas);
        }else {
            saleSummaryAdapter.addData(datas);
            saleSummaryAdapter.getLoadMoreModule().loadMoreComplete();
        }

        if (lastPage) {
            saleSummaryAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
        WaitDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10000 && resultCode==RESULT_OK){
            try {
                shop_id.setRightText(data.getStringExtra("goodsName"));
                currentPage = 1;
                lastPage = false;
                goodsId = String.valueOf(data.getIntExtra("goodsId",0));
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                    saleSummaryPresenter.findOrderSumList(startTime,endTime,categoryId,goodsId,currentPage);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
