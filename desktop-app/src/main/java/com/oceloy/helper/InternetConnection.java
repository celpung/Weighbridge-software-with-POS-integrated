package com.oceloy.helper;

import com.oceloy.Env;

public class InternetConnection {
    public static String connection_status;

    public static void connect() {

        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping " + Env.host + "");
            int x = process.waitFor();
            if (x == 0) {
                connection_status = "connected";
            } else {
                connection_status = "no connections";
            }
        } catch (Exception e) {
            connection_status = "no connections";
            e.printStackTrace();
        }
    }
}
