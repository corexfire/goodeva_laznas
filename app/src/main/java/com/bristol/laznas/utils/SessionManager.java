package com.bristol.laznas.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref_login;
    SharedPreferences pref_user;
    SharedPreferences pref_pass;
    SharedPreferences pref_uid;
    SharedPreferences pref_name;
    SharedPreferences pref_roles_id;

    SharedPreferences.Editor editor_login;
    SharedPreferences.Editor editor_user;
    SharedPreferences.Editor editor_pass;
    SharedPreferences.Editor editor_uid;
    SharedPreferences.Editor editor_name;
    SharedPreferences.Editor editor_roles_id;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_LOGIN = "lz_login";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String PREF_USER = "lz_user";
    private static final String KEY_USER = "user";
    private static final String PREF_PASS = "lz_pass";
    private static final String KEY_PAS = "pass";
    private static final String PREF_UID = "lz_uid";
    private static final String KEY_UID = "uid";
    private static final String PREF_NAME = "lz_name";
    private static final String KEY_NAME = "name";
    private static final String PREF_ROLES_ID = "lz_roles_id";
    private static final String KEY_ROLES_ID = "roles_id";

    public SessionManager(Context context) {
        this._context = context;
        pref_login = _context.getSharedPreferences(PREF_LOGIN, PRIVATE_MODE);
        pref_user = _context.getSharedPreferences(PREF_USER, PRIVATE_MODE);
        pref_pass = _context.getSharedPreferences(PREF_PASS, PRIVATE_MODE);
        pref_uid = _context.getSharedPreferences(PREF_UID, PRIVATE_MODE);
        pref_name = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref_roles_id = _context.getSharedPreferences(PREF_ROLES_ID, PRIVATE_MODE);


        editor_login = pref_login.edit();
        editor_user = pref_user.edit();
        editor_pass = pref_pass.edit();
        editor_uid = pref_uid.edit();
        editor_name = pref_name.edit();
        editor_roles_id = pref_roles_id.edit();
    }

    public void setUser(String user){
        editor_user.putString(KEY_USER, user);
        editor_user.commit();
    }

    public String getUser(){
        return pref_user.getString(KEY_USER, "");
    }

    public void clearUser(){
        editor_user.clear();
        editor_user.commit();
    }

    public void setRolesId(String rolesId){
        editor_roles_id.putString(KEY_ROLES_ID, rolesId);
        editor_roles_id.commit();
    }

    public String getRolesId(){
        return pref_roles_id.getString(KEY_ROLES_ID, "");
    }

    public void clearRolesId(){
        editor_roles_id.clear();
        editor_roles_id.commit();
    }

    public void setUID(String uid){
        editor_uid.putString(KEY_UID, uid);
        editor_uid.commit();
    }

    public String getUID(){
        return pref_uid.getString(KEY_UID, "");
    }

    public void clearUID(){
        editor_uid.clear();
        editor_uid.commit();
    }

    public void setName(String name){
        editor_name.putString(KEY_NAME, name);
        editor_name.commit();
    }

    public String getName(){
        return pref_name.getString(KEY_NAME, "");
    }

    public void clearName(){
        editor_name.clear();
        editor_name.commit();
    }


    public void setPass(String pass){
        editor_user.putString(KEY_PAS, pass);
        editor_user.commit();
    }

    public String getPass(){
        return pref_user.getString(KEY_PAS, "");
    }

    public void clearPass(){
        editor_pass.clear();
        editor_pass.commit();
    }


    public void setLogin ( boolean isLoggedIn){
        editor_login.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor_login.commit();
    }


    public boolean isLoggedIn () {
        return pref_login.getBoolean(KEY_IS_LOGGED_IN, false);
    }

}

