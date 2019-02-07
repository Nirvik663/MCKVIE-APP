package com.techclub.mckvie;

import android.app.DownloadManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class books_journals extends AppCompatActivity {


    DownloadManager downloadManager1;
    DownloadManager downloadManager2;
    DownloadManager downloadManager3;
    DownloadManager downloadManager4;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        TextView t1 =  this.findViewById(R.id.textView12);
        TextView t2 =  this.findViewById(R.id.textView23);
        TextView t3 =  this.findViewById(R.id.textView25);
        TextView t4 =  this.findViewById(R.id.textView26);
        String text1="Click here to Download New Arrivals of Books in the Period from 01-09-2018 to 31-10-2018 at Central Library.";
        SpannableString ss =new SpannableString(text1);
        ForegroundColorSpan fcsblue=new ForegroundColorSpan(Color.BLUE);
        ss.setSpan(fcsblue,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        t1.setText(ss);

        String text2="Click here to Download New Arrivals of Books in the Period from 01-01-2017 to 31-05-2017 at Central Library.";
        SpannableString ss1=new SpannableString(text2);
        ForegroundColorSpan fcsblue1=new ForegroundColorSpan(Color.BLUE);
        ss1.setSpan(fcsblue1,0,10,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        t2.setText(ss1);

        String text3="Click here to Download New Arrivals of Books in the Period from 01-01-2018 to 31-01-2018 at Central Library.";
        SpannableString ss2=new SpannableString(text3);
        ForegroundColorSpan fcsblue2=new ForegroundColorSpan(Color.BLUE);
        ss2.setSpan(fcsblue2,0,10,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        t3.setText(ss2);

        String text4="Click here to Download New Arrivals of Books in the Period from 01-02-2018 to 28-02-2018 at Central Library.";
        SpannableString ss3=new SpannableString(text4);
        ForegroundColorSpan fcsblue3=new ForegroundColorSpan(Color.BLUE);
        ss3.setSpan(fcsblue3,0,10,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        t4.setText(ss3);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager1 = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://mckvie.edu.in/site/assets/files/1518/n__arraivals_sep_-oc-_2018.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager1.enqueue(request);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager2 = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1518/newarrivals_jan2june_2017.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager2.enqueue(request);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager3 = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1518/new_arraivals.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager3.enqueue(request);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager4 = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1518/new_arraivals_feb18.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager4.enqueue(request);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}