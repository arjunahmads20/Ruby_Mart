/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;


/**
 *
 * @author ACER
 */
public class APIUtility {
    
    public static String getAiRecommendation(String statisticDataPrompt) {
        String apiKey = System.getenv("DEEPSEEK_API_KEY");
        String targetURL = "https://openrouter.ai/api/v1/chat/completions";
        String urlParameters = "{\"model\":\"deepseek/deepseek-chat:free\",\"messages\":[{\"role\":\"user\",\"content\":\" " + statisticDataPrompt + " \"}]}";
        HttpURLConnection connection = null;
        try {
            // Create connection 
            
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
           
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();
            // Get response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String response_string = response.toString();
            String response_content = response_string.split("\"content\":\"")[1].split("\",\"refusal\":")[0];
            return response_content;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            // Create connection 
            
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            
            
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();
            // Get response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
                
            }
            rd.close();
            return response.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    } 
    
}
