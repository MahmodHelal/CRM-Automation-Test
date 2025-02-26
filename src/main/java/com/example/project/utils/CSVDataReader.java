package com.example.project.utils;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVDataReader {
    public static List<String[]> readCSV(String filePath) throws Exception {
        List<String[]> data = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            data = csvReader.readAll();
        }
        return data;
    }
}

