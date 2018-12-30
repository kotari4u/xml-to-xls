package org.xml.xls;

import org.json.JSONObject;

public class XmlToXls {
    public static void main(String args[]){
        try {
            String requestUrl = "https://staging.connects.catalyst.harvard.edu/API/Profiles/Public/ProfilesAuthorshipAPI/ProfilesAuthorshipAPI.svc/GetDataByInstitution?InstitutionAbbr=CHILDRENS&Offset=0&Count="+Constants.NUM_PERSONS_TO_FETCH+"&IncludeNonPrimaryAffiliations=false";

            System.out.println("Reading response from : " + requestUrl);
            JSONObject xmlJSONObj = new URLResponseReader().read(requestUrl);
            System.out.println("Completed reading response. Generating file : " + Constants.FILE_NAME);

            XlsWriter xlsWriter = new XlsWriter(Constants.FILE_NAME);
            xlsWriter.writeToXls(xmlJSONObj);

            System.out.println("Xls file generated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
