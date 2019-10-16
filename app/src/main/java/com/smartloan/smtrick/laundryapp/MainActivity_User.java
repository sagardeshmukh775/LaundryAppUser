package com.smartloan.smtrick.laundryapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class MainActivity_User extends AppCompatActivity
        implements OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private NavigationView navigationView;
    private Fragment selectedFragement;
    private FirebaseAuth firebaseAuth;

    private String uid;
    private FirebaseUser Fuser;
    private DatabaseReference databaseReference;

    private TextView userEmail, userRole;
    private TextView username;
    String Language, Userid, acctemail, acctname;
    private Menu top_menu;
    Users user;
    LeedRepository leedRepository;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private AppSharedPreference appSharedPreference;
    ImageView ProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isNetworkAvailable()) {

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        leedRepository = new LeedRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        user = new Users();
        // NOTE : Just remove the fab button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Boolean per = isStoragePermissionGranted();
        if (per) {
            //   Toast.makeText(this, "Storage Premission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Storage Premission Required", Toast.LENGTH_SHORT).show();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.memberregistration);
        View headerview = navigationView.getHeaderView(0);
        username = (TextView) headerview.findViewById(R.id.username);
        userEmail = (TextView) headerview.findViewById(R.id.useremail);
        userRole = (TextView) headerview.findViewById(R.id.userrole);

        ProfileImage = (ImageView) headerview.findViewById(R.id.image_view_profile);

        getCurrentuserdetails();

        //setMenuTitles();

        //NOTE:  Open fragment1 initially.
        selectedFragement = new Fragment_View_Service_Providers();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, selectedFragement);
        ft.commit();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        ProfileImage.setOnClickListener(this);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Permission Denied", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    private void getCurrentuserdetails() {

        try {

            username.setText(appSharedPreference.getName());
            userEmail.setText(appSharedPreference.getNumber());
            userRole.setText(appSharedPreference.getRole());

        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //NOTE: creating fragment object
        Fragment fragment = null;
        if (id == R.id.serviceproviders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_View_Service_Providers()).commit();
//            Intent intent = new Intent(MainActivity_User.this, dummyActivity.class);
//            startActivity(intent);

        } else if (id == R.id.requests) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_View_Service_Providers_Requests()).commit();

        } else if (id == R.id.user_requests) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_Relation_Form()).commit();

        } else if (id == R.id.users) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Admin_Users_Tab_Fragment()).commit();

        } else if (id == R.id.addcategory) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_Add_Categories()).commit();

        }else if (id == R.id.services) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_Add_Services()).commit();

        }
        else if (id == R.id.logout) {

//             clearDataWithSignOut();
            appSharedPreference.clear();
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity_User.this, LoginActivity.class));

        }

        //NOTE: Fragment changing code
        selectedFragement = fragment;
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(String title) {

        // NOTE:  Code to replace the toolbar title based current visible fragment
        getSupportActionBar().setTitle(title);
    }


    @Override
    public void changeFragement(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        if (v == ProfileImage) {
            Intent intent = new Intent(MainActivity_User.this, Update_user_profile_activity.class);
            startActivity(intent);
        }
    }
}
