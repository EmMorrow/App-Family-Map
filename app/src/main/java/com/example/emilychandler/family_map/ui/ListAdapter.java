package com.example.emilychandler.family_map.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emilychandler.family_map.R;

/**
 * Created by emilychandler on 12/7/17.
 */

public class ListAdapter extends ArrayAdapter {
    private final Activity context;
    private final String[] eventTypes;

    public ListAdapter(Activity context, String[] eventTypes) {
        super(context, R.layout.filter_list_item, eventTypes);
        this.context = context;
        this.eventTypes = eventTypes;
    }

    public View getView(int position, View view, ViewGroup Parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.filter_list_item,null,true);

        TextView eventText = (TextView) rowView.findViewById(R.id.event_text);
        TextView description = (TextView) rowView.findViewById(R.id.description);

        eventText.setText(eventTypes[position] + " Events");
        description.setText("FILTER BY " + eventTypes[position] + " EVENTS");
        return rowView;
    }
}
