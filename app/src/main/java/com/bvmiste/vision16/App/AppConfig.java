package com.bvmiste.vision16.App;

/**
 * Created by Arvindiyer on 08-09-2015.
 */
public class AppConfig {
    // Server user login url
    public  static final  String URL_LOGIN = "URL To BE INCLUDED";

    // Server user register url
    public static String URL_REGISTER = "URL To BE INCLUDED";

    // Server forgot register url
    public static String URL_Forgot = "URL To BE INCLUDED";

    public static String[] titles;
    public static String[] msgs;

    public static final String GET_URL = "URL To BE INCLUDED";
    public static final String GET_URL1 = "URL To BE INCLUDED";

    public static final String TAG_IMAGE_URL = "msg";
    public static final String TAG_IMAGE_NAME = "ename";
    public static final String TAG_JSON_ARRAY="feed";

    public AppConfig(int i){
        titles = new String[i];
        msgs = new String[i];
    }

}
