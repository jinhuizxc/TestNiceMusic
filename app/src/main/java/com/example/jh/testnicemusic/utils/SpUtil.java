package com.example.jh.testnicemusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xian on 2018/1/13.
 */

public class SpUtil {
    private volatile static SpUtil mInstance;

    private Context mContext;
    private SharedPreferences mPref;

    private SpUtil() {
    }

    public static SpUtil getInstance() {
        if (null == mInstance) {
            synchronized (SpUtil.class) {
                if (null == mInstance) {
                    mInstance = new SpUtil();
                }
            }
        }
        return mInstance;
    }

    //在AppBase里面初始化
    public void init(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        }
    }

    public void putString(String key, String value) {
        Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        Editor editor = mPref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        Editor editor = mPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        return mPref.getBoolean(key, def);
    }

    public String getString(String key) {
        return mPref.getString(key, "");
    }

    public String getString(String key, String def) {
        return mPref.getString(key, def);
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    public long getLong(String key, long defInt) {
        return mPref.getLong(key, defInt);
    }

    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public long getInt(String key, int defInt) {
        return mPref.getInt(key, defInt);
    }

    /**
     * 存ArrayList
     *
     * @param key
     * @param list
     */
    public <T> void putList(String key, List<T> list) {

        try {
            String jsonStr;
            if (list == null) {
                jsonStr = "{}";
            } else {
                jsonStr = new Gson().toJson(list, List.class);
            }

            Editor editor = mPref.edit();
            editor.putString(key, jsonStr);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取ArrayList
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            String jsonStr = mPref.getString(key, "{}");
            if (!jsonStr.equals("{}")) {
                JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(gson.fromJson(elem, cls));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean contains(String key) {
        return mPref.contains(key);
    }


    public void remove(String key) {
        Editor editor = mPref.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }
}
