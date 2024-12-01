/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsafinal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
/**
 *
 * 
 */
public class DSAFinal {

    // Method to generate an Excel sheet from the job data
    public static void generateExcelSheet(String[][] onlineJobsData, String[][] bossJobsData, String keyword) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Job Data");
        int rowIndex = 0;

        // Header row
        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Source");
        headerRow.createCell(1).setCellValue("Job Name");
        headerRow.createCell(2).setCellValue("Post Date");
        headerRow.createCell(3).setCellValue("Shift Type");
        headerRow.createCell(4).setCellValue("Tags");
        headerRow.createCell(5).setCellValue("URL");

        // OnlineJobs.ph
        for (String[] job : onlineJobsData) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("OnlineJobs.ph");
            row.createCell(1).setCellValue(job[0]);
            row.createCell(2).setCellValue(job[1]);
            row.createCell(3).setCellValue(job[2]);
            row.createCell(4).setCellValue(job[3]);
            row.createCell(5).setCellValue(job[4]);
        }

        // BossJob.ph
        for (String[] job : bossJobsData) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("BossJob.ph");
            row.createCell(1).setCellValue(job[0]);
            row.createCell(2).setCellValue(job[1]);
            row.createCell(3).setCellValue(job[2]);
            row.createCell(4).setCellValue(job[3]);
            row.createCell(5).setCellValue(job[4]);
        }

        // Set the file path dynamically based on the input keyword and OS
        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            // macOS file path
            filePath = Paths.get(System.getProperty("user.home"), "Documents", "JobData for " + keyword + ".xlsx").toString();
        } else {
//            String currentDir = System.getProperty("user.dir");

            String userHome = System.getProperty("user.home");
            String documentsPath = userHome + "\\Documents\\";
            // Windows OS file path
            filePath = documentsPath + keyword + ".xlsx";
        }

        // Generate and save the excel sheet
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();

        System.out.println("Excel sheet has been generated at: " + filePath);
    }

    public static void main(String[] args) {
        while (true) {
            try {
                // Show input dialog for keyword
                String keyword = JOptionPane.showInputDialog(null, ""
                        + "Submitted by: \n"
                        + "Name: Ulysses Grant S. Audan(Developer), Mobile Number: +639457849133, Email: audanulysses320@gmail.com \n"
                        + "Name: Ferdinand John F. Dobli(Developer), Mobile Number: +639321917366, Email: ferdinandjohndobli@gmail.com\n\n"
                        
                        + "Search for jobs:", "Job Search", JOptionPane.QUESTION_MESSAGE);

                if (keyword == null || keyword.trim().isEmpty()) {
                    System.out.println("No keyword provided. Exiting program.");
                    return;
                }

                // Fetch data from both websites using the keyword
                String[][] onlineJobsData = WebsiteData.getOnlineJobsPH(keyword);
                String[][] bossJobsData = WebsiteData.getBossJob(keyword);

                // Generate the Excel sheet
                generateExcelSheet(onlineJobsData, bossJobsData, keyword);

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}