package info.duhovniy.tutorialapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import info.duhovniy.tutorialapp.MyApplication;
import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.SectionsPagerAdapter;
import info.duhovniy.tutorialapp.databinding.ActivityMainBinding;
import info.duhovniy.tutorialapp.viewmodel.MainViewModel;

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
        binding.setViewModel(mainViewModel);

        setSupportActionBar(binding.toolbar);

        setViewPager();
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
    public void onFragmentsChanged(boolean isFetched) {
        if(isFetched) {
            Snackbar.make(binding.mainContent, R.string.fetched_snackbar, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            setViewPager();
        } else
            Snackbar.make(binding.mainContent, R.string.not_fetched_snackbar, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }
}
