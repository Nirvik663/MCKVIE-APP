package com.techclub.mckvie;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private FirebaseAuth mAuth;
    private NavigationView navigationView;

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setuptoolbar();


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TextView tv = (TextView) this.findViewById(R.id.textView6);
        TextView t = (TextView) this.findViewById(R.id.more);
        TextView notice = (TextView) this.findViewById(R.id.textView15);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer =new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        mAuth = FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Syllabus.class));
            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MoreActivity.class));
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),notices.class));
            }
        });
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run(){

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }else if (viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    }else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent myIntent;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        switch (item.getItemId()) {

            case R.id.nav_signin:
                finish();
                myIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(myIntent);

                break;

            case R.id.nav_account:
                finish();
                myIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(myIntent);
                break;

            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivityForResult(myIntent, 0);
                Toast.makeText(HomeActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                break;

        }

        return true;
    }

    private void setuptoolbar()
    {
        drawerLayout= (DrawerLayout) findViewById(R.id.draw);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu_login);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu_logout);
        }

        }
    }