package com.example.projectkm.ui.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projectkm.MainActivity;
import com.example.projectkm.R;
import com.example.projectkm.ui.gallery.DataHolder;
import com.example.projectkm.ui.gallery.Gallery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment{

    int t1Hour, t1Minute;
    DatePickerDialog.OnDateSetListener setListener;
    EditText title, date, time, memo, address;
    String a,b,c,d,e;
    ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        title=root.findViewById(R.id.myTitleEdit);
        date=root.findViewById(R.id.myDateEdit);
        time=root.findViewById(R.id.myTimeEdit);
        memo=root.findViewById(R.id.myMemoEdit);
        address=root.findViewById(R.id.myAddressEdit);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), R.style.Theme_AppCompat_DayNight, setListener, year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String d = day+"-"+month+"-"+year;
                date.setText(d);

            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String d = day+"-"+month+"-"+year;
                        date.setText(d);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1Hour=hourOfDay;
                        t1Minute=minute;
                        Calendar calendar1=Calendar.getInstance();
                        calendar1.set(0,0,0,t1Hour,t1Minute);
                        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                        time.setText(android.text.format.DateFormat.format("hh:mm aa", calendar1));

                    }
                },12,0,false
                );
                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();
            }
        });



        loadData();
        int index=-1;
        Intent intent2 = getActivity().getIntent();
        final Bundle bundle1 = intent2.getExtras();
        if(bundle1!=null) {
            title.setText(bundle1.getString("tttitle"));
            date.setText(bundle1.getString("dddate"));
            time.setText(bundle1.getString("tttime"));
            memo.setText(bundle1.getString("mmmemo"));
            address.setText(bundle1.getString("aaaddress"));
            index=intent2.getIntExtra("iindex", -1);
        }

        Button btn = (Button) root.findViewById(R.id.myButton);
        final int finalIndex = index;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MainActivity.class);
                a=title.getText().toString();
                b=date.getText().toString();
                c=time.getText().toString();
                d=memo.getText().toString();
                e=address.getText().toString();

                if(a==null || b==null || c==null || a.length()==0 || b.length()==0 || c.length()==0){
                    Toast.makeText(getActivity(),"NON HAI COMPILATO I CAMPI OBBLIGATORI",Toast.LENGTH_LONG).show();
                }else {
                    if (finalIndex != -1) {
                        arrayList.get(bundle1.getInt("iindex")).setTitle(a);
                        arrayList.get(bundle1.getInt("iindex")).setDate(b);
                        arrayList.get(bundle1.getInt("iindex")).setTime(c);
                        arrayList.get(bundle1.getInt("iindex")).setMemo(d);
                        arrayList.get(bundle1.getInt("iindex")).setAddress(e);
                        Toast.makeText(getActivity(), "MEMO MODIFICATA", Toast.LENGTH_LONG).show();
                    } else {
                        insertValues(a, b, c, d, e);
                        Toast.makeText(getActivity(), "MEMO CREATA, CONTROLLA LA TUA LISTA", Toast.LENGTH_LONG).show();
                    }

                    saveData();

                    intent.putExtra("title", a);
                    intent.putExtra("date", b);
                    intent.putExtra("time", c);
                    intent.putExtra("memo", d);
                    intent.putExtra("address", e);

                    startActivity(intent);
                }
            }
        });

        return root;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Gallery>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) {
            arrayList = DataHolder.getInstance().array;
        }
    }

    public void insertValues(String a, String b, String c, String d, String e){
        Gallery gallery = new Gallery(a,b,c,d,e);
        arrayList.add(gallery);
    }
}