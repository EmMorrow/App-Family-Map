package com.example.emilychandler.family_map.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Filter;
import com.example.emilychandler.family_map.data.Model;

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
        Switch filterSwitch = (Switch) rowView.findViewById(R.id.eventTypeSwitch);

        final String eventType = eventTypes[position];
        String event = eventType.substring(0, 1).toUpperCase() + eventType.substring(1);

        eventText.setText(event + " Events");
        description.setText("FILTER BY " + eventTypes[position] + " EVENTS");

        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter filter = Model.getInstance().getFilter();
                if (isChecked) {
                    filter.addEventType(eventType);
                    System.out.println("Does this work " + filter.getFEventTypes().size());
                    System.out.println("What is you doin " + eventTypes.length);
                    System.out.println("Size of thing in model " + Model.getInstance().getEventTypes().size());
                }
                else {
                    filter.removeEventType(eventType);
                    System.out.println("Does this work " + filter.getFEventTypes().size());
                    System.out.println("What is you doin " + eventTypes.length);
                }
            }
        });
        return rowView;
    }
}
