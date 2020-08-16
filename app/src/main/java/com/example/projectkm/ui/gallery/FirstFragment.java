package com.example.projectkm.ui.gallery;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.projectkm.MainActivity;
import com.example.projectkm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class FirstFragment extends Fragment {


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_first, container, false);
    }
    private Notification notification;
    private NotificationManagerCompat notificationManager;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView title = getActivity().findViewById(R.id.myTitleText1);
        final TextView date = getActivity().findViewById(R.id.myDateText1);
        final TextView time = getActivity().findViewById(R.id.myTimeText1);
        final TextView memo = getActivity().findViewById(R.id.myMemoText1);
        final TextView address = getActivity().findViewById(R.id.myAddressText1);

        int index=0;

        final Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            title.setText(bundle.getString("ttitle"));
            date.setText(bundle.getString("ddate"));
            time.setText(bundle.getString("ttime"));
            memo.setText(bundle.getString("mmemo"));
            address.setText(bundle.getString("aaddress"));
            index = bundle.getInt("index");
        }

        final int finalIndex = index;

        Button btn = getActivity().findViewById(R.id.myChangeButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                intent1.putExtra("tttitle", title.getText().toString());
                intent1.putExtra("dddate", date.getText().toString());
                intent1.putExtra("tttime", time.getText().toString());
                intent1.putExtra("mmmemo", memo.getText().toString());
                intent1.putExtra("aaaddress", address.getText().toString());
                intent1.putExtra("iindex", finalIndex);
                startActivity(intent1);

            }
        });

        Button btn2 = getActivity().findViewById(R.id.myfindAddress);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String address1 = address.getText().toString();
                bundle.putString("addressmap", address1);
                SecondFragment secondFragment = new SecondFragment();
                secondFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, secondFragment).commit();
            }
        });

        Button btn3 = getActivity().findViewById(R.id.myrememberButton);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*

                 */
                String str1, str2, str3;
                String tttt = time.getText().toString();
                char primo = tttt.charAt(0), secondo = tttt.charAt(1), terzo = tttt.charAt(3), quarto = tttt.charAt(4), quinto = tttt.charAt(6), sesto = tttt.charAt(7);
                StringBuilder sb1 = new StringBuilder();
                sb1.append(primo);
                sb1.append(secondo);
                str1 = sb1.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(terzo);
                sb2.append(quarto);
                str2 = sb2.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(quinto);
                sb3.append(sesto);
                str3 = sb3.toString();
                if(str3.equals("PM")){
                    if(!str1.equals("12")) {
                        int s1 = Integer.parseInt(str1) + 12;
                        str1 = String.valueOf(s1);
                    }
                }else{
                    if(str1.equals("12")){
                        str1="00";
                    }
                }

                String qqqq= date.getText().toString();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 30);
                cal.set(Calendar.MONTH, 8);
                cal.set(Calendar.YEAR, 2020);
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(str1));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(str2));
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, title.getText().toString());
                intent.putExtra(AlarmClock.EXTRA_DAYS, cal.get(Calendar.DAY_OF_MONTH));
                startActivity(intent);
            }
        });
    }
}

