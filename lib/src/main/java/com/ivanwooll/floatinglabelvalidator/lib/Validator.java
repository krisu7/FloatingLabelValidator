package com.ivanwooll.floatinglabelvalidator.lib;

import android.text.TextUtils;

/**
 * Created by Ivan on 15/03/2015.
 *
 * Edited by Krzysztof on 11/11/2016.
 */

class Validator {
    private static final String ALPHA_ERROR_MESSAGE =
            MyApplication.resources.getString(R.string.alpha_error_message);
    private static final String NUMERIC_ERROR_MESSAGE =
            MyApplication.resources.getString(R.string.numeric_error_message);
    private static final String ALPHA_NUMERIC_ERROR_MESSAGE =
            MyApplication.resources.getString(R.string.alpha_numeric_error_message);
    private static final String EMAIL_ERROR_MESSAGE =
            MyApplication.resources.getString(R.string.email_error_message);
    private static final String PHONE_ERROR_MESSAGE =
            MyApplication.resources.getString(R.string.phone_error_message);
    private static final String MUST_NOT_BE_EMPTY =
            MyApplication.resources.getString(R.string.must_not_be_empty);

    private boolean mAllowEmpty;
    private int mValidatorType;
    private String mString;
    private char[] mChars;
    private CharSequence mCharSequence;

    Validator(boolean allowEmpty, int validatorType) {
        this.mAllowEmpty = allowEmpty;
        this.mValidatorType = validatorType;
    }

    String validate(CharSequence s) {
        mCharSequence = s;
        String result = "";
        mString = stringFromCharSequence(s);
        mChars = mString.toCharArray();
        if (mAllowEmpty && mValidatorType < 0) {
            return "";
        }
        if (mString.length() < 1) {
            return MUST_NOT_BE_EMPTY;
        }
        switch (mValidatorType) {
            case Constants.ALPHA:
                result += validateAlpha();
                break;
            case Constants.NUMERIC:
                result += validateNumeric();
                break;
            case Constants.ALPHA_NUMERIC:
                result += validateAlphaNumeric();
                break;
            case Constants.EMAIL:
                result += validateEmail();
                break;
            case Constants.PHONE:
                result += validatePhoneNO();
                break;
            default:
                break;
        }
//        result += validateNotEmpty();
        return result;
    }


    private String validateNotEmpty() {
        if (mString.length() > 0) {
            return "";
        } else {
            return MUST_NOT_BE_EMPTY;
        }
    }

    private String validateAlpha() {
        for (char c : mChars) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return ALPHA_ERROR_MESSAGE;
            }
        }
        return "";
    }

    private String validateNumeric() {
        for (char c : mChars) {
            if (!Character.isDigit(c)) {
                return NUMERIC_ERROR_MESSAGE;
            }
        }
        return "";
    }

    private String validateAlphaNumeric() {
        for (char c : mChars) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                return ALPHA_NUMERIC_ERROR_MESSAGE;
            }
        }
        return "";
    }

    private String validateEmail() {
        if (!isValidEmail(mCharSequence)) {
            return EMAIL_ERROR_MESSAGE;
        } else {
            return "";
        }
    }

    private String validatePhoneNO() {
        if (!isValidPhoneNo(mCharSequence)) {
            return PHONE_ERROR_MESSAGE;
        } else {
            return "";
        }
    }


    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPhoneNo(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.PHONE.matcher(target).matches();
    }

    private String stringFromCharSequence(CharSequence s) {
        String str;
        str = String.valueOf(s);
        return str;
    }

    int getValidatorType() {
        return this.mValidatorType;
    }

}