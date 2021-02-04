package pro.haichuang.tzs144.fragment;

import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;

public class MainActivityPresenter {



    public MainActivityPresenter(){

    }

    /**
     * [客户]获取客户类型
     */
    public void findKhTypes(){

        HttpRequestEngine.postRequest(ConfigUrl.FIND_KH_TYPES, null, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                SPUtils.putString(Config.CLIENT_TYPE,result);
            }

            @Override
            public void error(String error) {

            }
        });
    }
}
