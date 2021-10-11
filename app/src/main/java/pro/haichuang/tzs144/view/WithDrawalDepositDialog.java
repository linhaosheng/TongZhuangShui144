package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.UpdateWithDrawalAdapter;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;


/**
 *
 */
public class WithDrawalDepositDialog extends DialogFragment {


    @BindView(R.id.recycle_data)
    RecyclerView recycle_data;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.input_btn)
    Button inputBtn;
    private View view;
    private UpdateWithDrawalAdapter updateWithDrawalAdapter;

    private Context context;
    private AddDepositListener addDepositListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public WithDrawalDepositDialog(Context mContext, AddDepositListener mAddDepositListener) {
        this.context = mContext;
        this.addDepositListener = mAddDepositListener;
        updateWithDrawalAdapter = new UpdateWithDrawalAdapter();
    }

    public void setData(List<WithDrawalOrderModel.DataBean> data){

      List<WithDrawalOrderModel.DataBean> tempData = new ArrayList<>();
        for (WithDrawalOrderModel.DataBean dataBean : data){
            if (dataBean.isChecked()){
                tempData.add(dataBean);
            }
        }
        updateWithDrawalAdapter.setList(tempData);
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.dialog_with_drawal_deposit, container, false);
            ButterKnife.bind(this, view);
        }
        recycle_data.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        recycle_data.setAdapter(updateWithDrawalAdapter);
        return view;
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

                StringBuilder returnCountBuilder = new StringBuilder();
                StringBuilder returnPriceBuilder  = new StringBuilder();
                StringBuilder idBuilder = new StringBuilder();
                for (int i = 0;i<updateWithDrawalAdapter.getData().size();i++){
                   EditText editTextNum =  (EditText)updateWithDrawalAdapter.getViewByPosition(i,R.id.num);
                   EditText editTextMoney =  (EditText)updateWithDrawalAdapter.getViewByPosition(i,R.id.money);

                    returnCountBuilder.append(editTextNum.getText().toString()).append(",");
                    returnPriceBuilder.append(editTextMoney.getText().toString()).append(",");

                    WithDrawalOrderModel.DataBean dataBean = updateWithDrawalAdapter.getData().get(i);
                    idBuilder.append(dataBean.getId()).append(",");
                }

                String ids = idBuilder.substring(0, idBuilder.toString().length() - 1);
                String returnCount = returnCountBuilder.substring(0, returnCountBuilder.toString().length() - 1);
                String returnPrice = returnPriceBuilder.substring(0, returnPriceBuilder.toString().length() - 1);
                addDepositListener.addDespositStatus(ids,returnCount,returnPrice);
                dismiss();
                break;
        }
    }

    public static interface AddDepositListener{
        void addDespositStatus(String ids,String returnCount,String returnPrice);
    }
}
