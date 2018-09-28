package com.careclues.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.careclues.app.R;
import com.careclues.app.adapter.ChatAdapter;
import com.careclues.app.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatList;
    ChatAdapter chatAdapter;
    ImageView chatSubmitButton;
    EditText messageTxt;
    ImageView attachBtn;
    ImageView sendChatBtn;
    Toolbar toolbar;
    List<MessageModel> mList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        initWidgets();

    }

    private void initWidgets() {
        chatList = findViewById(R.id.chatList);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        chatList.setLayoutManager(llm);
        chatAdapter = new ChatAdapter(this, mList, true);
        chatList.setAdapter(chatAdapter);
        chatSubmitButton = findViewById(R.id.chatSubmitButton);
        messageTxt = findViewById(R.id.messageTxt);
        attachBtn = findViewById(R.id.attBtn);

        attachBtn.setOnClickListener(onClick -> {
            PopupMenu popup = new PopupMenu(ChatActivity.this, attachBtn);
            popup.getMenuInflater().inflate(R.menu.pop_up_image, popup.getMenu());
            popup.show();
        });


        chatSubmitButton.setOnClickListener(onClick -> {
            MessageModel messageModel = new MessageModel();

            if (messageTxt.getText().toString().trim().length() != 0) {
                messageModel.setMessage(messageTxt.getText().toString());
                mList.add(messageModel);
                if (chatAdapter != null)
                    chatAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }

        });


    }
}
