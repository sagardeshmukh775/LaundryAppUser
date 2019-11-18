package com.smartloan.smtrick.user_laundryapp.Repository.Impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.smartloan.smtrick.user_laundryapp.CallBack.CallBack;
import com.smartloan.smtrick.user_laundryapp.Constants.Constant;
import com.smartloan.smtrick.user_laundryapp.Constants.Constants;
import com.smartloan.smtrick.user_laundryapp.Repository.FirebaseTemplateRepository;
import com.smartloan.smtrick.user_laundryapp.Repository.LeedRepository;
import com.smartloan.smtrick.user_laundryapp.Models.Requests;
import com.smartloan.smtrick.user_laundryapp.Models.ServiceProviderServices;
import com.smartloan.smtrick.user_laundryapp.Models.SubCategory;
import com.smartloan.smtrick.user_laundryapp.Models.User;
import com.smartloan.smtrick.user_laundryapp.Models.UserServices;
import com.smartloan.smtrick.user_laundryapp.Models.Users;

import java.util.ArrayList;
import java.util.Map;

public class LeedRepositoryImpl extends FirebaseTemplateRepository implements LeedRepository {
//    @Override
//    public void readAllLeeds(final CallBack callBack) {
//        final Query query = Constants.LEEDS_TABLE_REF;
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    @Override
//    public void readLeedsByUserIdReport(final Context context, String userId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("createdBy").equalTo(userId);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
//                        int colorCount = 0;
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            LeedsModel leedsModel = suggestionSnapshot.getValue(LeedsModel.class);
//                            if (colorCount % 5 == 0)
//                                colorCount = 0;
//                           // setColor(context, leedsModel, colorCount);
//                            colorCount++;
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//
//    @Override
//    public void createLeed(LeedsModel leedsModel, final CallBack callBack) {
//        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedsModel.getLeedId());
//        fireBaseCreate(databaseReference, leedsModel, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                callBack.onSuccess(object);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//
//
//    @Override
//    public void deleteLeed(String leedId, CallBack callback) {
//        DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseDelete(databaseReference, callback);
//    }
//
//    @Override
//    public void updateLeed(String leedId, Map leedMap, final CallBack callBack) {
//        final DatabaseReference databaseReference = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseUpdateChildren(databaseReference, leedMap, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                callBack.onSuccess(object);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    public void readLeedsByStatus(String status, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.orderByChild("status").equalTo(status);
//        fireBaseNotifyChange(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        ArrayList<Invoice> leedsModelArrayList = new ArrayList<>();
//                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
//                            Invoice leedsModel = suggestionSnapshot.getValue(Invoice.class);
//                            leedsModelArrayList.add(leedsModel);
//                        }
//                        callBack.onSuccess(leedsModelArrayList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//    @Override
//    public void readLeedByLeedId(String leedId, final CallBack callBack) {
//        final Query query = Constant.LEEDS_TABLE_REF.child(leedId);
//        fireBaseReadData(query, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    DataSnapshot dataSnapshot = (DataSnapshot) object;
//                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
//                        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
//                        LeedsModel leedsModel = child.getValue(LeedsModel.class);
//                        callBack.onSuccess(leedsModel);
//                    } else
//                        callBack.onSuccess(null);
//                } else
//                    callBack.onSuccess(null);
//            }
//
//            @Override
//            public void onError(Object object) {
//                callBack.onError(object);
//            }
//        });
//    }
//
//
//    @Override
//    public void updateLeedDocuments(String leedId, Map leedMap, CallBack callback) {
//
//    }
//
//    @Override
//    public void updateLeedHistory(String leedId, Map leedMap, CallBack callback) {
//
//    }

    @Override
    public void deleteLeed(String leedId, CallBack callback) {
        DatabaseReference databaseReference = Constants.LEEDS_TABLE_REF.child(leedId);
        fireBaseDelete(databaseReference, callback);
    }

    @Override
    public void updateLeed(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.MEMBERS_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
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
    public void updateRelative(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.RELATIVES_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
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
    public void updateUser(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.USER_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
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
    public void readRequestUser(String name, final CallBack callBack) {
        final Query query = Constants.USER_TABLE_REF.orderByChild("status").equalTo(name);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Users> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Users leedsModel = suggestionSnapshot.getValue(Users.class);

                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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
    public void readActiveUser(String name, final CallBack callBack) {
        final Query query = Constants.USER_TABLE_REF.orderByChild("status").equalTo(name);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Users> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Users leedsModel = suggestionSnapshot.getValue(Users.class);

                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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
    public void updateUserProfile(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constants.USERS_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
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
    public void readUserById(String Id, final CallBack callBack) {
        final Query query = Constant.USER_TABLE_REF.orderByChild("userid").equalTo(Id);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User leedsModel = suggestionSnapshot.getValue(User.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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
    public void readServicesByUserId(String Id, final CallBack callBack) {
        final Query query = Constant.SERVICES_TABLE_REF.orderByChild("userid").equalTo(Id);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<User> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            User leedsModel = suggestionSnapshot.getValue(User.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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
    public void readServicesByName(String ServicesName, final CallBack callBack) {
        final Query query = Constant.SUBCATEGORY_TABLE_REF.orderByChild("maincatitem").equalTo(ServicesName);
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<SubCategory> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            SubCategory leedsModel = suggestionSnapshot.getValue(SubCategory.class);
                            leedsModelArrayList.add(leedsModel);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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
    public void createUserServices(UserServices leedsModel, final CallBack callBack) {
        DatabaseReference databaseReference = Constant.USER_SERVICES_TABLE_REF.child(leedsModel.getServiceId());
        fireBaseCreate(databaseReference, leedsModel, new CallBack() {
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
    public void sendRequest(Requests request, final CallBack callBack) {
        DatabaseReference databaseReference = Constant.REQUESTS_TABLE_REF.child(request.getRequestId());
        fireBaseCreate(databaseReference, request, new CallBack() {
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
    public void updateRequest(String leedId, Map leedsMap, final CallBack callBack) {
        final DatabaseReference databaseReference = Constant.REQUESTS_TABLE_REF.child(leedId);
        fireBaseUpdateChildren(databaseReference, leedsMap, new CallBack() {
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
    public void readAllServices(final CallBack callBack) {
        final Query query = Constant.SERVICE_PROVIDERS_SERVICES_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<ServiceProviderServices> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            ServiceProviderServices service = suggestionSnapshot.getValue(ServiceProviderServices.class);
                            leedsModelArrayList.add(service);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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
    public void readAllRequests(final CallBack callBack) {
        final Query query = Constant.REQUESTS_TABLE_REF;
        fireBaseNotifyChange(query, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DataSnapshot dataSnapshot = (DataSnapshot) object;
                    if (dataSnapshot.getValue() != null & dataSnapshot.hasChildren()) {
                        ArrayList<Requests> leedsModelArrayList = new ArrayList<>();
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            Requests service = suggestionSnapshot.getValue(Requests.class);
                            leedsModelArrayList.add(service);
                        }
                        callBack.onSuccess(leedsModelArrayList);
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

}
