package com.mohware.ikujatwende;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class daEvent extends AppCompatActivity {
    TextView Item;
    TextView date;
    TextView loct;
    TextView tim;
    TextView rept, desc;
    ImageView img3;
    Toolbar toolbar;
    TextView title;
    ImageView img;
    ImageView img2;

    DBHelper DB;
    Dialog dialog;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_event);
        assign();

        title.setText("Description");
        img.setImageResource(R.drawable.ic_back);
        img2.setImageResource(R.drawable.ikuja_logo);
        setSupportActionBar(toolbar);

        Item.setText(getIntent().getStringExtra("key"));
        date.setText(getIntent().getStringExtra("key1"));
        loct.setText(getIntent().getStringExtra("key2"));
        tim.setText(getIntent().getStringExtra("key3"));
        rept.setText(getIntent().getStringExtra("key4"));

        String paths = getIntent().getStringExtra("key5");
        String rep = getIntent().getStringExtra("key4");
        String time =getIntent().getStringExtra("key3");
        String locti = getIntent().getStringExtra("key2");
        String Desc = getIntent().getStringExtra("key6");

        if (paths.equals("NONE")) {
            img3.setImageBitmap(null);
        } else {
            try {
                File f = new File(Environment.getExternalStorageDirectory(),"/iKujaTwende/" + paths);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                img3.setImageBitmap(b);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (rep.equals("NONE")) {
            rept.setText("");
        } else {
            rept.setText("BY: "+rep);
        }
        if (Desc.equals("NONE")) {
            desc.setText("");
        } else {
            desc.setText(rep);
        }
        if (time.equals("NONE")) {
            tim.setText("");
        } else {
            tim.setText("TIME: "+time);
        }
        if (locti.equals("NONE")) {
            loct.setText("");
        } else {
            loct.setText("@ "+locti);
        }
    }

    private void assign() {

        Item = findViewById(R.id.itm_name);
        date = findViewById(R.id.itm_date);
        loct = findViewById(R.id.itm_loc);
        tim = findViewById(R.id.itm_time);
        rept = findViewById(R.id.itm_rep);
        img3 = findViewById(R.id.itm_pic);
        toolbar = findViewById(R.id.main_toolbar);
        title = findViewById(R.id.titleText);
        img = findViewById(R.id.title_image);
        img2 = findViewById(R.id.logo_image);
        desc = findViewById(R.id.itm_descpte);


    }

    private void actions() {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(daEvent.this, home.class);
                startActivity(intent);
                finish();
            }

        });


    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Intent intent = new Intent(daEvent.this, home.class);
        startActivity(intent);
        finish();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}