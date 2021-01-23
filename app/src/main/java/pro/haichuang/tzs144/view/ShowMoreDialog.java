package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.EnterOrderActivity;
import pro.haichuang.tzs144.activity.MainActivity;
import pro.haichuang.tzs144.activity.SalesListActivity;
import pro.haichuang.tzs144.activity.StartDepositActivity;
import pro.haichuang.tzs144.activity.WithDrawalOrderActivity;

public class ShowMoreDialog extends DialogFragment {


    @BindView(R.id.sale)
    TextView sale;
    @BindView(R.id.make_up_order)
    TextView makeUpOrder;
    @BindView(R.id.open_deposit)
    TextView openDeposit;
    @BindView(R.id.withdrawal)
    TextView withdrawal;
    private View view;

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

   public ShowMoreDialog(Context mContext){
        this.context = mContext;
   }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.y = 150;
        window.setAttributes(params);
        //window.setWindowAnimations(R.style.BottomDialog_Animation);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
       // getDialog().getWindow().setLayout(dm.widthPixels, (int) (dm.heightPixels * 0.28));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.dialog_show_more, container, false);
            ButterKnife.bind(this, view);
        }
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


    @OnClick({R.id.sale, R.id.make_up_order, R.id.open_deposit, R.id.withdrawal})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.sale:
                intent.setClass(context, SalesListActivity.class);
                break;
            case R.id.make_up_order:
                intent.setClass(context, EnterOrderActivity.class);
                break;
            case R.id.open_deposit:
                intent.setClass(context, StartDepositActivity.class);
                break;
            case R.id.withdrawal:
                intent.setClass(context, WithDrawalOrderActivity.class);
                break;
        }
        startActivity(intent);
    }
}
