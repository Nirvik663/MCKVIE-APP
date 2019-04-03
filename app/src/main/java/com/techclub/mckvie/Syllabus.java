package com.techclub.mckvie;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Syllabus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView button1st= (TextView) findViewById(R.id.button1st);
        TextView buttonCSE= (TextView) findViewById(R.id.buttonCSE);
        TextView buttonECE= (TextView) findViewById(R.id.buttonECE);
        TextView buttonEE= (TextView) findViewById(R.id.buttonEE);
        TextView buttonIT= (TextView) findViewById(R.id.buttonIT);
        TextView buttonME= (TextView) findViewById(R.id.buttonME);
        TextView buttonAUE= (TextView) findViewById(R.id.buttonAUE);
        TextView MTECHECE= (TextView) findViewById(R.id.MTECHECE);
        TextView MTECHCSE= (TextView) findViewById(R.id.MTECHCSE);
        TextView MTECHAE= (TextView) findViewById(R.id.MTECHAE);
        TextView MTECHVLSI= (TextView) findViewById(R.id.MTECHVLSI);
        TextView MCA=(TextView) findViewById(R.id.MCA);

        button1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/1st_year_b_tech_syllabus_revised_18_08_10.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        buttonCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.wbut.ac.in/syllabus/CSE_Final_Upto_4h_Year%20Syllabus_14.03.14.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        buttonAUE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/aue_final_upto_4th_year-syllabus_05_06_13.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        buttonME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/me_final_upto_4th_year-syllabus_04_06_13.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        MTECHECE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/mtech_ececommunication_comm_detail_syllabus_2010.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        MTECHCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.wbut.ac.in/syllabus/M.Tech_CSE_IT_Unified_19.02.14_2.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        MTECHAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/automotive-technology-syllabus-mckvie.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        MTECHVLSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/mtech_ecemicroelectronics_vlsi-designs_comm_detail_syllabus_2010.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

        MCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.mckvie.edu.in/site/assets/files/1161/mca_new_syllabus.pdf");
                DownloadManager.Request request=new DownloadManager.Request(uri);
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
