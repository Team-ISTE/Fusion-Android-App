package com.bvmiste.vision16.Helper;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by arvind on 20/1/16.
 */
@ParseClassName("Login")
public class ParseLogin extends ParseObject {
    public ParseLogin() {
        // A default constructor is required.
    }
    public String getno() {
        return getString("name");
    }

    public void setno(String name) {
        put("name", name);
    }
}

