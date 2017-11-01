package cn.com.reformer.brake.utils;

import org.greenrobot.eventbus.EventBus;

import cn.com.reformer.ble.BleKey;
import mutils.ByteUtils;
import cn.com.reformer.ble.OnBleListListener;
import cn.com.reformer.ble.OnDatasListener;
import cn.com.reformer.ble.OnStateListener;
import mutils.ThreadUtils;
import mutils.CRC16;

public class BleUtils {
    private static BleKey bleKey;

    public static void init() {
        bleKey = new BleKey(UIUtils.getContext());
        bleKey.setOnDatasListener(new OnDatasListener() {
            @Override
            public void OnDatas(final byte[] result) {
                MyBroadCastUtils.receive(result);
                verify(result);
            }
        });
        bleKey.setOnStateListener(new OnStateListener() {
            @Override
            public void state(int step) {
                switch (step) {
                    case 211://ble服务断开
                        EventBus.getDefault().post(String.valueOf(211));
                        break;
                    case 203://连接成功
                        EventBus.getDefault().post(String.valueOf(203));
                        break;
                    case 212://连接失败
                        EventBus.getDefault().post(String.valueOf(212));
                        break;
                }
            }
        });
    }

    public static void conn(String mac) {
        bleKey.connect(mac);
    }

    public static void setScanListener(OnBleListListener onBleListListener) {
        bleKey.setOnBleDevListListener(onBleListListener);
    }

    public static void scan() {
        bleKey.scanStop();
        bleKey.scanStart(SaveUtils.getBleBeans());
        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                bleKey.scanStop();//5s停止扫描
            }
        }, 5000);
    }

    public static void scanStop() {
        bleKey.scanStop();
    }

    public static void close() {
        bleKey.closeBle();
    }

    public static boolean send(byte[] cmd) {
        byte[] concat = ByteUtils.concat(cmd, ByteUtils.int2Bytes2(CRC16.calcCrc16(cmd)));
        MyBroadCastUtils.send(concat);
        return bleKey.writeChar(concat);
    }

    //校验数据
    private static void verify(byte[] result) {
        switch (result[1]) {
            case (byte) 0x83://5.2
            case (byte) 0x84://5.1
            case (byte) 0x85://5.4
            case (byte) 0x90://5.3
            case (byte) 0xAB:
                switch (result[2]) {
                    case 0x01:
//                        ToastUtils.showToast(UIUtils.getContext(), "通用错误");
                        break;
                    case 0x02:
//                        ToastUtils.showToast(UIUtils.getContext(), "地址溢出");
                        break;
                    case 0x03:
//                        ToastUtils.showToast(UIUtils.getContext(), "字节异常");
                        break;
                    case 0x04:
                        ToastUtils.showToast(UIUtils.getContext(), "紧急模式,禁止操作!");//参数数据不符合要求范围;
                        break;
                    case 0x05:
//                        ToastUtils.showToast(UIUtils.getContext(), "Crc16校验失败");
                        break;
                    case 0x06:
//                        ToastUtils.showToast(UIUtils.getContext(), "CAN通信错误");
                        break;
                }
                return;
            default:
                EventBus.getDefault().post(result);
                break;
        }
    }
}
