package com.mini.mini_2.openapi.restarea.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.openapi.restarea.dto.dto.RestareaRequestDTO;
import com.mini.mini_2.openapi.restarea.dto.dto.RestareaResponseDTO;
import com.mini.mini_2.openapi.restarea.dto.entity.RestareaEntity;
import com.mini.mini_2.openapi.restarea.service.RestareaApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/mini/restarea-api")
public class RestareaApiCtrl {
    
    @Value("${OPENAPI_KEY}")
    private String apiKey;
    @Value("${CALLBACKURL}")
    private String callBackUrl;
    @Value("${TYPE}")
    private String dataType;
    
    @Autowired
    private RestareaApiService restareaApiService;
    
    @GetMapping("/get")
    public ResponseEntity<List<RestareaResponseDTO>> get(RestareaRequestDTO params) {
        System.out.println("debug >>>> end point : /forcast/getData");
        System.out.println("debug >>>> service key : " + apiKey);
        System.out.println("debug >>>> callBackUrl : " + callBackUrl);
        System.out.println("debug >>>> data type : " + dataType);
        // System.out.println("debug >>>> params : " + params);
        
        String requestURL = callBackUrl +
                "?key=" + apiKey +
                "&type=" + dataType +
                "&numOfRows=" + params.getNumOfRows() +
                "&pagNo=" + params.getPageNo();
        System.out.println("debug >>> url check : " + requestURL);
                
        HttpURLConnection http = null;
        InputStream stream = null;
        String result = null;

        List<RestareaResponseDTO> list = null;
        
        try {
            URL url = new URL(requestURL);
            http = (HttpURLConnection) url.openConnection();
            System.out.println("http connection = " + http);
            int code = http.getResponseCode();
            System.out.println("http response code  = " + code);
            if (code == 200) {
                stream = http.getInputStream();
                result = readString(stream);
                System.out.println("result = " + result);

                // 서비스 구현
                // list = service.parsingJson(result);

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        ///////////////
        return ResponseEntity.ok().body(list);
    }
    public String readString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String input = null;
        StringBuilder result = new StringBuilder();
        while ((input = br.readLine()) != null) {
            result.append(input + "\\n\\r");
        }
        br.close();
        return result.toString();
    }

}
