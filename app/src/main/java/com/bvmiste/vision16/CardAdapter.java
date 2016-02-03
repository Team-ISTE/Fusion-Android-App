package com.bvmiste.vision16;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bvmiste.vision16.Helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arvind on 3/2/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<ListItem> items;
    // SqLite database handler




    public CardAdapter(String[] titles, String[] msgs){
        super();
        items = new ArrayList<ListItem>();
        int j=titles.length-1;
        for(int i =0; i<titles.length; i++,j--){
            ListItem item = new ListItem();
            item.setName(titles[j]);
            item.setUrl(msgs[j]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  items.get(position);
        holder.textViewName.setText(list.getName());
        holder.textViewUrl.setText(list.getUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewUrl = (TextView) itemView.findViewById(R.id.textViewMessage);

        }
    }
}
