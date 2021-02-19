package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.DepositManagementSearchActivity;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class AddOrderDepositDialog extends DialogFragment {


    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.guarantee_persion_phone)
    LSettingItem guaranteePersionPhone;
    @BindView(R.id.deposit_items)
    LSettingItem depositItems;
    @BindView(R.id.mortgage_price)
    LSettingItem mortgagePrice;
    @BindView(R.id.mortgage_num)
    LSettingItem mortgageNum;
    @BindView(R.id.total_pr)
    LSettingItem totalPr;
    @BindView(R.id.mortgage_type)
    LSettingItem mortgageType;
    @BindView(R.id.finish_btn)
    Button finishBtn;
    private View view;

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public AddOrderDepositDialog(Context mContext) {
        this.context = mContext;
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
            view = inflater.inflate(R.layout.dialog_add_order_deposit, container, false);
            initView();
            ButterKnife.bind(this, view);
        }
        return view;
    }

    private void initView() {
        guaranteePersionPhone.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

            }
        });

        depositItems.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

            }
        });

        mortgageType.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

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


    /**
     * 添加押金本
     *
     * @param number
     * @param numCount
     * @param endNumber
     */
    public void addDepositBook(String number, String numCount, String endNumber) {

        Map<String, Object> params = new ArrayMap<>();
        params.put("number", number);
        params.put("numCount", numCount);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_SHOP, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {

            }

            @Override
            public void error(String error) {

            }
        });

    }

    @OnClick({R.id.delete, R.id.finish_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete:
                dismiss();
                break;
            case R.id.finish_btn:
                dismiss();
                break;
        }
    }
}