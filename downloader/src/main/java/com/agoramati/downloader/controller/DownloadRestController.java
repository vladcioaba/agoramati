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
    QuotesRepository quotesRepository = null;

    @Autowired
    private AvatarRepository avatarRepository;

    public DownloadRestController() {
        System.out.println("DownloadRestController");
        quotesRepository = QuotesRepository.getInstance();
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
    @RequestMapping(value = "/getsymbolsavatar", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<AvatarResponseVO> getAvatarForSymbols(@RequestBody List<String> userRequestVo) {
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
        quotesRepository.addSymbol(symbol);
    }

    @CrossOrigin
    @RequestMapping(value = "/removesymbol", method = RequestMethod.POST, produces = "application/json")
    public void removeSymbol(@RequestBody String symbol) {
        System.out.println("removeSymbol " + symbol);
        quotesRepository.removeSymbol(symbol);
    }

    @CrossOrigin
    @RequestMapping(value = "/listsymbols", method = RequestMethod.POST, produces = "application/json")
    public List<String> listSymbols() {
        System.out.println("listSymbols");
        return quotesRepository.listSymbols();
    }

    @CrossOrigin
    @RequestMapping(value = "/getquotes", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<TickerQuoteResultVO> getQuotesForSymbols(@RequestBody List<String> symbols) {
        List<TickerQuoteResultVO> retur = new ArrayList<TickerQuoteResultVO>();
        System.out.println("getQuotesForSymbols " + symbols);

        try {
            String symbol = symbols.get(0);
            String range = "1/hour";
            String date1 = "2020-01-01";
            String date2 = "2021-12-31";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate1 = dateFormat.parse(date1);
            Date parsedDate2 = dateFormat.parse(date2);

            if (quotesRepository.getTimeSeriesForSymbolCount(symbol,
                                                        parsedDate1.getTime(),
                                                        parsedDate2.getTime()) > 0) {
                List<String> ts = quotesRepository.getTimeSeriesForSymbol(symbol,
                                                    parsedDate1.getTime(),
                                                    parsedDate2.getTime());
                System.out.println("getQuotesForSymbols from cache " + symbols);
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