package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;

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
    public void loadSubjectList(String userName){

        Map<String,Object>params = new ArrayMap<>();
        params.put("userName",userName);

        HttpRequestEngine.postRequest(ConfigUrl.SUBJECT_LIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                Log.i("TAH===","start====");
            }

            @Override
            public void success(String result) {
                Log.i("TAH===","result====");
                SPUtils.putString(Config.SUBJECT_LIST,result);
            }

            @Override
            public void error(String error) {
                Log.i("TAH===","error====");
            }
        });
    }

}
