package com.bfcai.islamy.Adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bfcai.islamy.Model.MuslimDoaa;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Service.ArabicTTS;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class MuslimDoaaAdapter extends RecyclerView.Adapter<MuslimDoaaAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MuslimDoaa> doaa;
    Context context;

    public MuslimDoaaAdapter(Context ctx, List<MuslimDoaa> doaa,Context context){
        this.inflater=LayoutInflater.from(ctx);
        this.doaa=doaa;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_api_content_layout,parent,false);
        return new ViewHolder(view);
    }
    ArabicTTS tts;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Creating a new object of the ArabicTTS librrary
        tts = new ArabicTTS();
        // Preparing the language
        tts.prepare(context);

        holder.doaaTitle.setText(doaa.get(position).getTitle());
        holder.doaaSubtitle.setText(doaa.get(position).getSubtitle());
        holder.doaaNum.setText(doaa.get(position).getNum());
       holder.shareContent.setOnClickListener(v -> {
           Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
           sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           sharingIntent.setType("text/plain");
           String shareBody =doaa.get(position).getTitle() + "\n"+ doaa.get(position).getSubtitle();
           sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
           Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
           chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(chooserIntent);
       });
       holder.copyContent.setOnClickListener(v -> {
           android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
           android.content.ClipData clipData = android.content.ClipData.newPlainText("Text Label",doaa.get(position).getTitle() + "\n"+ doaa.get(position).getSubtitle());
           clipboardManager.setPrimaryClip(clipData);
           Toast.makeText(context.getApplicationContext(),"تم النسخ",Toast.LENGTH_SHORT).show();
       });
      /* holder.listenContent.setOnClickListener(v -> {
           String ip =doaa.get(position).getTitle() + "\n"+ doaa.get(position).getSubtitle();
           if(ip!=null && !ip.equals("")){
               // To read the text inserted
               tts.talk(ip);
           }
       });*/

    }

    @Override
    public int getItemCount() {
        return doaa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView doaaTitle,doaaSubtitle,doaaNum;
        ImageView shareContent, copyContent;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doaaTitle=itemView.findViewById(R.id.doaaTitle);
            doaaSubtitle=itemView.findViewById(R.id.doaaSubtitle);
            doaaNum=itemView.findViewById(R.id.doaaNum);
            shareContent= itemView.findViewById(R.id.shareContent);
            copyContent= itemView.findViewById(R.id.copyContent);
            cardView = itemView.findViewById(R.id.week_card_view);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            cardView.setAnimation(translate_anim);

        }
    }
}