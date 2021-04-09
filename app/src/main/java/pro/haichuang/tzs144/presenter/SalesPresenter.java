package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.IncomeCountEvent;
import pro.haichuang.tzs144.model.SaleCountEvent;
import pro.haichuang.tzs144.model.SaleModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class SalesPresenter {

    /**
     * 库存主体 - 结账汇总 - 销售情况 - 商品列表
     * @param scMainId
     * @param type
     * @param startTime
     * @param endTime
     * @param categoryId
     */
    public void findSummarySaleQs(String scMainId,String type,String startTime,String endTime,String categoryId){

        Map<String,Object> params = new ArrayMap<>();
        params.put("scMainId",scMainId);
        params.put("type",type);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("categoryId",categoryId);
        params.put("page","0");
        params.put("limit","10");

        HttpRequestEngine.postRequest(ConfigUrl.FIND_SUMMARY_SALE_QS, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {

                    SaleModel saleModel = Utils.gsonInstane().fromJson(result, SaleModel.class);
                    if (saleModel.getResult()==1){
                        EventBus.getDefault().post(new SaleCountEvent(saleModel, Config.LOAD_SUCCESS,0));
                    }else {
                        EventBus.getDefault().post(new SaleCountEvent(saleModel, Config.LOAD_FAIL,0));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {

            }
        });
    }

    /**
     * 库存主体 - 结账汇总 - 销售情况 - 材料列表
     * @param scMainId
     * @param type
     * @param startTime
     * @param endTime
     * @param categoryId
     */
    public void findSummarySaleHt(String scMainId,String type,String startTime,String endTime,String categoryId){

        Map<String,Object> params = new ArrayMap<>();
        params.put("scMainId",scMainId);
        params.put("type",type);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("categoryId",categoryId);
        params.put("page","0");
        params.put("limit","10");

        HttpRequestEngine.postRequest(ConfigUrl.FIND_SUMMARY_SALE_HT, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {

                    SaleModel saleModel = Utils.gsonInstane().fromJson(result, SaleModel.class);
                    if (saleModel.getResult()==1){
                        EventBus.getDefault().post(new SaleCountEvent(saleModel, Config.LOAD_SUCCESS,1));
                    }else {
                        EventBus.getDefault().post(new SaleCountEvent(saleModel, Config.LOAD_FAIL,1));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {

            }
        });
    }


}
