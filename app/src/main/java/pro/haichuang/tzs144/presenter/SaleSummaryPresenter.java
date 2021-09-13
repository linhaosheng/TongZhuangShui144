package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.SaleSummaryModel;
import pro.haichuang.tzs144.model.SummaryModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class SaleSummaryPresenter {

    private ILoadDataView iLoadDataView;

    public SaleSummaryPresenter(ILoadDataView iLoadDataView){
        this.iLoadDataView = iLoadDataView;
    }

    /**
     * 销售汇总
     * @param startTime
     * @param endTime
     * @param categoryId
     * @param goodsId
     * @param page
     */
    public void findOrderSumList(String startTime,String endTime,String categoryId,String goodsId,int page){

        Map<String,Object> params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("categoryId",categoryId);
        params.put("goodsId",goodsId);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_ORDER_SUM_LIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    SaleSummaryModel summaryModel = Utils.gsonInstane().fromJson(result, SaleSummaryModel.class);
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
