package com.techclub.mckvie;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class admin_app extends AppCompatActivity {

    EditText Desc,Image,Url,Title;
    Button Insert;
    FirebaseDatabase database;
    DatabaseReference ref, ref1;
    Query ref2;
    object object1;
    int c = 10000;
    String msg1 = "10000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_app);

        Desc = (EditText) findViewById(R.id.desc1);
        Image = (EditText) findViewById(R.id.image1);
        Url = (EditText) findViewById(R.id.url1);
        Title = (EditText) findViewById(R.id.title1);
        Insert = (Button) findViewById(R.id.insert1);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Notices");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Notices").orderByKey().limitToFirst(1);
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    msg1 = child.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        object1 = new object();

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(admin_app.this);
                builder.setMessage("Are you sure to upload?");
                builder.setCancelable(true);
                builder.setPositiveButton("Recheck", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                if (Title.getText().toString().isEmpty()) {
                    Title.setError(getString(R.string.title_error));
                    Title.requestFocus();
                    return;
                }
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Integer.parseInt(msg1) != 0)
                            c = Integer.parseInt(msg1);
                        c = c - 1;
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                getvalues();
                                ref.child(Integer.toString(c)).setValue(object1);
                                Toast.makeText(admin_app.this, "Notice Inserted", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
    }

    private void getvalues(){
        object1.setDesc(Desc.getText().toString());
        object1.setImage(Image.getText().toString());
        object1.setTitle(Title.getText().toString());
        object1.setUrl(Url.getText().toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}
