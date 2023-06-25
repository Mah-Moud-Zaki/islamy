package com.bfcai.islamy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bfcai.islamy.Model.Notes;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Notes.Components.UpdateNotesActivity;

import java.util.ArrayList;
import java.util.Collection;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>implements Filterable {
    Context context;
    Activity activity;
    ArrayList<Notes> notesList;
    ArrayList<Notes> newList;

    public NoteAdapter(Context context, Activity activity, ArrayList<Notes> notesList) {
        this.context = context;
        this.activity = activity;
        this.notesList = notesList;
        newList =new ArrayList<>(notesList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_recycleview_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notes note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNotesActivity.class);
                intent.putExtra("title",notesList.get(position).getTitle());
                intent.putExtra("description",notesList.get(position).getDescription());
                intent.putExtra("id",notesList.get(position).getId());

                activity.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //take a new list that will store the filtered items
            ArrayList<Notes> filteredList = new ArrayList<>();

            //now if you will search something and the text is empty or length is equal to zero
            if(constraint==null||constraint.length()==0)
            {
                //then filtered List will contain all items from the new list
                filteredList.addAll(newList);
            }
            else
            {
                //take a string and store the search string to it
                String filterPattern = constraint.toString().toLowerCase().trim();

                //now loop for the whole new list
                for (Notes item:newList)
                {
                    if(item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        //that list item will be added to our filtered list
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        //now inside publish result method we will publish these results
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            notesList.clear(); //first clear the notes list
            notesList.addAll((Collection<? extends Notes>) results.values);//add a new items
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rv_tv_title);
            description = itemView.findViewById(R.id.rv_tv_description);
            layout = itemView.findViewById(R.id.rv_relative_layout);

            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            layout.setAnimation(translate_anim);
        }
    }

    public ArrayList<Notes> getList(){
        return notesList;
    }

    public void removeItem(int position)
    {
        notesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Notes item ,int position)
    {
        notesList.add(position,item);
        notifyItemInserted(position);
    }
    public void editItem(int position)
    {
        Intent intent = new Intent(context, UpdateNotesActivity.class);
        intent.putExtra("title",notesList.get(position).getTitle());
        intent.putExtra("description",notesList.get(position).getDescription());
        intent.putExtra("id",notesList.get(position).getId());

        activity.startActivity(intent);
    }



}