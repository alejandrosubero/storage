package com.js.storage.recycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.R;


public class SearchListViewHolder extends RecyclerView.ViewHolder{

    TextView cardStoreSecction,  cardStoreType, cardStoreArea;
    TextView cardItemType, cardItemName, cardItemUse;
    ImageView itemImageView;

    public SearchListViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.itemImageView);

        cardItemType = itemView.findViewById(R.id.cardItemType);
        cardItemName = itemView.findViewById(R.id.cardItemName);
        cardItemUse = itemView.findViewById(R.id.cardItemUse);

        cardStoreArea = itemView.findViewById(R.id.cardStoreArea);
        cardStoreType = itemView.findViewById(R.id.cardStoreType);
        cardStoreSecction = itemView.findViewById(R.id.cardStoreSecction);
    }
}
