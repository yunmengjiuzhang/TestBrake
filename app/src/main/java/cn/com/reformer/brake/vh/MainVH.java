package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.fragments.FragmentFactory;
import cn.com.reformer.brake.utils.UIUtils;
import mutils.ThreadUtils;

public class MainVH extends BaseVH{
    private RadioGroup radioGroup;
    private ViewPager viewPager;

    public final ObservableField<String> title = new ObservableField<>();
    private String[] names = {"信息", "设置", "日志", "我的"};

    public MainVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onItemClick(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.bottom1);
                break;
            case 1:
                radioGroup.check(R.id.bottom2);
                break;
            case 2:
                radioGroup.check(R.id.bottom3);
                break;
            case 3:
                radioGroup.check(R.id.bottom4);
                break;
            default:
                break;
        }
        title.set(names[position]);
    }

    @BindingAdapter({"app:mainRadioGroup"})
    public static void sasdfasfasda(final RadioGroup radioGroup, final MainVH mainVH) {
        if (mainVH != null) {
            mainVH.radioGroup = radioGroup;
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    int index = 0;
                    switch (checkedId) {
                        case R.id.bottom1:
                            index = 0;
                            break;
                        case R.id.bottom2:
                            index = 1;
                            break;
                        case R.id.bottom3:
                            index = 2;
                            break;
                        case R.id.bottom4:
                            index = 3;
                            break;
                        default:
                            break;
                    }
                    //切换viewpager
                    mainVH.viewPager.setCurrentItem(index);
                }
            });
        }
    }

    @BindingAdapter({"app:radioSize"})
    public static void radiosized(final RadioButton radioButton, final int position) {
        Drawable drawable = UIUtils.getDrawable(R.drawable.main_bottom1);
        switch (position) {
            case 0:
                drawable = UIUtils.getDrawable(R.drawable.main_bottom1);
                break;
            case 1:
                drawable = UIUtils.getDrawable(R.drawable.main_bottom2);
                break;
            case 2:
                drawable = UIUtils.getDrawable(R.drawable.main_bottom3);
                break;
            case 3:
                drawable = UIUtils.getDrawable(R.drawable.main_bottom4);
                break;
        }
        drawable.setBounds(0, 0, 64, 64);
        radioButton.setCompoundDrawables(null, drawable, null, null);//只放上面
    }

    @BindingAdapter({"app:mainSwipeRefresh"})
    public static void mainSwipe(final SwipeRefreshLayout swipeRefreshLayout, final MainVH mainVH) {
        if (mainVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                    scanVH.refresh();
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            });
        }
    }


    @BindingAdapter({"app:asddf"})
    public static void setmainviewpagerAdapter(final ViewPager viewPager, final MainVH mainVH) {
        if (mainVH != null) {
            mainVH.viewPager = viewPager;
            viewPager.setOffscreenPageLimit(3);
            final FragmentPagerAdapter adapter = new FragmentPagerAdapter(mainVH.mCtx.getSupportFragmentManager()) {
                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public Fragment getItem(int position) {
                    return FragmentFactory.create(position);
                }
            };
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    mainVH.onItemClick(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }


}
