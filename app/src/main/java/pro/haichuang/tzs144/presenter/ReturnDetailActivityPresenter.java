package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.ReturnDetailModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class ReturnDetailActivityPresenter {


    private ILoadDataView iLoadDataView;

    public ReturnDetailActivityPresenter(ILoadDataView iLoadDataView){
      this.iLoadDataView = iLoadDataView;
    }

    /**
     * [实时库存]查询取水收捅明细
     * @param startTime
     * @param endTime
     * @param staffId
     * @param page
     */
    public void findQsstLogs(String startTime,String endTime,String staffId,int page ,String query){

        Map<String,Object>params = new ArrayMap<>();
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("staffId",staffId);
        params.put("page",page);
        params.put("query",query);
        params.put("limit",10);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_QSST_LOGS, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                ReturnDetailModel returnDetailModel = Utils.gsonInstane().fromJson(result, ReturnDetailModel.class);
                if (returnDetailModel.getResult()==1){
                    iLoadDataView.successLoad(returnDetailModel.getData());
                }else {
                    iLoadDataView.errorLoad(returnDetailModel.getMessage());
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });


    }
}
