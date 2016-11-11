package com.ivanwooll.floatinglabelvalidator.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Ivan on 15/03/2015.
 *
 * Edited by Krzysztof on 11/11/2016.
 */

public class FloatingLabelTextView extends FrameLayout implements TextWatcher, View.OnFocusChangeListener {
    private TextInputEditText mEditText;
    private TextInputLayout mTextViewHintTop;
    private String mValidationMessage;
    private String mHintText;
    private boolean mAllowEmpty;
    private int mValidatorType;
    private Validator mValidator;
    private boolean mIsEmpty;
    private boolean mIsValid;

    public FloatingLabelTextView(Context context) {
        super(context);
    }

    public FloatingLabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingLabelTextView, 0, 0);
        try {
            mValidationMessage = a.getString(R.styleable.FloatingLabelTextView_validationMessage);
            mHintText = a.getString(R.styleable.FloatingLabelTextView_hint);
            mAllowEmpty = a.getBoolean(R.styleable.FloatingLabelTextView_allowEmpty, true);
            mValidatorType = a.getInt(R.styleable.FloatingLabelTextView_validatorType, -1);
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
        mTextViewHintTop = (TextInputLayout) findViewById(R.id.textViewHintTop);
        mEditText = (TextInputEditText) findViewById(R.id.editText);
        mEditText.addTextChangedListener(this);
        mTextViewHintTop.setHint(mHintText);
        mValidator = new Validator(mAllowEmpty, mValidatorType);
        switch (mValidator.getValidatorType()) {
            case Constants.ALPHA:
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case Constants.NUMERIC:
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case Constants.ALPHA_NUMERIC:
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case Constants.EMAIL:
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case Constants.PHONE:
                mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            default:
                break;
        }

        mEditText.setOnFocusChangeListener(this);
        mIsEmpty = true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mIsEmpty = s.length() < 1;
        mValidationMessage = mValidator.validate(s);
        mIsValid = TextUtils.isEmpty(mValidationMessage);
        mTextViewHintTop.setHintTextAppearance(mIsValid ? R.style.TextAppearance_AppCompat_TextTrue
                : R.style.TextAppearance_AppCompat_TextFalse);
        mTextViewHintTop.setHint(mIsValid ? mHintText + "" : mHintText + " - " + mValidationMessage);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    }

    public Editable getText() {
        return mEditText.getText();
    }

    public void setText(String text) {
        mTextViewHintTop.animate().translationY(0);
        mEditText.setText(text);
    }

    public boolean isValid() {
        return mIsValid;
    }
}