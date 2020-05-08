package com.example.projectkm.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectkm.MainActivity;
import com.example.projectkm.R;
import com.example.projectkm.ui.gallery.DataHolder;
import com.example.projectkm.ui.gallery.Gallery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.invoke.ConstantCallSite;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    EditText title, date, time, memo;
    String a,b,c,d;
    ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        title=root.findViewById(R.id.myTitleEdit);
        date=root.findViewById(R.id.myDateEdit);
        time=root.findViewById(R.id.myTimeEdit);
        memo=root.findViewById(R.id.myMemoEdit);
        loadData();
        Button btn = (Button) root.findViewById(R.id.myButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                a=title.getText().toString();
                b=date.getText().toString();
                c=time.getText().toString();
                d=memo.getText().toString();

                insertValues(a,b,c,d);
                saveData();

                intent.putExtra("title", a);
                intent.putExtra("date", b);
                intent.putExtra("time", c);
                intent.putExtra("memo", d);

                startActivity(intent);

                Toast.makeText(getActivity(),"MEMO CREATA, CONTROLLA LA TUA LISTA",Toast.LENGTH_LONG).show();
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

    public void insertValues(String a, String b, String c, String d){
        Gallery gallery = new Gallery(a,b,c,d);
        arrayList.add(gallery);
    }
}