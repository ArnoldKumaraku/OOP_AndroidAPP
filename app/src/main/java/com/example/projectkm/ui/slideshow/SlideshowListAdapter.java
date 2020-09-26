package com.example.projectkm.ui.slideshow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projectkm.R;

import java.util.ArrayList;


public class SlideshowListAdapter extends ArrayAdapter<Slideshow> {

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView title;
        TextView date;
    }

    public SlideshowListAdapter(Context context, int resource, ArrayList<Slideshow> objects){
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String title=getItem(position).getTitle();
        String date=getItem(position).getDate();
        final View result;
        ViewHolder holder;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.title=convertView.findViewById(R.id.myTitleText2);
            holder.date=convertView.findViewById(R.id.myDateText2);
            result=convertView;
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
            result=convertView;
        }

        holder.title.setText(title);
        holder.date.setText(date);

        return result;
    }
}
