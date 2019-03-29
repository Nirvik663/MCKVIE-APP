package com.techclub.mckvie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NoticeActivity extends AppCompatActivity {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private TextView banner_j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        banner_j = (TextView) findViewById(R.id.banner);

        Intent intent = getIntent();
        int intValue = intent.getIntExtra("flag", 0);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase mdatabase1 = FirebaseDatabase.getInstance();
            DatabaseReference ref1 = mdatabase1.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());



            if (intValue == 1) {
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder> mPeopleRVAdapter1;
                        String dept = dataSnapshot.child("dept").getValue(String.class);

                        if (dept.equals("CSE")) {
                            mPeopleRVAdapter1 = mckvie_notices("Notices/CSE");
                            mPeopleRVAdapter1.startListening();
                            banner_j.setText("CSE NOTICE");
                        } else if (dept.equals("IT")) {
                            mPeopleRVAdapter1 = mckvie_notices("Notices/IT");
                            mPeopleRVAdapter1.startListening();
                            banner_j.setText("IT NOTICE");
                        } else if (dept.equals("ME")) {
                            mPeopleRVAdapter1 = mckvie_notices("Notices/ME");
                            mPeopleRVAdapter1.startListening();
                            banner_j.setText("ME NOTICE");
                        } else if (dept.equals("EE")) {
                            mPeopleRVAdapter1 = mckvie_notices("Notices/EE");
                            mPeopleRVAdapter1.startListening();
                            banner_j.setText("EE NOTICES");
                        } else if (dept.equals("ECE")) {
                            mPeopleRVAdapter1 = mckvie_notices("Notices/ECE");
                            mPeopleRVAdapter1.startListening();
                            banner_j.setText("ECE NOTICES");
                        } else if (dept.equals("AU")) {
                            mPeopleRVAdapter1 = mckvie_notices("Notices/AU");
                            mPeopleRVAdapter1.startListening();
                            banner_j.setText("AU NOTICES");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }

        if(intValue == 2){
            FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder> mPeopleRVAdapter1;
            mPeopleRVAdapter1 = mckvie_notices("Notices/all");
            mPeopleRVAdapter1.startListening();
            banner_j.setText("NOTICE");
        } else if(intValue == 3) {
            FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder> mPeopleRVAdapter1;
            mPeopleRVAdapter1 = mckvie_notices("Notices/News");
            mPeopleRVAdapter1.startListening();
            banner_j.setText("NEWS");
        } else if(intValue == 4) {
            FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder> mPeopleRVAdapter1;
            mPeopleRVAdapter1 = mckvie_notices("Notices/Events");
            mPeopleRVAdapter1.startListening();
            banner_j.setText("EVENTS");
        }

    }

    public FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder> mckvie_notices(String dept_out) {

        FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder> mPeopleRVAdapter;

        setTitle(dept_out);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(dept_out);
        mDatabase.keepSynced(true);
        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child(dept_out);
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<object>().setQuery(personsQuery, object.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<object, NoticeActivity.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(NoticeActivity.NewsViewHolder holder, final int position, final object model) {
                holder.setTitle(model.getTitle());
                if(model.getDesc().equals("none")) {
                    holder.setDesc(model.getTitle());
                } else {
                    holder.setDesc(model.getDesc());
                }
                if(!model.getImage().equals("none")) {
                    holder.setImage(getBaseContext(), model.getImage());
                }

                if(!model.getTime().equals("none")) {
                    holder.setTime(model.getTime());
                }

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
            public NoticeActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row, parent, false);

                return new NoticeActivity.NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);

        return mPeopleRVAdapter;

    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title.substring(0,1).toUpperCase() + title.substring(1));
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc.substring(0,1).toUpperCase() + desc.substring(1));
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
        public void setTime(String time){
            TextView post_time = (TextView)mView.findViewById(R.id.upload_time);
            post_time.setText(time);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}