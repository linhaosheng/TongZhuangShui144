package pro.haichuang.tzs144.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.InventoryNumAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.InventoryNumModel;
import pro.haichuang.tzs144.presenter.InventoryNumFragmentPresenter;
import pro.haichuang.tzs144.util.Utils;

public class FindGoodsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ILoadDataView<InventoryNumModel> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.recycle_data)
    RecyclerView recycle_data;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private InventoryNumAdapter inventoryNumAdapter;
    private InventoryNumFragmentPresenter inventoryNumFragmentPresenter;
    private String categoryId = "";
    private String type;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_find_good;
    }

    @Override
    protected void setUpView() {
        title.setText("商品");
        refresh.setOnRefreshListener(this);
        categoryId = getIntent().getStringExtra("categoryId");
        type = "";

        inventoryNumAdapter = new InventoryNumAdapter();

        recycle_data.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycle_data.setAdapter(inventoryNumAdapter);

        inventoryNumFragmentPresenter = new InventoryNumFragmentPresenter(this);

        inventoryNumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                InventoryNumModel.DataBean.ListBean listBean = inventoryNumAdapter.getData().get(position);
                int goodsId = listBean.getId();
                String goodsName = listBean.getGoodsName();
                Intent intent = new Intent();
                intent.putExtra("goodsId", goodsId);
                intent.putExtra("goodsName", goodsName);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    inventoryNumFragmentPresenter.findGoodsWithType(search_edit.getText().toString().trim(), categoryId, type);
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        inventoryNumFragmentPresenter.findGoodsWithType("", categoryId, type);
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
    public void onRefresh() {
        inventoryNumFragmentPresenter.findGoodsWithType(search_edit.getText().toString().trim(), categoryId, type);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(InventoryNumModel data) {
        refresh.setRefreshing(false);
        inventoryNumAdapter.setList(data.getData().getList());
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }
}
