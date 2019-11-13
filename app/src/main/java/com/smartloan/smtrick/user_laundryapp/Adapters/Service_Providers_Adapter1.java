package com.smartloan.smtrick.user_laundryapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Models.User;

import java.util.ArrayList;
import java.util.List;

public class Service_Providers_Adapter1 extends RecyclerView.Adapter<Service_Providers_Adapter1.ViewHolder> {

    private Context context;
    private List<User> uploads;

    public Service_Providers_Adapter1(Context context, List<User> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    public Service_Providers_Adapter1(List<User> mUsers) {
        this.uploads = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceproviders_layout1, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = uploads.get(position);

        holder.textViewName.setText(user.getName());
        holder.textViewMobile.setText(user.getNumber());
        holder.textViewAddress.setText(user.getAddress());
        holder.textViewPinCode.setText(user.getPincode());
        holder.textViewId.setText(user.getUserid());

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewMobile;
        public TextView textViewAddress;
        public TextView textViewPinCode;
        public TextView textViewId;
        public CardView userCard;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            textViewPinCode = (TextView) itemView.findViewById(R.id.user_pincodevalue);
            textViewId = (TextView) itemView.findViewById(R.id.user_idvalue);
            userCard = (CardView) itemView.findViewById(R.id.card_userid);

        }
    }

    public void reload(ArrayList<User> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
