package pro.haichuang.tzs144.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientDetailModel;

public class MainTainRecordAdapter extends BaseQuickAdapter<ClientDetailModel.DataBean.MaintainListBean, BaseViewHolder> {


    private EditContentListener editContentListener;

    public MainTainRecordAdapter(EditContentListener editContentListener) {
        super(R.layout.item_main_tain_record);
        this.editContentListener = editContentListener;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClientDetailModel.DataBean.MaintainListBean data) {

        baseViewHolder.setText(R.id.time,data.getTime())
                .setText(R.id.record_txt,data.getMaintainInfo())
        .setText(R.id.edit_content,data.getMaintainInfo());

        if (data.isEdit()){
            baseViewHolder.getView(R.id.edit_content).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.record_txt).setVisibility(View.VISIBLE);
        }

       EditText editContent =  baseViewHolder.getView(R.id.edit_content);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editContentListener.editContent(editContent.getText().toString(),baseViewHolder.getPosition());
            }
        });
    }

    public static interface EditContentListener{
        void editContent(String content,int position);
    }
}
