package info.duhovniy.tutorialapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import info.duhovniy.tutorialapp.R;
import info.duhovniy.tutorialapp.SectionsPagerAdapter;
import info.duhovniy.tutorialapp.databinding.ActivityMainBinding;
import info.duhovniy.tutorialapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {


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
        binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_fetch) {
            mainViewModel.loadFragmentsFromFile(getString(R.string.json_static_source));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentsChanged(boolean isLocal) {
        if (isLocal)
            Snackbar.make(binding.container, R.string.fetched_from_file_snackbar, Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make(binding.container, R.string.fetched_from_API_snackbar, Snackbar.LENGTH_LONG).show();
        setViewPager();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainViewModel.saveState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }
}
