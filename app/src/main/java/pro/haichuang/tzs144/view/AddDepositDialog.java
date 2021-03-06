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

import com.kongzue.dialog.v3.WaitDialog;

import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;


/**
 * 添加押金本
 */
public class AddDepositDialog extends DialogFragment {


    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.deposit_num_txt)
    TextView depositNumTxt;
    @BindView(R.id.deposit_num)
    EditText depositNum;
    @BindView(R.id.start_deposit_num_txt)
    TextView startDepositNumTxt;
    @BindView(R.id.start_deposit_num)
    EditText startDepositNum;
    @BindView(R.id.end_deposit_num_txt)
    TextView endDepositNumTxt;
    @BindView(R.id.end_deposit_num)
    EditText endDepositNum;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.input_btn)
    Button inputBtn;
    private View view;

    private Context context;
    private AddDepositListener addDepositListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TurnTableDilogTheme);
    }

    public AddDepositDialog(Context mContext,AddDepositListener mAddDepositListener) {
        this.context = mContext;
        this.addDepositListener = mAddDepositListener;

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
            view = inflater.inflate(R.layout.dialog_add_deposit, container, false);
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

    @OnClick(R.id.delete)
    public void onViewClicked() {
        dismiss();
    }

    @OnClick({R.id.delete, R.id.cancel_btn, R.id.input_btn})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.delete:
                dismiss();
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.input_btn:

                if (TextUtils.isEmpty(depositNum.getText())){
                    Utils.showCenterTomast("请输入押金本编号");
                    return;
                }
                if (TextUtils.isEmpty(startDepositNum.getText())){
                    Utils.showCenterTomast("请输入开始编号");
                    return;
                }
                if (TextUtils.isEmpty(endDepositNum.getText())){
                    Utils.showCenterTomast("请输入结束编号");
                    return;
                }

                try {
                    int startNum =  Integer.parseInt(startDepositNum.getText().toString());
                    int endNum = Integer.parseInt(endDepositNum.getText().toString());
                    String number = depositNum.getText().toString();
                    int num = endNum - startNum;
                    addDepositBook(String.valueOf(num),number);

                }catch (Exception e){
                    e.printStackTrace();
                    Utils.showCenterTomast("请输入正确的数字开始编号和结束编号");
                }

                break;
        }
    }

    /**
     * 添加押金本
     * @param num   编号数量
     * @param number  押金本编号
     * @param
     */
    public void addDepositBook(String num,String number){

        Map<String,Object> params = new ArrayMap<>();
        params.put("num",num);
        params.put("number",number);

        HttpRequestEngine.postRequest(ConfigUrl.ADD_DESPOSIT_BOOK, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                Utils.showCenterTomast("提交中...");
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        Utils.showCenterTomast("添加成功");
                        AddDepositDialog.this.dismiss();
                        if (addDepositListener!=null){
                            addDepositListener.addDespositStatus("success","添加成功");
                        }

                    }else {
                        if (addDepositListener!=null){
                            String errorResult= jsonObject.getString("message");
                            Utils.showCenterTomast(errorResult);
                            addDepositListener.addDespositStatus("fail",errorResult);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                Utils.showCenterTomast("提交失败");
            }
        });
    }

    public static interface AddDepositListener{
        void addDespositStatus(String status,String result);
    }
}
