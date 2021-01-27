package com.agoramati.downloader.controller;

import com.agoramati.downloader.model.AvatarData;
import com.agoramati.downloader.repository.AvatarRepository;
import com.agoramati.downloader.repository.QuotesRepository;
import com.agoramati.downloader.vo.AvatarResponseVO;
import com.agoramati.downloader.vo.TickerQuoteResultVO;
import com.agoramati.downloader.vo.PolygonSearchResultVO;
import com.agoramati.downloader.vo.TickerResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class DownloadRestController {

    @Autowired
    private AvatarRepository avatarRepository;

    public DownloadRestController() {
        System.out.println("DownloadRestController");
    }

    @GetMapping("/hello")
    @CrossOrigin
    public String hello() {
        return "hello!";
    }


    @CrossOrigin
    @RequestMapping(value = "/searchsymbol", method = RequestMethod.POST, produces = "application/json")
    public List<TickerResultVO> searchSymbol(@RequestBody String symbol) {
        List<TickerResultVO> retur = null;

        System.out.println("searchSymbol " + symbol);

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.polygon.io/v2/reference/tickers?market=stocks&search=" + symbol + "&apiKey=4jpIO1nCwzmwHp0SoWiPMCL0zIll4ski"))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest,
                                                                HttpResponse.BodyHandlers.ofString());

            System.out.println("Status of operation performed:"
                            + httpResponse.body() + " "
                            + httpResponse.statusCode());
            ObjectMapper objectMapper = new ObjectMapper();
            PolygonSearchResultVO mappedResponse = objectMapper.readValue(httpResponse.body(), PolygonSearchResultVO.class);
            retur = new ArrayList<TickerResultVO>();
            if (mappedResponse.getStatus().equals("OK")) {
                retur = Arrays.asList(mappedResponse.getTickers());
            }
        }
        catch (Exception e) {
            System.out.println("Exception" + e);
        }

        return retur;
    }

    @CrossOrigin
    @RequestMapping(value = "/getsymbolavatars", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<AvatarResponseVO> getSymbolAvatars(@RequestBody List<String> userRequestVo) {
        List<AvatarResponseVO> list = new ArrayList<AvatarResponseVO>();
        userRequestVo.forEach(avatarId -> {
            try {
                final AvatarData data = avatarRepository.getAvatarData(avatarId);
                list.add(new AvatarResponseVO(data.getAvatarId(), data.getAvatarUrl()));
            } catch (Exception e) {
                System.out.println("Error avatarId " + avatarId + " not found!");
            }
        });
        return list;
    }

    @CrossOrigin
    @RequestMapping(value = "/addsymbol", method = RequestMethod.POST, produces = "application/json")
    public void addSymbol(@RequestBody String symbol) {
        System.out.println("addSymbol " + symbol);
        QuotesRepository.getInstance().addSymbol(symbol);
    }

    @CrossOrigin
    @RequestMapping(value = "/removesymbol", method = RequestMethod.POST, produces = "application/json")
    public void removeSymbol(@RequestBody String symbol) {
        System.out.println("removeSymbol " + symbol);
        QuotesRepository.getInstance().removeSymbol(symbol);
    }

    @CrossOrigin
    @RequestMapping(value = "/listsymbols", method = RequestMethod.POST, produces = "application/json")
    public List<String> listSymbols() {
        System.out.println("listSymbols");
        return QuotesRepository.getInstance().listSymbols();
    }

    @CrossOrigin
    @RequestMapping(value = "/getquotes", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<TickerQuoteResultVO> getQuotesForSymbols(@RequestBody List<String> query) {
        List<TickerQuoteResultVO> retur = new ArrayList<TickerQuoteResultVO>();
        System.out.println("getQuotesForSymbols " + query);

        try {
            String symbol = query.get(0);
            long date1 = Long.parseLong(query.get(1));
            long date2 = Long.parseLong(query.get(2));
            if (QuotesRepository.getInstance().getTimeSeriesForSymbolCount(symbol,
                                                                            date1,
                                                                            date2) > 0) {
                List<String> ts = QuotesRepository.getInstance().getTimeSeriesForSymbol(symbol,
                                                                            date1,
                                                                            date2);
                System.out.println("getQuotesForSymbols from cache " + symbol);
                ObjectMapper objectMapper = new ObjectMapper();
                retur = new ArrayList<TickerQuoteResultVO>();
                for (String str : ts) {
                    TickerQuoteResultVO data = objectMapper.readValue(str, TickerQuoteResultVO.class);
                    retur.add(data);
                }
            } else {
                //@TODO submit to priority
            }
        }
        catch (Exception e) {
            System.out.println("Exception" + e);
        }

        return retur;
    }
}