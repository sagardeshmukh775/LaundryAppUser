package com.washtag.smtrick.user_laundryapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.washtag.smtrick.user_laundryapp.Listeners.OnImageClickListener;
import com.washtag.smtrick.user_laundryapp.R;

import java.util.List;

public class SubList_Adapter extends RecyclerView.Adapter<SubList_Adapter.ViewHolder> {

    private Context context;
    private List<String> uploads;
    private OnImageClickListener onImageClickListener;
    SparseBooleanArray checkedItems = new SparseBooleanArray();


    public SubList_Adapter(Context context, List<String> uploads, OnImageClickListener onImageClickListener) {
        this.uploads = uploads;
        this.context = context;
        this.onImageClickListener = onImageClickListener;
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
        final int[] i = {0};

        holder.textViewName.setText(upload);
        holder.count.setText(String.valueOf(i[0]));


        // Remember to change access modifier of checkBox in your ViewHolder
        // Get the state from checkedItems. If no previous value, it will return false.
        holder.textViewName.setChecked(checkedItems.get(position));

        // This is a sample. Do not use create listener here.
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the check state
                checkedItems.put(position, true);
            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i[0] == 0) {
                    Toast.makeText(context, "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i[0]--;
                    holder.count.setText(String.valueOf(i[0]));
                }
            }
        });
        holder.pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i[0] = i[0] + 1;
                holder.count.setText(String.valueOf(i[0]));
            }
        });

        holder.textViewName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String item = holder.textViewName.getText() + " - " + holder.count.getText().toString();
                    onImageClickListener.onImageClick(item, true);

                } else if (!isChecked) {
                    String item1 = holder.textViewName.getText() + " - " + holder.count.getText().toString();
                    onImageClickListener.onImageClick(item1, false);
//                    Toast.makeText(holder.count.getContext(), String.valueOf(servicesList.size()), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
            return uploads.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox textViewName;
        public ImageView pluse, minus;
        public EditText count;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (CheckBox) itemView.findViewById(R.id.textViewName);
            pluse = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            count = (EditText) itemView.findViewById(R.id.count);

        }
    }


}
