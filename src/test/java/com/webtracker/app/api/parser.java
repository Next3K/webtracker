package com.webtracker.app.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.*;

public class parser {
    public static JSONObject makeObject(String path) throws IOException, JSONException {
        File f = new File(path);
        JSONObject object = new JSONObject();
        if (f.exists()) {
            InputStream is = new FileInputStream(f);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            object = new JSONObject(jsonTxt);
        }
        return object;
    }
}
