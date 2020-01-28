package com.juanma.greennews.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.juanma.greennews.R;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, publisher, date;

    AppCompatImageView image;

    OnNewsListener mOnNewsListener;


    public NewsViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
        super(itemView);

        this.mOnNewsListener = onNewsListener;
        title = itemView.findViewById(R.id.news_title);
        publisher = itemView.findViewById(R.id.news_publisher);
        date = itemView.findViewById(R.id.news_date);
        image = itemView.findViewById(R.id.news_image);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        mOnNewsListener.onNewsClick(getAdapterPosition());

    }
}
