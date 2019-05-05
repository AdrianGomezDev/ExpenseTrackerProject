package com.example.expensetrackerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.expensetrackerproject.expense.ExpenseFragment;
import com.example.expensetrackerproject.graphing.GraphingFragment;
import com.example.expensetrackerproject.login.LoginActivity;
import com.example.expensetrackerproject.menu.PasswordFragment;

import java.util.Objects;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener{

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        Button signOut = headerView.findViewById(R.id.navSignOutButton);

        //Toggles keeping user signed in option
        final CheckBox staySignedIn = headerView.findViewById(R.id.staySignedIn);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Remove stored username.  Uncheck signed in checkbox
                SavedPreferences.removeUsername(NavigationActivity.this);
                staySignedIn.setChecked(false);

                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //Gets stored username or username from login bundle extra
        if(SavedPreferences.isSignedIn(this)) {
            username = SavedPreferences.getSavedUsername(this);
            staySignedIn.setChecked(true);
        }
        else
            username = getIntent().getStringExtra("USERNAME");

        TextView profileUserName = headerView.findViewById(R.id.profileUsername);
        profileUserName.setText(username);

        staySignedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();
                if(checked)
                    SavedPreferences.saveUsername(NavigationActivity.this, username);
                else
                    SavedPreferences.removeUsername(NavigationActivity.this);

            }
        });


        // Creates and displays the home fragment
        Fragment home = new Home();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, home);
        ft.commit();
        // Highlights the Home item in the nav drawer
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment newFragment = null;

        //initializing the fragment object which is selected
        switch (item.getItemId()) {
            case R.id.nav_home:
                newFragment = new Home();
                break;
            case R.id.nav_expense:
                newFragment = ExpenseFragment.newInstance(username);
                break;
            case R.id.nav_graphing:
                newFragment = GraphingFragment.newInstance(username, 5, 2019);
                break;
            case R.id.nav_share:
                newFragment = new Share();
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_password:
                newFragment = PasswordFragment.newInstance(username);
                break;
        }

        // Replace the current fragment with the selected fragment if it is different fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (newFragment != null && !Objects.requireNonNull(currentFragment).getClass().equals(newFragment.getClass())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, newFragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        //TODO: Handles fragment messages

    }
}
