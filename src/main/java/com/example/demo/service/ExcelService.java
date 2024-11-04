//package com.example.demo.service;
//
//import com.example.demo.excel.ExcelToCSVConverter;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//public class ExcelService {
//    private final ExcelToCSVConverter excelToCSVConverter;
//
//    public ExcelService(ExcelToCSVConverter excelToCSVConverter) {
//        this.excelToCSVConverter = excelToCSVConverter;
//    }
//
//    public String convertExcelToCsv(MultipartFile file) throws IOException {
//        String csvFilePath = "/path/to/save/output.csv";
//        excelToCSVConverter.convert(file.getInputStream(), csvFilePath);
//        return csvFilePath;
//    }
//}
