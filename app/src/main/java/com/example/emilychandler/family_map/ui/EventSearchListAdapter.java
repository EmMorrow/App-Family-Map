package com.example.emilychandler.family_map.ui;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilychandler on 12/12/17.
 */

public class EventSearchListAdapter extends BaseAdapter implements Filterable {
    private final Activity context;
    private String type;
    private List<Event> eventList;
    private List<Event> filteredList;
    private EventSearchFilter filter;

    public EventSearchListAdapter(Activity context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.filteredList = new ArrayList<>();
    }


    /**
     * Get size of user list
     *
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredList.size();
    }

    /**
     * Get specific item from user list
     *
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    /**
     * Get user list item id
     *
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Create list row view
     *
     * @param position index
     * @param view     current list item view
     * @param parent   parent
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Event event = (Event) getItem(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.person_list_item,null,true);

        TextView mainText = (TextView) rowView.findViewById(R.id.first_line);
        TextView description = (TextView) rowView.findViewById(R.id.second_line);
        IconTextView image = (IconTextView) rowView.findViewById(R.id.icon);

        image.setText("{fa-map-marker}");

        mainText.setText(event.getEventType() + ": " + event.getCity() + ", " +
                event.getCountry() + " (" + event.getYear() + ")");

        Person person = Model.getInstance().getPeople().get(event.getPerson());
        description.setText(person.getFirstName() + " " + person.getLastName());
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onPersonClick();
//            }
//        });
        return rowView;
    }

    private void onPersonClick() {
        Toast.makeText(context, "event", Toast.LENGTH_SHORT);
    }
    /**
     * Get custom filter
     *
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new EventSearchFilter();
        }

        return filter;
    }

    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    static class ViewHolder {
        TextView iconText;
        TextView name;
    }

    private class EventSearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Event> tempList = new ArrayList<>();

                // search content in friend list
                for (Event event : eventList) {
                    if (event.getCity().toLowerCase().contains(constraint)) {
                        tempList.add(event);
                    }
                    else if (event.getCountry().toLowerCase().contains(constraint)) {
                        tempList.add(event);
                    }
                    else if (event.getEventType().toLowerCase().contains(constraint)) {
                        tempList.add(event);
                    }
                    else if (event.getYear().contains(constraint)) {
                        tempList.add(event);
                    }
 //                    if (event.getFirstName().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        tempList.add(event);
//                    }
//                    else if (event.getLastName().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        tempList.add(event);
//                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = eventList.size();
                filterResults.values = eventList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Event>) results.values;
            notifyDataSetChanged();
        }


    }


}
