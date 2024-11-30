/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dsafinal;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author My PC
 */
public class WebsiteData {

    public static String[][] getBossJob(String keyword) throws IOException{
        // Replace spaces with "+" for URL encoding
        keyword = keyword.replace(" ", "+");

        // Construct the BossJob URL dynamically
        String url = "https://bossjob.ph/en-us/jobs-hiring/" + keyword + "-jobs?page=1&sort=1&salaryType=monthly";
        Document doc = Jsoup.connect(url).get();

        Elements jobNames = doc.select("div._path__title__iAn2G > p");
        Elements shiftTypes = doc.select("div._path__label__36X7S");
        Elements jobTags = doc.select("div._path__skills__QtaGs");

        // BossJob does not provide post dates or URLs directly, so placeholders will be used
        String[][] jobData = new String[jobNames.size()][5];

        for (int i = 0; i < jobNames.size(); i++){
            String jobName = jobNames.get(i).text();
            String shiftType = shiftTypes.size() > i ? shiftTypes.get(i).text() : "N/A";
            String tags = jobTags.size() > i ? jobTags.get(i).text() : "N/A";

            // Populate job data array
            jobData[i][0] = jobName;
            jobData[i][1] = "N/A";
            jobData[i][2] = shiftType;
            jobData[i][3] = tags;
            jobData[i][4] = "https://bossjob.ph/en-us/"; // brute forced
        }

        return jobData;
    }

    public static String[][] getOnlineJobsPH(String keyword) throws IOException{
        // Replace spaces with "+" for URL encoding
        keyword = keyword.replace(" ", "+");

        // Construct the OnlineJobs URL dynamically
        String url = "https://www.onlinejobs.ph/jobseekers/jobsearch?jobkeyword=" + keyword + "&skill_tags=&gig=on&partTime=on&fullTime=on&isFromJobsearchForm=1";
        Document doc = Jsoup.connect(url).get();

        Elements jobs = doc.select("div.jobpost-cat-box");
        Elements jobNames = jobs.select("h4");
        Elements postDates = jobs.select("em");
        Elements shiftTypes = jobs.select("span.badge");
        Elements jobTags = jobs.select("div.job-tag");

        Elements[] jobTagsArr = new Elements[jobTags.size()];
        for (int i = 0; i < jobTags.size(); i++){
            jobTagsArr[i] = jobTags.get(i).select("a.badge");
        }

        // Prepare to store job data
        String[][] jobData = new String[jobNames.size()][5];

        for (int i = 0; i < jobNames.size(); i++){
            String tags = "";
            for (int j = 0; j < jobTagsArr[i].size(); j++){
                tags += jobTagsArr[i].get(j).text() + ", ";
            }
            if (!tags.isEmpty()) {
                tags = tags.substring(0, tags.length() - 2);
            }

            jobData[i][0] = jobNames.get(i).text();
            jobData[i][1] = postDates.size() > i ? postDates.get(i).text() : "N/A";
            jobData[i][2] = shiftTypes.size() > i ? shiftTypes.get(i).text() : "N/A";
            jobData[i][3] = tags.isEmpty() ? "N/A" : tags;
            jobData[i][4] = "https://www.onlinejobs.ph" + jobs.get(i).selectFirst("a").attr("href"); // URL
        }

        return jobData;
    }
}