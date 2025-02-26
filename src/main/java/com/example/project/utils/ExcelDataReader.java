package com.example.project.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelDataReader {


    public static Map<String, String> readExcel(String filePath, int rowIndex) throws Exception {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(rowIndex);
        Map<String, String> data = new HashMap<>();

        // Assuming the first row contains headers
        Row headerRow = sheet.getRow(0);
        for (Cell cell : row) {
            String key = headerRow.getCell(cell.getColumnIndex()).getStringCellValue();
            String value = cell.getStringCellValue();
            data.put(key, value);
        }
        workbook.close();
        return data;
    }
}
