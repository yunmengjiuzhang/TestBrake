package cn.com.reformer.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import java.util.UUID;

public class Ble extends BluetoothGattCallback {
    protected static final UUID UUID_SERVICE = UUID.fromString("0000fdea-0000-1000-8000-00805f9b34fb");
    protected static final UUID UUID_WRITE = UUID.fromString("0000fde7-0000-1000-8000-00805f9b34fb");
    protected static final UUID UUID_NOTIFY = UUID.fromString("0000fde8-0000-1000-8000-00805f9b34fb");
    protected static final UUID UUID_DESC = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private Context mCtx;
    private BluetoothAdapter mBtAdapter;
    private String mAddress;
    private OnBleListener mInnerGattCallBack;
    private byte[] tempCmd2 = null;
    private BluetoothGattCharacteristic mWriteChar;
    private BluetoothGatt mBluetoothGatt;

    public Ble(Context ctx) {
        mCtx = ctx;
        final BluetoothManager bluetoothManager = (BluetoothManager)
                ctx.getSystemService(Context.BLUETOOTH_SERVICE);
        mBtAdapter = bluetoothManager.getAdapter();
    }

    public boolean scanStart() {
        return mBtAdapter != null && mBtAdapter.startLeScan(null, m18LeScanCallback);
    }

    public void scanStop() {
        if (mBtAdapter != null)//华为:G7-Ul20;CHM-00;G7-TL00;蓝牙4.0,会出现空指针异常
            mBtAdapter.stopLeScan(m18LeScanCallback);
    }

    public boolean writeChar(byte[] bytes) {
        if (mWriteChar == null || mBluetoothGatt == null || bytes == null)
            return false;
        if (bytes.length <= 20) {
            return writeCharInner(bytes);
        } else {
            byte[] tempCmd1 = new byte[20];
            tempCmd2 = new byte[bytes.length - 20];
            System.arraycopy(tempCmd1, 0, bytes, 0, 20);
            System.arraycopy(tempCmd2, 0, bytes, 0, bytes.length - 20);
            return writeCharInner(tempCmd1);
        }
    }

    private boolean writeCharInner(byte[] bytes) {//写特征值
        return mWriteChar.setValue(bytes) && mBluetoothGatt.writeCharacteristic(mWriteChar);
    }

    public int connect(String address) {
        mAddress = address;
        if (mInnerGattCallBack != null)
            mInnerGattCallBack.onConnectStatus(1);
        close();
        if (mBtAdapter == null) {
            return 201;
        }
        final BluetoothDevice device = mBtAdapter.getRemoteDevice(mAddress);
        if (device == null) {
            return 202;
        }
        mBluetoothGatt = device.connectGatt(mCtx, false, this);//第一步:连接gatt:获取gatt管道
        return 0;
    }

    public void close() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            if (mBluetoothGatt != null) {
                mBluetoothGatt.close();
                if (mBluetoothGatt != null)
                    mBluetoothGatt = null;
            }
        }
        if (mBtAdapter != null) {
            if (mAddress != null && !mAddress.equals("")) {
                BluetoothDevice device = mBtAdapter.getRemoteDevice(mAddress);
                if (device != null) {
                    if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                        try {
                            Utils.removeBond(BluetoothDevice.class, device);//适配魅族某款手机
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            gatt.discoverServices();//第二步:gatt连接成功,发现服务
        } else {
            connect(mAddress);
        }
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == 0) {//第三步:发现服务成功;获取服务,订阅特征值
            BluetoothGattService service = gatt.getService(UUID_SERVICE);
            mWriteChar = service.getCharacteristic(UUID_WRITE);
            BluetoothGattCharacteristic mIndicateChar = service.getCharacteristic(UUID_NOTIFY);
            gatt.setCharacteristicNotification(mIndicateChar, true);//写入特征值改变时,通知
            BluetoothGattDescriptor descriptor = mIndicateChar.getDescriptor(UUID_DESC);//获取修饰
//            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);//修饰配置
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);//修饰配置
            gatt.writeDescriptor(descriptor);
        } else {
            connect(mAddress);
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status == 0 && mInnerGattCallBack != null) {//第四步:订阅特征值成功;发送数据;交换随即密钥
            mInnerGattCallBack.onDescriptorWrite();
            mInnerGattCallBack.onConnectStatus(0);
        } else {
            connect(mAddress);
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == 0 && mInnerGattCallBack != null) {//第六步:写入第一包数据后睡眠,发送第二包数据
            if (tempCmd2 != null) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] a = tempCmd2;
                tempCmd2 = null;
                writeCharInner(a);
            }
        } else {
            connect(mAddress);
        }
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        if (mInnerGattCallBack != null)
            mInnerGattCallBack.onCharacteristicChanged(characteristic.getValue());
    }

    public void setOnGattListener(OnBleListener innerGattCallBack) {
        mInnerGattCallBack = innerGattCallBack;
    }

    public interface OnBleListener {

        public void onConnectStatus(int status);

        public void onDescriptorWrite();

        public void onCharacteristicChanged(byte[] value);

        public void onScanResult(String name, String address, int rssi, byte[] mac);
    }

    private BluetoothAdapter.LeScanCallback m18LeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] bytes) {
            if (mInnerGattCallBack == null)
                return;
            byte[] macTemp = new byte[9];
            System.arraycopy(bytes, 18, macTemp, 0, 9);
            mInnerGattCallBack.onScanResult(device.getName(), device.getAddress(), rssi, macTemp);
        }
    };

//    private ScanCallback m21ScanCallback = new ScanCallback() {
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            byte[] scanRecord = result.getScanRecord().getBytes();
//            BluetoothDevice device = result.getDevice();
//            int rssi = result.getRssi();
////            foundNewDevice(scanRecord, device, rssi);
//        }
//    };
}
