package org.xml.xls;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResponseReader {

    public JSONObject read(String requestUrl){
        JSONObject xmlJSONObj = null;
        try(InputStream inputStream = new URL(requestUrl).openStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            IOUtils.copy(inputStream, outputStream);

            String response = new String(outputStream.toByteArray(), "UTF-8");
            xmlJSONObj = XML.toJSONObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return xmlJSONObj;
    }

}
