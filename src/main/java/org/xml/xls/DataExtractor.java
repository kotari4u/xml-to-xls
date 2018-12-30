package org.xml.xls;

import org.xml.xls.bean.Person;
import org.xml.xls.bean.Publication;

import java.util.*;

public class DataExtractor {

    public List<Person> extractData(List<Object> personList){
        List<Person> resultPersonList = new ArrayList<>();
        for(Object personObject: personList ) {
            HashMap personMap = (HashMap) personObject;

            Person person = new Person();
            int personId = (int) personMap.get("PersonID");
            person.setPersonId(personId);

            extractName(personMap, person);
            extractAddress(personMap, person);
            extractAffiliation(personMap, person);
            extractPublicationList(personMap, person);

            resultPersonList.add(person);
        }

        return resultPersonList;
    }

    private void extractName(HashMap personMap, Person person) {
        Map<String, Map> nameMap = (Map) personMap.get("Name");
        person.setFullName(String.valueOf(nameMap.get("FullName")));
        person.setFirstName(String.valueOf(nameMap.get("FirstName")));
        person.setLastName(String.valueOf(nameMap.get("LastName")));
    }

    private void extractAddress(HashMap personMap, Person person) {
        Map<String, Map> addressMap = (Map) personMap.get("Address");
        person.setAddress1(String.valueOf(addressMap.get("Address1")));
        person.setAddress2(String.valueOf(addressMap.get("Address2")));
        person.setAddress3(String.valueOf(addressMap.get("Address3")));
        person.setAddress4(String.valueOf(addressMap.get("Address4")));
        person.setPhone(String.valueOf(addressMap.get("Telephone")));
    }

    private void extractAffiliation(HashMap personMap, Person person) {
        // If a personMap has more than one affiliation, then affiliation can be a List instead of Map
        Map<String, Object> affiliationMap = null;
        Object affiliationObject = ((Map) personMap.get("AffiliationList")).get("Affiliation");
        if (affiliationObject instanceof Map) {
            affiliationMap = (Map<String, Object>) affiliationObject;
        } else if (affiliationObject instanceof List) {
            affiliationMap = (Map<String, Object>) ((List) affiliationObject).get(0);
        }

        if (null != affiliationMap) {
            person.setInstitutionName(String.valueOf(affiliationMap.get("InstitutionName")));
            person.setDepartmentName(String.valueOf(affiliationMap.get("DepartmentName")));
            person.setDivisionName(String.valueOf(affiliationMap.get("DivisionName")));
            person.setJobTitle(String.valueOf(affiliationMap.get("JobTitle")));
            person.setFacultyType(String.valueOf(((Map) affiliationMap.get("FacultyType")).get("content")));
        }
    }

    private void extractPublicationList(HashMap personMap, Person person) {
        List<Publication> resultPublicationList = new ArrayList<>();
        Map publications = (Map) personMap.get("PublicationList");
        if(null != publications && publications.size() > 0) {
            Object object = publications.get("Publication");
            List<Map> publicationMapList = null;

            if (object instanceof Map) {
                publicationMapList = Arrays.asList((Map)object);
            } else if (object instanceof List) {
                publicationMapList = (List<Map>) object;
            }

            for (int publicationCounter = 0;
                    publicationCounter < Constants.MAX_PUBLICATIONS
                                && publicationCounter < publicationMapList.size();
                    publicationCounter++) {
                Map publicationMap = publicationMapList.get(publicationCounter);
                if(null != publicationMap && publicationMap.size() > 0) {
                    Publication publication = new Publication();
                    publication.setPublicationSource(String.valueOf(publicationMap.get("Source")));
                    publication.setPmId(String.valueOf(publicationMap.get("PMID")));
                    publication.setUrl(String.valueOf(publicationMap.get("URL")));
                    publication.setPublicationReference(String.valueOf(publicationMap.get("PublicationReference")));
                    publication.setTitle(String.valueOf(publicationMap.get("Title")));
                    publication.setAuthors(String.valueOf(publicationMap.get("Authors")));
                    publication.setJournal(String.valueOf(publicationMap.get("Journal")));
                    publication.setDate(String.valueOf(publicationMap.get("Date")));

                    resultPublicationList.add(publication);
                }
            }
        }

        person.setPublicationList(resultPublicationList);
    }
}
