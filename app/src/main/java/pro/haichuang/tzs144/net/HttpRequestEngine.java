package pro.haichuang.tzs144.net;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import pro.haichuang.tzs144.activity.LoginActivity;
import pro.haichuang.tzs144.application.MyApplication;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.util.Config;
import rxhttp.RxHttp;
import rxhttp.RxHttpFormParam;
import rxhttp.RxHttpJsonParam;
import rxhttp.RxHttpNoBodyParam;
import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.entity.UpFile;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.Param;

public class HttpRequestEngine {


    /**
     * get request
     *
     * @param url
     * @param params
     * @param httpRequestResultListener
     */
    public static void getRequestWithParams(String url, Map<String, Object> params, HttpRequestResultListener httpRequestResultListener) {

        if (!Utils.isNetworkConnected(MyApplication.getApplication())) {
            Utils.showCenterTomast("请检查网络");
            return;
        }
        addHeadParams();
        RxHttp.setDebug(true);

        RxHttpNoBodyParam rxHttpNoBodyParam = RxHttp.get(url);
        if (params != null) {
            rxHttpNoBodyParam.addAll(params);
        }
        rxHttpNoBodyParam.asString()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.start();
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.success(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void getRequest(String url, HttpRequestResultListener httpRequestResultListener) {
        getRequestWithParams(url, null, httpRequestResultListener);
    }

    /**
     * post request
     *
     * @param url
     * @param jsonParams
     * @param httpRequestResultListener
     */
    public static void postRequest(String url, Map<String, Object> jsonParams, HttpRequestResultListener httpRequestResultListener) {

        if (!Utils.isNetworkConnected(MyApplication.getApplication())) {
            Utils.showCenterTomast("Please check the network");
            return;
        }

        addHeadParams();
        RxHttp.setDebug(true);

        RxHttpJsonParam rxHttpJsonParam = RxHttp.postJson(url);
        if (jsonParams != null) {
            rxHttpJsonParam.addAll(jsonParams);
        }
        rxHttpJsonParam.asString()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.start();
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        try {

                            if (httpRequestResultListener != null) {
                                httpRequestResultListener.success(s);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.error(e.getMessage());
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 上传故障报修单
     *
     * @param url
     * @param
     * @param
     * @param httpRequestResultListener
     */
    public static void uploadGuaranteeData(String url, String pictures, String videos, HttpRequestResultListener httpRequestResultListener) {
        if (!Utils.isNetworkConnected(MyApplication.getApplication())) {
            Utils.showCenterTomast("Please check the network");
            return;
        }

        addHeadParams();
        RxHttp.setDebug(true);

        RxHttpJsonParam rxHttpJsonParam = RxHttp.postJson(url);

        rxHttpJsonParam.asString()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.start();
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.success(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.error(e.getMessage());
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 上传文件
     *
     * @param url
     * @param
     * @param httpRequestResultListener
     */
    public static void uploadFile(String url, UpFile upFile, HttpRequestResultListener httpRequestResultListener) {

        addFileHeadParams();

        //android 10 版本 通过获取的图片URI 来上传，否则通过图片路径来上传
        Log.i("TAG===", "version 28");
        RxHttp.postForm(url)
                .addFile(upFile)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.start();
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.success(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (httpRequestResultListener != null) {
                            httpRequestResultListener.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * set common params
     */
    private static void addHeadParams() {

        RxHttp.setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param<?> apply(Param<?> param) throws Exception {
                return param
                        .add("longitude",Config.LONGITUDE+"")
                        .add("latitude",Config.LATITUDE+"")
                        .add("limit",Config.LIMIT)
                        .add("verification",SPUtils.getString(Config.VERIFICATION,""))
                        .addHeader("Content-Type", "application/json");
            }
        });
    }

    /**
     * set common params
     */
    private static void addFileHeadParams() {

        RxHttp.setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param<?> apply(Param<?> param) throws Exception {
                return param
                        .add("longitude",Config.LONGITUDE)
                        .add("latitude",Config.LATITUDE)
                        .add("limit",Config.LIMIT)
                        .add("verification",SPUtils.getString(Config.VERIFICATION,""))
                        .addHeader("Content-Type", "multipart/form-data");
            }
        });
    }

    private static final void gotoLogin() {
        String currentPageName = Utils.getCurrentPageName(MyApplication.getApplication());
        if (!currentPageName.contains("LoginActivity")) {
            Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getInstance().startActivity(intent);
        }
    }
}
