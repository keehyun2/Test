package yahoo.finance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.base.Stopwatch;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Test00 {
    
    public static void main(String[] args) throws IOException, ParseException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        
        Stock samsung = YahooFinance.get("005930.KS");    // 삼성
//        Stock kospi = YahooFinance.get("^KS11", false);    // kospi
        
        LocalDate now = LocalDate.now();
        
        samsung.getQuote().getPrice();
//        
        List<HistoricalQuote> hist = getHistory(samsung, now.minusDays(6), now.plusDays(1), Interval.DAILY);
        
//        hist.size();
        for (HistoricalQuote historicalQuote : hist) {
            System.out.println(historicalQuote);
        }
        
//        
//        HistoricalQuote quote = hist.get(0);
//        LocalDateTime date = convertDateTime(quote.getDate());
//        BigDecimal oldPrice = quote.getClose();
//        
//        System.out.println(date);
//        System.out.println(oldPrice);
//        System.out.println(samsung.getQuote().getPrice());
//        System.out.println(samsung.getStats().getMarketCap());
//        System.out.println(samsung.get);
        
        
        
//        System.out.println("stop :" + stopwatch);
        
//        Stock asia = YahooFinance.get("020560.KS", false);    // kospi
//        
//        System.out.println(asia.getQuote().getLastTradeTime().getTime());
        
        
//        Random randomGenerator = new Random();
//        
//        System.out.println(randomGenerator.nextInt(2000));
//        
//        System.out.println(LocalDateTime.parse("2021-06-11T00:00:00"));
        
    }
    
    
    public static Calendar convertCal(LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        return calendar;
    }

    public static LocalDateTime convertDateTime(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.of("Asia/Seoul"));
    }

    public static List<HistoricalQuote> getHistory(Stock stock, LocalDate from, LocalDate to, Interval interval)
            throws IOException {

        return stock.getHistory(convertCal(from), convertCal(to), interval);
    }
}
