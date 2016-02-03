package com.bvmiste.vision16.Helper;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by arvind on 15/1/16.
 */
@ParseClassName("Feedback")
public class ParseRegistration extends ParseObject {
    public ParseRegistration() {
        // A default constructor is required.
    }
    public String getname() {
        return getString("name");
    }

    public void setname(String name) {
        put("name", name);
    }
    public String getclg() {
        return getString("clg");
    }

    public void setclg(String clg) {
        put("clg", clg);
    }
    public String getevent() {
        return getString("event");
    }

    public void setevent(String title) {
        put("event", title);
    }
}
