package com.smartloan.smtrick.user_laundryapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Update_user_profile_activity extends AppCompatActivity implements View.OnClickListener {

    EditText inputUsername, inputMobile, inputAddress, inputPinCode, inputPassword, spinnerRole;
    Button btnUpdate;
    private AppSharedPreference appSharedPreference;
    private ProgressDialogClass progressDialog;
    ImageView addImages;

    private static final int RESULT_LOAD_IMAGE = 1;
    private List<Uri> fileDoneList;
    private UploadListAdapter uploadListAdapter;
    RecyclerView imagesRecyclerView;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    LeedRepository leedRepository;
    ArrayList<String> imageList;
    ArrayList<User> userlist;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appSharedPreference = new AppSharedPreference(this);
        progressDialog = new ProgressDialogClass(this);
        leedRepository = new LeedRepositoryImpl();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        storageReference = FirebaseStorage.getInstance().getReference();

        fileDoneList = new ArrayList<>();
        imageList = new ArrayList<>();
        userlist = new ArrayList<>();

        inputUsername = (EditText) findViewById(R.id.username);
        inputMobile = (EditText) findViewById(R.id.mobilenumber);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPinCode = (EditText) findViewById(R.id.pincode);
        inputPassword = (EditText) findViewById(R.id.password);
        spinnerRole = (EditText) findViewById(R.id.spinnerselectusertype);

        addImages = (ImageView) findViewById(R.id.addcommission);

        imagesRecyclerView = (RecyclerView) findViewById(R.id.cropedimageRecyclerView);

        btnUpdate = (Button) findViewById(R.id.update_button);

        getUserDetails();
        addImages.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    private void getUserDetails() {
        String userId = appSharedPreference.getUserid();
        leedRepository.readUserById(userId, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    userlist = (ArrayList<User>) object;
                    inputUsername.setText(userlist.get(0).getName());
                    inputMobile.setText(userlist.get(0).getNumber());
                    inputAddress.setText(userlist.get(0).getAddress());
                    inputPinCode.setText(userlist.get(0).getPincode());
                    inputPassword.setText(userlist.get(0).getPassword());
                    spinnerRole.setText(userlist.get(0).getRole());
                    imageList.addAll(userlist.get(0).getImageList());

                } else {
//                    Utility.showTimedSnackBar(Update_user_profile_activity.this, etpassword, getMessage(R.string.login_fail_try_again));
                }
                if (progressDialog != null)
                    progressDialog.dismissDialog();
            }
            @Override
            public void onError(Object object) {
                if (progressDialog != null)
                    progressDialog.dismissDialog();
                Toast.makeText(Update_user_profile_activity.this, "Error Fetching user", Toast.LENGTH_SHORT).show();            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == addImages) {
            pickImage();
        } else if (v == btnUpdate) {
            uploadFile();
        }
    }

    public void pickImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();

                for (int i = 0; i < totalItemsSelected; i++) {

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    fileDoneList.add(data.getClipData().getItemAt(i).getUri());

                    //String fileName = getFileName(fileUri);
                }
                uploadListAdapter = new UploadListAdapter(Update_user_profile_activity.this, fileDoneList);
                imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Update_user_profile_activity.this, LinearLayoutManager.HORIZONTAL, true));
                imagesRecyclerView.setHasFixedSize(true);
                imagesRecyclerView.setAdapter(uploadListAdapter);

            } else if (data.getData() != null) {

                Uri image = data.getData();
                fileDoneList.add(image);

                uploadListAdapter = new UploadListAdapter(Update_user_profile_activity.this, fileDoneList);
                imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Update_user_profile_activity.this, LinearLayoutManager.HORIZONTAL, true));
                imagesRecyclerView.setHasFixedSize(true);
                imagesRecyclerView.setAdapter(uploadListAdapter);

                Toast.makeText(getApplicationContext(), "Selected Single File", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void uploadFile() {
        //checking if file is available
        if (fileDoneList != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(Update_user_profile_activity.this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            for (int i = 0; i < fileDoneList.size(); i++) {

                //getting the storage reference
                final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(fileDoneList.get(i)));

                //adding the file to reference
                sRef.putFile(fileDoneList.get(i))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying success toast
//                                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadurl = uri.toString();
                                        imageList.add(downloadurl);

                                        if (imageList != null && imageList.size() != 0) {
                                            User upload = new User();
                                            upload.setName(inputUsername.getText().toString());
                                            upload.setNumber(inputMobile.getText().toString());
                                            upload.setAddress(inputAddress.getText().toString());
                                            upload.setPincode(inputPinCode.getText().toString());
                                            upload.setPassword(inputPassword.getText().toString());
                                            upload.setRole(spinnerRole.getText().toString());
                                            upload.setUserid(appSharedPreference.getUserid());
                                            upload.setGeneratedId(appSharedPreference.getGeneratedId());
                                            upload.setImageList(imageList);

                                            updateLeed(upload.getGeneratedId(), upload.getLeedStatusMap());
                                            progressDialog.dismiss();
                                        }

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying the upload progress
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        });

            }

        } else {
            //display an error if no file is selected
            Toast.makeText(this, "Please Select a file", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLeed(String leedId, Map leedsMap) {

        leedRepository.updateUserProfile(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {

                Toast.makeText(Update_user_profile_activity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_user_profile_activity.this, MainActivity_User.class);
                startActivity(intent);
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
