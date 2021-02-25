package pro.haichuang.tzs144.presenter;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.TypeListModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class InventoryFragmentPresenter {

    private ILoadDataView iLoadDataView;

    public InventoryFragmentPresenter(ILoadDataView mILoadDataView){
      this.iLoadDataView = mILoadDataView;
    }

    /**
     * [实时库存]获取商品品类
     */
    public void findGoodsCategory(){

        HttpRequestEngine.postRequest(ConfigUrl.FIND_GOODS_CATEGORY, null, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    TypeListModel typeListModel = Utils.gsonInstane().fromJson(result, TypeListModel.class);
                    if (typeListModel.getResult()==1){
                        iLoadDataView.successLoad(typeListModel.getData());
                    }else {
                        iLoadDataView.errorLoad("加载错误");
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
