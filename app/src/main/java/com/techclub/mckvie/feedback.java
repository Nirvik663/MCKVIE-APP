package com.techclub.mckvie;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.techclub.mckvie.R;

public class feedback extends AppCompatActivity {
    DownloadManager downloadManager;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView textView91 =(TextView)findViewById(R.id.textView91);
        TextView textView89 =(TextView)findViewById(R.id.textView89);

        textView91.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to = {"sarkararghya@rediffmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.setType("message/rfc822");
                Intent chooser = Intent.createChooser(intent, "Send Email");
                startActivity(chooser);

            }
        });
        String text = "3. Click here for Student Feedback";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://139.59.37.116:4011/student_feedback"));
                startActivity(intent);
            }

            @Override
            public void updateDrawState( TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 3,13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView89.setText(ss);
        textView89.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}