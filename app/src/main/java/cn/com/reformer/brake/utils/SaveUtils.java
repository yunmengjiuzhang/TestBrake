package cn.com.reformer.brake.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.reformer.brake.BaseApp;
import cn.com.reformer.ble.BleBean;
import mutils.ByteUtils;
import cn.com.reformer.ble.Utils;
import mutils.SpUtil;

public class SaveUtils {

    public static boolean putBleBeans(ArrayList<BleBean> list) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        try {
            for (BleBean bean : list) {
                jsonObject = new JSONObject();
                jsonObject.put("name", bean.name);
                jsonObject.put("nameServer", bean.nameServer);
                jsonObject.put("address", bean.address);
                jsonObject.put("rssi", bean.rssi);
                jsonObject.put("mac", ByteUtils.bytesToString(bean.mac));
                jsonArray.put(jsonObject);
            }
            String s1 = jsonArray.toString();
            SpUtil.putString(BaseApp.getInstance(), "BleBeans", s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<BleBean> getBleBeans() {
        String bleBeans = SpUtil.getString(BaseApp.getInstance(), "BleBeans", null);
        if (bleBeans == null) {
            return new ArrayList<BleBean>();
        }
        try {
            JSONArray jsonArray = new JSONArray(bleBeans);
            ArrayList<BleBean> bleBeensss = new ArrayList<BleBean>();
            JSONObject jsonObject;
            String name;
            String macString;
            String nameServer;
            String address;
            int rssi = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                name = jsonObject.optString("name");
                nameServer = jsonObject.optString("nameServer");
                address = jsonObject.optString("address");
                macString = jsonObject.optString("mac");
                rssi = jsonObject.optInt("rssi");
                bleBeensss.add(new BleBean(name, ByteUtils.stringToBytes(macString), rssi, address, nameServer));
            }
            return bleBeensss;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<BleBean>();
    }

    public static void addBeanName(BleBean bleBean, String nameServer) {
        ArrayList<BleBean> bleBeans = getBleBeans();
        if (Utils.containBytes(bleBean.mac, bleBeans)) {
            BleBean bean = Utils.bytes1Bean(bleBean.mac, bleBeans);
            bean.nameServer = nameServer;
        } else {
            bleBeans.add(new BleBean(bleBean.mac, nameServer));
        }
        putBleBeans(bleBeans);
    }

}
