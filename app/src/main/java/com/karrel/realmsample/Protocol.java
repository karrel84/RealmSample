package com.karrel.realmsample;

import io.realm.RealmObject;

/**
 * Created by Rell on 2017. 8. 30..
 */

public class Protocol extends RealmObject {

    public String name;
    public String hexCode;

    @Override
    public String toString() {
        return "Protocol{" +
                "hexCode='" + hexCode + '\'' +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
