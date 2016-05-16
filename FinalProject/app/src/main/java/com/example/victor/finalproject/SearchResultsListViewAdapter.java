package com.example.victor.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Jacob on 16/05/2016.
 */
public class SearchResultsListViewAdapter extends BaseAdapter{
    private List<Item> items;
    private Context context;

    public SearchResultsListViewAdapter(Context context, List<Item> items)
    {
        this.context = context;
        this.items = items;

    }

    @Override
    public int getCount() {
        if (items != null)
        {
            return items.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (items != null && position < items.size())
        {
            return items.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.search_result_listitem, null);
        }
        return null;
    }
}
