package com.techclub.mckvie;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView iv_youtube_thumnail,iv_play;
    String videoId;



    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<object, HomeActivity.NewsViewHolder> mPeopleRVAdapter;
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
        TextView notice = (TextView) this.findViewById(R.id.textView15);
        TextView event = (TextView) this.findViewById(R.id.textView16);
        TextView news = (TextView) this.findViewById(R.id.textView13);
        TextView noragging = (TextView) this.findViewById(R.id.textView10);
        ImageView iv_play=(ImageView)findViewById(R.id.iv_play_pause);
        TextView handbook = (TextView) findViewById(R.id.handbook);
        TextView career = (TextView) findViewById(R.id.textView3);
        TextView book = (TextView) findViewById(R.id.books);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer =new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        mAuth = FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices");
        mDatabase.keepSynced(true);
        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Notices");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<object>().setQuery(personsQuery, object.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<object, HomeActivity.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(HomeActivity.NewsViewHolder holder, final int position, final object model) {
                holder.setTitle(model.getTitle());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getUrl();
                        Intent intent = new Intent(getApplicationContext(), webview.class);
                        intent.putExtra("id", url);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public HomeActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_row, parent, false);

                return new HomeActivity.NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Syllabus.class));
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),notices.class));
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), events.class));
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), news.class));
            }
        });
        noragging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,NoRagging.class));
            }
        });

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,test.class));
            }
        });

        handbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1162/revised_anti_ragging_committee_2018.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(HomeActivity.this,"Downloading", Toast.LENGTH_SHORT).show();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,books_journals.class));
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

        init();

        try
        {
            videoId=extractYoutubeId("https://www.youtube.com/watch?v=atmWWi5bIbg");

            Log.e("VideoId is->","" + videoId);

            String img_url="http://img.youtube.com/vi/"+videoId+"/0.jpg"; // this is link which will give u thumnail image of that video

            // picasso jar file download image for u and set image in imagview

            Picasso.with(HomeActivity.this)
                    .load(img_url)
                    .into(iv_youtube_thumnail);

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

    }
    public void init()
    {
        iv_youtube_thumnail=(ImageView)findViewById(R.id.img_thumnail);
    }

    // extract youtube video id and return that id
    // ex--> "http://www.youtube.com/watch?v=t7UxjpUaL3Y"
    // videoid is-->t7UxjpUaL3Y


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





    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
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
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();
    }

}