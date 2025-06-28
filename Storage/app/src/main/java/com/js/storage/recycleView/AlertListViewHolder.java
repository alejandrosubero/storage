package com.js.storage.recycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.R;

public class AlertListViewHolder extends RecyclerView.ViewHolder{

    ImageView materialImageViewMA;
    TextView materialCardNameMA, materialCardQuantityA, cardStoreAreaA, cardStoreSecctionA;

    public AlertListViewHolder(@NonNull View itemView) {
        super(itemView);
        materialImageViewMA = itemView.findViewById(R.id.materialImageViewMA);
        materialCardNameMA = itemView.findViewById(R.id.materialCardNameMA);
        materialCardQuantityA = itemView.findViewById(R.id.materialCardQuantityA);
        cardStoreAreaA = itemView.findViewById(R.id.cardStoreAreaA);
        cardStoreSecctionA = itemView.findViewById(R.id.cardStoreSecctionA);
    }
}
