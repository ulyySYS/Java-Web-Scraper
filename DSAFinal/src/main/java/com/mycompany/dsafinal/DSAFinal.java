/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsafinal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 *
 * @author My PC
 */
public class DSAFinal {
        

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try{
          WebsiteData.getOnlineJobsPH("python");  
        }
        catch(Exception e){
            System.out.println("error");
        }
         
        
    }
}
