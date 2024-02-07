package com.mohware.ikujatwende;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.RecyclerViewadapter> {

    private Context context;
    private List<EventsModel> items;
    private ItemClickListener itemClickListener;

    public ItemsAdapter(Context context, List<EventsModel> items, ItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemsAdapter.RecyclerViewadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_items, parent, false);

        return new ItemsAdapter.RecyclerViewadapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.RecyclerViewadapter holder, int position) {
        EventsModel item = items.get(position);
        holder.Item.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class RecyclerViewadapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Item;
        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewadapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            Item  = itemView.findViewById(R.id.item_name);
            card_item  = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());

        }
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
