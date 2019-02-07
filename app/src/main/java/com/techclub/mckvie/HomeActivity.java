package com.techclub.mckvie;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Tab1.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener,Tab3.OnFragmentInteractionListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView iv_youtube_thumnail,iv_play;
    String videoId;
    FloatingActionButton fab_plus,fab_call,fab_message,fab_email;
    Animation FabOpen,FabClose,FabRClockwise,FabRanti;
    boolean isOpen = false;
    DatabaseReference ref1;
    FirebaseDatabase database1;

    TextView textViewname, textViewemail;
    String admin1;


    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private NavigationView navigationView;

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setuptoolbar();

        textViewname = findViewById(R.id.username);
        textViewemail = findViewById(R.id.useremail);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TextView tv = (TextView) this.findViewById(R.id.textView6);
        TextView noragging = (TextView) this.findViewById(R.id.textView10);
        ImageView iv_play = (ImageView) findViewById(R.id.iv_play_pause);
        TextView handbook = (TextView) findViewById(R.id.handbook);
        TextView career = (TextView) findViewById(R.id.textView3);
        TextView book = (TextView) findViewById(R.id.books);
        TextView Contactus = (TextView) findViewById(R.id.contactus);
        TextView Knowmckvie = (TextView) findViewById(R.id.textView7);
        TextView feed_back = (TextView)findViewById(R.id.textView8);

        //floating action button start
        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_call = (FloatingActionButton) findViewById(R.id.fab_call);
        fab_message = (FloatingActionButton) findViewById(R.id.fab_message);
        fab_email = (FloatingActionButton) findViewById(R.id.fab_email);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_opn);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clkwse);
        FabRanti = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlkwse);


        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fab_call.startAnimation(FabClose);
                    fab_message.startAnimation(FabClose);
                    fab_email.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRanti);
                    fab_message.setClickable(false);
                    fab_call.setClickable(false);
                    fab_email.setClickable(false);
                    isOpen = false;
                } else {
                    fab_call.startAnimation(FabOpen);
                    fab_message.startAnimation(FabOpen);
                    fab_email.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockwise);
                    fab_call.setClickable(true);
                    fab_message.setClickable(true);
                    fab_email.setClickable(true);
                    isOpen = true;
                }
            }
        });
        //floating action button end

        //chat start
        fab_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, chatmain.class);
                startActivity(intent);
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        mAuth = FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Syllabus.class));
            }
        });

        noragging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NoRagging.class));
            }
        });

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, test.class));
            }
        });

        handbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1310/handbook_corrected.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(HomeActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, books_journals.class));
            }
        });

        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.mckvie.org/sms/mckvie/career/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, contact_us.class));
            }
        });

        Knowmckvie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, know_mckvie.class));
            }
        });
        feed_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, feedback.class));
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Notices"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        init();

        try {
            videoId = extractYoutubeId("https://www.youtube.com/watch?v=atmWWi5bIbg");
            Log.e("VideoId is->", "" + videoId);
            String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video
            Picasso.with(HomeActivity.this)
                    .load(img_url)
                    .into(iv_youtube_thumnail);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (mAuth.getCurrentUser() != null) {
            database1 = FirebaseDatabase.getInstance();
            ref1 = database1.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    admin1 = dataSnapshot.child("admin").getValue(String.class);
                    textViewname.setText(name);
                    textViewemail.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            textViewname.setText("Welcome to the Official App of");
            textViewemail.setText("MCKV Institute of Engineering");
        }
    }

    public void init()
    {
        iv_youtube_thumnail=(ImageView)findViewById(R.id.img_thumnail);
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
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
                myIntent = new Intent(HomeActivity.this, LoginActivity.class);
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

            case R.id.notices:
                finish();
                myIntent = new Intent(HomeActivity.this, notices.class);
                startActivity(myIntent);
                break;

            case R.id.news:
                finish();
                myIntent = new Intent(HomeActivity.this, news.class);
                startActivity(myIntent);
                break;

            case R.id.events:
                finish();
                myIntent = new Intent(HomeActivity.this, events.class);
                startActivity(myIntent);
                break;

            case R.id.admin:
                if(mAuth.getCurrentUser() == null) {
                    myIntent = new Intent(HomeActivity.this,LoginActivity.class);
                    startActivity(myIntent);
                }
                else {
                    if(admin1.equals("true")) {
                        myIntent = new Intent(HomeActivity.this, admin_app.class);
                        startActivity((myIntent));
                    }
                    else {
                        Toast.makeText(HomeActivity.this, "Administrator Rights Required", Toast.LENGTH_SHORT).show();
                    }
                }
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
    public void onFragmentInteraction(Uri uri) {

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

    @Override
    public void onStop() {
        super.onStop();
    }

}