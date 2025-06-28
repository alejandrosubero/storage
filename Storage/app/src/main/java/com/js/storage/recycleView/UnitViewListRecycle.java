package com.js.storage.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.R;
import com.js.storage.SelectUnitActivity;
import com.js.storage.ShowStoreList;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class UnitViewListRecycle  extends RecyclerView.Adapter<UnitListViewHolder>{

    private List<Unit> data;
    protected List<Unit> listaOriginalData;

    public UnitViewListRecycle(List<Unit> storages) {
        data = storages;
        listaOriginalData = new ArrayList<>();
        listaOriginalData.addAll(data);
    }

    @NonNull
    @Override
    public UnitListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.unit_list_select_layout,parent,false
        );
        return new UnitListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitListViewHolder holder, int position) {

        Unit unit = data.get(position);
        if(unit.getUnitImage() != null){
            holder.unitSelectImageView.setImageBitmap(DataConverter.convertArrayToImage(unit.getUnitImage()));
        }
        if(unit.getName() !=null){
            holder.unitNameU.setText(unit.getName());
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SelectUnitActivity selectUnitActivity = new SelectUnitActivity();
//                selectUnitActivity.selectUnit(unit);
//            }
//        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                SelectUnitActivity selectUnitActivity = new SelectUnitActivity();
                selectUnitActivity.selectUnitv(unit, view);
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
                List<Unit> collecion = data.stream()
                        .filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                data.clear();
                data.addAll(collecion);
            } else {
                data.clear();
                for (Unit c : listaOriginalData) {
                    if (c.getName().toLowerCase().contains(txtBuscar.toLowerCase())) {
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
