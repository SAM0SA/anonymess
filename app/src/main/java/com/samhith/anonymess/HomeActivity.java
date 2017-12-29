package com.samhith.anonymess;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SectionsPageAdapter sectionsPageAdapter;
    private TabLayout tabLayout;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        toolbar = (Toolbar) findViewById(R.id.homepage_app_bar);
        toolbar.setTitle("CHAT");
        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener((v)->{
//
//        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open,R.string.Close);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //getSupportActionBar().setHomeAsUpIndicator()
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Tabs
        viewPager = (ViewPager) findViewById(R.id.home_tabPager);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(sectionsPageAdapter);

        tabLayout = (TabLayout) findViewById(R.id.homepage_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;

        switch (item.getItemId()){
            case R.id.action_search:
                //
                break;
            default:
                //
        }
        return super.onOptionsItemSelected(item);
    }
}
