package yahoo.finance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;

import com.google.common.base.Stopwatch;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Test00 {
    
    public static void main(String[] args) throws IOException, ParseException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        
        Stock samsung = YahooFinance.get("005930" + ".KS", false);    // 삼성
        Stock kospi = YahooFinance.get("^KS11", false);    // kospi
        
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar fromDt = Calendar.getInstance();
//        fromDt.setTime(format.parse("2018-01-01"));
//        Calendar toDt = Calendar.getInstance();
        
        LocalDate now = LocalDate.now();
        
        List<HistoricalQuote> hist =getHistory(samsung, now.minusYears(10), now.minusYears(10).plusDays(2), Interval.DAILY);
        
        HistoricalQuote quote = hist.get(0);
        LocalDateTime date = convertDateTime(quote.getDate());
        BigDecimal oldPrice = quote.getClose();
        
        System.out.println(date);
        System.out.println(oldPrice);
        System.out.println(samsung.getQuote().getPrice());
        System.out.println(samsung.getStats().getMarketCap());
//        System.out.println(samsung.get);
        
        
        
        System.out.println("stop :" + stopwatch);
        
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
