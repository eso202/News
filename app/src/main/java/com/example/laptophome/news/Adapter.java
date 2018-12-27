package com.example.laptophome.news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myviewholder>{

    private List<articles> articles;
    private Context context;

    public Adapter(List<com.example.laptophome.news.articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    private OnItemClickListener onItemClickListener;
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.cardview_item,viewGroup,false);

        return new  myviewholder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder myviewholder, int i) {
        final com.example.laptophome.news.articles model=articles.get(i);
        Picasso.get().load(model.getUrlToImage()).into(myviewholder.img, new Callback() {
            @Override
            public void onSuccess() {
                myviewholder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        myviewholder.title.setText(model.getTitle());
        myviewholder.description.setText(model.getDescription());
        myviewholder.source.setText("From "+model.getSource().getName());
        myviewholder.date.setText("\u2022"+Utils.DateFormat(model.getPublishedAt()));
        myviewholder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),details_articles.class);
                intent.putExtra("url",model.getUrl());
                intent.putExtra("title",model.getTitle());
                intent.putExtra("imageurl",model.getUrlToImage());
                intent.putExtra("source",model.getSource().getName());
                intent.putExtra("date",model.getPublishedAt());
                intent.putExtra("author",model.getAuthor());
                v.getContext().startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void onitemclicklistener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }

    public class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
            TextView title,description,source,date;
            ImageView img;
            ProgressBar progressBar;
            OnItemClickListener onItemClickListener;
            Button details;
        public myviewholder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);

            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.descr);
            source=itemView.findViewById(R.id.source);
            date=itemView.findViewById(R.id.date);
            img=itemView.findViewById(R.id.img);
            details=itemView.findViewById(R.id.details_button);
            progressBar=itemView.findViewById(R.id.progress_bar);
            this.onItemClickListener=onItemClickListener;

        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface OnItemClickListener
    {
        void onitemclick(View view,int position);
    }
}
