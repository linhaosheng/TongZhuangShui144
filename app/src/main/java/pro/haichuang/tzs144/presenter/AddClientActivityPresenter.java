package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AddClientModel;
import pro.haichuang.tzs144.model.AreaEvent;
import pro.haichuang.tzs144.model.AreaModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class AddClientActivityPresenter {

    private ILoadDataView iLoadDataView;

    public  AddClientActivityPresenter(ILoadDataView mILoadDataView){
     this.iLoadDataView = mILoadDataView;
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


    /**
     * [客户]新增客户
     */
    public void addKh(AddClientModel addClientModel){

        Map<String,Object>params = new ArrayMap<>();
        params.put("customerTypeId",addClientModel.getCustomerTypeId());
        params.put("honeycombId",addClientModel.getHoneycombId());
        params.put("honeycombGridId",addClientModel.getHoneycombGridId());
        params.put("customerName",addClientModel.getCustomerName());
        params.put("contacts",addClientModel.getContacts());
        params.put("phone",addClientModel.getPhone());
        params.put("isMonopoly",addClientModel.isIsMonopoly());
        params.put("inviterId",addClientModel.getInviterId());
        params.put("businessId",addClientModel.getBusinessId());
        params.put("addressList",addClientModel.getAddressList());

        HttpRequestEngine.postRequest(ConfigUrl.ADD_KH, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        iLoadDataView.successLoad(result);
                    }else {
                        iLoadDataView.errorLoad(jsonObject.getString("message"));
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
