package com.example.projectkm.ui.gallery;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.projectkm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class Memo extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    String a,b,c,d,e;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        Window w = this.getWindow();
        w.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            a=bundle.getString("ttitle");
            b=bundle.getString("ddate");
            c=bundle.getString("ttime");
            d=bundle.getString("mmemo");
            e=bundle.getString("aaddress");
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Titolo: " + a + "\n" + "Date: " +  b + "\n" + "Time: " + c + "\n" + "Memo:\n" + d + "\n" + "Indirizzo: " +  e);
                startActivity(intent.createChooser(intent, "Share"));
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
