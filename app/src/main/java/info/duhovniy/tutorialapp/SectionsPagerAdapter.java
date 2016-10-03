package info.duhovniy.tutorialapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.duhovniy.tutorialapp.view.PlaceholderFragment;
import info.duhovniy.tutorialapp.viewmodel.MainViewModel;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private MainViewModel mViewModel;

    public SectionsPagerAdapter(FragmentManager fm, MainViewModel mainViewModel) {
        super(fm);
        mViewModel = mainViewModel;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(mViewModel.getFragments().get(position));
    }

    @Override
    public int getCount() {
        return mViewModel.getFragments().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mViewModel.getFragments().get(position).getTitle();
    }
}