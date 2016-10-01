package info.duhovniy.tutorialapp.view;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.databinding.ActivityMainBinding;
import info.duhovniy.tutorialapp.model.FragmentModel;
import info.duhovniy.tutorialapp.viewmodel.MainViewModel;
import info.duhovniy.tutorialapp.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, this);

        setSupportActionBar(binding.toolbar);

        setViewPager();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fetching data for fragments!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mainViewModel.loadFragmentsFromFile();
            }
        });

    }

    private void setViewPager() {
        // Set up the ViewPager with the sections adapter.
        binding.container.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), mainViewModel));

        binding.tabs.setupWithViewPager(binding.container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fetch) {
            mainViewModel.loadFragmentsFromFile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentsChanged() {
        setViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION = "section";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(FragmentModel fragmentModel) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            if (fragmentModel != null) {
                Bundle args = new Bundle();
                args.putParcelable(ARG_SECTION, fragmentModel);
                fragment.setArguments(args);
            }
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            FragmentModel fm = getArguments().getParcelable(ARG_SECTION);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(fm.getTitle());
            return rootView;
        }
    }
}
