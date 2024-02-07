package com.mohware.ikujatwende;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    ImageView img;
    ImageView img2;
    Button eventsBTN, deleteBTN;
    RecyclerView recyclerView;
    EditText srch_evnt;

    DBHelper DB;
    Dialog dialog;
    homeAdapter adapter;
    homeAdapter.ItemClickListener itemClickListener;
    List<EventsModel> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        assign();
        Buttons_Events();
        textEvents();

        DB = new DBHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(null);
        title.setText("Home");
        img.setImageResource(R.drawable.ic_home);
        img2.setImageResource(R.drawable.ikuja_logo);
        setSupportActionBar(toolbar);
        getAllItems();

    }

    private void assign() {

        toolbar = findViewById(R.id.main_toolbar);
        title = findViewById(R.id.titleText);
        img = findViewById(R.id.title_image);
        recyclerView = findViewById(R.id.recycler);
        img2 = findViewById(R.id.logo_image);
        eventsBTN = findViewById(R.id.mng_eventsBTN);
        deleteBTN = findViewById(R.id.del_eventsBTN);
        srch_evnt = findViewById(R.id.edt_items);

    }

    private void textEvents() {
        srch_evnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    item = new ArrayList<>();
                    getSearchItems(s.toString());
                } else {
                    getAllItems();
                }
            }
        });
    }

    private void Buttons_Events() {
        eventsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, events.class);
                startActivity(intent);
                finish();

            }

        });
        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkInsrt = DB.deleteall();
                if (checkInsrt == true) {

                    getAllItems();
                } else {
                    showDialog("no", "Ooooops!!", "The Events Not Deleted!");
                }
            }

        });
        itemClickListener = ((view, position) -> {
            String name = item.get(position).getName();
            String location = item.get(position).getLocation();
            String reporter = item.get(position).getReporter();
            String Time = item.get(position).getTime();
            String date = item.get(position).getDate();
            String path = item.get(position).getPath();
            String desc = item.get(position).getDesc();
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(home.this, daEvent.class);
            intent.putExtra("key", name);
            intent.putExtra("key2", location);
            intent.putExtra("key1", date);
            intent.putExtra("key3", Time);
            intent.putExtra("key4", reporter);
            intent.putExtra("key5", path);
            intent.putExtra("key6", desc);
            startActivity(intent);
            finish();
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cd = DB.alterTB();
            }

        });
    }

    private void getAllItems() {
        item = new ArrayList<>();
        Cursor res = DB.getData();
        if (res.getCount() == 0) {
            showDialog("no", "Ooooops!!", "No Events Exist. Add Events.");
        } else {
            while (res.moveToNext()) {
                String evtname = res.getString(0);
                String evtlocation = res.getString(1);
                String evtreporter = res.getString(2);
                String evttime = res.getString(3);
                String evtdate = res.getString(4);
                String evtpath = res.getString(5);
                String evtdesc = res.getString(6);
                EventsModel model = new EventsModel(evtname, evtlocation, evtreporter, evttime, evtdate, evtpath, evtdesc);
                item.add(model);
            }
        }

        adapter = new homeAdapter(this, item, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void getSearchItems(String k) {

        Cursor res = DB.srcData(k);
        if (res.getCount() == 0) {
            showDialog("no", "Ooooops!!", "No Such Event Exisis.");
        } else {
            while (res.moveToNext()) {
                String evtname = res.getString(0);
                String evtlocation = res.getString(1);
                String evtreporter = res.getString(2);
                String evttime = res.getString(3);
                String evtdate = res.getString(4);
                String evtpath = res.getString(5);
                String evtDesc = res.getString(6);
                EventsModel model = new EventsModel(evtname, evtlocation, evtreporter, evttime, evtdate, evtpath,evtDesc);
                item.add(model);
            }
        }

        adapter = new homeAdapter(this, item, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void showDialog(String yes, String t1, String t2) {
        Button back;
        TextView tv1, tv2;
        ImageView status;

        dialog = new Dialog(home.this);
        dialog.setContentView(R.layout.ok);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //window.getAttributes().windowAnimations=R.style.DialogAnimation;

        back = dialog.findViewById(R.id.back);
        tv1 = dialog.findViewById(R.id.tv1);
        tv2 = dialog.findViewById(R.id.tv2);
        status = dialog.findViewById(R.id.status);

        tv1.setText(t1);
        tv2.setText(t2);

        if (yes.equals("yes")) {
            status.setImageResource(R.drawable.happy_emoji);
            tv1.setTextColor(getResources().getColor(R.color.green));
            back.setBackgroundResource(R.drawable.green);
        } else {
            status.setImageResource(R.drawable.sad_emoji);
            tv1.setTextColor(getResources().getColor(R.color.red));
            tv2.setTextColor(getResources().getColor(R.color.red));
            back.setBackgroundResource(R.drawable.red);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}