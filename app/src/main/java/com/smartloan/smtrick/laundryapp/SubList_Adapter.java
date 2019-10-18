package com.smartloan.smtrick.laundryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SubList_Adapter extends RecyclerView.Adapter<SubList_Adapter.ViewHolder> {

    private Context context;
    private List<String> uploads;
    private List<String> servicesList;
    int i=0;


    public SubList_Adapter(Context context, List<String> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sublist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String upload = uploads.get(position);

        holder.textViewName.setText(upload);
        holder.count.setText(String.valueOf(i));

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0){
                    Toast.makeText(context, "Please increase", Toast.LENGTH_SHORT).show();
                }else {
                    i = i-1;
                    holder.count.setText(String.valueOf(i));
                }
            }
        });
        holder.pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i+1;
                holder.count.setText(String.valueOf(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView pluse,minus;
        public EditText count;



        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            pluse = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            count = (EditText) itemView.findViewById(R.id.count);

        }
    }
}
