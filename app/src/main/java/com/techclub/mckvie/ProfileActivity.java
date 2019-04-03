package com.techclub.mckvie;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView btnSelectImage = (ImageView) findViewById(R.id.btn_image);
        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.testTextVIew);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        final TextView textViewname = (TextView) findViewById(R.id.textView94);
        final TextView textViewemail = (TextView) findViewById(R.id.textView96);
        final TextView textViewuid = (TextView) findViewById(R.id.textView98);
        final TextView textViewdept = (TextView) findViewById(R.id.textView101);

        Button button = (Button) findViewById(R.id.button);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //Get instance
        mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filePath = mStorage.child("CameraPhotos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(ProfileActivity.this);

            }
        });

        progressBar.setVisibility(View.VISIBLE);

        try {
            File f=new File("/data/user/0/com.techclub.mckvie/app_imageDir", FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            mImageView.setImageBitmap(b);
            progressBar.setVisibility(View.GONE);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
        }


        if (mAuth.getCurrentUser() != null) {
            FirebaseDatabase mdatabase1 = FirebaseDatabase.getInstance();
            DatabaseReference ref1 = mdatabase1.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String uid = dataSnapshot.child("id").getValue(String.class);
                    String dept = dataSnapshot.child("dept").getValue(String.class);
                    textViewname.setText(name);
                    textViewemail.setText(email);
                    textViewuid.setText(uid);
                    textViewdept.setText(dept);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Hoyni Ekhono -_-", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

        if(resultCode==RESULT_OK) {

            progressBar.setVisibility(View.VISIBLE);
            Uri uri = result.getUri();

            StorageReference filePath = mStorage.child("CameraPhotos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    try {

                        final StorageReference filePath = mStorage.child("CameraPhotos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        final File localFile = File.createTempFile("profile_pic", "jpg");

                        filePath.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                saveToInternalStorage(bmp);
                                progressBar.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(ProfileActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // progress percentage
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            }
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File mypath=new File(directory,FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            loadImageFromStorage(directory.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpg");
            Log.v("pathos",path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.imageView);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}