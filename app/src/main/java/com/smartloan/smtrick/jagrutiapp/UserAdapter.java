package com.smartloan.smtrick.jagrutiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<Users> invoiceArrayList;
    private Context context;

    public UserAdapter(Context context, ArrayList<Users> data) {
        this.invoiceArrayList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist, parent, false);
        UserAdapter.ViewHolder viewHolder = new UserAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Users users = invoiceArrayList.get(position);

        holder.Dname.setText(users.getName());
        holder.Designation.setText(users.getStorename());

        holder.usercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(holder.usercard.getContext(),UserDetailsActivity.class);
//                intent.putExtra("report",users);
//                holder.usercard.getContext().startActivity(intent);

                final Dialog dialog = new Dialog(holder.usercard.getRootView().getContext());
//                View v = LayoutInflater.from(holder.usercard.getContext())
//                        .inflate(R.layout.activity_user_details, null, false);
                dialog.setContentView(R.layout.activity_user_details);

                dialog.setTitle("Title...");
                TextView name, email, mobile, store;
                Button dismiss;
                name = (TextView) dialog.findViewById(R.id.user_name);
                store = (TextView) dialog.findViewById(R.id.user_store1);
                email = (TextView) dialog.findViewById(R.id.user_email1);
                mobile = (TextView) dialog.findViewById(R.id.user_mobile1);
                dismiss = (Button) dialog.findViewById(R.id.dialogButtonaccept);

                name.setText(users.getName());
                store.setText(users.getStorename());
                email.setText(users.getEmail());
                mobile.setText(users.getContact());
                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return invoiceArrayList.size();
    }

    public void reload(ArrayList<Users> arrayList) {
        invoiceArrayList.clear();
        invoiceArrayList.addAll(arrayList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Dname, Designation;
        CardView usercard;

        public ViewHolder(View itemView) {
            super(itemView);

            Dname = (TextView) itemView.findViewById(R.id.name);
            Designation = (TextView) itemView.findViewById(R.id.user_mobile);
            usercard = (CardView) itemView.findViewById(R.id.usercard);
        }
    }


//    @Override
//    public UserViewHolder onCreateViewHolder(ViewGroup parent,
//                                                int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.layout_images, parent, false);
//        MyAdapter.ViewHolder viewHolder = new UsersAdapter(v).ViewHolder(v);
//        return viewHolder;
//    }
//
//    private Users getModel(int position) {
//        return (invoiceArrayList.get(invoiceArrayList.size() - 1 - position));
//    }
//
//    @Override
//    public void onBindViewHolder(final UserViewHolder holder, final int listPosition) {
//        try {
//            Users user = getModel(listPosition);
//            holder.txtcnamevalue.setText(user.getUserName());
//            holder.txtbankvalue.setText(user.getMobileNumber());
//           // holder.invoiceAdapterLayoutBinding.txtbankvalue.setText(invoice.getUserName());
//          //  holder.invoiceAdapterLayoutBinding.txtStatusValue.setText(invoice.getUserName());
//          //  holder.invoiceAdapterLayoutBinding.txtcommisionvalue.setText(invoice.getUserName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return invoiceArrayList.size();
//    }
//
//    public void reload(ArrayList<Users> arrayList) {
//        invoiceArrayList.clear();
//        invoiceArrayList.addAll(arrayList);
//        notifyDataSetChanged();
//    }
}