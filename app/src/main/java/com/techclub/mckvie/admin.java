package com.techclub.mckvie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {

    private Button login1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        login1=(Button)findViewById(R.id.button14);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoapp();
            }
        });
    }

    private void movetoapp(){
        Intent intent = new Intent(admin.this,admin_app.class);
        startActivity(intent);
    }
}
