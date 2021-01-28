package com.agoramati.downloader.repository;

import com.agoramati.downloader.vo.TickerQuoteResultVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class QuotesRepository {

    public static synchronized QuotesRepository getInstance() {
        return _instance;
    }

    private static QuotesRepository _instance = null;

    public QuotesRepository() {
        System.out.println("QuotesRepository");
        _instance = this;
    }

    @Autowired
    private RedisTemplate<String, Number> redisTemplateUseCount;

    @Autowired
    private RedisTemplate<String, String> redisTemplateTimeSeries;

    private ObjectMapper _mapper = new ObjectMapper();
    private final String ACTIVE_SYMBOLS_COUNT_KEY = "symb:uc";
    private final String ACTIVE_SYMBOLS_DATA_KEY = "symb:ts";
    private final String ACTIVE_SYMBOLS_DATA_PRIORITY_KEY = "symb:tsp";
    private final String ACTIVE_SYMBOLS_LAST_UPDATE_KEY = "symb:lu";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addSymbol(String symbol) {
        if (redisTemplateUseCount.opsForHash().hasKey(ACTIVE_SYMBOLS_COUNT_KEY, symbol)) {
            int val = (int) redisTemplateUseCount.opsForHash().get(ACTIVE_SYMBOLS_COUNT_KEY, symbol);
            redisTemplateUseCount.opsForHash().put(ACTIVE_SYMBOLS_COUNT_KEY, symbol, val+1);
        } else {
            redisTemplateUseCount.opsForHash().put(ACTIVE_SYMBOLS_COUNT_KEY, symbol, 1);
            addPrioritySymbol(symbol);
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.YEAR, -1);
            cal.add(Calendar.DATE, -14);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDateEnd = simpleDateFormat.format(cal.getTime());
            redisTemplateTimeSeries.opsForHash().put(ACTIVE_SYMBOLS_LAST_UPDATE_KEY, symbol, strDateEnd);
        }
    }
    public void removeSymbol(String symbol) {
        if (redisTemplateUseCount.opsForHash().hasKey(ACTIVE_SYMBOLS_COUNT_KEY, symbol)) {
            int val = (int) redisTemplateUseCount.opsForHash().get(ACTIVE_SYMBOLS_COUNT_KEY, symbol);
            if (val == 1) {
                redisTemplateUseCount.opsForHash().delete(ACTIVE_SYMBOLS_COUNT_KEY, symbol);
                redisTemplateTimeSeries.opsForZSet().remove(ACTIVE_SYMBOLS_DATA_KEY + ':' + symbol);
                redisTemplateTimeSeries.opsForHash().delete(ACTIVE_SYMBOLS_LAST_UPDATE_KEY, symbol);
            } else {
                redisTemplateUseCount.opsForHash().put(ACTIVE_SYMBOLS_COUNT_KEY, symbol, val - 1);
            }
        } else {
            redisTemplateUseCount.opsForHash().put(ACTIVE_SYMBOLS_COUNT_KEY, symbol, 1);
        }
    }
    public List<String> listSymbols() {
        System.out.println("listSymbols");
        Map<Object, Object> map = redisTemplateUseCount.opsForHash().entries(ACTIVE_SYMBOLS_COUNT_KEY);
        ArrayList<String> ret = new ArrayList<String>();
        //map.keySet().forEach(key -> System.out.println(key + "=" + map.get(key)));
        map.keySet().forEach(key -> ret.add((String)key));
        return ret;
    }
    public void addPrioritySymbol(String symbol) {
        redisTemplateUseCount.opsForHash().put(ACTIVE_SYMBOLS_DATA_PRIORITY_KEY, symbol, 1);
    }
    public void removePrioritySymbol(String symbol) {
        redisTemplateUseCount.opsForHash().delete(ACTIVE_SYMBOLS_DATA_PRIORITY_KEY, symbol);
    }
    public List<String> listPrioritySymbols() {
        System.out.println("listPrioritySymbols");
        Map<Object, Object> map = redisTemplateUseCount.opsForHash().entries(ACTIVE_SYMBOLS_DATA_PRIORITY_KEY);
        ArrayList<String> ret = new ArrayList<String>();
        //map.keySet().forEach(key -> System.out.println(key + "=" + map.get(key)));
        map.keySet().forEach(key -> ret.add((String)key));
        return ret;
    }
    public boolean isSymbolStillValid(String symbol) {
        //System.out.println("isSymbolStillValid");
        Map<Object, Object> map = redisTemplateUseCount.opsForHash().entries(ACTIVE_SYMBOLS_COUNT_KEY);
        return (int)map.get(symbol) > 0;
    }
    public String getLastUpdateForSymbol(String symbol) {
        //System.out.println("getLastUpdateForSymbol");
        return (String) redisTemplateTimeSeries.opsForHash().get(ACTIVE_SYMBOLS_LAST_UPDATE_KEY, symbol);
    }
    public long getTimeSeriesForSymbolCount(String symbol, long start, long end) {
        //System.out.println("getTimeSeriesForSymbolCount " + symbol + " " + start + " " + end);
        long val = redisTemplateTimeSeries.opsForZSet().count(ACTIVE_SYMBOLS_DATA_KEY + ':' + symbol, start, end);
        //System.out.println("result = " + val);
        return val;
    }
    public List<String> getTimeSeriesForSymbol(String symbol, long start, long end) {
        //System.out.println("getTimeSeriesForSymbol " + symbol + " " + start + " " + end);
        Set<String> range = redisTemplateTimeSeries.opsForZSet().rangeByScore(ACTIVE_SYMBOLS_DATA_KEY + ':' + symbol, start, end);
        ArrayList<String> ret = new ArrayList<String>();
        //System.out.println("result size: " + ret.size());
        range.forEach(obj -> {
            //System.out.println("out: " + obj);
            ret.add(obj);
        });
        return ret;
    }

    public void addTimeSeriesForSymbol(String symbol, List<TickerQuoteResultVO> list) {
        //@TODO there is the possibility that the symbol could be removed at this point and this will just add again
        //      it's time series
        // System.out.println("addTimeSeriesForSymbol " + symbol);
        String key = ACTIVE_SYMBOLS_DATA_KEY + ':' + symbol;
        list.forEach(obj -> {
            try {
                String jsonStr = objectMapper.writeValueAsString(obj);
                //System.out.println(jsonStr);
                redisTemplateTimeSeries.opsForZSet().add(key, jsonStr, obj.getT());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        if (list.size() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = simpleDateFormat.format(new Date(list.get(list.size() - 1).getT()));
            System.out.println("lastUpdate is " + strDate);
            redisTemplateTimeSeries.opsForHash().put(ACTIVE_SYMBOLS_LAST_UPDATE_KEY, symbol, strDate);
        }
    }

}

