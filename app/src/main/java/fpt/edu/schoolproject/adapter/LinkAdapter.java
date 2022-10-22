package fpt.edu.schoolproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.edu.schoolproject.R;

public class LinkAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> mlistLink;

    public LinkAdapter(Context context, ArrayList<String> mlistLink) {
        this.context = context;
        this.mlistLink = mlistLink;
    }

    @Override
    public int getCount() {
        return mlistLink.size();
    }

    @Override
    public Object getItem(int i) {
        return mlistLink.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        ViewOfItem viewOfItem = null;
        if(view == null){
            view = layoutInflater.inflate(R.layout.layout_item_link_news, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvLink = view.findViewById(R.id.tvLink);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.tvLink.setText(mlistLink.get(i));
        return view;
    }

    public static class ViewOfItem{
        TextView tvLink;
    }
}
