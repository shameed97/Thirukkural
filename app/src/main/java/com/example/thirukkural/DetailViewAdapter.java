package com.example.thirukkural;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailViewAdapter extends BaseAdapter {
    private ArrayList<athigaram> arrayList = new ArrayList<>();
    private List<athigaram> aName;
    private Context Mcontext;
    private LayoutInflater inflater;
    private String id, name;

    public DetailViewAdapter(Context context, List<athigaram> aName) {
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
        TextView textView, textView1;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ProductHolder productHolder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.list_detail, null);
            productHolder = new ProductHolder();
            productHolder.textView = row.findViewById(R.id.ano);
            productHolder.textView1 = row.findViewById(R.id.alist);

            row.setTag(productHolder);
        } else {
            productHolder = (ProductHolder) row.getTag();
        }

        final athigaram det = (athigaram) getItem(position);
        id = det.getId();
        name = det.getName();
        productHolder.textView.setText("குறள் " + id);
        productHolder.textView1.setText(name);

        //OnClickListener for Listview row Click
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idNum=det.getId();
                String nameNa=det.getName();
                String[] value={idNum,nameNa};
                Intent intent=new Intent(Mcontext,MeaningActivity.class);
                intent.putExtra("value",value);
                Mcontext.startActivity(intent);
            }
        });
        // End OnClickListener for Listview row Click

        return row;
    }


}
