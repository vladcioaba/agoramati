package com.agoramati.downloader.rest;

import com.agoramati.downloader.vo.TickerResultVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DownloadRestController {
    //@Autowired


    @GetMapping("/hello")
    @CrossOrigin
    public String hello() {
        return "hello!";
    }

    @CrossOrigin
    @RequestMapping(value = "/getsymbols", method = RequestMethod.GET, produces = "application/json")
    public List<Object> getSymbols() {
        return new ArrayList<>();
    }

    @CrossOrigin
    @RequestMapping(value = "/getquotes", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<TickerResultVO> getQuotesForSymbols(@RequestBody List<String> symbols) {
        List<TickerResultVO> retur = null;
        JSONObject json = null;
        System.out.println(
            "getQuotesForSymbols " + symbols);

        try {
            String symbol = symbols.get(0);
            String range = "1/day";
            String date1 = "2020-06-01";
            String date2 = "2020-06-17";


            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest
                    = HttpRequest
                    .newBuilder()
                    .uri(new URI("https://api.polygon.io/v2/aggs/ticker/" + symbol + "/range/" + range + "/" + date1 + "/" + date2 + "?apiKey=4jpIO1nCwzmwHp0SoWiPMCL0zIll4ski"))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse
                    = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(
                    "Status of operation performed:"
                            + httpResponse.body() + " "
                            + httpResponse.statusCode());

            json = new JSONObject(httpResponse.body());

            retur = new ArrayList<TickerResultVO>();
            JSONArray jarr = (JSONArray) json.get("results");

            ObjectMapper objectMapper = new ObjectMapper();
            TickerResultVO[] data = objectMapper.readValue(jarr.toString(), TickerResultVO[].class);
            retur = Arrays.asList(data);
        }
        catch (Exception e) {
            System.out.println("Exception" + e);
        }

        return retur;
    }
}