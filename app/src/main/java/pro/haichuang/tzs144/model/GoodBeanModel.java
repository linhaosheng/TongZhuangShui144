package pro.haichuang.tzs144.model;

public class GoodBeanModel {
    private String goodsId;
    private int num;
    private String name;

    public GoodBeanModel(String name,String goodsId, int num) {
        this.goodsId = goodsId;
        this.num = num;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
