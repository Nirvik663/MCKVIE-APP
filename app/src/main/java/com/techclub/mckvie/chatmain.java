package com.techclub.mckvie;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

import java.lang.String;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class chatmain extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_chat;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton,submitButton;
    EmojIconActions emojIconActions;
    TextView typing;
    ImageView attachments;
    String m;
    Integer flag=0;


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
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Snackbar.make(activity_chat,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }
            else{
                Snackbar.make(activity_chat,"We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);

        activity_chat = (RelativeLayout)findViewById(R.id.activity_chat);

        //Add Emoji
        emojiButton = (ImageView)findViewById(R.id.emoji_button);
        submitButton = (ImageView)findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
        typing=(TextView)findViewById(R.id.typing);
        attachments= (ImageView) findViewById(R.id.attach);
        emojIconActions = new EmojIconActions(getApplicationContext(),activity_chat,emojiButton,emojiconEditText);
        emojIconActions.ShowEmojicon();
        emojIconActions.setIconsIds(R.drawable.ic_action_keyboard,R.drawable.happy_256);
        final RelativeLayout att=(RelativeLayout)findViewById(R.id.att);
        final ChatMessage t=new ChatMessage();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m=emojiconEditText.getText().toString();
                m=m.trim();
                if(!m.equals("")) {
                    FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(new ChatMessage(m,
                            FirebaseAuth.getInstance().getCurrentUser().getEmail()));
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
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
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
                    TextView messageText1,messageText2, messageUser, messageTime, messageUser2,messageTime2;
                    messageText1 = (BubbleTextView) v.findViewById(R.id.message_text);
                    messageText2 = (BubbleTextView) v.findViewById(R.id.message_text2);
                    messageUser = (TextView) v.findViewById(R.id.message_user);
                    messageTime = (TextView) v.findViewById(R.id.message_time);
                    messageUser2 =(TextView)v.findViewById(R.id.message_user2);
                    messageTime2=(TextView)v.findViewById(R.id.message_time2);


                    if(model.getMessageUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        messageText2.setVisibility(View.VISIBLE);
                        messageText1.setVisibility(View.INVISIBLE);
                        messageUser2.setVisibility(View.VISIBLE);
                        messageUser.setVisibility(View.INVISIBLE);
                        messageTime2.setVisibility(View.VISIBLE);
                        messageTime.setVisibility(View.INVISIBLE);
                        messageText2.setText(model.getMessageText());
                        messageUser2.setText(model.getMessageUser());
                        messageTime2.setText(DateFormat.format("dd-MMM (HH:mm)", model.getMessageTime()));
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
            };
            listOfMessage.setAdapter(adapter);
            adapter.startListening();
    }
}
