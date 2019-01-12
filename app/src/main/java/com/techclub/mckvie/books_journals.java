package com.techclub.mckvie;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        TextView t1 = (TextView) this.findViewById(R.id.textView12);
        TextView t2 = (TextView) this.findViewById(R.id.textView23);
        TextView t3 = (TextView) this.findViewById(R.id.textView25);
        TextView t4 = (TextView) this.findViewById(R.id.textView26);
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