package com.mohware.ikujatwende;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class events extends AppCompatActivity {
    Toolbar toolbar;
    Button addEvtDatBTN, saveBTN, clearBTN, edtEvtDatBTN, delete, update;
    TextView title, addEvtDat, EdtEvt, EdtEvtDate;
    ImageView img;
    RecyclerView recyclerView;
    ImageView img2, img3, img4, itempic;
    CardView View1, View2, View3, View4, gal1;
    ConstraintLayout expand, expand2;
    DatePickerDialog picker;
    TextInputLayout addEvtNm, addEvtLoc, addEvtRep, addEvtTim, srch_evnt, EdtEvtLoc, EdtEvtRep, EdtEvtTim, EdtEvtNm;
    ItemsAdapter adapter;
    ItemsAdapter.ItemClickListener itemClickListener;
    ProgressDialog progressDialog;
    List<EventsModel> item;

    boolean doubleBackToExitPressedOnce = false;
    int getphoto = 88;
    Uri path;
    Bitmap bitmap;
    String encodedImage = "s";
    String itemy="";
    Dialog dialog;
    Calendar calendar;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        assign();
        actions();
        ButtonsEvents();
        textEvents();

        calendar = Calendar.getInstance();
        DB = new DBHelper(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        title.setText("Events Manager");
        img.setImageResource(R.drawable.ic_back);
        img2.setImageResource(R.drawable.ikuja_logo);
        img3.setImageResource(R.drawable.ic_down);
        img4.setImageResource(R.drawable.ic_down);

        setSupportActionBar(toolbar);
    }

    private void assign() {

        toolbar = findViewById(R.id.main_toolbar);
        title = findViewById(R.id.titleText);
        img = findViewById(R.id.title_image);
        img2 = findViewById(R.id.logo_image);
        img3 = findViewById(R.id.imageView9);
        img4 = findViewById(R.id.imageView12);
        View1 = findViewById(R.id.view);
        recyclerView = findViewById(R.id.recycler);
        View2 = findViewById(R.id.cardView2);
        View3 = findViewById(R.id.view1);
        View4 = findViewById(R.id.cardView3);
        expand = findViewById(R.id.expandable);
        expand2 = findViewById(R.id.expandable2);
        gal1 = findViewById(R.id.galleryPhoto);
        itempic = findViewById(R.id.itmImage);
        addEvtNm = findViewById(R.id.evtName);
        addEvtLoc = findViewById(R.id.evtLoca);
        addEvtRep = findViewById(R.id.evtReport);
        addEvtTim = findViewById(R.id.evttime);
        addEvtDat = findViewById(R.id.date_TXT);
        addEvtDatBTN = findViewById(R.id.date_btn);
        saveBTN = findViewById(R.id.save_btn);
        clearBTN = findViewById(R.id.clear_btn);
        srch_evnt = findViewById(R.id.src_item);
        EdtEvt = findViewById(R.id.edt_item);
        EdtEvtNm = findViewById(R.id.edt_evtname);
        EdtEvtLoc = findViewById(R.id.edt_location);
        EdtEvtRep = findViewById(R.id.edt_reporter);
        EdtEvtTim = findViewById(R.id.edt_time);
        EdtEvtDate = findViewById(R.id.edtdate_TXT);
        edtEvtDatBTN = findViewById(R.id.edtdate_btn);
        delete = findViewById(R.id.delete_btn);
        update = findViewById(R.id.update_btn);


    }

    private void actions() {
        View1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand2.setVisibility(View.GONE);
                img4.setImageResource(R.drawable.ic_down);
                if (expand.getVisibility() == View.GONE) {
                    expand2.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(View2, new AutoTransition());
                    expand.setVisibility(View.VISIBLE);
                    img3.setImageResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(View2, new AutoTransition());
                    expand.setVisibility(View.GONE);
                    img3.setImageResource(R.drawable.ic_down);

                }
            }

        });
        View3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand.setVisibility(View.GONE);
                img3.setImageResource(R.drawable.ic_down);
                if (expand2.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(View4, new AutoTransition());
                    expand2.setVisibility(View.VISIBLE);
                    img4.setImageResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(View4, new AutoTransition());
                    expand2.setVisibility(View.GONE);
                    img4.setImageResource(R.drawable.ic_down);

                }
            }

        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(events.this, home.class);
                startActivity(intent);
                finish();
            }

        });
        gal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateText(addEvtNm, "Event Name Required")
                        | !validateText(addEvtRep, "Event Reporter Required")
                        | !validateTextView(addEvtDat, "Event Date Required")) {
                    return;

                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, getphoto);
            }
        });
        itemClickListener = ((view, position) -> {
            itemy = item.get(position).getName();
            String name = item.get(position).getName();
            String location = item.get(position).getLocation();
            String reporter = item.get(position).getReporter();
            String Time = item.get(position).getTime();
            String date = item.get(position).getDate();

            EdtEvt.setText("CHANGE " + itemy + " DETAILS");
            EdtEvtNm.getEditText().setText(name);
            EdtEvtLoc.getEditText().setText(location);
            EdtEvtRep.getEditText().setText(reporter);
            EdtEvtTim.getEditText().setText(Time);
            EdtEvtDate.setText(date);

            srch_evnt.getEditText().setText("");
            //ITMprice1.requestFocus();
            //recyclerView.removeAllViewsInLayout();
        });
    }

    private void textEvents() {
        srch_evnt.getEditText().addTextChangedListener(new TextWatcher() {
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
                    recyclerView.setAdapter(null);
                }
            }
        });
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

        adapter = new ItemsAdapter(this, item, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void ButtonsEvents() {
        addEvtDatBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(events.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        try {
                            int eex = mMonth + 1;
                            // dateEnd.setText(mDay + "/" + eex + "/" + mYear);

                            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
                            Date d1 = sdformat.parse(mDay + "/" + eex + "/" + mYear);
                            addEvtDat.setText("" + sdformat.format(d1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            showDialog("no", "Ooooops!!", e.toString());
                        }

                    }
                }, year, month, day);
                picker.show();
            }
        });
        edtEvtDatBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(events.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        try {
                            int eex = mMonth + 1;
                            // dateEnd.setText(mDay + "/" + eex + "/" + mYear);

                            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
                            Date d1 = sdformat.parse(mDay + "/" + eex + "/" + mYear);
                            EdtEvtDate.setText("" + sdformat.format(d1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            showDialog("no", "Ooooops!!", e.toString());
                        }

                    }
                }, year, month, day);
                picker.show();
            }
        });
        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearme();
            }
        });
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateText(addEvtNm, "Event Name Required")
                        | !validateText(addEvtRep, "Event Reporter Required")
                        | !validateTextView(addEvtDat, "Event Date Required")) {
                    return;

                }
                String event_name = addEvtNm.getEditText().getText().toString();
                String reporter = addEvtRep.getEditText().getText().toString();
                String date = addEvtDat.getText().toString();
                String time = addEvtTim.getEditText().getText().toString();
                String location = addEvtLoc.getEditText().getText().toString();
                String encoded_string = encodedImage;
                if (time.equals("")) {
                    time = "NONE";
                }
                if (location.equals("")) {
                    location = "NONE";
                }
                if (encoded_string.equals("s")) {
                    encoded_string = "NONE";
                } else {

                    storeImage(bitmap, event_name);
                    encoded_string = event_name + ".jpg";
                }
                Boolean checkInsrt = DB.insertData(event_name, location, reporter, time, date, encoded_string, "NONE");
                if (checkInsrt == true) {
                    showDialog("yes", "Hoooray!!", "Events Added Successfully!");
                    clearme();
                } else {
                    showDialog("no", "Ooooops!!", "The Event Already Exists!");
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemy.equals("")) {
                    showDialog("no", "Ooooops!!", "Search Event to Delete.");

                } else {
                    Boolean checkInsrt = DB.deleteData(itemy);
                    if (checkInsrt == true) {
                        showDialog("yes", "Hoooray!!", "Events Deleted Successfully!");
                        clearme();
                    } else {
                        showDialog("no", "Ooooops!!", "The Event Not Deleted!");
                    }
                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateText(EdtEvtNm, "Event Name Required")
                        | !validateText(EdtEvtRep, "Event Reporter Required")
                        | !validateTextView(EdtEvtDate, "Event Date Required")) {
                    return;

                }
                if (itemy.equals("")) {
                    showDialog("no", "Ooooops!!", "Search Event to Change.");

                } else {
                    String event_name = EdtEvtNm.getEditText().getText().toString();
                    String reporter = EdtEvtRep.getEditText().getText().toString();
                    String date = EdtEvtDate.getText().toString();
                    String time = EdtEvtTim.getEditText().getText().toString();
                    String location = EdtEvtLoc.getEditText().getText().toString();
                    String encoded_string = encodedImage;
                    if (time.equals("")) {
                        time = "NONE";
                    }
                    if (location.equals("")) {
                        location = "NONE";
                    }
                    Boolean checkInsrt = DB.updateData(itemy, location, reporter, time, date, encoded_string);
                    if (checkInsrt == true) {
                        showDialog("yes", "Hoooray!!", "Event Updated Successfully!");
                        clearme();
                    } else {
                        showDialog("no", "Ooooops!!", "The Event Not Updated!");
                    }


                }

            }
        });
    }

    private boolean validateText(TextInputLayout theTXT, String note) {
        String val = theTXT.getEditText().getText().toString();

        if (val.isEmpty()) {
            theTXT.setError(note);
            return false;

        } else {
            theTXT.setError(null);
            theTXT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTextView(TextView theTXT, String note) {
        String val = theTXT.getText().toString();

        if (val.isEmpty()) {
            showDialog("no", "Ooooops!!", note);
            return false;

        } else {
            return true;
        }
    }

    private void showDialog(String yes, String t1, String t2) {
        Button back;
        TextView tv1, tv2;
        ImageView status;

        dialog = new Dialog(events.this);
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

    private void encoding() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageInByte, Base64.DEFAULT);
    }

    private void storeImage(Bitmap bitmapImage, String x) {
        File f = new File(Environment.getExternalStorageDirectory(), "iKujaTwende");
        if (!f.exists()) {
            f.mkdir();
            f.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory(), "/iKujaTwende/" + x + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearme() {
        addEvtNm.getEditText().setText("");
        addEvtLoc.getEditText().setText("");
        addEvtRep.getEditText().setText("");
        addEvtTim.getEditText().setText("");
        addEvtDat.setText("");
        itempic.setImageBitmap(null);
        encodedImage = "s";
        addEvtNm.requestFocus();

        EdtEvt.setText("");
        srch_evnt.getEditText().setText("");
        EdtEvtNm.getEditText().setText("");
        EdtEvtLoc.getEditText().setText("");
        EdtEvtRep.getEditText().setText("");
        EdtEvtTim.getEditText().setText("");
        EdtEvtDate.setText("");
        itemy = "";
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Intent intent = new Intent(events.this, home.class);
        startActivity(intent);
        finish();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == getphoto && resultCode == RESULT_OK && data != null) {

            path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                itempic.setImageBitmap(bitmap);
                encoding();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}