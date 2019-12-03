package com.smartloan.smtrick.user_laundryapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartloan.smtrick.user_laundryapp.Listeners.OnRecycleClickListener;
import com.smartloan.smtrick.user_laundryapp.Models.User;
import com.smartloan.smtrick.user_laundryapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Providers_Adapter extends RecyclerView.Adapter<Providers_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<User> uploads;
    OnRecycleClickListener recycleClickListener;
    private Dialog dialog;

    public Providers_Adapter(Context context, ArrayList<User> uploads, OnRecycleClickListener onRecycleClickListener, Dialog dialog) {
        this.uploads = uploads;
        this.context = context;
        this.recycleClickListener = onRecycleClickListener;
        this.dialog = dialog;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceproviders_layout_new, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = uploads.get(position);

        holder.textViewName.setText(user.getName());
        holder.textViewMobile.setText(user.getNumber());
        holder.textViewAddress.setText(user.getAddress());
        if (user.getProfileImage() != null) {
            Picasso.with(holder.userCard.getContext())
                    .load(user.getProfileImage())
                    .placeholder(R.drawable.loading)
                    .into(holder.Provider_Image);
        }
//        else {
//            Picasso.with(holder.userCard.getContext())
//                    .load(R.drawable.user)
//                    .placeholder(R.drawable.user)
//                    .into(holder.Provider_Image);
//        }


        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleClickListener.onRecycleClick(user);
                dialog.dismiss();
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
        public ImageView Provider_Image;

        public CardView userCard;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            Provider_Image = (ImageView) itemView.findViewById(R.id.memberImage);

            userCard = (CardView) itemView.findViewById(R.id.card_user);

        }
    }

    public void reload(ArrayList<User> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
