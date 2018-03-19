package com.describe.taskmanager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("");
        }
        ViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MainPagerAdapter(getSupportFragmentManager(),MainActivity.this);
        vpPager.setAdapter(adapterViewPager);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }
    public static class MainPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
        private Context context;

        MainPagerAdapter(FragmentManager fragmentManager,Context context) {
            super(fragmentManager);
            this.context = context;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            //Add new fragments here (Add a new case)
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return CategoryList.newInstance();
                case 1:
                    return CategoryList.newInstance();
                default:
                    return null;
            }

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

    }

}
