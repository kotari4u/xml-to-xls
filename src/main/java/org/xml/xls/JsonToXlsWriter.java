package org.xml.xls;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.xls.util.XlsUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonToXlsWriter {
    private final XlsUtil xlsUtil = new XlsUtil();
    private final String fileName;
    private final String sheetName;

    public JsonToXlsWriter(String fileName, String sheetName){
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    public void writeToXls(JSONArray xmlJsonArray) throws IOException {
        List<String> columns = new ArrayList<>();
        for(Object object : xmlJsonArray){
            JSONObject jsonObject = (JSONObject) object;
            for(String key: jsonObject.keySet()){
                if(!columns.contains(key)){
                    columns.add(key);
                }
            }
        }

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        CellStyle headerCellstyle = this.xlsUtil.createCellStyle(xssfWorkbook, true, HorizontalAlignment.CENTER);
        CellStyle recordCellStyle = this.xlsUtil.createCellStyle(xssfWorkbook, false, HorizontalAlignment.LEFT);
        initWorkBook(xssfWorkbook, headerCellstyle, columns);

        XSSFSheet xssfSheet = xssfWorkbook.getSheet(this.sheetName);

        int rowNum = 1;
        for(Object object : xmlJsonArray){
            int cellNum = 0;
            Row row = xssfSheet.createRow(rowNum);
            createCells(row, columns.size(), recordCellStyle);
            for(String key: columns){
                row.getCell(cellNum).setCellValue(getJsonValue((JSONObject) object, key));
                cellNum++;
            }
            rowNum++;
        }

        this.xlsUtil.writeToFile(xssfWorkbook, this.fileName);
    }

    private String getJsonValue(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException jsonEx){
            // Do nothing if column does not exist
        }

        return "";
    }

    private XSSFWorkbook initWorkBook(XSSFWorkbook workbook, CellStyle headerCellStyle, List<String> columns){

        Sheet sheet = workbook.createSheet(this.sheetName);
        Row row = sheet.createRow(0);

        for(int columnNum = 0; columnNum < columns.size(); columnNum++) {
            Cell cell = this.xlsUtil.createCell(row, columnNum, headerCellStyle);

            cell.setCellValue(columns.get(columnNum));

            sheet.setColumnWidth(columnNum, 10);
            sheet.autoSizeColumn(columnNum);
        }

        return workbook;
    }

    private void createCells(Row row, int columns, CellStyle recordCellStyle) {
        for (int columnNum = 0; columnNum < columns; columnNum++) {
            this.xlsUtil.createCell(row, columnNum, recordCellStyle);
        }
    }
}
