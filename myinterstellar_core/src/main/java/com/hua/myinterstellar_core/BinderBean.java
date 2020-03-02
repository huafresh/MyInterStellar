package com.hua.myinterstellar_core;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 09:46
 */

public class BinderBean implements Parcelable {
    private String processName;
    private IBinder transfer;

    public String getProcessName() {
        return processName;
    }

    public IBinder getTransfer() {
        return transfer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.processName);
        dest.writeStrongBinder(transfer);
    }

    public BinderBean(String processName, IBinder transfer) {
        this.processName = processName;
        this.transfer = transfer;
    }

    private BinderBean(Parcel in) {
        this.processName = in.readString();
        this.transfer = in.readStrongBinder();
    }

    public static final Creator<BinderBean> CREATOR = new Creator<BinderBean>() {
        @Override
        public BinderBean createFromParcel(Parcel source) {
            return new BinderBean(source);
        }

        @Override
        public BinderBean[] newArray(int size) {
            return new BinderBean[size];
        }
    };
}
