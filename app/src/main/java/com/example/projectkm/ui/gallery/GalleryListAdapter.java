package com.example.projectkm.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projectkm.R;

import java.util.ArrayList;

public class GalleryListAdapter extends ArrayAdapter<Gallery> {

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView title;
        TextView date;
        TextView time;
        TextView memo;
    }

    public GalleryListAdapter(Context context, int resource, ArrayList<Gallery> objects){
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String title=getItem(position).getTitle();
        String date=getItem(position).getDate();
        String time=getItem(position).getTime();
        String memo=getItem(position).getMemo();
        final View result;
        ViewHolder holder;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.title=convertView.findViewById(R.id.myTitleText);
            holder.date=convertView.findViewById(R.id.myDateText);
            holder.time=convertView.findViewById(R.id.myTimeText);
            holder.memo=convertView.findViewById(R.id.myMemotext);
            result=convertView;
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
            result=convertView;
        }

        holder.title.setText(title);
        holder.date.setText(date);
        holder.time.setText(time);
        holder.memo.setText(memo);

        return result;
    }
}
