package org.jim.util;

public class NetworkException extends Exception {

    public NetworkException(int code, String msg) {
        super("{\"code\":" + code + ", \"msg\":\"" + msg + "\"");
    }

}
