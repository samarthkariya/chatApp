package com.raja.sammy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.raja.sammy.Models.Message;
import com.raja.sammy.R;
import com.raja.sammy.databinding.ItemReciveBinding;
import com.raja.sammy.databinding.ItemSendBinding;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SEND = 1;
    final int ITEM_RECIVE = 2;
    String senderRoom;
    String receiverRoom;
    public MessageAdapter(Context context, ArrayList<Message> messages,String senderRoom,String receiverRoom){
        this.context=context;
        this.messages=messages;
        this.senderRoom=senderRoom;
        this.receiverRoom =receiverRoom;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            return new SendViewHolder(view);
        }else {
            View view =LayoutInflater.from(context).inflate(R.layout.item_recive,parent,false);
            return new ReciverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message=messages.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_SEND;
        }else {return ITEM_RECIVE;}

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Message message = messages.get(position);
// feeling
//        int reactions[]=new int[]{
//                R.drawable.angry,
//                R.drawable.happy,
//                R.drawable.shocked,
//                R.drawable.sad,
//                R.drawable.thinking,
//                R.drawable.love
//        };
//        ReactionsConfig config = new ReactionsConfigBuilder(context)
//                .withReactions(reactions)
//                .build();
//
//
//
//        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
//            if (holder.getClass()==SendViewHolder.class){
//                SendViewHolder viewHolder=(SendViewHolder)holder;
//                viewHolder.binding.feeling.setImageResource(reactions[pos]);
//                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//            }else {
//                ReciverViewHolder viewHolder=(ReciverViewHolder) holder;
//                viewHolder.binding.feeling.setImageResource(reactions[pos]);
//                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//            }
//
//            message.setFeeling(pos);
//
//            FirebaseDatabase.getInstance().getReference()
//                    .child("chats")
//                    .child(senderRoom)
//                    .child("messages")
//                    .child(message.getMessageId()).setValue(message);
//
//            FirebaseDatabase.getInstance().getReference()
//                    .child("chats")
//                    .child(receiverRoom)
//                    .child("messages")
//                    .child(message.getMessageId()).setValue(message);
//
//            return true; // true is closing popup, false is requesting a new selection
//        });
//
//        feeling

            if (holder.getClass()==SendViewHolder.class){
                SendViewHolder viewHolder=(SendViewHolder)holder;
                viewHolder.binding.message.setText(message.getMessage());
//
//feeling
//                if (message.getFeeling() >= 0){
//                    viewHolder.binding.feeling.setImageResource(reactions[message.getFeeling()]);
//                    viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//                }else {viewHolder.binding.feeling.setVisibility(View.GONE);}
//
//
//                viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popup.onTouch(v,event);
//                        return false;
//                    }
//                });


//    feeling
            }else {
                ReciverViewHolder viewHolder=(ReciverViewHolder)holder;
                viewHolder.binding.message.setText(message.getMessage());
//
//      feeling
//                if (message.getFeeling() >= 0){
////                    message.setFeeling(reactions[(int)message.getFeeling()]);
//                    viewHolder.binding.feeling.setImageResource(reactions[message.getFeeling()]);
//                    viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//                }else {viewHolder.binding.feeling.setVisibility(View.GONE);}
//
//
//                viewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popup.onTouch(v,event);
//                        return false;
//                    }
//                });

//       feeling
            }





    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SendViewHolder extends RecyclerView.ViewHolder {
        ItemSendBinding binding;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =ItemSendBinding.bind(itemView);
        }
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder {

        ItemReciveBinding binding;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =ItemReciveBinding.bind(itemView);
        }
    }
}
