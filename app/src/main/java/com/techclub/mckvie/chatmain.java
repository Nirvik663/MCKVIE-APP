package com.techclub.mckvie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

public class chatmain extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_chat;
    RelativeLayout base;
    private StorageReference mStorage;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton,submitButton;
    EmojIconActions emojIconActions;
    TextView typing;
    ImageView attachments;
    String m;
    Button gallery,camera;
    Integer flag=0;
    private static final int CHOOSE_IMAGE = 101;
    ImageView GalleryImage;
    RelativeLayout GalleryLayout;
    Uri uri;
    FloatingActionButton ImageSend;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_chat,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK) {

            uri = data.getData();
            GalleryImage.setImageURI(uri);
            GalleryLayout.setVisibility(View.VISIBLE);
            base.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);

        base=(RelativeLayout)findViewById(R.id.base);
        activity_chat = (RelativeLayout)findViewById(R.id.activity_chat);
        GalleryImage=(ImageView)findViewById(R.id.Galleryimage);
        GalleryLayout=(RelativeLayout)findViewById(R.id.GalleryLayout);
        mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filePath = mStorage.child("ChatImages").child(String.valueOf(DateFormat.format("dd-MMM(HH:mm:ss)",new Date())));
        ImageSend= findViewById(R.id.imagesend);

        //Add Emoji

        emojiButton = (ImageView)findViewById(R.id.emoji_button);
        submitButton = (ImageView)findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
        typing=(TextView)findViewById(R.id.typing);
        attachments= (ImageView) findViewById(R.id.attach);
        gallery= (Button) findViewById(R.id.gallery);
        camera = (Button) findViewById(R.id.camera);
        emojIconActions = new EmojIconActions(getApplicationContext(),activity_chat,emojiButton,emojiconEditText);
        emojIconActions.ShowEmojicon();
        emojIconActions.setIconsIds(R.drawable.ic_action_keyboard,R.drawable.happy_256);
        final RelativeLayout att=(RelativeLayout)findViewById(R.id.att);
        final ChatMessage t =new ChatMessage();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m = emojiconEditText.getText().toString();
                m = m.trim();
                if(!m.equals("")) {
                    FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(new ChatMessage(m,
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),"text"));
                    emojiconEditText.setText("");
                    emojiconEditText.requestFocus();
                }
                else{
                    Toast.makeText(chatmain.this,"Enter text...",Toast.LENGTH_SHORT).show();
                    emojiconEditText.setText("");
                    emojiconEditText.requestFocus();
                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSE_IMAGE);

            }
        });
        ImageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String m1 = String.valueOf(DateFormat.format("dd_MMM(HH:mm:ss)",new Date()));

                StorageReference filePath = mStorage.child("ChatImages").child(m1);
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            if(!m1.equals("")) {
                                FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(new ChatMessage(m1,
                                        FirebaseAuth.getInstance().getCurrentUser().getEmail(),"image"));
                                emojiconEditText.setText("");
                                emojiconEditText.requestFocus();
                            }
                    }

                });
                GalleryLayout.setVisibility(View.INVISIBLE);
                base.setVisibility(View.VISIBLE);
                att.setVisibility(View.INVISIBLE);
                //FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(new ChatMessage(uri,
                        //FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            }
        });

        attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    att.setVisibility(View.VISIBLE);
                    flag=flag+1;
                }
                else{
                    flag=flag-1;
                    att.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Check if not sign-in then navigate Signin page
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent myIntent = new Intent(this,LoginActivity.class);
            startActivity(myIntent);
        }
        else
        {
            Snackbar.make(activity_chat,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            //Load content
            displayChatMessage();
        }


    }

    private void displayChatMessage() {

        ListView listOfMessage = (ListView) findViewById(R.id.list_of_message);
        Query query = FirebaseDatabase.getInstance().getReference().child("chats");


        ChatMessage m = new ChatMessage();

        FirebaseListOptions<ChatMessage> options2 = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.list_item)
                .build();

        adapter = new FirebaseListAdapter<ChatMessage>(options2) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                final TextView messageText1,messageText2, messageUser, messageTime, messageUser2,messageTime2;
                final ImageView chat_image1, chat_image2;
                messageText1 = (BubbleTextView) v.findViewById(R.id.message_text);
                messageText2 = (BubbleTextView) v.findViewById(R.id.message_text2);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);
                messageUser2 =(TextView)v.findViewById(R.id.message_user2);
                messageTime2=(TextView)v.findViewById(R.id.message_time2);
                chat_image1 = (ImageView)v.findViewById(R.id.chat_image1);
                chat_image2 = (ImageView)v.findViewById(R.id.chat_image2);

                String msg_type = model.getMessageType();


                if(model.getMessageUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {

                    if(msg_type=="image") {
                        try {
                            messageText2.setVisibility(View.INVISIBLE);
                            messageText1.setVisibility(View.INVISIBLE);
                            messageUser2.setVisibility(View.INVISIBLE);
                            messageUser.setVisibility(View.INVISIBLE);
                            messageTime2.setVisibility(View.INVISIBLE);
                            messageTime.setVisibility(View.INVISIBLE);

                            final File localFile = File.createTempFile(model.getMessageText(), "jpg");
                            StorageReference filePath = mStorage.child("ChatImages").child(model.getMessageText());
                            filePath.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    chat_image2.setImageBitmap(bmp);
                                    //chat_image2.setVisibility(View.VISIBLE);
                                    //progressBar.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(chatmain.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                                    //progressBar.setVisibility(View.GONE);
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

                    else {
                        messageText2.setVisibility(View.VISIBLE);
                        messageText1.setVisibility(View.INVISIBLE);
                        messageUser2.setVisibility(View.VISIBLE);
                        messageUser.setVisibility(View.INVISIBLE);
                        messageTime2.setVisibility(View.VISIBLE);
                        messageTime.setVisibility(View.INVISIBLE);
                        messageText2.setText(model.getMessageText());
                        messageUser2.setText(model.getMessageUser());
                        messageTime2.setText(DateFormat.format("dd/MMM (HH:mm)", model.getMessageTime()));
                    }
                }

                else {

                    if(msg_type == "image") {
                        try {
                            messageText2.setVisibility(View.INVISIBLE);
                            messageText1.setVisibility(View.INVISIBLE);
                            messageUser2.setVisibility(View.INVISIBLE);
                            messageUser.setVisibility(View.INVISIBLE);
                            messageTime2.setVisibility(View.INVISIBLE);
                            messageTime.setVisibility(View.INVISIBLE);
                            
                            final File localFile = File.createTempFile(model.getMessageText(), "jpg");
                            StorageReference filePath = mStorage.child("ChatImages").child(model.getMessageText());
                            filePath.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    chat_image1.setImageBitmap(bmp);
                                    //chat_image1.setVisibility(View.VISIBLE);
                                    //progressBar.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(chatmain.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                                    //progressBar.setVisibility(View.GONE);
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

                    else {
                        messageText1.setVisibility(View.VISIBLE);
                        messageText2.setVisibility(View.INVISIBLE);
                        messageUser.setVisibility(View.VISIBLE);
                        messageUser2.setVisibility(View.INVISIBLE);
                        messageTime.setVisibility(View.VISIBLE);
                        messageTime2.setVisibility(View.INVISIBLE);
                        messageText1.setText(model.getMessageText());
                        messageUser.setText(model.getMessageUser());
                        messageTime.setText(DateFormat.format("dd/MMM(HH:mm)", model.getMessageTime()));
                    }

                }
            }
        };
        listOfMessage.setAdapter(adapter);
        adapter.startListening();

    }
}
