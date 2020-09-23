package distance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddrRegExp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String addr = "서울특별시 성북구 성북동 185-9";
//		String cv = addr.replaceAll("^[0-9]$", "***");
//		String cv = addr.replaceAll("(?<=.{4}).", "*");
		String cv = addr.replaceAll("((([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길)))", "*");
		
        Pattern pattern = Pattern.compile("((([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길)))");
        Matcher matcher = pattern.matcher(addr);
        System.out.println(matcher.find());
        System.out.println(addr.substring(0, matcher.end()));
//		System.out.println(cv);
		
		// String cv = addr.replaceAll("((([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길)))", "*");
	}

}
