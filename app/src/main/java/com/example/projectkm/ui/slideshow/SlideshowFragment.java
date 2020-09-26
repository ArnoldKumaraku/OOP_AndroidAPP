package com.example.projectkm.ui.slideshow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projectkm.MainActivity;
import com.example.projectkm.R;
import com.example.projectkm.ui.gallery.DataHolder;
import com.example.projectkm.ui.gallery.Gallery;
import com.example.projectkm.ui.gallery.Memo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private ArrayList<Slideshow> arrayList2 = DataHolderS.getInstance().array;
    private static SlideshowListAdapter adapter;
    String a,b,c,d,e;
    ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        loadDataS();
        loadData();
        final ListView listView=(ListView) root.findViewById(R.id.list2);
        adapter = new SlideshowListAdapter(getContext(), R.layout.listview_activity2, arrayList2);

        final Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Cosa vuoi farne di questa memo?")
                        .setPositiveButton("Ripristina", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent2 = new Intent(getContext(), MainActivity.class);
                                a = arrayList2.get(which_item).getTitle();
                                b=arrayList2.get(which_item).getDate();
                                c=arrayList2.get(which_item).getTime();
                                d=arrayList2.get(which_item).getMemo();
                                e=arrayList2.get(which_item).getAddress();
                                intent2.putExtra("_title", a);
                                intent2.putExtra("_date", b);
                                intent2.putExtra("_time", c);
                                intent2.putExtra("_memo", d);
                                intent2.putExtra("_address", e);
                                intent2.putExtra("_index", which_item);
                                insertValues(a, b, c, d, e);
                                arrayList2.remove(which_item);
                                saveDataS();
                                saveData();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "MEMO RIPRISTINATA, CONTROLLA LA TUA LISTA", Toast.LENGTH_LONG).show();
                                startActivity(intent2);
                            }
                        })
                        .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "MEMO ELIMINATA DEFINITIVAMENTE", Toast.LENGTH_LONG).show();
                                arrayList2.remove(which_item);
                                saveDataS();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setIcon(R.drawable.puntoesclamativo)
                        .show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getContext(), Memo.class);
                intent1.putExtra("ttitle", arrayList2.get(position).getTitle());
                intent1.putExtra("ddate", arrayList2.get(position).getDate());
                intent1.putExtra("ttime", arrayList2.get(position).getTime());
                intent1.putExtra("mmemo", arrayList2.get(position).getMemo());
                intent1.putExtra("aaddress", arrayList2.get(position).getAddress());
                intent1.putExtra("index", position);
                startActivity(intent1);
            }
        });

        return root;
    }

    private void saveDataS() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList2);
        editor.putString("garbage list", json);
        editor.apply();
    }

    public void loadDataS() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("garbage list", null);
        Type type = new TypeToken<ArrayList<Slideshow>>() {}.getType();
        arrayList2 = gson.fromJson(json, type);

        if (arrayList2 == null) {
            arrayList2 = DataHolderS.getInstance().array;
        }
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

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    public void insertValues(String a, String b, String c, String d, String e){
        Gallery gallery = new Gallery(a,b,c,d,e);
        arrayList.add(gallery);
    }
}
