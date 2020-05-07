package com.example.projectkm.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectkm.MainActivity;
import com.example.projectkm.R;
import com.example.projectkm.ui.gallery.DataHolder;
import com.example.projectkm.ui.gallery.Gallery;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    EditText title, date, time, memo;
    String a,b,c,d;
    private ArrayList<Gallery> arrayList = DataHolder.getInstance().array;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        title=root.findViewById(R.id.myTitleEdit);
        date=root.findViewById(R.id.myDateEdit);
        time=root.findViewById(R.id.myTimeEdit);
        memo=root.findViewById(R.id.myMemoEdit);
        Button btn = (Button) root.findViewById(R.id.myButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                a=title.getText().toString();
                b=date.getText().toString();
                c=time.getText().toString();
                d=memo.getText().toString();
                Gallery gallery = new Gallery(a,b,c,d);
                arrayList.add(gallery);

                intent.putExtra("title", a);
                intent.putExtra("date", b);
                intent.putExtra("time", c);
                intent.putExtra("memo", d);

                startActivity(intent);

                Toast.makeText(getActivity(),"LA TUA MEMO E' STATA CREATA, CONTROLLA LA TUA LISTA",Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}