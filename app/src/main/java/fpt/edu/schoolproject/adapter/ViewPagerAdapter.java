package fpt.edu.schoolproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fpt.edu.schoolproject.ui.map.BanDoFragment;
import fpt.edu.schoolproject.ui.khoahoc.KhoaHocFragment;
import fpt.edu.schoolproject.ui.news.TinTucFragment;
import fpt.edu.schoolproject.ui.mxh.XaHoiFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TinTucFragment();
            case 2:
                return new BanDoFragment();
            case 3:
                return new XaHoiFragment();
            case 0:
            default:
                return new KhoaHocFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
