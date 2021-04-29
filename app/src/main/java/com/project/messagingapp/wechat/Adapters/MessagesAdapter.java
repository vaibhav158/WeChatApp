 package com.project.messagingapp.wechat.Adapters;

 import android.annotation.SuppressLint;
 import android.app.AlertDialog;
 import android.content.Context;
 import android.view.LayoutInflater;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.view.ViewGroup;
 import androidx.annotation.NonNull;
 import androidx.appcompat.view.ActionMode;
 import androidx.recyclerview.widget.RecyclerView;
 import com.bumptech.glide.Glide;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.FirebaseDatabase;
 import com.project.messagingapp.wechat.Models.Message;
 import com.project.messagingapp.wechat.R;
 import com.project.messagingapp.wechat.databinding.DeleteDialogBinding;
 import com.project.messagingapp.wechat.databinding.ItemReceiveBinding;
 import com.project.messagingapp.wechat.databinding.ItemSendBinding;

 import java.util.ArrayList;


 @SuppressWarnings("ALL")
 public class MessagesAdapter extends RecyclerView.Adapter {

     Context context;
     ArrayList<Message> messages;

     final int ITEM_SENT = 1;
     final int ITEM_RECEIVE = 2;

     String senderRoom;
     String receiverRoom;

     public MessagesAdapter(Context context, ArrayList<Message> messages, String senderRoom, String receiverRoom) {
         this.context = context;
         this.messages = messages;
         this.senderRoom = senderRoom;
         this.receiverRoom = receiverRoom;
     }

     @NonNull
     @Override
     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         if(viewType == ITEM_SENT) {
             View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);
             return new SentViewHolder(view);
         } else {
             View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
             return new ReceiverViewHolder(view);
         }
     }

     @Override
     public int getItemViewType(int position) {
         Message message = messages.get(position);
         if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())) {
             return ITEM_SENT;
         } else {
             return ITEM_RECEIVE;
         }
     }

     @Override
     public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         Message message = messages.get(position);

         if(holder.getClass() == SentViewHolder.class) {
             SentViewHolder viewHolder = (SentViewHolder)holder;

             if(message.getMessage().equals("photo")) {
                 viewHolder.binding.image.setVisibility(View.VISIBLE);
                 viewHolder.binding.message.setVisibility(View.GONE);
                 Glide.with(context)
                         .load(message.getImageUrl())
                         .placeholder(R.drawable.placeholder)
                         .into(viewHolder.binding.image);
             }

             viewHolder.binding.message.setText(message.getMessage());

             viewHolder.itemView.setOnLongClickListener(v -> {

                 new ActionMode.Callback() {
                     @Override
                     public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                         mode.getMenuInflater().inflate(R.menu.menu_context, menu);
                         return true;
                     }

                     @Override
                     public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                         return false;
                     }

                     @Override
                     public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                         @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                         DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                         AlertDialog dialog = new AlertDialog.Builder(context)
                                 .setTitle("Delete Message")
                                 .setView(binding.getRoot())
                                 .create();

                         binding.everyone.setOnClickListener(v15 -> {
                             message.setMessage("This message is removed.");
                             FirebaseDatabase.getInstance().getReference()
                                     .child("chats")
                                     .child(senderRoom)
                                     .child("messages")
                                     .child(message.getMessageId()).setValue(message);

                             FirebaseDatabase.getInstance().getReference()
                                     .child("chats")
                                     .child(receiverRoom)
                                     .child("messages")
                                     .child(message.getMessageId()).setValue(message);
                             dialog.dismiss();
                         });

                         binding.delete.setOnClickListener(v14 -> {
                             FirebaseDatabase.getInstance().getReference()
                                     .child("chats")
                                     .child(senderRoom)
                                     .child("messages")
                                     .child(message.getMessageId()).setValue(null);
                             dialog.dismiss();
                         });

                         binding.cancel.setOnClickListener(v13 -> dialog.dismiss());

                         dialog.show();
                         return false;
                     }

                     @Override
                     public void onDestroyActionMode(ActionMode mode) {
                     }
                 };
                 return false;
             });
         } else {
             ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
             if(message.getMessage().equals("photo")) {
                 viewHolder.binding.image.setVisibility(View.VISIBLE);
                 viewHolder.binding.message.setVisibility(View.GONE);
                 Glide.with(context)
                         .load(message.getImageUrl())
                         .placeholder(R.drawable.placeholder)
                         .into(viewHolder.binding.image);
             }
             viewHolder.binding.message.setText(message.getMessage());

             viewHolder.itemView.setOnLongClickListener(v -> {

                 new ActionMode.Callback() {
                     @Override
                     public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                         mode.getMenuInflater().inflate(R.menu.menu_context, menu);
                         return true;
                     }

                     @Override
                     public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                         return false;
                     }

                     @Override
                     public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                         @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                         DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                         AlertDialog dialog = new AlertDialog.Builder(context)
                                 .setTitle("Delete Message")
                                 .setView(binding.getRoot())
                                 .create();

                         binding.everyone.setOnClickListener(v16 -> {
                             message.setMessage("This message is removed.");
                             FirebaseDatabase.getInstance().getReference()
                                     .child("chats")
                                     .child(senderRoom)
                                     .child("messages")
                                     .child(message.getMessageId()).setValue(message);

                             FirebaseDatabase.getInstance().getReference()
                                     .child("chats")
                                     .child(receiverRoom)
                                     .child("messages")
                                     .child(message.getMessageId()).setValue(message);
                             dialog.dismiss();
                         });

                         binding.delete.setOnClickListener(v12 -> {
                             FirebaseDatabase.getInstance().getReference()
                                     .child("chats")
                                     .child(senderRoom)
                                     .child("messages")
                                     .child(message.getMessageId()).setValue(null);
                             dialog.dismiss();
                         });

                         binding.cancel.setOnClickListener(v1 -> dialog.dismiss());

                         dialog.show();

                         return false;

                     }

                     @Override
                     public void onDestroyActionMode(ActionMode mode) {

                     }
                 };
                 return false;
             });
         }
     }

     @Override
     public int getItemCount() {
         return messages.size();
     }

     public static class SentViewHolder extends RecyclerView.ViewHolder {

         ItemSendBinding binding;
         public SentViewHolder(@NonNull View itemView) {
             super(itemView);
             binding = ItemSendBinding.bind(itemView);
         }
     }

     public static class ReceiverViewHolder extends RecyclerView.ViewHolder {

         ItemReceiveBinding binding;

         public ReceiverViewHolder(@NonNull View itemView) {
             super(itemView);
             binding = ItemReceiveBinding.bind(itemView);
         }
     }

 }