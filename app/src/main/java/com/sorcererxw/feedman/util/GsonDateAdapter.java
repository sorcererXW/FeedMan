package com.sorcererxw.feedman.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class GsonDateAdapter extends TypeAdapter<Date> {
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(String.valueOf(value.getTime()));
        }
    }

    public Date read(JsonReader in) throws IOException {
        if (in.peek() != JsonToken.NULL) {
            return new Date(in.nextLong());
        }
        in.nextNull();
        return null;
    }
}
