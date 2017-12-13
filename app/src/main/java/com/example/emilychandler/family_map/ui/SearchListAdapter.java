package com.example.emilychandler.family_map.ui;

import android.app.Activity;
import android.content.Context;
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
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.User;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * Created by emilychandler on 12/10/17.
 */

public class SearchListAdapter extends BaseAdapter implements Filterable {
    private final Activity context;
    private String type;
    private List<Person> personList;
    private List<Person> filteredList;
    private SearchFilter filter;

    public SearchListAdapter(Activity context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
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
        Person person = (Person) getItem(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.person_list_item,null,true);

        TextView mainText = (TextView) rowView.findViewById(R.id.first_line);
        TextView description = (TextView) rowView.findViewById(R.id.second_line);
        IconTextView image = (IconTextView) rowView.findViewById(R.id.icon);

        if (person.getGender().equals("f")) {
            image.setText("{fa-female}");
            image.setTextColor(Color.MAGENTA);
        }
        else if (person.getGender().equals("m")) {
            image.setText("{fa-male}");
            image.setTextColor(Color.BLUE);
        }

        mainText.setText(person.getFirstName() + " " + person.getLastName());
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onPersonClick();
//            }
//        });
        return rowView;
    }


    private void onPersonClick() {
        Toast.makeText(context, "person", Toast.LENGTH_SHORT);
    }
    /**
     * Get custom filter
     *
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SearchFilter();
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

    private class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Person> tempList = new ArrayList<>();

                // search content in friend list
                for (Person person : personList) {
                    if (person.getFirstName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(person);
                    }
                    else if (person.getLastName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(person);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = personList.size();
                filterResults.values = personList;
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
            filteredList = (ArrayList<Person>) results.values;
            notifyDataSetChanged();
        }


    }
}
