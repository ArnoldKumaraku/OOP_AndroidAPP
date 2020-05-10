package com.example.projectkm.ui.gallery;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.projectkm.MainActivity;
import com.example.projectkm.NotificationReceiver;
import com.example.projectkm.R;

public class FirstFragment extends Fragment  {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_first, container, false);
    }
    private NotificationManagerCompat notificationManager;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView title = getActivity().findViewById(R.id.myTitleText1);
        final TextView date = getActivity().findViewById(R.id.myDateText1);
        final TextView time = getActivity().findViewById(R.id.myTimeText1);
        final TextView memo = getActivity().findViewById(R.id.myMemoText1);
        final TextView address = getActivity().findViewById(R.id.myAddressText1);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"indirizzo cliccato",Toast.LENGTH_LONG).show();
            }
        });

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
        notificationManager = NotificationManagerCompat.from(getActivity());
        Button btn3 = getActivity().findViewById(R.id.myrememberButton);
        btn3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ServiceCast")
            @Override
            public void onClick(View v) {
                RemoteViews collapsedView = new RemoteViews(getActivity().getPackageName(),
                        R.layout.notification_collapsed);
                RemoteViews expandedView = new RemoteViews(getActivity().getPackageName(),
                        R.layout.notification_expanded);

                Intent clickIntent = new Intent(getActivity(), NotificationReceiver.class);
                PendingIntent contentIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                long timeAtButtonClick = System.currentTimeMillis();
                long tenSecondsInMillis = 1000*10;
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick+tenSecondsInMillis, contentIntent);
            }
        });
    }
}
