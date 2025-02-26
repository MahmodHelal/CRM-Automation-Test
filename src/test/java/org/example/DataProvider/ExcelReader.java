package org.example.DataProvider;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {

    /**
     * Reads data from an Excel sheet and returns it as a list of maps.
     * Each map represents a row, where the key is the column header, and the value is the cell content.
     *
     * @param filePath  The absolute path of the Excel file.
     * @param sheetName The name of the sheet to be read.
     * @return A list of maps, where each map represents a row with column headers as keys.
     */
    public List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        // Using try-with-resources to ensure the file is properly closed
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(file); // Auto-detects Excel format (XLS or XLSX)
            Sheet sheet = workbook.getSheet(sheetName);

            // Validate if the sheet exists
            if (sheet == null) {
                throw new IllegalArgumentException("❌ Sheet '" + sheetName + "' not found in file: " + filePath);
            }

            // Retrieve the header row (first row)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("❌ Header row is missing in sheet: " + sheetName);
            }

            int numOfColumns = headerRow.getPhysicalNumberOfCells();
            System.out.println("✅ Number of Columns: " + numOfColumns);

            // Iterate over each row, skipping the header row (index 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // Skip null rows (e.g., empty rows in Excel)
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>(); // Preserves column order

                // Iterate through all columns
                for (int j = 0; j < numOfColumns; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell valueCell = row.getCell(j);

                    // Skip empty columns (when either header or value is missing)
                    if (headerCell == null || valueCell == null) continue;

                    String key = headerCell.getStringCellValue().trim(); // Column header
                    String value = getCellValue(valueCell); // Extract formatted cell value

                    // Print for debugging purposes
                    System.out.println("🔹 Column: " + key + " | Value: " + value);

                    rowData.put(key, value); // Store in row data
                }
                data.add(rowData); // Add the processed row to the list
            }
        } catch (Exception e) {
            System.err.println("❌ Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Extracts the value from a cell, handling different data types correctly.
     *
     * @param cell The cell to extract data from.
     * @return A properly formatted string representation of the cell value.
     */
    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim(); // Return text as is
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Convert date to string
                }
                return String.valueOf(cell.getNumericCellValue()); // Convert numeric to string
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // Convert boolean to string
            case FORMULA:
                return cell.getCellFormula(); // Return formula as string
            case BLANK:
                return ""; // Return empty string for blank cells
            default:
                return "⚠ Unsupported Cell Type"; // Handle unexpected types gracefully
        }
    }
}
