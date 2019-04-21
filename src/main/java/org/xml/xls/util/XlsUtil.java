package org.xml.xls.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class XlsUtil {

    public Cell createCell(Row row, int columnNum, CellStyle cellStyle) {
        Cell cell = row.createCell(columnNum);
        cell.setCellStyle(cellStyle);

        return cell;
    }

    public CellStyle createCellStyle(XSSFWorkbook xssfWorkbook, boolean b, HorizontalAlignment center) {
        CellStyle cellStyle = xssfWorkbook.createCellStyle();
        XSSFFont boldFont = xssfWorkbook.createFont();
        boldFont.setBold(b);
        cellStyle.setFont(boldFont);
        cellStyle.setAlignment(center);
        cellStyle.setHidden(false);

        return cellStyle;
    }

    public void writeToFile(XSSFWorkbook workbook, String fileName) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
    }
}