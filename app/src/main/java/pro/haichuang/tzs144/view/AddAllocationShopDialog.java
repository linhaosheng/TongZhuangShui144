package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddShopDialogAdapter;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.model.ShopModelEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;


/**
 * 添加商品
 */
public class AddAllocationShopDialog extends DialogFragment {


    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.input_btn)
    Button inputBtn;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    private AddShopDialogAdapter addShopDialogAdapter;
    private View view;
    private Context context;
    private SelectShopListener selectShopListener;
    private int selectShopPosition;
    private List<ShopModel.DataBean>dataBeans;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public AddAllocationShopDialog(Context mContext,List<ShopModel.DataBean>dataBeans, SelectShopListener selectShopListener) {
        this.context = mContext;
        this.selectShopListener = selectShopListener;
        this.dataBeans = dataBeans;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        //window.setWindowAnimations(R.style.BottomDialog_Animation);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // getDialog().getWindow().setLayout(dm.widthPixels, (int) (dm.heightPixels * 0.28));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.dialog_add_shop, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        return view;
    }

    private void initView() {
        addShopDialogAdapter = new AddShopDialogAdapter(context);
        recycleData.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(addShopDialogAdapter);
        addShopDialogAdapter.setList(dataBeans);
        addShopDialogAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ShopModel.DataBean dataBean = addShopDialogAdapter.getData().get(position);
                List<ShopModel.DataBean> data = addShopDialogAdapter.getData();
                if (dataBean.isCheck()){
                    dataBean.setCheck(false);
                }else {
                    dataBean.setCheck(true);
                }
                if (dataBean.isCheck()){
                    selectShopPosition = position;
                }
                data.set(position,dataBean);
                addShopDialogAdapter.setList(data);
            }
        });

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
                    findDemandGoods(searchEdit.getText().toString());
                }
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    @OnClick({R.id.cancel_btn, R.id.input_btn})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.input_btn:
                if (selectShopListener!=null){
                    List<ShopModel.DataBean>dataBeanList = new ArrayList<>();
                    for (ShopModel.DataBean dataBean : addShopDialogAdapter.getData()){
                        if (dataBean.isCheck()){
                            dataBeanList.add(dataBean);
                        }
                    }
                    selectShopListener.selectShop(dataBeanList);
                }
                dismiss();
                //    addDepositBook();
                break;
        }
    }


    public interface  SelectShopListener{
        void selectShop(List<ShopModel.DataBean> dataBeanList);
    }


    /**
     * [实时库存]获取需求单商品列表
     * @param query
     */
    public void findDemandGoods(String query){
        Map<String,Object>params = new ArrayMap<>();
        params.put("query",query);
        HttpRequestEngine.postRequest(ConfigUrl.FIND_DEMAND_GOODS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                ShopModel shopModel = Utils.gsonInstane().fromJson(result, ShopModel.class);
                addShopDialogAdapter.setList(shopModel.getData());
            }

            @Override
            public void error(String error) {

            }
        });
    }
}
