package cn.com.reformer.ble;

import android.bluetooth.BluetoothDevice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
    public static boolean containBytes(byte[] bytes, ArrayList<BleBean> arrlt) {
        if (arrlt == null)
            return false;
        for (BleBean dev : arrlt) {
            if (Arrays.equals(dev.mac, bytes))
                return true;
        }
        return false;
    }

    public static BleBean bytes1Bean(byte[] bytes, ArrayList<BleBean> arrlt) {
        if (arrlt == null)
            return null;
        for (BleBean dev : arrlt) {
            if (Arrays.equals(dev.mac, bytes))
                return dev;
        }
        return null;
    }

    public static String getAddressFromMac(byte[] mac, ArrayList<BleBean> list) {
        for (BleBean devContext : list) {
            if (Arrays.equals(mac, devContext.mac))
                return devContext.address;
        }
        return "";
    }

    public static boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    public static boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }
}
