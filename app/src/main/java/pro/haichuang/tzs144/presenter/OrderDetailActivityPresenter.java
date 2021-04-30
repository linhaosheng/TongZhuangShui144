package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.AreaEvent;
import pro.haichuang.tzs144.model.AreaModel;
import pro.haichuang.tzs144.model.FileUploadEvent;
import pro.haichuang.tzs144.model.OrderDetailDataModel;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.ShopDeleveModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.UploadFileModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import rxhttp.wrapper.entity.UpFile;

public class OrderDetailActivityPresenter {

    private ILoadDataView iLoadDataView;

    public OrderDetailActivityPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }


    /**
     * [首页]-订单接单
     * @param verification
     * @param id
     */
    public void takeOrder(String verification,String id){
        Map<String,Object> params = new ArrayMap<>();
        params.put("verification",verification);
        params.put("id",id);
        HttpRequestEngine.postRequest(ConfigUrl.TAKE_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {

            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * [首页]-订单详情
     * @param id
     * @param
     * @param
     */
    public void getHomeOrderInfo(String id){
        Map<String,Object> params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.HOME_ORDER_INFO, params, new HttpRequestResultListener() {
            @Override
            public void start() {
              iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                OrderDetailDataModel orderDetailModel = Utils.gsonInstane().fromJson(result, OrderDetailDataModel.class);
                if (orderDetailModel.getResult()==1){
                    iLoadDataView.successLoad(orderDetailModel.getData());
                }else {
                    iLoadDataView.errorLoad("加载错误");
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * [首页]-转订单
     */
    public void turnOrder(String id,int mainId){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        params.put("mainId",mainId);

        HttpRequestEngine.postRequest(ConfigUrl.TURN_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,4));
                    }else {
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,4));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * [首页]-作废订单
     * @param id
     */
    public void directSelling(String id){
        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.DIRECT_SELLING, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,2));
                    }else {
                        String error = jsonObject.getString("message");
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,2);
                        statusEvent.setResult(error);
                        EventBus.getDefault().post(statusEvent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * 订单接单
     * @param id
     */
    public void takeOrder(String id){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);

        HttpRequestEngine.postRequest(ConfigUrl.TAKE_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,1));
                    }else {
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,1));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });

    }

    /**
     * 配送
     * @param
     */
    public void deliveryOrder(String id, String totalPrice,String receivablePrice,String realPrice,List<ShopDeleveModel.GoodsListBean> goodsListBeanList){

        Map<String,Object>params = new ArrayMap<>();
        params.put("id",id);
        params.put("totalPrice",totalPrice);
        params.put("receivablePrice",receivablePrice);
        params.put("realPrice",realPrice);
        params.put("goodsList",goodsListBeanList);

        HttpRequestEngine.postRequest(ConfigUrl.DELIVERY_ORDER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int result1 = jsonObject.getInt("result");
                    if (result1==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,3));
                    }else {
                        String error = jsonObject.getString("message");
                        StatusEvent statusEvent =  new StatusEvent(Config.LOAD_FAIL,3);
                        statusEvent.setResult(error);
                        EventBus.getDefault().post(statusEvent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }

    /**
     * [直接销售]-加载绑定回收材料
     */
    public final void findGoodsMaterial(String goodsId){

        Map<String,Object>params = new ArrayMap<>();
        params.put("goodsId",goodsId);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_GOODS_MATERIAL, params, new HttpRequestResultListener() {
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
     * 上传文件
     * @param key
     * @param path
     */
    public void uploadFile(String key,String path){

        Log.i("uploadFile====",path);
        UpFile upFile = new UpFile("file",new File(path));

        HttpRequestEngine.uploadFile(ConfigUrl.UPLOAD_FILE, upFile, new HttpRequestResultListener() {
            @Override
            public void start() {
                EventBus.getDefault().post(new FileUploadEvent(0,null,key));
            }

            @Override
            public void success(String result) {
                UploadFileModel uploadFileModel = Utils.gsonInstane().fromJson(result, UploadFileModel.class);
                if (uploadFileModel.getResult()!=1){
                    //失败
                    EventBus.getDefault().post(new FileUploadEvent(1,null,key));
                }else {
                    EventBus.getDefault().post(new FileUploadEvent(2,uploadFileModel.getData().getFileUrl(),key));
                }
            }

            @Override
            public void error(String error) {
                EventBus.getDefault().post(new FileUploadEvent(1,null,key));
            }
        });
    }
    /**
     * [客户]获取所在片区列表
     * @param parentId
     */
    public void findAreas(String parentId){
        Map<String,Object>params = new ArrayMap<>();
        params.put("parentId",parentId);
        HttpRequestEngine.postRequest(ConfigUrl.FIND_AREAS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                AreaModel areaModel = Utils.gsonInstane().fromJson(result, AreaModel.class);
                EventBus.getDefault().post(new AreaEvent(areaModel));
            }

            @Override
            public void error(String error) {

            }
        });

    }

}
