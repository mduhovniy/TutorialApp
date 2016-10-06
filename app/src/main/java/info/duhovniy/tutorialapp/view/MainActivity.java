package info.duhovniy.tutorialapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
            mainViewModel.onClickFetch(binding.fab);
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
}
