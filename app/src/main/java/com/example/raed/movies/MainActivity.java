package com.example.raed.movies;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String STATE_SELECTED = "selected";
    public static final String SELECTED_MOVIE = "selected_movie";

    MainPresenter presenter;

    boolean isSelectedTop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null) {
            isSelectedTop = savedInstanceState.getBoolean(STATE_SELECTED);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (!isSelectedTop) {
            menu.findItem(R.id.menu_popular).setChecked(true);
        }else menu.findItem(R.id.menu_top_rated).setChecked(true);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_popular:
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                break;
            case R.id.menu_top_rated:
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SELECTED, isSelectedTop);
        super.onSaveInstanceState(outState);
    }


    class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private static final int FRAG_NUM = 3;
        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 : return new PopularFragment();
                case 1 : return new TopRatedFragment();
                case 2 : return new FavouriteFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return FRAG_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 : return "Popular";
                case 1 : return "Top";
                case 2 : return "Fav";
                default: return null;
            }
        }
    }
}
