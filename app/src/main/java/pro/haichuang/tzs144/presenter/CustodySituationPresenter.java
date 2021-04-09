package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import pro.haichuang.tzs144.model.CustodyEvent;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

public class CustodySituationPresenter {


    /**
     *  库存主体 - 结账汇总 - 开退押情况
     * @param scMainId
     * @param type
     * @param startTime
     * @param endTime
     * @param categoryId
     */
    public void findSummaryKy(String scMainId,String type,String startTime,String endTime,String categoryId){

        Map<String,Object>params = new ArrayMap<>();
        params.put("scMainId",scMainId);
        params.put("type",type);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("categoryId",categoryId);
        params.put("page","0");
        params.put("limit","10");

        HttpRequestEngine.postRequest(ConfigUrl.FIND_SUMMARY_KY, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
               try {

                   CustodyModel custodyModel = Utils.gsonInstane().fromJson(result, CustodyModel.class);
                   if (custodyModel.getResult()==1){
                       EventBus.getDefault().post(new CustodyEvent(custodyModel, Config.LOAD_SUCCESS));
                   }else {
                       EventBus.getDefault().post(new CustodyEvent(custodyModel, Config.LOAD_FAIL));
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
