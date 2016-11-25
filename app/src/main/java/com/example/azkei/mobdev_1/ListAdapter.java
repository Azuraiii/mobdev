package com.example.azkei.mobdev_1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by azkei on 21/11/2016.
 */

public class ListAdapter extends CursorAdapter {

    public  ListAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row, parent,false);
    }
    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView TEXTitem = (TextView) view.findViewById(R.id.TEXTVIEW_item);
        TextView TEXTcost = (TextView) view.findViewById(R.id.TEXTVIEW_cost);
        TextView TEXTamount = (TextView) view.findViewById(R.id.TEXTVIEW_amount);
        //Extract properties from cursor
        String item = cursor.getString(cursor.getColumnIndexOrThrow("item"));
        String cost = cursor.getString(cursor.getColumnIndexOrThrow("cost"));
        String amount = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
        //Populate fields with extracted properties
        TEXTitem.setText(item);
        TEXTcost.setText(cost);
        TEXTamount.setText(amount);
    }
}
