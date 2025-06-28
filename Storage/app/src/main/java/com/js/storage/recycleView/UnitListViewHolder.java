package com.js.storage.recycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.R;

public class UnitListViewHolder extends RecyclerView.ViewHolder{

    TextView unitNameU;
    ImageView unitSelectImageView;


    public UnitListViewHolder(@NonNull View itemView) {
        super(itemView);
        unitNameU = itemView.findViewById(R.id.unitNameU);
        unitSelectImageView = itemView.findViewById(R.id.unitSelectImageView);


    }
}
