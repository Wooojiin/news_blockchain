package com.block.chain.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;
//    public String getMorpheme(String contents){
//        return RestTemplateUtil.post(contents);
//    }

    public String getMorpheme(String contents){
        MultiValueMap<String, String> params =new LinkedMultiValueMap<>(); // 이런식으로 하면 되나?
        params.add("text", contents);
        ResponseEntity<String[]> response = restTemplate.postForEntity("http://localhost:8197/api/morpheme",params,String[].class);
        List<String> result = Arrays.asList(response.getBody());
        StringBuilder sb = new StringBuilder();
        for(int idx = 0; idx < result.size(); idx ++){
            if(idx == (result.size()-1)){
                sb.append(result.get(idx));
            }else {
                sb.append(result.get(idx));
                sb.append(",");
            }
        }
        return sb.toString();
    };
}
