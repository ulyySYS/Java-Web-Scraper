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
/**
 *
 * @author My PC
 */
public class DSAFinal {

    // Method to generate an Excel sheet from the job data
    public static void generateExcelSheet(String[][] onlineJobsData, String[][] bossJobsData) throws IOException {
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

        // Set the file path based on the operating system
        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            // macOS file path
            filePath = Paths.get(System.getProperty("user.home"), "Documents", "JobData.xlsx").toString();
        } else {
            // Windows OS file path
            filePath = "C:\\Users\\GrantmyIdol\\Documents\\JobData.xlsx";
        }

        // Generate and save the excel sheet
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();

        System.out.println("Excel sheet has been generated at: " + filePath);
    }

    public static void main(String[] args) {
        try {
            // Show input dialog for keyword
            String keyword = JOptionPane.showInputDialog(null, "Enter a keyword to search for jobs:", "Job Search", JOptionPane.QUESTION_MESSAGE);

            if (keyword == null || keyword.trim().isEmpty()) {
                System.out.println("No keyword provided. Exiting program.");
                return;
            }

            // Fetch data from both websites using the keyword
            String[][] onlineJobsData = WebsiteData.getOnlineJobsPH(keyword);
            String[][] bossJobsData = WebsiteData.getBossJob(keyword);

            // Generate the Excel sheet
            generateExcelSheet(onlineJobsData, bossJobsData);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}