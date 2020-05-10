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
import android.telephony.SmsManager;
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


    EditText title, date, time, memo, address;
    String a,b,c,d,e,aa,bb,cc,dd,ee;
    ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        title=root.findViewById(R.id.myTitleEdit);
        date=root.findViewById(R.id.myDateEdit);
        time=root.findViewById(R.id.myTimeEdit);
        memo=root.findViewById(R.id.myMemoEdit);
        address=root.findViewById(R.id.myAddressEdit);

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

                if(finalIndex !=-1 ){
                    arrayList.get(bundle1.getInt("iindex")).setTitle(a);
                    arrayList.get(bundle1.getInt("iindex")).setDate(b);
                    arrayList.get(bundle1.getInt("iindex")).setTime(c);
                    arrayList.get(bundle1.getInt("iindex")).setMemo(d);
                    arrayList.get(bundle1.getInt("iindex")).setAddress(e);
                    Toast.makeText(getActivity(),"MEMO MODIFICATA",Toast.LENGTH_LONG).show();
                }else{
                    insertValues(a,b,c,d,e);
                    Toast.makeText(getActivity(),"MEMO CREATA, CONTROLLA LA TUA LISTA",Toast.LENGTH_LONG).show();
                }

                saveData();

                intent.putExtra("title", a);
                intent.putExtra("date", b);
                intent.putExtra("time", c);
                intent.putExtra("memo", d);
                intent.putExtra("address", e);

                startActivity(intent);
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