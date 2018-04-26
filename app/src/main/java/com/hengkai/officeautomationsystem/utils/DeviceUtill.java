package com.hengkai.officeautomationsystem.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.hengkai.officeautomationsystem.application.OfficeAutomationSystemApplication;

import java.util.UUID;

/**
 * Created by Harry on 2017/7/20.
 */

public class DeviceUtill {


    public static String getDeviceUUID() {
        String uuid;
        final TelephonyManager tm = (TelephonyManager) OfficeAutomationSystemApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        if (ActivityCompat.checkSelfPermission(OfficeAutomationSystemApplication.getAppContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(OfficeAutomationSystemApplication.getAppContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        uuid = deviceUuid.toString();
        return uuid;
    }

}
