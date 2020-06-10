package com.block.chain.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverAPIService {

    private String HOST = "https://naveropenapi.apigw.ntruss.com";
    private String TRANSLATION_URL = "/nmt/v1/translation";

    @Value("${naver.clientId}")
    private String clientId;

    @Value("${naver.clientSecret}")
    private String clientSecret;

    //inputLanguage = ko , outputLanguage = en
    public String translation(String text, String inputLanguage, String outputLanguage){

        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String apiUrl = HOST + TRANSLATION_URL;
            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            String postParams = "source=" + inputLanguage + "&target=" + outputLanguage + "&text=" + encodedText;
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if(responseCode == 200) { // Success
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }else { // Fail
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }

            br.close();
            String jsonString = response.toString();
            JSONObject jsonObject = new JSONObject(jsonString);

            String result = jsonObject.getJSONObject("message").getJSONObject("result").getString("translatedText");
            return  result;

        } catch (Exception e) {
            log.error("Translation error {}", e);
        }
        return "fail";
    }
}
