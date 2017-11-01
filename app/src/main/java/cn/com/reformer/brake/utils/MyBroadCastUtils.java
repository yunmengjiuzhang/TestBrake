package cn.com.reformer.brake.utils;

import android.content.Intent;

public class MyBroadCastUtils {
    public static void send(String msg) {
        Intent intent = new Intent();
        intent.setAction("asdasdasdfasfasfasf");
        intent.putExtra("msg", msg);
        UIUtils.getContext().sendBroadcast(intent);
    }

    private static void send(String a, byte[] msg) {
        StringBuffer s = new StringBuffer(a);
        for (int i = 0; i < msg.length; i++) {
            s.append(Integer.toHexString(((int) msg[i])));
            s.append(",");
        }
        Intent intent = new Intent();
        intent.setAction("asdasdasdfasfasfasf");
        intent.putExtra("msg", s.toString());
        UIUtils.getContext().sendBroadcast(intent);
    }

    public static void send(byte[] msg) {
        send("a", msg);
    }

    public static void receive(byte[] msg) {
        send("b", msg);
    }
}
