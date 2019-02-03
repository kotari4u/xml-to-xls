package org.xml.xls;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.xls.bean.Person;
import org.xml.xls.bean.Publication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsWriter {

    private String fileName;

    public XlsWriter(String fileName){
        this.fileName = fileName;
    }

    public void writeToXls(JSONObject xmlJSONObj) throws IOException {
        JSONArray personArray = (xmlJSONObj.getJSONObject("PersonList")).getJSONArray("Person");
        List personJsonList = personArray.toList();

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        CellStyle headerCellstyle = createCellStyle(xssfWorkbook, true, HorizontalAlignment.CENTER);
        CellStyle recordCellStyle = createCellStyle(xssfWorkbook, false, HorizontalAlignment.LEFT);
        initWorkBook(xssfWorkbook, headerCellstyle);

        XSSFSheet xssfSheet = xssfWorkbook.getSheet(Constants.SHEET_NAME);

        List<Person> personList = new DataExtractor().extractData(personJsonList);
        int rowNum = 1; // As row = "0" has headers

        for(Person person : personList){
            if(person.getPublicationList().size() > 0 ) {
                for (Publication publication : person.getPublicationList()) {
                    Row row = xssfSheet.createRow(rowNum);
                    createCells(row, recordCellStyle);
                    generateRow(row, person, publication);
                    rowNum++;
                }
            } else { // A person may not have any publications
                Row row = xssfSheet.createRow(rowNum);
                createCells(row, recordCellStyle);
                generateRow(row, person, null);
                rowNum++;
            }
        }

        writeToFile(xssfWorkbook, this.fileName);
    }

    private void generateRow(Row row, Person person, Publication publication) {
        row.getCell(0).setCellValue(person.getPersonId());
        row.getCell(1).setCellValue(person.getFullName());
        row.getCell(2).setCellValue(person.getFirstName());
        row.getCell(3).setCellValue(person.getLastName());
        row.getCell(4).setCellValue(person.getAddress1());
        row.getCell(5).setCellValue(person.getAddress2());
        row.getCell(6).setCellValue(person.getAddress3());
        row.getCell(7).setCellValue(person.getAddress4());
        row.getCell(8).setCellValue(person.getPhone());
        row.getCell(9).setCellValue(person.getInstitutionName());
        row.getCell(10).setCellValue(person.getDepartmentName());
        row.getCell(11).setCellValue(person.getDivisionName());
        row.getCell(12).setCellValue(person.getJobTitle());
        row.getCell(13).setCellValue(person.getFacultyType());
        if(null != publication) {
            row.getCell(14).setCellValue(publication.getPublicationSource());
            row.getCell(15).setCellValue(publication.getPmId());
            row.getCell(16).setCellValue(publication.getUrl());
            row.getCell(17).setCellValue(publication.getPublicationReference());
            row.getCell(18).setCellValue(publication.getTitle());
            row.getCell(19).setCellValue(publication.getAuthors());
            row.getCell(20).setCellValue(publication.getJournal());
            row.getCell(21).setCellValue(publication.getIssueInfo());
            row.getCell(22).setCellValue(publication.getDate());
        }
    }

    private XSSFWorkbook initWorkBook(XSSFWorkbook workbook, CellStyle cellStyle){

        Sheet sheet = workbook.createSheet(Constants.SHEET_NAME);
        Row row = sheet.createRow(0);

        for(int columnNum = 0; columnNum < Constants.COLUMN_LIST.size(); columnNum++) {
            Cell cell = createCell(row, columnNum, cellStyle);

            String[] cellAttributes = Constants.COLUMN_LIST.get(columnNum);
            cell.setCellValue(cellAttributes[0]);

            Short columnWidth = Short.parseShort(cellAttributes[1]);
            sheet.setColumnWidth(columnNum, columnWidth);
            sheet.autoSizeColumn(columnNum);
        }

        return workbook;
    }

    private void createCells(Row row, CellStyle recordCellStyle) {
        for (int columnNum = 0; columnNum < Constants.COLUMN_LIST.size(); columnNum++) {
            createCell(row, columnNum, recordCellStyle);
        }
    }

    private Cell createCell(Row row, int columnNum, CellStyle cellStyle) {
        Cell cell = row.createCell(columnNum);
        cell.setCellStyle(cellStyle);

        return cell;
    }

    private CellStyle createCellStyle(XSSFWorkbook xssfWorkbook, boolean b, HorizontalAlignment center) {
        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont boldFont = xssfWorkbook.createFont();
        boldFont.setBold(b);
        cellStyle.setFont(boldFont);
        cellStyle.setAlignment(center);
        cellStyle.setHidden(false);

        return cellStyle;
    }

    private void writeToFile(XSSFWorkbook workbook, String fileName) throws IOException {
        try(FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
    }
}
