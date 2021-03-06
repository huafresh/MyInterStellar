package com.hua.myinterstellar_core;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-02-26 21:34
 */

public class StellarMethod implements Parcelable {
    private String methodName;
    private @Nullable
    Object[] args;

    private static final int VAL_BASE_CALLBACK = -2;

    @SuppressWarnings("unchecked")
    public void readFromParcel(Parcel in) {
        this.methodName = in.readString();
        int count = in.readInt();
        if (count != -1 && args != null) {
            for (int i = 0; i < count; i++) {
                Object arg = args[i];
                Object argFromParcel = null;
                try {
                    argFromParcel =  in.readValue(getClass().getClassLoader());
                } catch (Exception e) {
                    // 应该是ICallback导致的异常
                    in.setDataPosition(in.dataPosition() - 4);
                    int type = in.readInt();
                    if (type == VAL_BASE_CALLBACK) {
                        argFromParcel = ICallback.Stub.asInterface(in.readStrongBinder());
                    } else {
                        throw e;
                    }
                }
                if (arg instanceof Outable) {
                    ((Outable) arg).copy(argFromParcel);
                } else {
                    args[i] = argFromParcel;
                }
            }
        }
    }

    public StellarMethod(String methodName, @Nullable Object[] args) {
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.methodName);
        if (args == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(args.length);
            for (Object arg : args) {
                if (arg instanceof ICallback) {
                    dest.writeInt(VAL_BASE_CALLBACK);
                    dest.writeStrongBinder(((ICallback) arg).asBinder());
                } else {
                    dest.writeValue(arg);
                }
            }
        }
    }

    protected StellarMethod(Parcel in) {
        this.methodName = in.readString();
        int count = in.readInt();
        if (count != -1) {
            args = new Object[count];
            for (int i = 0; i < count; i++) {
                Object arg = null;
                try {
                    arg = in.readValue(getClass().getClassLoader());
                } catch (Exception e) {
                    // 应该是ICallback导致的异常
                    in.setDataPosition(in.dataPosition() - 4);
                    int type = in.readInt();
                    if (type == VAL_BASE_CALLBACK) {
                        arg = ICallback.Stub.asInterface(in.readStrongBinder());
                    } else {
                        throw e;
                    }
                }
                args[i] = arg;
            }
        }
        Log.d("@@@hua", "恢复StellarMethod : " + this);
    }

    public static final Creator<StellarMethod> CREATOR = new Creator<StellarMethod>() {
        @Override
        public StellarMethod createFromParcel(Parcel source) {
            return new StellarMethod(source);
        }

        @Override
        public StellarMethod[] newArray(int size) {
            return new StellarMethod[size];
        }
    };

    public String getMethodName() {
        return methodName;
    }

    @Nullable
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "StellarMethod{" +
                "methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
