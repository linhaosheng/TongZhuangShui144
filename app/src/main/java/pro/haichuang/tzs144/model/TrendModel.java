package pro.haichuang.tzs144.model;

public class TrendModel {

    public String name;
    public String num;
    public String lastOne;
    public String lastTwo;

    public TrendModel(String name, String num, String lastOne, String lastTwo) {
        this.name = name;
        this.num = num;
        this.lastOne = lastOne;
        this.lastTwo = lastTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLastOne() {
        return lastOne;
    }

    public void setLastOne(String lastOne) {
        this.lastOne = lastOne;
    }

    public String getLastTwo() {
        return lastTwo;
    }

    public void setLastTwo(String lastTwo) {
        this.lastTwo = lastTwo;
    }
}
