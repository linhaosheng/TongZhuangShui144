package pro.haichuang.tzs144.view;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.ClientFilterAdapter;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.model.ClientTypeSearchModel;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;


/**
 * 添加押金本
 */
public class ClientFilterDialog extends DialogFragment {


    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.time)
    LinearLayout time;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.recycle_data_warn)
    RecyclerView recycleDataWarn;

    ClientTypeSearchModel clientTypeSearchModel;
    private ClientTypeListener clientTypeListener;
    private ClientFilterAdapter clientFilterAdapter;
    private ClientFilterAdapter clientFilterWarnAdapter;
    private View view;
    private Context context;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;

    private String khStatus = "";
    private String khTypeId = "";
    private String warnType = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public ClientFilterDialog(Context mContext,ClientTypeListener mClientTypeListener) {
        this.context = mContext;
        this.clientTypeListener = mClientTypeListener;
    }

    public void setStatus(String khStatus,String khTypeId,String type){
        this.khStatus = khStatus;
        this.khTypeId = khTypeId;
        this.warnType = type;
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
            view = inflater.inflate(R.layout.dialog_client_filter, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void initView(){
        startTime.setText(SPUtils.getString("filt_start_time","2019-06-22"));
        clientTypeSearchModel = new ClientTypeSearchModel();
        Log.i("TAG==","initView===");
        clientFilterAdapter = new ClientFilterAdapter(context);
        recycleData.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleData.setAdapter(clientFilterAdapter);

        clientFilterWarnAdapter = new ClientFilterAdapter(context);
        recycleDataWarn.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleDataWarn.setAdapter(clientFilterWarnAdapter);

        String clientType = SPUtils.getString(Config.CLIENT_TYPE, "");
        if (!clientType.equals("")){
            ClientTypeModel clientTypeModel = Utils.gsonInstane().fromJson(clientType, ClientTypeModel.class);

            List<ClientTypeModel.DataBean> data = clientTypeModel.getData();

            ClientTypeModel.DataBean allDataBean = new ClientTypeModel.DataBean();
            allDataBean.setId(0);
            allDataBean.setName("全部");
            allDataBean.setTimeType(true);
            allDataBean.setCheck(true);
            data.add(allDataBean);

            ClientTypeModel.DataBean datBean1 = new ClientTypeModel.DataBean();
            datBean1.setId(1);
            datBean1.setName("有效客户");
            datBean1.setTimeType(true);
            datBean1.setCheck(false);
            data.add(datBean1);

            ClientTypeModel.DataBean datBean2 = new ClientTypeModel.DataBean();
            datBean2.setId(2);
            datBean2.setName("无效客户");
            datBean2.setTimeType(true);
            datBean2.setCheck(false);
            data.add(datBean2);

            clientTypeModel.setData(data);

            List<ClientTypeModel.DataBean>tempData = new ArrayList<>();

            for (ClientTypeModel.DataBean dataBean : data){
                if (dataBean.isTimeType()){
                    if (khStatus.contains(dataBean.getId()+"")){
                        dataBean.setCheck(true);
                    }else {
                        dataBean.setCheck(false);
                    }
                }else {
                    if (khTypeId.contains(dataBean.getId()+"")){
                        dataBean.setCheck(true);
                    }else {
                        dataBean.setCheck(false);
                    }
                }
                tempData.add(dataBean);
            }

            clientFilterAdapter.setList(tempData);
        }

        clientFilterAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClientTypeModel.DataBean dataBean = clientFilterAdapter.getData().get(position);
                if (dataBean.isCheck()){
                    dataBean.setCheck(false);
                    clientFilterAdapter.setData(position,dataBean);
                }else {
                    dataBean.setCheck(true);
                    if (dataBean.isTimeType()){

                        List<ClientTypeModel.DataBean> data = clientFilterAdapter.getData();
                        List<ClientTypeModel.DataBean> tempData = new ArrayList<>();
                        for (int i=0;i<data.size();i++){
                            ClientTypeModel.DataBean dataBean1 = data.get(i);
                            if (dataBean1.isTimeType()){
                                if (i==position){
                                    dataBean1.setCheck(true);
                                }else {
                                    dataBean1.setCheck(false);
                                }
                            }
                            tempData.add(dataBean1);
                        }
                        clientFilterAdapter.setList(tempData);
                    }else {
                        clientFilterAdapter.setData(position,dataBean);
                    }
                }

            }
        });

        endTime.setText(Utils.formatSelectTime(new Date()));
        initWarnTypeData();
        initWarnClickListener();
    }


    /***
     * 初始化下单预警类型数据
     */
    private final void initWarnTypeData(){

        List<ClientTypeModel.DataBean> data = new ArrayList<>();
        for (int i = 0; i<4;i++){
            String name = "";
             if (i==0){
                name = "正常";
            }else if (i==1){
                name = "提醒";
            }else if (i==2){
                name = "警告";
            }else if (i==3){
                name = "超期";
            }

            ClientTypeModel.DataBean datBean1 = new ClientTypeModel.DataBean();

            datBean1.setId(i);
            datBean1.setName(name);
            datBean1.setWarnType(true);
            if (warnType!=null && warnType.equals(String.valueOf(i))){
                datBean1.setCheck(true);
            }else {
                datBean1.setCheck(false);
            }
            data.add(datBean1);
        }
        clientFilterWarnAdapter.setList(data);
    }


    /**
     * 初始化下单预警点击事件
     */
    private final void initWarnClickListener(){
        clientFilterWarnAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                ClientTypeModel.DataBean dataBean = clientFilterWarnAdapter.getData().get(position);
                if (dataBean.isCheck()){
                    dataBean.setCheck(false);
                    clientFilterWarnAdapter.setData(position,dataBean);
                    warnType = null;
                }else {
                    Log.i("TAG====","position==="+position);
                    warnType = String.valueOf(position);
                    List<ClientTypeModel.DataBean> data = clientFilterWarnAdapter.getData();
                    List<ClientTypeModel.DataBean> tempData = new ArrayList<>();
                    for (int i=0;i<data.size();i++){
                        ClientTypeModel.DataBean dataBean1 = data.get(i);
                        if (i==position){
                            dataBean1.setCheck(true);
                        }else {
                            dataBean1.setCheck(false);
                        }
                        tempData.add(dataBean1);
                    }
                    clientFilterWarnAdapter.setList(tempData);
                }
            }
        });

    }



    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    @OnClick({R.id.delete, R.id.start_time, R.id.end_time,R.id.filter_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete:
                dismiss();
                break;
            case R.id.start_time:
                endTime.setTextColor(Color.parseColor("#6D7278"));
                startTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn36));
                endTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn37));
                selectTime(SELECT_START_TIME);
                this.getDialog().hide();
                break;
            case R.id.end_time:
                startTime.setTextColor(Color.parseColor("#6D7278"));
                endTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn37));
                endTime.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn36));
                selectTime(SELECT_END_TIME);
                this.getDialog().hide();
                break;
            case R.id.filter_txt:

                clientTypeSearchModel.setEndTime(endTime.getText().toString());
                clientTypeSearchModel.setStartTime(startTime.getText().toString());
                clientTypeListener.filterSearch(clientFilterAdapter.getData(),warnType,startTime.getText().toString(),endTime.getText().toString());
                dismiss();
                break;
        }
    }


    private void selectTime(int type){

        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                ClientFilterDialog.this.getDialog().show();
                if (type==SELECT_START_TIME){
                    startTime.setText(Utils.formatSelectTime(date));
                }else {
                    endTime.setText(Utils.formatSelectTime(date));
                }
            }
        })
                .build();
        pvTime.show();
    }

    public interface ClientTypeListener{
        void filterSearch(List<ClientTypeModel.DataBean> data,String type,String startTime,String endTime);
    }
}
