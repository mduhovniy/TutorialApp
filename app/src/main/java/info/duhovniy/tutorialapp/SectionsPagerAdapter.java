package info.duhovniy.tutorialapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.duhovniy.tutorialapp.view.PlaceholderFragment;
import info.duhovniy.tutorialapp.viewmodel.MainViewModel;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private MainViewModel mViewModel;

    public SectionsPagerAdapter(FragmentManager fm, MainViewModel mainViewModel) {
        super(fm);
        mViewModel = mainViewModel;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(mViewModel.getFragmentModel(position));
    }

    @Override
    public int getCount() {
        return mViewModel.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mViewModel.getFragmentModel(position).getTitle();
    }
}