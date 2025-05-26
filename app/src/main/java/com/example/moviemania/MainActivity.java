package com.example.moviemania;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviemania.fragments.FavoriteMoviesFragment;
import com.example.moviemania.fragments.PopularMoviesFragment;
import com.example.moviemania.ui.MainPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MainPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        searchView = findViewById(R.id.searchView);

        pagerAdapter = new MainPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Popular");
                    else if (position == 1) tab.setText("Favorites");
                    else if (position == 2) tab.setText("New");
                }).attach();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovies(newText);
                return true;
            }
        });
    }

    private void filterMovies(String query) {
        int currentItem = viewPager.getCurrentItem();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + currentItem);

        if (currentItem == 0 && currentFragment instanceof PopularMoviesFragment) {
            ((PopularMoviesFragment) currentFragment).filterMovies(query);
        } else if (currentItem == 1 && currentFragment instanceof FavoriteMoviesFragment) {
            ((FavoriteMoviesFragment) currentFragment).filterMovies(query);
        } else if (currentItem == 2 && currentFragment instanceof com.example.moviemania.fragments.NewMoviesFragment) {
            ((com.example.moviemania.fragments.NewMoviesFragment) currentFragment).filterMovies(query);
        }
    }
}