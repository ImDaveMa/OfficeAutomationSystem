package com.hengkai.officeautomationsystem.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Harry on 2018/5/19.
 */
public class ReportContactsEntity {

    /**
     * DATE : [{"iconLink":"20180519093257528_231.jpeg","name":"管理员","userId":1},{"iconLink":"","name":"刘浩","userId":7}]
     * CODE : 1
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean implements Parcelable {
        /**
         * iconLink : 20180519093257528_231.jpeg
         * name : 管理员
         * userId : 1
         */

        public String iconLink;
        public String name;
        public int userId;
        public boolean checked;

        protected DATEBean(Parcel in) {
            iconLink = in.readString();
            name = in.readString();
            userId = in.readInt();
            checked = in.readByte() != 0;
        }

        public static final Creator<DATEBean> CREATOR = new Creator<DATEBean>() {
            @Override
            public DATEBean createFromParcel(Parcel in) {
                return new DATEBean(in);
            }

            @Override
            public DATEBean[] newArray(int size) {
                return new DATEBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(iconLink);
            dest.writeString(name);
            dest.writeInt(userId);
            dest.writeByte((byte) (checked ? 1 : 0));
        }
    }
}
