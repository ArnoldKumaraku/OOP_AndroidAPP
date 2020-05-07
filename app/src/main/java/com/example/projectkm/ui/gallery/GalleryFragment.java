package com.example.projectkm.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectkm.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    TextView title, date, time, memo;
    String a,b,c,d;
    ListView listView;
    private ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    private static GalleryListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        ListView listView=(ListView) root.findViewById(R.id.list);
        adapter = new GalleryListAdapter(getContext(), R.layout.listview_activity, arrayList);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            a = bundle.getString("title");
            b = bundle.getString("date");
            c = bundle.getString("time");
            d = bundle.getString("memo");
            Gallery gallery = new Gallery(a,b,c,d);
            arrayList.add(gallery);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Vuoi cancellare questa memo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(which_item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        return root;
    }
}
