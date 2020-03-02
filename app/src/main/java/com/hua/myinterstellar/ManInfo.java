package com.hua.myinterstellar;

import android.os.Parcel;
import android.os.Parcelable;

import com.hua.myinterstellar_core.Outable;
import com.hua.myinterstellar_core.StellarMethod;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 11:40
 */
public class ManInfo implements Parcelable, Outable<ManInfo> {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void copy(ManInfo source) {
        this.name = source.getName();
        this.age = source.getAge();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public ManInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected ManInfo(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<ManInfo> CREATOR = new Creator<ManInfo>() {
        @Override
        public ManInfo createFromParcel(Parcel source) {
            return new ManInfo(source);
        }

        @Override
        public ManInfo[] newArray(int size) {
            return new ManInfo[size];
        }
    };

    @Override
    public String toString() {
        return "ManInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
