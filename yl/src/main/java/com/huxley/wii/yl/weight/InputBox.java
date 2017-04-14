package com.huxley.wii.yl.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.huxley.wii.yl.R;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_PHONE;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

/**
 * 自定义输入框
 * Created by huxley on 16/11/5.
 */

public class InputBox extends LinearLayout {

    private static final int   DEFAULT_TEXT_COLOR_DES     = 0;
    private static final int   DEFAULT_TEXT_COLOR_CONTENT = 0;
    private static final int   DEFAULT_INPUT_BOX_TYPE     = 0;
    private static final float DEFAULT_TEXT_SIZE          = 0;
    public static final  int   TYPE_EDIT                  = 0;
    public static final  int   TYPE_SELECT                = 1;
    public static final  int   TYPE_JUMP_SELECT           = 2;
    public static final  int   TYPE_SPINNER               = 3;
    public static final  int   EDIT_INPUT_TYPE_USER_NAME  = 0;
    public static final  int   EDIT_INPUT_TYPE_PASSWORD   = 1;
    public static final  int   EDIT_INPUT_TYPE_PHONE_NUM  = 2;
    public static final  int   EDIT_INPUT_TYPE_NUM        = 3;

    private int            mTextColorDes;
    private int            mTextColorContent;
    private int            mInputBoxType;
    private float          mTextSize;
    private String         mTextDes;
    private String         mTextContent;
    private String         mTextHint;
    private String         mTextSeparator;
    private int            mEditInputType;
    private CharSequence[] mSpinnerData;
    private TextView       mDesView;
    private View           mContentView;

    public InputBox(Context context) {
        this(context, null);
    }

    public InputBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainStyledAttributes(context, attrs);
        initDesView();
        initContentView(mInputBoxType);
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.input_box);
        mTextColorDes = ta.getColor(R.styleable.input_box_text_color_des, DEFAULT_TEXT_COLOR_DES);
        mTextColorContent = ta.getColor(R.styleable.input_box_text_color_content, DEFAULT_TEXT_COLOR_CONTENT);
        mTextSize = ta.getDimension(R.styleable.input_box_text_size, DEFAULT_TEXT_SIZE);
        mTextContent = ta.getString(R.styleable.input_box_text_content);
        mTextDes = ta.getString(R.styleable.input_box_text_des);
        mTextHint = ta.getString(R.styleable.input_box_text_hint);
        mTextSeparator = ta.getString(R.styleable.input_box_text_separator);
        mSpinnerData = ta.getTextArray(R.styleable.input_box_spinner_data);
        mInputBoxType = ta.getInt(R.styleable.input_box_input_box_type, DEFAULT_INPUT_BOX_TYPE);
        mEditInputType = ta.getInt(R.styleable.input_box_edit_input_type, EDIT_INPUT_TYPE_USER_NAME);
        if (mTextSeparator == null) {
            mTextSeparator = "：";
        }
        ta.recycle();
    }

    private void initDesView() {
        mDesView = new TextView(getContext());
        mDesView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mDesView.setTextColor(mTextColorDes);
        mDesView.setText(String.format("%s%s", mTextDes, mTextSeparator));
        mDesView.setGravity(Gravity.CENTER_VERTICAL);
        if (getChildCount() >= 1) removeViewAt(0);
        addView(mDesView, 0);
    }

    private void initContentView(int type) {
        switch (type) {
            case TYPE_EDIT:
                mContentView = getEditView();
                break;
            case TYPE_SELECT:
                mContentView = getSelectView();
                break;
            case TYPE_JUMP_SELECT:
                mContentView = getJumpSelectView();
                break;
            case TYPE_SPINNER:
                mContentView = getSpinnerView();
                break;
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContentView.setLayoutParams(params);
        if (getChildCount() == 2) removeViewAt(1);
        addView(mContentView);
    }

    private Spinner getSpinnerView() {
        Spinner spinner = new Spinner(getContext());
        spinner.setGravity(Gravity.CENTER_VERTICAL);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, mSpinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private EditText getEditView() {
        EditText editText = new EditText(getContext());
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setTextColor(mTextColorContent);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        editText.setHint(mTextHint);
        switch (mEditInputType) {
            case EDIT_INPUT_TYPE_NUM:
                editText.setInputType(TYPE_CLASS_NUMBER);
                break;
            case EDIT_INPUT_TYPE_PASSWORD:
                editText.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case EDIT_INPUT_TYPE_PHONE_NUM:
                editText.setInputType(TYPE_CLASS_PHONE);
                break;
            case EDIT_INPUT_TYPE_USER_NAME:
                editText.setInputType(TYPE_CLASS_TEXT);
                break;
        }
        return editText;
    }

    private TextView getJumpSelectView() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(mTextColorContent);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_common_back),null);
        textView.setHint(mTextHint);
        return textView;
    }

    private TextView getSelectView() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(mTextColorContent);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setHint(mTextHint);
        return textView;
    }

    public String getContent() {
        switch (mInputBoxType) {
            case TYPE_EDIT:
                return ((EditText)mContentView).getText().toString();
            case TYPE_SELECT:
            case TYPE_JUMP_SELECT:
                return ((TextView)mContentView).getText().toString();
            case TYPE_SPINNER:
                return ((Spinner)mContentView).getSelectedItem().toString();
            default:
                return "";
        }
    }

    public int getSpinnerPosition() {
        return ((Spinner)mContentView).getSelectedItemPosition();
    }

    public int getInputBoxType() {
        return mInputBoxType;
    }

    public void clearContent() {
        ((TextView)mContentView).setText("");
    }
}
