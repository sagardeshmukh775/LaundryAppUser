package com.cleanandcomfort.smtrick.user_laundryapp.Repository.Impl;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.cleanandcomfort.smtrick.user_laundryapp.CallBack.CallBack;
import com.cleanandcomfort.smtrick.user_laundryapp.Constants.Constant;
import com.cleanandcomfort.smtrick.user_laundryapp.Exception.ExceptionUtil;
import com.cleanandcomfort.smtrick.user_laundryapp.Models.TimeSlot;
import com.cleanandcomfort.smtrick.user_laundryapp.Models.Types;
import com.cleanandcomfort.smtrick.user_laundryapp.Models.User;
import com.cleanandcomfort.smtrick.user_laundryapp.Models.Wash;
import com.cleanandcomfort.smtrick.user_laundryapp.Repository.FirebaseTemplateRepository;
import com.cleanandcomfort.smtrick.user_laundryapp.Repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cleanandcomfort.smtrick.user_laundryapp.Constants.Constant.EMAIL_POSTFIX;


public class UserRepositoryImpl extends FirebaseTemplateRepository implements UserRepository {
    private Activity _activity;

    public UserRepositoryImpl(final Activity activity) {
        _activity = activity;
    }

    public UserRepositoryImpl() {

    }
    /********************************************** Firebase Call ***************************************************/
    /**
     * @param mobileNumber
     * @param callBack
     */
    @Override
    public void readUserByMobileNumber(final String mobileNumber, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("mobileNumber").equalTo(mobileNumber);
        fireBaseReadData(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        User user = firstChild.getValue(User.class);
                        callBack.onSuccess(user);
                    } else {
                        callBack.onError(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    /**
     * @param mobileNumber
     * @param callBack
     */
    @Override
    public void notifyReadUserByMobileNumber(final String mobileNumber, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("mobileNumber").equalTo(mobileNumber);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        User user = firstChild.getValue(User.class);
                        callBack.onSuccess(user);
                    } else {
                        callBack.onError(null);
                    }
                } else callBack.onError(null);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    /**
     * @param userId
     * @param callback
     */
    @Override
    public void readUser(final String userId, final CallBack callback) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                    try {
                        final DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        callback.onSuccess(firstChild.getValue(User.class));
                    } catch (Exception e) {
                        callback.onError(e);
                        ExceptionUtil.logException(e);
                    }
                } else
                    callback.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    /**
     * @param callback
     */
    @Override
    public void readLoggedInUser(final CallBack callback) {
        final FirebaseUser firebaseUser = Constant.AUTH.getCurrentUser();
        if (firebaseUser == null) {
            return;
        }
        final String userId = firebaseUser.getUid();
        readUser(userId, callback);
    }

    /**
     * @param callback
     */
    @Override
    public void createUser(final User userModel, final CallBack callback) {
        Constant.AUTH.createUserWithEmailAndPassword(userModel.getNumber() + EMAIL_POSTFIX, userModel.getPassword())
                .addOnCompleteListener(_activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser firebaseUser = Constant.AUTH.getCurrentUser();
                            if (firebaseUser == null) {
                                callback.onError(task);
                                return;
                            }
                            userModel.setUserid(firebaseUser.getUid());
                            //userModel.setPassword(null);
                            // FireBase Create User
                            Map userMap = userModel.getLeedStatusMap();
                            // userMap.put("imeiNumber", REGISTRATION_CONSTANT);
                            createUserData(userModel, callback);
                        } else {
                            callback.onError(task);
                        }
                    }
                });
    }

    @Override
    public void readUserByStatus(String status, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("status").equalTo(status);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User invoice = suggestionSnapshot.getValue(User.class);
                            invoiceArrayList.add(invoice);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void createUserData(User user, final CallBack callBack) {
        DatabaseReference databaseReference = Constant.USER_TABLE_REF.child(user.getUserid());
        fireBaseCreate(databaseReference, user, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void signIn(final String mobileNumber, final String password, final CallBack callback) {
        Constant.AUTH.signInWithEmailAndPassword(mobileNumber + EMAIL_POSTFIX, password)
                .addOnCompleteListener(_activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(task);
                        } else {
                            callback.onError(task);
                        }
                    }
                });
    }

    /**
     * @param newPassword
     * @param callback
     */
    @Override
    public void changePassword(String newPassword, final CallBack callback) {
        final FirebaseUser firebaseUser = Constant.AUTH.getCurrentUser();
        if (firebaseUser == null) {
            callback.onError(null);
            return;
        }
        firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(task);
                } else {
                    callback.onError(task);
                }
            }
        });
    }

    /**
     * @param callback
     */
    @Override
    public void updateUser(final User userModel, final CallBack callback) {
        final Map<String, Object> userProfileMap = new HashMap<>();
        userProfileMap.put(userModel.getGeneratedId(), userModel);
        final DatabaseReference databaseReference = Constant.USER_TABLE_REF;
        fireBaseUpdateChildren(databaseReference, userProfileMap, callback);
    }

    /**
     * @param userId
     * @param callback
     * @return
     */
    @Override
    public void deleteUser(final String userId, final CallBack callback) {
        DatabaseReference databaseReference = Constant.USER_TABLE_REF.child(userId);
        fireBaseDelete(databaseReference, callback);
    }

    @Override
    public void updateUser(String userId, Map userMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.USER_TABLE_REF.child(userId);
        fireBaseUpdateChildren(databaseReference, userMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                callBack.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }


    @Override
    public void readUserByUserId(String userId, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("userid").equalTo(userId);;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                callBack.onSuccess(dataSnapshot.getValue(User.class));
                            } else {
                                callBack.onError(null);
                            }
                        } catch (Exception e) {
                            ExceptionUtil.logException(e);
                        }
                    } else
                        callBack.onError(null);
                } else
                    callBack.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError);
            }
        });
    }

    @Override
    public void setDataChangeListnerOnUserLogin(String regId, final CallBack callback) {
        Query query = Constant.USER_TABLE_REF.child(regId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        User user = dataSnapshot.getValue(User.class);
                        callback.onSuccess(user);
                    } else {
                        callback.onSuccess(null);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }



    @Override
    public void readAllusers(final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User invoice = suggestionSnapshot.getValue(User.class);
                            invoiceArrayList.add(invoice);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readUserByName(final String username, final CallBack callback) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("userName").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                    try {
                        final DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        callback.onSuccess(firstChild.getValue(User.class));
                    } catch (Exception e) {
                        callback.onError(e);
                        ExceptionUtil.logException(e);
                    }
                } else
                    callback.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    @Override
    public void readUserByRole(String role, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("role").equalTo(role);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User invoice = suggestionSnapshot.getValue(User.class);
                            invoiceArrayList.add(invoice);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readUserByAgentId(final String agentid, final CallBack callback) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("agentId").equalTo(agentid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                    try {
                        final DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        callback.onSuccess(firstChild.getValue(User.class));
                    } catch (Exception e) {
                        callback.onError(e);
                        ExceptionUtil.logException(e);
                    }
                } else
                    callback.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    @Override
    public void readServiceProviders(final CallBack callBack) {
        final Query query = Constant.SERVICE_PROVIDERS_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User invoice = suggestionSnapshot.getValue(User.class);
                            invoiceArrayList.add(invoice);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readWash(final CallBack callBack) {
        final Query query = Constant.WASH_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Wash> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Wash wash = suggestionSnapshot.getValue(Wash.class);
                            invoiceArrayList.add(wash);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readTimeSlot(final CallBack callBack) {
        final Query query = Constant.TIMESLOT_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<TimeSlot> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            TimeSlot wash = suggestionSnapshot.getValue(TimeSlot.class);
                            invoiceArrayList.add(wash);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readTypes(final CallBack callBack) {
        final Query query = Constant.TYPES_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Types> invoiceArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Types type = suggestionSnapshot.getValue(Types.class);
                            invoiceArrayList.add(type);
                        }
                        callBack.onSuccess(invoiceArrayList);
                    } else {
                        callBack.onSuccess(null);
                    }
                }
            }

            @Override
            public void onError(Object object) {
                callBack.onError(object);
            }
        });
    }

    @Override
    public void readServiceProviderById(final String id, final CallBack callback) {
        final Query query = Constant.SERVICE_PROVIDERS_TABLE_REF.orderByChild("userid").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                    try {
                        final DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        callback.onSuccess(firstChild.getValue(User.class));
                    } catch (Exception e) {
                        callback.onError(e);
                        ExceptionUtil.logException(e);
                    }
                } else
                    callback.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    public void readServiceProviderByName(final String username, final CallBack callback) {
        final Query query = Constant.SERVICE_PROVIDERS_TABLE_REF.orderByChild("name").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null && dataSnapshot.hasChildren()) {
                    try {
                        final DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                        callback.onSuccess(firstChild.getValue(User.class));
                    } catch (Exception e) {
                        callback.onError(e);
                        ExceptionUtil.logException(e);
                    }
                } else
                    callback.onError(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }
}