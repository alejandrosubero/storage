package com.js.storage.recycleView;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.R;

public class MapListViewHolder extends RecyclerView.ViewHolder {

    TextView MapNameU;
    ImageView MapSelectImageView;

    public MapListViewHolder(@NonNull View itemView) {
        super(itemView);
        MapNameU = itemView.findViewById(R.id.MapNameTitleU);
        MapSelectImageView = itemView.findViewById(R.id.MapSelectImageView);
    }

}
