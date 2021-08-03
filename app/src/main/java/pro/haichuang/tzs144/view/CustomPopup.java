package pro.haichuang.tzs144.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.StaffModel;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;

public class CustomPopup extends PartShadowPopupView{

    TextView startTime;
    TextView endTime;
    Spinner inventory_subject;
    private StaffModel staffModel = null;
    private List<String> businessList;
    private ArrayAdapter<String> arr_adapter;
    private Context context;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;
    private FilterInterface filterInterface;
    private String Id;

    //注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
    public CustomPopup(@NonNull Context context,FilterInterface filterInterface) {
        super(context);
        this.context = context;
        this.filterInterface = filterInterface;
    }
    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_popup;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        endTime.setText(Utils.formatSelectTime(new Date()));
        inventory_subject = findViewById(R.id.inventory_subject);

        startTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime.setTextColor(Color.parseColor("#6D7278"));
                startTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn36));
                endTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn37));
                selectTime(SELECT_START_TIME);
            }
        });

        endTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime.setTextColor(Color.parseColor("#6D7278"));
                endTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn37));
                endTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn36));
                selectTime(SELECT_END_TIME);
            }
        });

        businessList = new ArrayList<>();
        String findStaffJson = SPUtils.getString(Config.FIND_STAFFS, "");
        if (!findStaffJson.equals("")) {
            staffModel = Utils.gsonInstane().fromJson(findStaffJson, StaffModel.class);
            for (StaffModel.DataBean dataBean : staffModel.getData()) {
                businessList.add(dataBean.getName());
            }
            Id = staffModel.getData().get(0).getId() +"";
        }

        //适配器
        arr_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, businessList);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        inventory_subject.setAdapter(arr_adapter);

        inventory_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Id = staffModel.getData().get(position).getId() +"";
                filterInterface.filter(startTime.getText().toString(),endTime.getText().toString(),Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    // 设置最大宽度，看需要而定，
    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }
    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }
    // 设置自定义动画器，看需要而定
    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }
    /**
     * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
     *
     * @return
     */
    protected int getPopupWidth() {
        return 0;
    }

    /**
     * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
     *
     * @return
     */
    protected int getPopupHeight() {
        return 0;
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
                filterInterface.filter(startTime.getText().toString(),endTime.getText().toString(),Id);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //把日期对话框显示在界面上
        dialog.show();
    }

    public interface FilterInterface{
        void filter(String startTime,String endTime,String id);
    }

}
