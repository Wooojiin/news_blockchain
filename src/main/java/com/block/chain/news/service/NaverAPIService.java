package com.block.chain.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverAPIService {

    private final static String HOST = "https://naveropenapi.apigw.ntruss.com";

    private final static String TRANSLATION_URL = "/nmt/v1/translation";

    private static String clientId = "";
    private static String clientSecret = "";

    //inputLanguage = ko , outputLanguage = en
    public static String tranlation(String text, String inputLanguage, String outputLanguage){

        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String apiUrl = HOST + TRANSLATION_URL;
            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

//            String postParams = "source=" + inputLanguage + "&target" + outputLanguage + "&text=" + encodedText;
            String postParams = "source=ko&target=en&text=" + encodedText;
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
            return response.toString();

        } catch (Exception e) {
            log.error("Translation error {}", e);
        }
        return "fail";
    }

    public static void main(String[] args) {

        System.out.println(tranlation("오늘 저녁에 뭐먹을거야?","ko","en"));
    }
}
