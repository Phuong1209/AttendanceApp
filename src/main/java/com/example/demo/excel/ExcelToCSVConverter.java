//package com.example.demo.excel;
//
//import org.apache.poi.ss.usermodel.*;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class ExcelToCSVConverter {
//    public void convert(InputStream excelInputStream, String csvFilePath) throws IOException {
//        try (Workbook workbook = WorkbookFactory.create(excelInputStream);
//             BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                StringBuilder csvRow = new StringBuilder();
//                for (Cell cell : row) {
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            csvRow.append(cell.getStringCellValue());
//                            break;
//                        case NUMERIC:
//                            csvRow.append(cell.getNumericCellValue());
//                            break;
//                        case BOOLEAN:
//                            csvRow.append(cell.getBooleanCellValue());
//                            break;
//                        default:
//                            csvRow.append("");
//                    }
//                    csvRow.append(",");
//                }
//                writer.write(csvRow.toString());
//                writer.newLine();
//            }
//        }
//    }
//}
