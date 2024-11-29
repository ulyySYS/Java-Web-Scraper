/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dsafinal;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author My PC
 */
public class WebsiteData {
    static String[][] getOnlineJobsPH(String keyword) throws IOException {
       Document doc = Jsoup.connect("https://www.onlinejobs.ph/jobseekers/jobsearch?jobkeyword=python&skill_tags=&gig=on&partTime=on&fullTime=on&isFromJobsearchForm=1").get();
          
          Elements jobs = doc.select("div.jobpost-cat-box");
          //String elementText = jobs.text(); 
          Elements jobNames = jobs.select("h4");
          Elements postDate = jobs.select("em");
          Elements shiftType = jobs.select("span.badge");
          Elements jobTags = jobs.select("div.job-tag");
          Elements[] jobTagsArr = new Elements[jobTags.size()]; 
          
          
          
          
          //Translates job names intro string and stored in array
          String[] jobNamesStr = new String[jobNames.size()];
          for(int i = 0; i < jobNames.size(); i++){
              jobNamesStr[i] = jobNames.get(i).text();
          }
          
          //Translates job names intro string and stored in array
          String[] postDateStr = new String[postDate.size()];
          for(int i = 0; i < postDate.size(); i++){
              postDateStr[i] = postDate.get(i).text();
          }
          
          //Translates job names intro string and stored in array
          String[] shiftTypeStr = new String[shiftType.size()];
          for(int i = 0; i < shiftType.size(); i++){
              shiftTypeStr[i] = shiftType.get(i).text();
          }
          
          //stores job tags inside an array
          for(int i = 0; i < jobTags.size(); i++){   
              jobTagsArr[i] = jobTags.get(i).select("a.badge");
          }
          //Translates the jobtags into Strings and concatenated
          String[] jobTagsStr = new String[jobTagsArr.length];
          for(int i = 0; i < jobTagsArr.length; i++){
              String tags = " ";
              for(int j = 0; j < jobTagsArr[i].size(); j++){
                  if(jobTagsArr[i].text().trim().isEmpty()){
                      tags = null;
                  } else{
                    tags = tags + " , " + jobTagsArr[i].get(j).text();  
                  }
                  
              }
              jobTagsStr[i] = tags;
          }
          //gets lenks and translates into strings
          Elements links = doc.select("div.jobpost-cat-box > a");
          String[] url = new String[links.size()];
          for(int i = 0; i < links.size(); i++){
              
              url[i] = "https://www.onlinejobs.ph" + links.get(i).attr("href");
          }
          

          String[][] returnData = {jobNamesStr, postDateStr, shiftTypeStr, jobTagsStr, url};
          return returnData;
      
    }
}
