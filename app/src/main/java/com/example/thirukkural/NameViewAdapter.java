package com.example.thirukkural;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NameViewAdapter extends BaseAdapter {

    private ArrayList<athigaram> arrayList = new ArrayList<>();
    private List<athigaram> aName;
    private Context Mcontext;
    private LayoutInflater inflater;

    public NameViewAdapter(Context context, List<athigaram> aName) {
        Mcontext = context;
        this.aName = aName;
        this.arrayList.addAll(aName);
        inflater = LayoutInflater.from(Mcontext);
    }

    @Override
    public int getCount() {
        return aName.size();
    }

    @Override
    public Object getItem(int position) {
        return aName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ProductHolder {
        TextView textView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ProductHolder productHolder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.list_names, null);
            productHolder = new ProductHolder();
            productHolder.textView = row.findViewById(R.id.alist);

            row.setTag(productHolder);
        } else {
            productHolder = (ProductHolder) row.getTag();
        }

        final athigaram det = (athigaram) getItem(position);
        final String id = det.getId();
        final String name = det.getName();
        productHolder.textView.setText(id + "." + name);

        //OnClickListener for Listview row Click
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameList = det.getName();
                String idList=det.getId();
                String[] value={idList,nameList};
                Toast.makeText(Mcontext, nameList, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mcontext, DetailActivity.class);
                intent.putExtra("value", value);
                Mcontext.startActivity(intent);
            }
        });
        // End OnClickListener for Listview row Click

        return row;
    }


}

