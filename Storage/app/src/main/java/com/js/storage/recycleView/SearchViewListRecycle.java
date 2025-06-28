package com.js.storage.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.js.storage.R;
import com.js.storage.ShowListSearchActivity;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SearchViewListRecycle extends RecyclerView.Adapter<SearchListViewHolder>{

    private List<Storage> data;
    protected List<Storage> listaOriginalData;


    public SearchViewListRecycle(List<Storage> storages) {
        data = storages;
        listaOriginalData = new ArrayList<>();
        listaOriginalData.addAll(data);
    }

    @NonNull
    @Override
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.storage_item_layout,parent,false
        );
        return new SearchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder holder, int position) {

        Storage storage = data.get(position);

        if(storage.getItemImage() != null){
            holder.itemImageView.setImageBitmap(DataConverter.convertArrayToImage(storage.getItemImage()));
        }
        if(storage.getItemName() !=null){
            holder.cardItemName.setText(storage.getItemName());
        }
        if(storage.getItemType() != null){
            holder.cardItemType.setText(storage.getItemType());
        }
        if(storage.getItemUse() != null){
            holder.cardItemUse.setText(storage.getItemUse());
        }
        if(storage.getStoreArea() != null) {
            holder.cardStoreArea.setText(storage.getStoreArea());
        }
        if(storage.getStoreType() != null){
            holder.cardStoreType.setText(storage.getStoreType());
        }
        if(storage.getStoreSecction() != null){
            holder.cardStoreSecction.setText(storage.getStoreSecction());
        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShowListSearchActivity showList = new ShowListSearchActivity();
                showList.viewDetaill(storage);
                return false;
            }
        });


    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filter(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            data.clear();
            data.addAll(listaOriginalData);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Storage> collecion = data.stream()
                        .filter(i -> i.getItemName().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                data.clear();
                data.addAll(collecion);
            } else {
                data.clear();
                for (Storage c : listaOriginalData) {
                    if (c.getItemName().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        data.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    private String formatDate(Date date){
        String forDate ="";
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy");
        try {
            Date parsedDate = sdf.parse(String.valueOf(date));
            forDate = print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return forDate;
    }



}
