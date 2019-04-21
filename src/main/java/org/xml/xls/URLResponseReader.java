package org.xml.xls;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResponseReader {

    public JSONObject readXmlToJson(String requestUrl){
        return XML.toJSONObject(read(requestUrl));
    }

    public JSONArray readJson(String requestUrl){
        String response = read(requestUrl);
        return new JSONArray(response);
    }

    public String read(String requestUrl){
        String response = null;
        try(InputStream inputStream = new URL(requestUrl).openStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            IOUtils.copy(inputStream, outputStream);

            response = new String(outputStream.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
