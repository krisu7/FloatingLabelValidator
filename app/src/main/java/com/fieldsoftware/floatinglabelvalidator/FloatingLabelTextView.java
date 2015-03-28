package com.fieldsoftware.floatinglabelvalidator;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Ivan on 15/03/2015.
 */
public class FloatingLabelTextView extends FrameLayout implements TextWatcher {
    private EditText mEditText;
    private TextView mTextViewHintBottom;
    private TextView mTextViewHintTop;
    private String validationMessage;
    private String hintText;
    private boolean allowEmpty;
    private int validatorType;
    private Validator validator;

    private int red;
    private int green;

    public FloatingLabelTextView(Context context) {
        super(context);
    }

    public FloatingLabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingLabelTextView,
                0, 0);
        try {
            validationMessage = a.getString(R.styleable.FloatingLabelTextView_validationMessage);
            hintText = a.getString(R.styleable.FloatingLabelTextView_hint);
            allowEmpty = a.getBoolean(R.styleable.FloatingLabelTextView_allowEmpty, true);
            validatorType = a.getInt(R.styleable.FloatingLabelTextView_validatorType, -1);
        } finally {
            a.recycle();
        }
        init();
    }

    public FloatingLabelTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.floating_label_text_view, this);
        mTextViewHintTop = (TextView) findViewById(R.id.textViewHintTop);
        mTextViewHintBottom = (TextView) findViewById(R.id.textViewHintBottom);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText.addTextChangedListener(this);
        mTextViewHintBottom.setText(hintText);
        mTextViewHintTop.setText(hintText);
        red = getResources().getColor(android.R.color.holo_red_light);
        green = getResources().getColor(android.R.color.holo_green_light);
        validator = new Validator(allowEmpty, validatorType);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTextViewHintBottom.setVisibility(s.length() > 0 ? View.INVISIBLE : View.VISIBLE);
        mTextViewHintTop.setVisibility(s.length() > 0 ? View.VISIBLE : View.VISIBLE);

        validationMessage = validator.validate(s);
        boolean isValid = TextUtils.isEmpty(validationMessage);
        mTextViewHintTop.setTextColor(isValid ? green : red);
        mTextViewHintTop.setText(isValid ? hintText + "" : hintText + " - " + validationMessage);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

//    public String validate(CharSequence s) {
//        if (s.length() > 0) {
//            return "";
//        } else {
//            return "must not be empty";
//        }
//    }
}