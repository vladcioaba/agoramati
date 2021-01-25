package com.agoramati.downloader.service;

import com.agoramati.downloader.repo.QuotesRepository;
import com.agoramati.downloader.vo.PolygonAggsTickerResultVO;
import com.agoramati.downloader.vo.TickerQuoteResultVO;
import com.agoramati.downloader.vo.TickerResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadingService {
    QuotesRepository quotesRepository = null;

    public DownloadingService() {
        System.out.println("DownloadingService");
        quotesRepository = QuotesRepository.getInstance();
    }

    public List<TickerQuoteResultVO> download(String symbol, String range, String dateStart, String dateEnd) throws URISyntaxException, IOException, InterruptedException {
        List<TickerQuoteResultVO> retur = new ArrayList<TickerQuoteResultVO>();

        String url = "https://api.polygon.io/v2/aggs/ticker/";
        url += symbol + "/range/" + range + "/" + dateStart + "/" + dateEnd;
        url += "?apiKey=4jpIO1nCwzmwHp0SoWiPMCL0zIll4ski";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());

        //System.out.println("Status of operation performed:" + httpResponse.body() + " " + httpResponse.statusCode());
        System.out.println("Status of operation performed: " + httpResponse.statusCode());
        if (httpResponse.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            PolygonAggsTickerResultVO data = objectMapper.readValue(httpResponse.body(), PolygonAggsTickerResultVO.class);
            if (data.getQueryCount().intValue() > 0) {
                retur = Arrays.asList(data.getResults());
            }
        }

        return retur;
    }

    private AtomicInteger counter = new AtomicInteger();

    public boolean execute() {
        List<String> listSymbols = quotesRepository.listSymbols();
        System.out.println("Downloading symbols " + listSymbols);

        listSymbols.forEach(symbol -> {
            if (quotesRepository.isSymbolStillValid(symbol)) {
                try {
                    String range = "1/hour";
                    String strDateStart = quotesRepository.getLastUpdateForSymbol(symbol);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String strDateEnd = simpleDateFormat.format(new Timestamp(System.currentTimeMillis()));
                    System.out.println("Downloading " + symbol + " " + strDateStart + " " + strDateEnd);
                    List<TickerQuoteResultVO> listTickers = download(symbol, range, strDateStart, strDateEnd);
                    System.out.println("Downloaded size " + listTickers.size());
                    quotesRepository.addTimeSeriesForSymbol(symbol, listTickers);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter.incrementAndGet() == 5) {
                    counter.set(0);
                    try {
                        System.out.println("Downloading limit reached.");
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return true;
    }
}
