package com.hua.myinterstellar_core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 11:30
 */

public class Reply implements Parcelable {
    public static final int REPLY_FAILED = -1;
    public static final int REPLY_SUCCEED = 0;
    private int code;
    private Object result;
    private String errorMsg;

    private Reply(int code, Object result) {
        this.code = code;
        this.result = result;
    }

    private Reply(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public static Reply success(Object result) {
        return new Reply(REPLY_SUCCEED, result);
    }

    public static Reply failed(String errorMsg) {
        return new Reply(REPLY_FAILED, errorMsg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (code == REPLY_SUCCEED) {
            dest.writeInt(this.code);
            dest.writeValue(this.result);
        } else {
            dest.writeInt(this.code);
            dest.writeString(this.errorMsg);
        }
    }

    private Reply(Parcel in) {
        this.code = in.readInt();
        if (code == REPLY_SUCCEED) {
            this.result = in.readValue(getClass().getClassLoader());
        } else {
            this.errorMsg = in.readString();
        }
    }

    public static final Creator<Reply> CREATOR = new Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel source) {
            return new Reply(source);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };

    public int getCode() {
        return code;
    }

    public Object getResult() {
        return result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
