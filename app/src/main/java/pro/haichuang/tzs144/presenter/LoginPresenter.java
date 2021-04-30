package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.LoginModel;
import pro.haichuang.tzs144.model.MessageEvent;
import pro.haichuang.tzs144.model.SubjectModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;

public class LoginPresenter {

    private ILoadDataView iLoadDataView;

    public LoginPresenter(ILoadDataView mILoadDataView){
       this.iLoadDataView = mILoadDataView;
    }

    /**
     * 登录
     * @param userName
     * @param passWord
     * @param stockMainId
     */
    public void loginServer(String userName,String passWord,String stockMainId){
        Map<String,Object>params = new ArrayMap<>();
        params.put("userName",userName);
        params.put("passWord",passWord);
        params.put("stockMainId",stockMainId);

        HttpRequestEngine.postRequest(ConfigUrl.LOGIN, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                if (result!=null){
                    LoginModel loginModel = Utils.gsonInstane().fromJson(result, LoginModel.class);
                    if (loginModel!=null && loginModel.getResult()==1){
                        SPUtils.putString(Config.VERIFICATION,loginModel.getData().getVerification());
                        iLoadDataView.successLoad(result);
                    }else {
                        iLoadDataView.errorLoad(loginModel.getMessage());
                    }
                }else {
                    iLoadDataView.errorLoad("登录失败");
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * 登录-获取主体列表
     * @param userName
     */
    public void loadSubjectList(String userName,boolean showDialog){

        Map<String,Object>params = new ArrayMap<>();
        params.put("userName",userName);

        HttpRequestEngine.postRequest(ConfigUrl.SUBJECT_LIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                if (showDialog){
                    iLoadDataView.startLoad();
                }
                Log.i("TAH===","start====");
            }

            @Override
            public void success(String result) {
                if (result!=null){
                    SubjectModel subjectModel = Utils.gsonInstane().fromJson(result, SubjectModel.class);
                    if (subjectModel!=null && subjectModel.getResult()==1){
                        Log.i("TAH===","result====");
                        EventBus.getDefault().post(new MessageEvent(subjectModel));
                        SPUtils.putString(Config.SUBJECT_LIST,result);
                    }else {
                        EventBus.getDefault().post(new MessageEvent(null));
                    }
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
                Log.i("TAH===","error===="+error);
            }
        });
    }

}
