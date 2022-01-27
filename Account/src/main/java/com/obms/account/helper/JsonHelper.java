package com.obms.account.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();

    public static <T> T convertMQMessage(final byte[] message, final Class<T> tClass) {
        final String rawObject = gson.fromJson(new String(message), String.class);
        return gson.fromJson(rawObject, tClass);
    }
}
