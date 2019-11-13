package com.smartloan.smtrick.user_laundryapp.Repository;

import com.smartloan.smtrick.user_laundryapp.CallBack.CallBack;
import com.smartloan.smtrick.user_laundryapp.Models.User;

import java.util.Map;

public interface UserRepository {

    void signIn(final String mobileNumber, final String password, final CallBack callback);

    void readLoggedInUser(final CallBack callback);

    void readUser(final String userId, final CallBack callback);

    void readUserByMobileNumber(final String mobileNumber, final CallBack callBack);

    void notifyReadUserByMobileNumber(final String mobileNumber, final CallBack callBack);

    void changePassword(final String newPassword, final CallBack callback);

    void createUser(final User userModel, final CallBack callback);

    void createUserData(final User userModel, final CallBack callback);

    void updateUser(final User userModel, final CallBack callback);

    void deleteUser(final String userId, final CallBack callback);

    void updateUser(final String regId, final Map userMap, final CallBack callback);

    void readUserByUserId(final String regId, final CallBack callBack);

    void readUserByStatus(final String status, final CallBack callBack);

    void setDataChangeListnerOnUserLogin(final String regId, final CallBack callback);

    void readAllusers(final CallBack callBack);

    void readUserByName(final String userName, final CallBack callback);

    void readUserByRole(final String role, final CallBack callBack);

    void readUserByAgentId(final String agentid, final CallBack callBack);

    void readServiceProviders(final CallBack callback);

    void readWash(final CallBack callBack);

    void readTimeSlot(final CallBack callBack);

    void readTypes(final CallBack callBack);
}