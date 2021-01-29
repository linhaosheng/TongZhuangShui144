package pro.haichuang.tzs144.model;

public class TextEvent {


    public final CurrentText currentText;

    public TextEvent(CurrentText mCurrentText){
        this.currentText = mCurrentText;
    }

    public static class CurrentText{
        public  final String text;
        public final int currentIndex;

        public CurrentText(String text,int currentIndex){
            this.text = text;
            this.currentIndex= currentIndex;
        }
    }
}
