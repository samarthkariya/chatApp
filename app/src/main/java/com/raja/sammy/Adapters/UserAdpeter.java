package com.raja.sammy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raja.sammy.Activities.chatActivity;
import com.raja.sammy.Models.User;
import com.raja.sammy.R;
import com.raja.sammy.databinding.SampleRowBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserAdpeter extends RecyclerView.Adapter<UserAdpeter.UserViewHolder>{

    ArrayList<User> users;
    Context context;

    public UserAdpeter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.sample_row,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpeter.UserViewHolder holder, int position) {
        User user =users.get(position);

        String senderId = FirebaseAuth.getInstance().getUid();

        String senderRoom = senderId +user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){
                    String lastMsg = snapshot.child("lastMsg").getValue(String.class);

                    long time = snapshot.child("lastMsgTime").getValue(Long.class);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    holder.binding.time.setText(dateFormat.format(new Date(time)));
                    holder.binding.lastmsg.setText(lastMsg);
                    }else {
                        holder.binding.lastmsg.setText("Tap To Chat");
                    }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.binding.samplename.setText(user.getName());


        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.avatar).into(holder.binding.profilechat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, chatActivity.class);
               intent.putExtra("name",user.getName());
               intent.putExtra("uid",user.getUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        

        SampleRowBinding binding;
        public UserViewHolder(@NonNull View itemView) {

            super(itemView);
            binding= SampleRowBinding.bind(itemView);
        }
    }
}
