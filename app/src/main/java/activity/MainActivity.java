package activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fragments.Fragment_CalendarEvent;
import fragments.Fragment_MainRoutine;
import fragments.Fragment_conf_class;
import fragments.Fragment_conf_exam;
import fragments.Fragment_conf_sub;
import fragments.Fragment_notes;
import fragments.Fragment_view_all_class;
import fragments.Fragment_view_all_exam;
import tdd.classroutine.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment currentFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private int onBackPrassed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //fragment transaction in first opening the application
    private void init() {
        currentFragment = new Fragment_MainRoutine();
        manager = getFragmentManager();
        fragmentTransitionManager(currentFragment);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            currentFragment = new Fragment_MainRoutine();
            fragmentTransitionManager(currentFragment);
            onBackPrassed++;
            if (onBackPrassed > 1) {
                super.onBackPressed();
                finish();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_activity) {
            currentFragment = new Fragment_MainRoutine();
        } else if (id == R.id.nav_full_routine) {
            currentFragment = new Fragment_view_all_class();
        } else if (id == R.id.nav_notes) {
            currentFragment = new Fragment_notes();
        } else if (id == R.id.nav_exam) {
            currentFragment = new Fragment_view_all_exam();
        } else if (id == R.id.nav_conf_subject) {
            currentFragment = new Fragment_conf_sub();
        } else if (id == R.id.nav_conf_routine) {
            currentFragment = new Fragment_conf_class();
        } else if (id == R.id.nav_conf_exam) {
            currentFragment = new Fragment_conf_exam();
        } else if (id == R.id.nav_calender) {
            currentFragment = new Fragment_CalendarEvent();
        }

        fragmentTransitionManager(currentFragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void fragmentTransitionManager(Fragment currentFragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
//}
