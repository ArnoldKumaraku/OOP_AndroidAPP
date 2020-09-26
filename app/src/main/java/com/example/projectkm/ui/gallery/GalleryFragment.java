package com.example.projectkm.ui.gallery;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projectkm.MainActivity;
import com.example.projectkm.R;
import com.example.projectkm.ui.slideshow.DataHolderS;
import com.example.projectkm.ui.slideshow.Slideshow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GalleryFragment extends Fragment {

    ArrayList<Slideshow> arrayList2 = DataHolderS.getInstance().array;
    private ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    private static GalleryListAdapter adapter;
    String a,b,c,d,e;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        loadData();
        loadDataS();
        ListView listView=(ListView) root.findViewById(R.id.list);
        adapter = new GalleryListAdapter(getContext(), R.layout.listview_activity, arrayList);

        final Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        final RadioButton titleRadio = root.findViewById(R.id.myTitleRadio);
        final RadioButton dateRadio = root.findViewById(R.id.myDateRadio);
        Button btn = root.findViewById(R.id.mySortButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleRadio.isChecked()){
                    Collections.sort(arrayList, new Comparator<Gallery>() {
                        @Override
                        public int compare(Gallery o1, Gallery o2) {
                            return o1.getTitle().compareTo(o2.getTitle());
                        }
                    });
                }

                if(dateRadio.isChecked()){
                    Collections.sort(arrayList, new Comparator<Gallery>() {
                        @Override
                        public int compare(Gallery o1, Gallery o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Vuoi cancellare questa memo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent2 = new Intent(getContext(), MainActivity.class);
                                a = arrayList.get(which_item).getTitle();
                                b=arrayList.get(which_item).getDate();
                                c=arrayList.get(which_item).getTime();
                                d=arrayList.get(which_item).getMemo();
                                e=arrayList.get(which_item).getAddress();
                                intent2.putExtra("_title", a);
                                intent2.putExtra("_date", b);
                                intent2.putExtra("_time", c);
                                intent2.putExtra("_memo", d);
                                intent2.putExtra("_address", e);
                                intent2.putExtra("_index", which_item);
                                insertValues(a, b, c, d, e);
                                arrayList.remove(which_item);
                                saveData();
                                saveDataS();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "MEMO ELIMINATA, LA TROVI NEL CESTINO", Toast.LENGTH_LONG).show();
                                startActivity(intent2);
                            }
                        })
                        .setNegativeButton("No", null)
                        .setIcon(R.drawable.puntoesclamativo)
                        .show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getContext(), Memo.class);
                intent1.putExtra("ttitle", arrayList.get(position).getTitle());
                intent1.putExtra("ddate", arrayList.get(position).getDate());
                intent1.putExtra("ttime", arrayList.get(position).getTime());
                intent1.putExtra("mmemo", arrayList.get(position).getMemo());
                intent1.putExtra("aaddress", arrayList.get(position).getAddress());
                intent1.putExtra("index", position);
                startActivity(intent1);
            }
        });

        return root;
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

    public void insertValues(String a, String b, String c, String d, String e){
        Slideshow slideshow = new Slideshow(a,b,c,d,e);
        arrayList2.add(slideshow);
    }
}