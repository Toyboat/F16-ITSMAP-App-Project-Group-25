package com.example.victor.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Jacob on 16/05/2016.
 */
public class SearchResultsListViewAdapter extends BaseAdapter{
    private List<Item> items;
    private Context context;
    private ListView container;
    private Item item;

    public SearchResultsListViewAdapter(ListView container, Context context, List<Item> items)
    {
        this.context = context;
        this.items = items;
        this.container = container;

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
        int layout_id = R.layout.search_result_listitem;

        if (position == this.container.getSelectedItemPosition())
        {
            layout_id = R.layout.search_result_listitem_selected;
        }

        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(layout_id, null);
        }

        item = this.items.get(position);
        if(item!=null){
            switch(layout_id)
            {
                case R.layout.search_result_listitem:
                    SRListItem.populateView(convertView,item);
                    break;

                case R.layout.search_result_listitem_selected:
                    SRListItemSelected.populateView(convertView,item);
                    break;

                default:
                    break;
            }

        }
        return convertView;
    }
}
