package com.mohware.ikujatwende;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.RecyclerViewadapter> {
    private Context context;
    private List<EventsModel> items;
    private ItemClickListener itemClickListener;

    public homeAdapter(Context context, List<EventsModel> items, ItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public homeAdapter.RecyclerViewadapter onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_item, parent, false);

        return new homeAdapter.RecyclerViewadapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull homeAdapter.RecyclerViewadapter recyclerViewadapter, int i) {
        EventsModel item = items.get(i);
        recyclerViewadapter.Item.setText(item.getName());
        recyclerViewadapter.date.setText("DATE: "+item.getDate());

        String paths = item.getPath();
        String rep = item.getReporter();
        String time = item.getTime();
        String locti = item.getLocation();

        if (paths.equals("NONE")) {
            recyclerViewadapter.img.setImageBitmap(null);
        } else {
            try {
                File f = new File(Environment.getExternalStorageDirectory(),"/iKujaTwende/" + paths);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                recyclerViewadapter.img.setImageBitmap(b);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (rep.equals("NONE")) {
            recyclerViewadapter.rept.setText("");
        } else {
            recyclerViewadapter.rept.setText("BY: "+rep);
        }
        if (time.equals("NONE")) {
            recyclerViewadapter.tim.setText("");
        } else {
            recyclerViewadapter.tim.setText("TIME: "+time);
        }
        if (locti.equals("NONE")) {
            recyclerViewadapter.loct.setText("");
        } else {
            recyclerViewadapter.loct.setText("@ "+locti);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class RecyclerViewadapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Item;
        TextView date;
        TextView loct;
        TextView tim;
        TextView rept;
        ImageView img;
        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewadapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            Item = itemView.findViewById(R.id.itm_name);
            date = itemView.findViewById(R.id.itm_date);
            loct = itemView.findViewById(R.id.itm_loc);
            tim = itemView.findViewById(R.id.itm_time);
            rept = itemView.findViewById(R.id.itm_rep);
            img = itemView.findViewById(R.id.itm_pic);
            card_item = itemView.findViewById(R.id.card_item);

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
