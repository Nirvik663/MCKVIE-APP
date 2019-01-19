package com.techclub.mckvie;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class know_mckvie extends AppCompatActivity {

    DownloadManager downloadManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knowmckvie);

        TextView list1 = (TextView)findViewById(R.id.textView59);
        TextView list2 = (TextView)findViewById(R.id.textView60);
        TextView list3 = (TextView)findViewById(R.id.textView61);
        TextView list4 = (TextView)findViewById(R.id.textView62);
        TextView list5 = (TextView)findViewById(R.id.textView58);
        TextView list6 = (TextView)findViewById(R.id.textView100);
        TextView list7 = (TextView)findViewById(R.id.textView64);
        TextView list8 = (TextView)findViewById(R.id.textView65);
        TextView list9 = (TextView)findViewById(R.id.textView69);
        TextView list10 = (TextView)findViewById(R.id.textView71);
        TextView list11 = (TextView)findViewById(R.id.textView73);
        TextView list12 = (TextView)findViewById(R.id.textView75);
        TextView list13 = (TextView)findViewById(R.id.textView76);
        TextView list14 = (TextView)findViewById(R.id.textView77);
        TextView list15 = (TextView)findViewById(R.id.textView78);
        TextView list16 = (TextView)findViewById(R.id.textView82);
        TextView list17 = (TextView)findViewById(R.id.textView83);

        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/automobile-engineering/"));
                startActivity(intent);
            }
        });

        list2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/computer-science-engineering/"));
                startActivity(intent);
            }
        });

        list3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/electrical-engineering/"));
                startActivity(intent);
            }
        });

        list4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/electronics-communication-engineering/"));
                startActivity(intent);
            }
        });

        list5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/information-technology/"));
                startActivity(intent);
            }
        });

        list6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/mechanical-engineering/"));
                startActivity(intent);
            }
        });

        list7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/electronics-communication-engineering/#index2"));
                startActivity(intent);
            }
        });

        list8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/academics/departments-and-programs/mca/"));
                startActivity(intent);
            }
        });



        list9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/placements/training/"));
                startActivity(intent);
            }
        });

        list10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/placements/placement-records/"));
                startActivity(intent);
            }
        });


        list11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/about/affiliations-accreditations/"));
                startActivity(intent);
            }
        });

        list12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1210/introduction-to-qeee-2015.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });


        list13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/campus-life/student-chapters/"));
                startActivity(intent);
            }
        });

        list14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/nostalgia/"));
                startActivity(intent);
            }
        });

        list15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/campus-life/svcpt/"));
                startActivity(intent);
            }
        });

        list16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mckvie.edu.in/campus-life/mckvie-rotaract-club/"));
                startActivity(intent);
            }
        });

        list17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://mckvie.edu.in/site/assets/files/1204/mckv_ie_pull_out_final_offset_setting_2_-curve.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}