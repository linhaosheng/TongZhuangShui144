package pro.haichuang.tzs144.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.SaleSearcAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleDataModel;
import pro.haichuang.tzs144.presenter.SaleSearchActivityPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import rxhttp.wrapper.utils.GsonUtil;

/**
 * 客户销售搜索页面
 */
public class SaleSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<List<SaleDataModel.DataBean>> {


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
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private SaleSearchActivityPresenter searchActivityPresenter;

    private SaleSearcAdapter saleSearcAdapter;
    private String type;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sales_search;
    }

    @Override
    protected void setUpView() {
        type = getIntent().getStringExtra("type");
        title.setText("客户搜索");
        tipImg.setVisibility(View.GONE);
        tipImg.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.more));
        refresh.setOnRefreshListener(this);
        saleSearcAdapter = new SaleSearcAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(saleSearcAdapter);
        saleSearcAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SaleDataModel.DataBean dataBean = saleSearcAdapter.getData().get(position);
                String dataStr = Utils.gsonInstane().toJson(dataBean);

                if (type != null && type.equals("add_with_drawal")) {

                    Intent intent = new Intent(SaleSearchActivity.this, AddWithDrawalOrderActivity.class);
                    intent.putExtra(Config.PERSION_INFO, dataStr);
                    startActivity(intent);
                } else if (type != null && type.equals("historical_deposit")) {
                    Intent intent1 = new Intent(SaleSearchActivity.this, HistoryWithDrawalOrderActivity.class);
                    intent1.putExtra(Config.PERSION_INFO, dataStr);
                    startActivity(intent1);
                } else if (type != null && type.equals("open_deposit")) {
                    Intent intent1 = new Intent(SaleSearchActivity.this, StartDepositActivity.class);
                    intent1.putExtra(Config.PERSION_INFO, dataStr);
                    startActivity(intent1);
                } else if (type != null && type.equals("add_order")) {
                    int order_type = getIntent().getIntExtra("order_type", 0);
                    Intent intent1 = new Intent(SaleSearchActivity.this, EnterOrderActivity.class);
                    intent1.putExtra("order_type", order_type);
                    intent1.putExtra(Config.PERSION_INFO, dataStr);
                    startActivity(intent1);

                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Config.PERSION_INFO, dataStr);
                    SaleSearchActivity.this.setResult(RESULT_OK, intent);
                    SaleSearchActivity.this.finish();
                }
            }
        });

    }

    @Override
    protected void setUpData() {
        searchActivityPresenter = new SaleSearchActivityPresenter(this);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchEdit.getText() != null) {
                    searchActivityPresenter.search(searchEdit.getText().toString(),type);
                }
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(SaleSearchActivity.this);
                }
                return false;
            }
        });
      // searchActivityPresenter.search("鸿运");
    }


    @OnClick({R.id.back, R.id.tip_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tip_img:
                break;
        }
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(List<SaleDataModel.DataBean> data) {
        refresh.setRefreshing(false);
        saleSearcAdapter.setList(data);
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
    }
}
