package com.laaficionmanda.android.util;

import android.util.Log;

public class LogIt {

    public static void e(Object src, Throwable t, Object... message) {
        StringBuilder builder = new StringBuilder();
        if (t != null) {
            builder.append(t.getMessage()).append(": ");
        } else {
            builder.append("ERROR: ");
        }
        for (Object o : message) {
            builder.append(o).append(", ");
        }

        Class c = src instanceof Class ? (Class) src : src.getClass();
        Log.e(c.getName(), builder.toString(), t);
    }

    public static void d(Object src, Object... message) {
        StringBuilder builder = new StringBuilder();
        for (Object o : message) {
            builder.append(o).append(", ");
        }
        Class c = src instanceof Class ? (Class) src : src.getClass();
        Log.d(c.getName(), builder.toString());
    }
}
