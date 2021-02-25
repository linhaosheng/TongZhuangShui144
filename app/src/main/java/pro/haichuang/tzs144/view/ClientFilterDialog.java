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

import java.util.Date;

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

    ClientTypeSearchModel clientTypeSearchModel;
    private ClientTypeListener clientTypeListener;
    private ClientFilterAdapter clientFilterAdapter;
    private View view;
    private Context context;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public ClientFilterDialog(Context mContext,ClientTypeListener mClientTypeListener) {
        this.context = mContext;
        this.clientTypeListener = mClientTypeListener;
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
        clientTypeSearchModel = new ClientTypeSearchModel();
        Log.i("TAG==","initView===");
        clientFilterAdapter = new ClientFilterAdapter(context);
        recycleData.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycleData.setAdapter(clientFilterAdapter);

        String clientType = SPUtils.getString(Config.CLIENT_TYPE, "");
        if (!clientType.equals("")){
            ClientTypeModel clientTypeModel = Utils.gsonInstane().fromJson(clientType, ClientTypeModel.class);
            clientFilterAdapter.setList(clientTypeModel.getData());
        }

        clientFilterAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClientTypeModel.DataBean dataBean = clientFilterAdapter.getData().get(position);
                if (dataBean.isCheck()){
                    dataBean.setCheck(false);
                }else {
                    dataBean.setCheck(true);
                }
                clientFilterAdapter.setData(position,dataBean);
                if (dataBean.isCheck()){
                    String khTypeId = "";
                    if (clientTypeSearchModel.getKhTypeId()!=null){
                        khTypeId = clientTypeSearchModel.getKhTypeId() + ","+dataBean.getId();
                    }else {
                        khTypeId = String.valueOf(dataBean.getId());
                    }
                    clientTypeSearchModel.setKhTypeId(khTypeId);
                }
            }
        });

        endTime.setText(Utils.formatSelectTime(new Date()));
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
                clientTypeSearchModel.setKhStatus("0");
                clientTypeListener.filterSearch(clientTypeSearchModel);

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
        void filterSearch(ClientTypeSearchModel clientTypeSearchModel);
    }
}
