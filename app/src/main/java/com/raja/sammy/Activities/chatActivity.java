package com.raja.sammy.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raja.sammy.Adapters.MessageAdapter;
import com.raja.sammy.Models.Message;
import com.raja.sammy.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class chatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    MessageAdapter adapter;
    ArrayList<Message> messages;

    String senderRoom ,reciveRoom;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        messages=new ArrayList<>();
        adapter=new MessageAdapter(this,messages,senderRoom,reciveRoom);
        binding.msgRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.msgRecycler.setAdapter(adapter);


        String name = getIntent().getStringExtra("name");
        String Reciveruid  = getIntent().getStringExtra("uid");
        String Senderuid = FirebaseAuth.getInstance().getUid();

        senderRoom =Senderuid + Reciveruid;
        reciveRoom = Reciveruid + Senderuid;

        database.getReference().child("chats")
                .child(senderRoom)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for (DataSnapshot Snapshot1: snapshot.getChildren()){
                            Message message=Snapshot1.getValue(Message.class);
                            message.setMessageId(Snapshot1.getKey());
                            messages.add(message);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messegetype =binding.msgBox.getText().toString();

                Date date=new Date();
                Message message=new Message(messegetype,Senderuid,date.getTime());
                binding.msgBox.setText("");


                String randomKey =database.getReference().push().getKey();

                HashMap<String, Object> lastmsgobj =new HashMap<>();
                lastmsgobj.put("lastMsg",message.getMessage());
                lastmsgobj.put("lastMsgTime",date.getTime());

                database.getReference().child("chats").child(senderRoom).updateChildren(lastmsgobj);
                database.getReference().child("chats").child(reciveRoom).updateChildren(lastmsgobj);


                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .child(randomKey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(reciveRoom)
                                .child("messages")
                                .child(randomKey)
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {}});
                    }});

            }
        });
       getSupportActionBar().setTitle(name);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}