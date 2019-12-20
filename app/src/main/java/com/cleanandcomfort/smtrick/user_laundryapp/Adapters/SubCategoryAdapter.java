package com.cleanandcomfort.smtrick.user_laundryapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.cleanandcomfort.smtrick.user_laundryapp.R;
import com.cleanandcomfort.smtrick.user_laundryapp.Utility.Utility;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {



    private ArrayList<String> leedModelArrayList;
    private ArrayList<String> checkedArrayList;
    private Context context;

    public SubCategoryAdapter(ArrayList<String> catalogList) {
        leedModelArrayList = catalogList;
    }

    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcataloglist, parent, false);
        SubCategoryAdapter.ViewHolder viewHolder = new SubCategoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.ViewHolder holder, final int position) {

        try {
            final String leedModel = getModel(position);
            if (!Utility.isEmptyOrNull(leedModel))
                holder.item.setText(leedModel);
            else
                holder.item.setText("NA");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getModel(int position) {
        return leedModelArrayList.get(getItemCount() - 1 - position);
    }

    @Override
    public int getItemCount() {
        return leedModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox item;

        public ViewHolder(View itemView) {
            super(itemView);

            item = (CheckBox) itemView.findViewById(R.id.subcatalog_name);

        }
    }
}
