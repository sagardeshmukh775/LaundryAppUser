package com.smartloan.smtrick.laundryapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Service_Providers_Adapter extends RecyclerView.Adapter<Service_Providers_Adapter.ViewHolder> {

    private Context context;
    private List<User> uploads;
    AppSharedPreference appSharedPreference;
    LeedRepository leedRepository;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;
    EditText edtDateTime;

    public Service_Providers_Adapter(Context context, List<User> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    public Service_Providers_Adapter(List<User> mUsers) {
        this.uploads = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceproviders_layout, parent, false);
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
        holder.textViewPinCode.setText(user.getPincode());
        holder.textViewId.setText(user.getUserid());

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(holder.userCard.getContext());
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
                dialog1.setContentView(R.layout.dialog_select_date);

                edtDateTime = (EditText) dialog1.findViewById(R.id.txtdatetime);
                Button Add = (Button) dialog1.findViewById(R.id.btnsendrequest);
                Button cancle = (Button) dialog1.findViewById(R.id.btncancle);

                setDateTimeField();
                edtDateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatePickerDialog.show();

                    }
                });

                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Requests requests = new Requests();
                        requests.setServiceProviderId(user.getUserid());
                        requests.setUserId(appSharedPreference.getUserid());
                        requests.setUserAddress(appSharedPreference.getAddress());
                        requests.setUserMobile(appSharedPreference.getNumber());
                        requests.setUserPinCode(appSharedPreference.getPincode());
                        requests.setDate(edtDateTime.getText().toString());
                        requests.setStatus(Constant.STATUS_GENERATED);
                        requests.setRequestId(Constant.REQUESTS_TABLE_REF.push().getKey());
                        leedRepository.sendRequest(requests, new CallBack() {
                            @Override
                            public void onSuccess(Object object) {
                                Toast.makeText(holder.userCard.getContext(), "Submitted", Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                            }

                            @Override
                            public void onError(Object object) {

                            }
                        });
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }

            private void setDateTimeField() {
                Calendar newCalendar = Calendar.getInstance();
                mDatePickerDialog = new DatePickerDialog(holder.userCard.getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                        final Date startDate = newDate.getTime();
                        fdate = sd.format(startDate);

                        timePicker();
                    }

                    private void timePicker() {
                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(holder.userCard.getContext(),
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        mHour = hourOfDay;
                                        mMinute = minute;

                                        edtDateTime.setText(fdate + " " + hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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
        public Button Request;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            textViewPinCode = (TextView) itemView.findViewById(R.id.user_pincodevalue);
            textViewId = (TextView) itemView.findViewById(R.id.user_idvalue);
            userCard = (CardView) itemView.findViewById(R.id.card_userid);
            Request = (Button) itemView.findViewById(R.id.request);

        }
    }

    public void reload(ArrayList<User> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
