package com.smartloan.smtrick.user_laundryapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartloan.smtrick.user_laundryapp.Models.User;
import com.smartloan.smtrick.user_laundryapp.Preferences.AppSharedPreference;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Repository.Impl.LeedRepositoryImpl;
import com.smartloan.smtrick.user_laundryapp.Repository.LeedRepository;

import java.util.ArrayList;
import java.util.List;

public class Service_Providers_Adapter_new extends RecyclerView.Adapter<Service_Providers_Adapter_new.ViewHolder> {

    private Context context;
    private List<User> uploads;
    AppSharedPreference appSharedPreference;
    LeedRepository leedRepository;


    public Service_Providers_Adapter_new(Context context, List<User> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    public Service_Providers_Adapter_new(List<User> mUsers) {
        this.uploads = mUsers;
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
        appSharedPreference = new AppSharedPreference(holder.userCard.getContext());
        leedRepository = new LeedRepositoryImpl();

        holder.textViewName.setText(user.getName());
        holder.textViewMobile.setText(user.getNumber());
        holder.textViewAddress.setText(user.getAddress());

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewMobile;
        public TextView textViewAddress;

        public CardView userCard;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            userCard = (CardView) itemView.findViewById(R.id.card_userid);

        }
    }

    public void reload(ArrayList<User> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
