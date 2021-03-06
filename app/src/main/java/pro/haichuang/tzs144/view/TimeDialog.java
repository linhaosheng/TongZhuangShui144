package pro.haichuang.tzs144.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ShopModel;
import pro.haichuang.tzs144.util.Utils;


/**
 * 添加商品
 */
public class TimeDialog extends DialogFragment {


    @BindView(R.id.cancel_txt)
    TextView cancelTxt;
    @BindView(R.id.yes_txt)
    TextView yesTxt;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    private SelectTimeListener selectTimeListener;
    private View view;
    private Context context;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public TimeDialog(Context mContext, SelectTimeListener mSelectTimeListener) {
        this.context = mContext;
        this.selectTimeListener = mSelectTimeListener;
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
            view = inflater.inflate(R.layout.time_shop, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        return view;
    }

    private void initView() {
        startTime.setText("2020-10-10");
        endTime.setText(Utils.formatSelectTime(new Date()));
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

    @OnClick({R.id.cancel_txt, R.id.yes_txt,R.id.start_time, R.id.end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_txt:
                dismiss();
                break;
            case R.id.yes_txt:
                dismiss();
                if (selectTimeListener!=null){
                    selectTimeListener.selectTime(startTime.getText().toString(),endTime.getText().toString());
                }
                break;
            case R.id.start_time:
                startTime.setTextColor(Color.parseColor("#32C5FF"));
                endTime.setTextColor(Color.parseColor("#6D7278"));
                startTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn36));
                endTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn37));
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
                startTime.setTextColor(Color.parseColor("#6D7278"));
                endTime.setTextColor(Color.parseColor("#32C5FF"));
                endTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn36));
                startTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn37));
                selectTime(SELECT_END_TIME);
                break;
        }
    }

    private void selectTime(int type){

        Calendar calendar=Calendar.getInstance();
        //构建一个日期对话框，该对话框已经集成了日期选择器
        //DatePickerDialog的第二个构造参数指定了日期监听器
        DatePickerDialog dialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int currentMonth = month +1;
                Log.i("TAG===",currentMonth+"====");
                String monthStr = "";
                if (currentMonth<10){
                    monthStr = "0"+currentMonth;
                }else {
                    monthStr = String.valueOf(currentMonth);
                }
                if (type==SELECT_START_TIME){
                    startTime.setText(year+"-"+monthStr+"-"+dayOfMonth);
                }else {
                    endTime.setText(year+"-"+monthStr+"-"+dayOfMonth);
                }
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //把日期对话框显示在界面上
        dialog.show();
    }

    public interface SelectTimeListener {
        void selectTime(String startTime,String endTime);
    }

}
