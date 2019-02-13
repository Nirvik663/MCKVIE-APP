package com.techclub.mckvie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class test1 extends AppCompatActivity {

    private FirebaseDatabase mdatabase1;
    private StorageReference mStorage;
    private Button btnSelectImage;
    private ImageView mImageView, mImageView2;
    private static final int CHOOSE_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        btnSelectImage = (Button) findViewById(R.id.button2);
        mImageView = (ImageView) findViewById(R.id.galleryimage);
        mImageView2 = (ImageView) findViewById(R.id.imageView4);

        mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filePath = mStorage.child("ChatImages/1");

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Chat Image"), CHOOSE_IMAGE);
            }
        });

        try {
            final File localFile = File.createTempFile("chat_pic", "jpg");

            filePath.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    mImageView2.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(test1.this, exception.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK) {

            Uri uri = data.getData();
            mImageView.setImageURI(uri);

            StorageReference filePath = mStorage.child("ChatImages/1");
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    try {

                        final StorageReference filePath = mStorage.child("ChatImages/1");
                        final File localFile = File.createTempFile("chat_pic", "jpg");

                        filePath.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                mImageView2.setImageBitmap(bmp);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(test1.this, exception.getMessage(), Toast.LENGTH_LONG).show();
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

