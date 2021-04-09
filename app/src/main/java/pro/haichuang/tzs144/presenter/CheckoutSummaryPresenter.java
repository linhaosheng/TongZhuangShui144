package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SummaryModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class CheckoutSummaryPresenter {

    private ILoadDataView iLoadDataView;

    public CheckoutSummaryPresenter(ILoadDataView iLoadDataView){
        this.iLoadDataView = iLoadDataView;
    }

    /**
     * 库存主体 - 结账汇总 - 合计
     * @param scMainId
     * @param type
     * @param startTime
     * @param endTime
     * @param categoryId
     */
    public void findSummaryHj(String scMainId,String type,String startTime,String endTime,String categoryId){

        Map<String,Object>params = new ArrayMap<>();
        params.put("scMainId",scMainId);
        params.put("type",type);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("categoryId",categoryId);
        params.put("page","0");
        params.put("limit","100");

        HttpRequestEngine.postRequest(ConfigUrl.FIND_SUMMARY_HJ, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    SummaryModel summaryModel = Utils.gsonInstane().fromJson(result, SummaryModel.class);
                    if (summaryModel.getResult()==1){
                        iLoadDataView.successLoad(summaryModel.getData());
                    }else {
                        iLoadDataView.errorLoad(summaryModel.getMessage());
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
}
