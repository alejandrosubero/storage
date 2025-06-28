package com.js.storage.recycleView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.storage.MainActivity;
import com.js.storage.MapListActivity;
import com.js.storage.R;
import com.js.storage.SelectUnitActivity;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Map;
import com.js.storage.entitys.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapViewListRecycle extends RecyclerView.Adapter<MapListViewHolder>{

    private List<Map> data;
    protected List<Map> listaOriginalData;

    public MapViewListRecycle(List<Map> storages) {
        data = storages;
        listaOriginalData = new ArrayList<>();
        listaOriginalData.addAll(data);
    }

    @NonNull
    @Override
    public MapListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.map_list_layout,parent,false
        );
        return new MapListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MapListViewHolder holder, int position) {

        Map map = data.get(position);
        if(map.getMapImage() != null){
            holder.MapSelectImageView.setImageBitmap(DataConverter.convertArrayToImage(map.getMapImage()));
        }
        if(map.getName() !=null){
            holder.MapNameU.setText(map.getName());
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MapListActivity mapList = new MapListActivity();
                mapList.detailSee(map);
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
                List<Map> collecion = data.stream()
                        .filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                data.clear();
                data.addAll(collecion);
            } else {
                data.clear();
                for (Map c : listaOriginalData) {
                    if (c.getName().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        data.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}
