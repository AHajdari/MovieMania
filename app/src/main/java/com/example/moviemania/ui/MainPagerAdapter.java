//package com.example.moviemania.ui;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//import com.example.moviemania.fragments.FavoriteMoviesFragment;
//import com.example.moviemania.fragments.PopularMoviesFragment;
//
//public class MainPagerAdapter extends FragmentStateAdapter {
//
//    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        if (position == 0) {
//            return new PopularMoviesFragment();
//        } else {
//            return new FavoriteMoviesFragment();
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 2;
//    }
//}

package com.example.moviemania.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.moviemania.fragments.FavoriteMoviesFragment;
import com.example.moviemania.fragments.PopularMoviesFragment;
import com.example.moviemania.fragments.NewMoviesFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PopularMoviesFragment();
            case 1:
                return new FavoriteMoviesFragment();
            case 2:
                return new NewMoviesFragment();
            default:
                return new PopularMoviesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}