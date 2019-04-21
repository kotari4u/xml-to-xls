package org.xml.xls;

import org.json.JSONArray;

public class JsonDataToXls {
    public static void main(String args[]){
        createXls("https://openpaymentsdata.cms.gov/resource/2qdu-6j3t.json?recipient_state=MA",
                "GeneralPaymentsData.xlsx",
                "GeneralPaymentsData");

        createXls("https://openpaymentsdata.cms.gov/resource/h42t-6mkd.json?recipient_state=MA",
                "OwnerShipPaymentData.xlsx",
                "OwnerShipPaymentData");

        createXls("https://openpaymentsdata.cms.gov/resource/rp9t-4vbw.json?recipient_state=MA",
                "ResearchPaymentData.xlsx",
                "ResearchPaymentData");
    }

    private static void createXls(String requestUrl, String fileName, String sheetName) {
        try {
            System.out.println("Reading response from : " + requestUrl);
            JSONArray xmlJSONObj = new URLResponseReader().readJson(requestUrl);
            System.out.println("Completed reading response. Generating file : " + fileName);

            JsonToXlsWriter xlsWriter = new JsonToXlsWriter(fileName, sheetName);
            xlsWriter.writeToXls(xmlJSONObj);

            System.out.println("Xls file "+ fileName +" generated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
