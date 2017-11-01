package cn.com.reformer.brake.fragments;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    private static Map<Integer, Fragment> fragents = new HashMap<>();

    public static Fragment create(int position) {
        Fragment fragment = fragents.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new MessageFragment();
                    break;
                case 1:
                    fragment = new SettingFragment();
                    break;
                case 2:
                    fragment = new LogFragment();
                    break;
                case 3:
                    fragment = new MeFragment();
                    break;
            }
            if (fragment != null) {
                fragents.put(position, fragment);
            }
        }
        return fragment;
    }
}
