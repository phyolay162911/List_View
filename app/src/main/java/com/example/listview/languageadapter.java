package com.example.listview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listview.model.languageinfo;

import java.util.ArrayList;

public class languageadapter extends BaseAdapter {

    ArrayList<languageinfo> infoArrayList;
    Context context;

    public languageadapter(Context context,ArrayList<languageinfo> infolist)
    {
            this.context = context;
            this.infoArrayList= infolist;
    }
    @Override
    public int getCount() {
        return infoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item,null);

        TextView txtlangname = v.findViewById(R.id.langname);
        TextView txtlangdate = v.findViewById(R.id.langdate);
        TextView txtlangdesc = v.findViewById(R.id.langdes);

        txtlangname.setText(infoArrayList.get(i).getName().toString());
        txtlangdate.setText(infoArrayList.get(i).getReleasedate().toString());
        txtlangdesc.setText(infoArrayList.get(i).getDescription().toString());

        //Styling Date color
        txtlangdate.setTextColor(Color.RED);
        txtlangname.setTextColor(Color.parseColor("#fff333"));
        txtlangdesc.setTextColor(Color.BLACK);

        return v;


    }
}
