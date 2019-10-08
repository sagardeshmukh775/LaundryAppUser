package com.smartloan.smtrick.laundryapp;

import java.util.Map;

public interface LeedRepository {


    void deleteLeed(final String leedId, final CallBack callback);

    void updateLeed(final String leedId, final Map leedsMap, final CallBack callBack);

    void updateUser(final String userid, final Map leedsMap, final CallBack callBack);


    void readRequestUser(final String userId, final CallBack callback);

    void readActiveUser(final String userId, final CallBack callback);

    void updateRelative(final String leedId, final Map leedsMap, final CallBack callBack);

    void updateUserProfile(final String leedId, final Map leedsMap, final CallBack callBack);

    void readUserById(final String userId, final CallBack callback);

    void readServicesByUserId(final String userId, final CallBack callback);
}
