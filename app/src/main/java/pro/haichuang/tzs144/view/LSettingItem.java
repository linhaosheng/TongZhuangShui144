package pro.haichuang.tzs144.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;

import pro.haichuang.tzs144.R;


public class LSettingItem extends RelativeLayout {
    /*左侧显示文本*/
    private String mLeftText;
    /**左侧左下方显示文本**/
    private String mLeftTextInfo;
    /*左侧图标*/
    private Drawable mLeftIcon;
    /*右侧图标*/
    private Drawable mRightIcon;
    /*左侧显示文本大小*/
    private int mTextSize;
    /*左侧显示文本颜色*/
    private int mTextColor;
    /*右侧显示文本大小*/
    private float mRightTextSize;
    /*右侧显示文本颜色*/
    private int mRightTextColor;
    /*整体根布局view*/
    private View mView;
    /*根布局*/
    private RelativeLayout mRootLayout;
    /*左侧文本控件*/
    private TextView mTvLeftText;
    /*左侧左下方文本控件*/
    private TextView mTvLeftInfoText;
    /*右侧文本控件*/
    private TextView mTvRightText;
    /*分割线*/
    private View mUnderLine;
    /*左侧图标控件*/
    private ImageView mIvLeftIcon;
    /*左侧图标大小*/
    private int mLeftIconSzie;
    /**
     * 右侧图标得大小
     */
    private int rightIconSize;
    /*右侧图标控件区域,默认展示图标*/
    private FrameLayout mRightLayout;
    /*右侧图标控件,默认展示图标*/
    private ImageView mIvRightIcon;
    /*右侧图标控件,选择样式图标*/
    private AppCompatCheckBox mRightIcon_check;
    /*右侧图标控件,开关样式图标*/
    private SwitchCompat mRightIcon_switch;
    /*右侧图标展示风格*/
    private int mRightStyle = 0;
    /*选中状态*/
    private boolean mChecked;
    /*点击事件*/

    private EditText editInfo;
    private EditText editinput;
    Button rightBtn;

    private OnLSettingItemClick mOnLSettingItemClick;
    private EditTextListner editTextListner;

    public LSettingItem(Context context) {
        this(context, null);
    }

    public LSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getCustomStyle(context, attrs);
        //获取到右侧展示风格，进行样式切换
        switchRightStyle(mRightStyle);
        mRootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOn(LSettingItem.this);
            }
        });
        mRightIcon_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mOnLSettingItemClick != null) {
                    mOnLSettingItemClick.click(isChecked, buttonView);
                }
            }
        });
        mRightIcon_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mOnLSettingItemClick != null) {
                    mOnLSettingItemClick.click(isChecked, buttonView);
                }
            }
        });
    }

    public void setmOnLSettingItemClick(OnLSettingItemClick mOnLSettingItemClick) {
        this.mOnLSettingItemClick = mOnLSettingItemClick;
    }

    public void setEditTextListner(EditTextListner mEditTextListner){
        this.editTextListner = mEditTextListner;
    }

    public void setEditinput(String editinput){
        this.editinput.setText(editinput);
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    public void getCustomStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LSettingView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr==R.styleable.LSettingView_leftTextInfo){
                mLeftTextInfo = a.getString(attr);
                mTvLeftInfoText.setText(mLeftTextInfo);
            }else if (attr == R.styleable.LSettingView_leftText) {
                mLeftText = a.getString(attr);
                mTvLeftText.setText(mLeftText);
            } else if (attr == R.styleable.LSettingView_leftIcon) {
                // 左侧图标
                mLeftIcon = a.getDrawable(attr);
                if (null != mLeftIcon) {
                    mIvLeftIcon.setImageDrawable(mLeftIcon);
                    mIvLeftIcon.setVisibility(VISIBLE);
                }
            } else if (attr == R.styleable.LSettingView_leftIconSize) {
                mLeftIconSzie = (int) a.getDimension(attr, 16);
                LayoutParams layoutParams = (LayoutParams) mIvLeftIcon.getLayoutParams();
                layoutParams.width = mLeftIconSzie;
                layoutParams.height = mLeftIconSzie;
                layoutParams.alignWithParent = true;
                mIvLeftIcon.setLayoutParams(layoutParams);
            } else if (attr == R.styleable.LSettingView_rightIconSize) {
                rightIconSize = (int) a.getDimension(attr, 16);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIvRightIcon.getLayoutParams();
                layoutParams.width = rightIconSize;
                layoutParams.height = rightIconSize;
                mIvRightIcon.setLayoutParams(layoutParams);
            } else if (attr == R.styleable.LSettingView_leftTextInfoMarginLeft){
                int leftMargin = (int) a.getDimension(attr, 8);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTvLeftInfoText.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                mTvLeftInfoText.setLayoutParams(layoutParams);
            } else if (attr == R.styleable.LSettingView_leftTextMarginLeft) {
                int leftMargin = (int) a.getDimension(attr, 8);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTvLeftText.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                mTvLeftText.setLayoutParams(layoutParams);
            } else if (attr == R.styleable.LSettingView_rightIcon) {
                // 右侧图标
                mRightIcon = a.getDrawable(attr);
                mIvRightIcon.setImageDrawable(mRightIcon);
            } else if (attr == R.styleable.LSettingView_LtextInfoSize) {
                // 默认设置为16sp
                float textSize = a.getDimensionPixelSize(attr, 16);
                mTvLeftInfoText.setTextSize(textSize);
            }else if (attr== R.styleable.LSettingView_LtextSize){
                // 默认设置为16sp
                mTextSize = a.getDimensionPixelSize(attr, 14);
                mTvLeftText.setTextSize(mTextSize);

            }else if (attr == R.styleable.LSettingView_LtextInfoColor){
                //文字默认灰色
                mTextColor = a.getColor(attr, Color.LTGRAY);
                mTvLeftInfoText.setTextColor(mTextColor);

            } else if (attr == R.styleable.LSettingView_LtextColor) {  //
                //文字默认灰色
                mTextColor = a.getColor(attr, Color.LTGRAY);
                mTvLeftText.setTextColor(mTextColor);
            } else if (attr == R.styleable.LSettingView_rightStyle) {
                mRightStyle = a.getInt(attr, 0);
            } else if (attr == R.styleable.LSettingView_isShowUnderLine) {
                //默认显示分割线
                if (!a.getBoolean(attr, true)) {
                    mUnderLine.setVisibility(View.GONE);
                }
            } else if (attr == R.styleable.LSettingView_isShowRightText) {
                //默认不显示右侧文字
                if (a.getBoolean(attr, false)) {
                    mTvRightText.setVisibility(View.VISIBLE);
                }
            } else if (attr == R.styleable.LSettingView_rightText) {
                mTvRightText.setText(a.getString(attr));
            } else if (attr == R.styleable.LSettingView_rightTextSize) {

                // 默认设置为16sp
                mRightTextSize = a.getFloat(attr, 14);
                mTvRightText.setTextSize(mRightTextSize);
            } else if (attr == R.styleable.LSettingView_rightTextColor) {
                //文字默认灰色
                mRightTextColor = a.getColor(attr, Color.GRAY);
                mTvRightText.setTextColor(mRightTextColor);
            }else if (attr==R.styleable.LSettingView_isShowRightLogo){
                if (a.getBoolean(attr,true)){
                    mIvRightIcon.setVisibility(VISIBLE);
                }else {
                    mIvRightIcon.setVisibility(GONE);
                }
            }else if (attr==R.styleable.LSettingView_isShowLeftInfoText){
                if (a.getBoolean(attr,false)){
                    mTvLeftInfoText.setVisibility(VISIBLE);
                }else {
                    mTvLeftInfoText.setVisibility(GONE);
                }
            }else if (attr==R.styleable.LSettingView_showEditInput){
                if (a.getBoolean(attr,false)){
                    editinput.setVisibility(VISIBLE);
                }else {
                    editinput.setVisibility(GONE);
                }
            }else if (attr==R.styleable.LSettingView_showEditInfo){
                if (a.getBoolean(attr,false)){
                    editInfo.setVisibility(VISIBLE);
                }else {
                    editInfo.setVisibility(GONE);
                }
            }
            else if (attr==R.styleable.LSettingView_isShowRightBtn){
                if (a.getBoolean(attr,false)){
                    rightBtn.setVisibility(VISIBLE);
                }else {
                    rightBtn.setVisibility(GONE);
                }
            }
        }
        a.recycle();
    }


    public void setmRightIcon(Drawable iconId) {
        mIvRightIcon.setImageDrawable(iconId);
    }

    /**
     * 根据设定切换右侧展示样式，同时更新点击事件处理方式
     *
     * @param mRightStyle
     */
    private void switchRightStyle(int mRightStyle) {
        switch (mRightStyle) {
            case 0:
                //默认展示样式，只展示一个图标
                mIvRightIcon.setVisibility(View.VISIBLE);
                mRightIcon_check.setVisibility(View.GONE);
                mRightIcon_switch.setVisibility(View.GONE);
                break;
            case 1:
                //隐藏右侧图标
                mRightLayout.setVisibility(View.INVISIBLE);
                mRightLayout.getLayoutParams().width = 38;//多加一行这个将文字设置靠右对齐即可
                break;
            case 2:
                //显示选择框样式
                mIvRightIcon.setVisibility(View.GONE);
                mRightIcon_check.setVisibility(View.VISIBLE);
                mRightIcon_switch.setVisibility(View.GONE);
                break;
            case 3:
                //显示开关切换样式
                mIvRightIcon.setVisibility(View.GONE);
                mRightIcon_check.setVisibility(View.GONE);
                mRightIcon_switch.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView(Context context) {
        mView = View.inflate(context, R.layout.settingitem, this);
        mRootLayout = mView.findViewById(R.id.rootLayout);
        mUnderLine = mView.findViewById(R.id.underline);
        mTvLeftText = mView.findViewById(R.id.tv_lefttext);
        mTvRightText = mView.findViewById(R.id.tv_righttext);
        mIvLeftIcon = mView.findViewById(R.id.iv_lefticon);
        mIvRightIcon = mView.findViewById(R.id.iv_righticon);
        mRightLayout = mView.findViewById(R.id.rightlayout);
        mRightIcon_check = mView.findViewById(R.id.rightcheck);
        mRightIcon_switch = mView.findViewById(R.id.rightswitch);
        mTvLeftInfoText = mView.findViewById(R.id.tv_left_info);
        rightBtn = mView.findViewById(R.id.tv_right_btn);
        editinput = mView.findViewById(R.id.edit_input);
        editInfo = mView.findViewById(R.id.edit_info);

        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnLSettingItemClick!=null){
                    mOnLSettingItemClick.click(true,v);
                }
            }
        });
        editinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (editinput.getText()!=null){
                 if (editTextListner!=null){
                     editTextListner.editListner(editinput.getText().toString());
                 }
              }
            }
        });

        editInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editInfo.getText()!=null){
                    if (editTextListner!=null){
                        editTextListner.editListner(editInfo.getText().toString());
                    }
                }
            }
        });
    }

    public void setEditInfo(String text){
        editInfo.setText(text);
    }

    public String getEditInfoData(){
        return editInfo.getText().toString();
    }


    /**
     * 处理点击事件
     */
    public void clickOn(View view) {
        switch (mRightStyle) {
            case 0:
            case 1:
                if (null != mOnLSettingItemClick) {
                  //  Log.i("click======", "clickOn===id==" + view.getId());
                    mOnLSettingItemClick.click(mChecked, view);
                }
                break;
            case 2:
                //选择框切换选中状态
                mRightIcon_check.setChecked(!mRightIcon_check.isChecked());
                mChecked = mRightIcon_check.isChecked();
                break;
            case 3:
                //开关切换状态
                mRightIcon_switch.setChecked(!mRightIcon_switch.isChecked());
                mChecked = mRightIcon_check.isChecked();
                break;
        }
    }

    /**
     * 获取根布局对象
     *
     * @return
     */
    public RelativeLayout getmRootLayout() {
        return mRootLayout;
    }

    /**
     * 更改左侧文字
     */
    public void setLeftText(String info) {
        mTvLeftText.setText(info);
    }

    /**
     * 更改右侧文字
     */
    public void setRightText(String info) {
        mTvRightText.setText(info);
    }

    /**
     * 获取右侧的文本
     */
    public String getRightText(){
       return mTvRightText.getText().toString();
    }

    public interface OnLSettingItemClick {
        void click(boolean isChecked, View view);
    }

    /**
     * 获取文本输入框
     * @return
     */
    public String getEditText(){
        return editinput.getText().toString();
    }


    /**
     * sp转px
     *
     * @param sp sp值
     * @return px值
     */
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    public static interface EditTextListner{
        void editListner(String text);
    }
}

