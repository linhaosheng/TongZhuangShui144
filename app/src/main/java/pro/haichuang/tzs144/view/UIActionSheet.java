package pro.haichuang.tzs144.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import java.util.ArrayList;
import java.util.List;

import pro.haichuang.tzs144.R;

public class UIActionSheet extends AppCompatDialog {

    private LinearLayout mUIActionSheetContent;
    private TextView mActionSheetTitle;
    private View mActionSheetTitleLine;
    private UIActionSheetDismissListener mUIActionSheetDismissListener;

    private void setUIActionSheetDismissListener(UIActionSheetDismissListener actionSheetDismissListener) {
        mUIActionSheetDismissListener = actionSheetDismissListener;
    }

    public interface UIActionSheetDismissListener {
        void onDismiss();
    }

    public interface OnOptionClickListener {
        void onOptionClickListener();
    }

    private UIActionSheet(@NonNull Context context) {
        super(context, R.style.UIActionSheetStyle);
        setContentView(R.layout.ui_action_sheet_layout);

        mUIActionSheetContent = findViewById(R.id.action_sheet_content);
        mActionSheetTitle = findViewById(R.id.action_sheet_title);
        mActionSheetTitleLine = findViewById(R.id.action_sheet_title_line);
        TextView cancel = findViewById(R.id.ui_action_sheet_cancel);
        if (cancel != null)
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIActionSheet.this.dismiss();
                }
            });

        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        Window window = this.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.UIActionSheetAnimation);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mUIActionSheetDismissListener != null) {
            mUIActionSheetDismissListener.onDismiss();
        }
    }

    public static class Builder {
        private Parameter mParameter;
        private Context mContext;

        public Builder(Context context) {
            mParameter = new Parameter();
            mContext = context;
        }

        public Builder setTitle(String title, int color) {
            mParameter.mTitle = title;
            mParameter.mTitleTextColor = color;
            return this;
        }

        public Builder addOption(String option, OnOptionClickListener optionClickListener) {
            mParameter.mOptions.add(new Option(option, Color.parseColor("#2376b7"), optionClickListener));
            return this;
        }

        public Builder addOption(String option, int color, OnOptionClickListener optionClickListener) {
            mParameter.mOptions.add(new Option(option, color, optionClickListener));
            return this;
        }

        public Builder addOption(String option, String color, OnOptionClickListener optionClickListener) {
            mParameter.mOptions.add(new Option(option, Color.parseColor(color), optionClickListener));

            return this;
        }

        public UIActionSheet create() {
            final UIActionSheet actionSheet = new UIActionSheet(mContext);
            // 标题判断
            if (mParameter.mTitle.isEmpty()) {
                actionSheet.mActionSheetTitle.setVisibility(View.GONE);
                actionSheet.mActionSheetTitleLine.setVisibility(View.GONE);
            } else {
                actionSheet.mActionSheetTitle.setText(mParameter.mTitle);
                actionSheet.mActionSheetTitle.setTextColor(mParameter.mTitleTextColor);
                actionSheet.mActionSheetTitle.setVisibility(View.VISIBLE);
                actionSheet.mActionSheetTitleLine.setVisibility(View.VISIBLE);
            }
            // 选项判断
            int optionSize = mParameter.mOptions.size();
            if (optionSize == 0) {
                actionSheet.mActionSheetTitleLine.setVisibility(View.GONE);
                actionSheet.mUIActionSheetContent.setVisibility(View.GONE);
            } else {
                // 条目 params
                LinearLayout.LayoutParams optionParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                optionParams.height = dp2px(mContext);
                // 分隔线 params
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1);
                for (int i = 0; i < optionSize; i++) {
                    final Option option = mParameter.mOptions.get(i);
                    TextView textView = new TextView(mContext);
                    textView.setText(option.getName());
                    textView.setTextColor(option.getColor());
                    textView.setGravity(Gravity.CENTER);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            actionSheet.dismiss();
                            if (option.getOptionClickListener() != null) {
                                option.getOptionClickListener().onOptionClickListener();
                            }
                        }
                    });
                    textView.setLayoutParams(optionParams);
                    actionSheet.mUIActionSheetContent.addView(textView);

                    //添加条目之间的分割线
                    if (i != optionSize - 1) {
                        View divider = new View(mContext);
                        divider.setBackgroundColor(Color.parseColor("#eeeeee"));
                        actionSheet.mUIActionSheetContent.addView(divider, dividerParams);
                    }

                    // 设置条目点击背景
                    if (optionSize == 1) {
                        if (mParameter.mTitle.isEmpty()) {
                            textView.setBackgroundResource(R.drawable.ui_action_sheet_cancel_shape);
                        } else {
                            textView.setBackgroundResource(R.drawable.ui_action_sheet_option_last_one);
                        }
                    } else if (i == 0) {
                        if (mParameter.mTitle.isEmpty()) {
                            textView.setBackgroundResource(R.drawable.ui_action_sheet_option_not_title_first);
                        } else {
                            textView.setBackgroundResource(R.drawable.ui_action_sheet_option_bg);
                        }
                    } else if (i < optionSize - 1) {
                        textView.setBackgroundResource(R.drawable.ui_action_sheet_option_bg);
                    } else {
                        textView.setBackgroundResource(R.drawable.ui_action_sheet_option_last_one);
                    }
                }
            }
            actionSheet.setUIActionSheetDismissListener(mParameter.mSheetDismissListener);
            return actionSheet;
        }

        //item的高度
        private int dp2px(Context context) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    (float) 50, context.getResources().getDisplayMetrics());
        }
    }
}

class Parameter {
    String mTitle;
    int mTitleTextColor;
    List<Option> mOptions;
    UIActionSheet.UIActionSheetDismissListener mSheetDismissListener;

    Parameter() {
        mTitle = "";
        mTitleTextColor = Color.BLACK;
        mOptions = new ArrayList<>();
    }
}

class Option {
    private String mName;
    private int mColor;
    private UIActionSheet.OnOptionClickListener mOptionClickListener;

    /**
     * @param name                选项名
     * @param color               选项颜色
     * @param optionClickListener 选项事件
     */
    Option(String name, int color, UIActionSheet.OnOptionClickListener optionClickListener) {
        mName = name;
        mColor = color;
        mOptionClickListener = optionClickListener;
    }

    String getName() {
        return mName;
    }

    int getColor() {
        return mColor;
    }

    UIActionSheet.OnOptionClickListener getOptionClickListener() {
        return mOptionClickListener;
    }
}
