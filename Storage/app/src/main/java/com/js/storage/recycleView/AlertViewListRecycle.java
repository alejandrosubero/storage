package com.js.storage.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.R;
import com.js.storage.ShowStoreList;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;

import java.util.ArrayList;
import java.util.List;

public class AlertViewListRecycle extends RecyclerView.Adapter<AlertListViewHolder>{

    private LayoutInflater inflater;
    private List<Storage> data;
    protected List<Storage> listaOriginalData;

    public AlertViewListRecycle(List<Storage> storages) {
        data = storages;
        listaOriginalData = new ArrayList<>();
        listaOriginalData.addAll(data);
    }

    public AlertViewListRecycle(Context ctx, List<Storage> storages) {
        data = storages;
        listaOriginalData = new ArrayList<>();
        listaOriginalData.addAll(data);
        inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public AlertListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alert_material_layout, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.alert_material_layout,parent,false
//        );
        return new AlertListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertListViewHolder holder, int position) {

        Storage storage = data.get(position);
        if(storage.getItemImage() != null){
            holder.materialImageViewMA.setImageBitmap(DataConverter.convertArrayToImage(storage.getItemImage()));
        }
        if(storage.getItemName() !=null){
            holder.materialCardNameMA.setText(storage.getItemName());
        }
        if(storage.getItemNumber() != null){
            holder.materialCardQuantityA.setText(storage.getItemType());
        }
        if(storage.getStoreArea() != null) {
            holder.cardStoreAreaA.setText(storage.getStoreArea());
        }
        if(storage.getStoreType() != null){
            holder.cardStoreAreaA.setText(storage.getStoreType());
        }
        if(storage.getStoreSecction() != null){
            holder.cardStoreSecctionA.setText(storage.getStoreSecction());
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShowStoreList showStoreList = new ShowStoreList();
                showStoreList.viewAlert(storage);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
