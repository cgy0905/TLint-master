package com.cgy.hupu.components.retrofit;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.utils.SecurityUtil;
import com.cgy.hupu.utils.SettingPrefUtil;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gzsll on 2014/9/23 0023.
 */
public class RequestHelper {

    private Context context;
    private UserStorage userStorage;

    public RequestHelper(Context mContext, UserStorage mUserStorage) {
        this.context = mContext;
        this.userStorage = mUserStorage;
    }

    public Map<String, String> getHttpRequestMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("client", getDeviceId());
        map.put("night", SettingPrefUtil.getNightModel(context) ? "1" : "0");
        if (userStorage.isLogin()) {
            try {
                map.put("token", URLEncoder.encode(userStorage.getToken(), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String getAndroidId() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getDeviceId() {
        String deviceId;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getDeviceId() == null) {
            deviceId = getAndroidId();
        } else {
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }

    public String getRequestSign(Map<String, String> map) {
        ArrayList<Map.Entry<String, String>> list =
                new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().compareTo(rhs.getKey());
            }
        });
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i = i + 1) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            Map.Entry<String, String> map1 = list.get(i);
            builder.append(map1.getKey()).append("=").append(map1.getValue());
        }
        builder.append(SettingPrefUtil.getHuPuSign(context));
        return SecurityUtil.getMD5(builder.toString());
    }
}
