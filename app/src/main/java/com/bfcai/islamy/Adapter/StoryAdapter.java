package com.bfcai.islamy.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bfcai.islamy.Model.Story;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Stories.Components.storyDetailsActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.storyViewHolder> {

    List<Story> storyList;
    Activity context;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public StoryAdapter(List<Story> storyList, Activity context) {
        this.storyList = storyList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoryAdapter.storyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.story_layout,parent,false);
       firestore = FirebaseFirestore.getInstance();
       auth = FirebaseAuth.getInstance();
       return new storyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull storyViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.setPostImage(story.getImage());
        holder.setPostTitle(story.getTitle());
        holder.setPostDes(story.getDes());
        long miliseconds = story.getDate().getTime();
        String date = DateFormat.format("MM/dd/yyyy",new Date(miliseconds)).toString();
        holder.setdate(date);
        holder.view.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), storyDetailsActivity.class);
            i.putExtra("title",  story.getTitle());
            i.putExtra("des",  story.getDes());
            i.putExtra("image",  story.getImage());
            i.putExtra("date",  story.getDate());
            i.putExtra("id", story.storyId);
            v.getContext().startActivity(i);


        });
        String storyId = story.storyId;
        String currentUserId = auth.getCurrentUser().getUid();
        holder.likePic.setOnClickListener(v ->{
            firestore.collection("Stories/"+storyId+"/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(!task.getResult().exists()){
                        Map<String , Object> likes = new HashMap<>();
                        likes.put("timestamp", FieldValue.serverTimestamp());
                        firestore.collection("Stories/"+storyId+"/Likes").document(currentUserId).set(likes);
                    }
                    else {
                        firestore.collection("Stories/"+storyId+"/Likes").document(currentUserId).delete();
                    }
                }
            });

            firestore.collection("Stories/"+storyId+"/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error == null){
                        if(value.exists()){
                            holder.likePic.setImageDrawable(context.getDrawable(R.drawable.fullfavorite));
                        }
                        else {
                            holder.likePic.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_favorite_border_24));
                        }
                    }
                }
            });

            firestore.collection("Stories/"+storyId+"/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error == null){
                        if (!value.isEmpty()){
                            int count = value.size();
                            holder.setLikeCount(count);
                        } else {
                            holder.setLikeCount(0);
                        }
                    }
                }
            });
        });

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
                    alert.setTitle("Delete")
                            .setMessage("Are You Sure ?")
                            .setNegativeButton("No" , null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    firestore.collection("Stories/" + storyId + "/Likes").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                                                        firestore.collection("Stories/" + storyId + "/Likes").document().delete();
                                                    }
                                                }
                                            });
                                    firestore.collection("Stories").document(storyId).delete();
                                    storyList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                    alert.show();
                }
            });

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class storyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView , likePic,deleteBtn;
        TextView title,des,Pdate,likeCount;
        View view;
        public storyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            likePic = view.findViewById(R.id.like_btn);
            deleteBtn= view.findViewById(R.id.delete_btn);
        }

        public void setLikeCount(int count) {
           likeCount = view.findViewById(R.id.like_count_tv);
           likeCount.setText(count + " Likes");
        }

        public void setPostImage(String imageurl){
            imageView = view.findViewById(R.id.user_post);
            Glide.with(context).load(imageurl).into(imageView);
        }
        public void setPostTitle(String titlePost){
            title = view.findViewById(R.id.caption_tv);
            title.setText(titlePost);
        }
        public void setPostDes(String desPost){
            des = view.findViewById(R.id.des_tv);
            des.setText(desPost.length() > 20 ? desPost.substring(0,20)+"..." : desPost);
        }
        public void setdate(String date){
            Pdate = view.findViewById(R.id.date_tv);
            Pdate.setText(date);
        }
    }
}
