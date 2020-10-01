package com.bookkaroShop.MainHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookkaroShop.OrdersHelper.AcceptedOrdersFragment;
import com.bookkaroShop.OrdersHelper.DeliveredOrdersFragment;
import com.bookkaroShop.OrdersHelper.NewOrdersFragment;
import com.bookkaroShop.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    NewOrdersFragment newOrdersFragment;
    AcceptedOrdersFragment acceptedOrdersFragment;
    DeliveredOrdersFragment deliveredOrdersFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar =view.findViewById(R.id.toolbar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.view_pager);

        toolbar.setTitle("Orders");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        newOrdersFragment = new NewOrdersFragment();
        acceptedOrdersFragment = new AcceptedOrdersFragment();
        deliveredOrdersFragment = new DeliveredOrdersFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        viewPagerAdapter.addFragment(newOrdersFragment,"New Orders");
        viewPagerAdapter.addFragment(acceptedOrdersFragment,"Accepted Orders");
        viewPagerAdapter.addFragment(deliveredOrdersFragment,"Delivered Orders");

        viewPager.setAdapter(viewPagerAdapter);

        return view;

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}