package pro.haichuang.tzs144.model;

public class GoodBeanModel {
    private String goodsId;
    private int num;

    public GoodBeanModel(String goodsId, int num) {
        this.goodsId = goodsId;
        this.num = num;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
