package name.yalsooni.genius.util;

import java.text.SimpleDateFormat;

/**
 * 정의한 패턴으로 날짜를 변환한다.
 * 
 * @since 2015. 01. 16.
 * @author yoon-iljoong
 * @see java.text.SimpleDateFormat
 */
public class Date {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
	private static final String defaultDateTime = "yyyy-MM-dd HH:mm:ss.SSS";
    
	/**
	 * 현재 날짜와 시간을 "yyyy-MM-dd HH:mm:ss.SSS" 형태로 표시한다.
	 * 
	 * @return String 결과값 2015-01-16 00:49:47.568
	 */
	public static String getDateTime(){				
		return getDateTime(defaultDateTime, System.currentTimeMillis());
	}
	
	/**
	 * 현재 날짜와 시간을 정의한 형태로 표시한다.
	 * 
	 * @param pattern String "yyyy-MM-dd HH:mm:ss.SSS"
	 * @return String 결과값 2015-01-16 00:49:47.568
	 */
	public static String getDateTime(String pattern){
		dateFormat.applyPattern(pattern);		
		return getDateTime(pattern, System.currentTimeMillis());
	}
	
	/**
	 * 지정한 날짜와 시간을 "yyyy-MM-dd HH:mm:ss.SSS" 형태로 표시한다.
	 * 
	 * @param time Long 1421337825721L
	 * @return String 결과값 2015-01-16 01:03:45.721
	 */
	public static String getDateTime(long time){
		dateFormat.applyPattern(defaultDateTime);		
		return getDateTime(defaultDateTime, time);
	}
	
	/**
	 * 지정한 날짜와 시간을 정의한 형태로 표시한다.
	 * 
	 * @param pattern String "yyyy-MM-dd HH:mm:ss.SSS"
	 * @param time Long 1421337825721L
	 * @return String 결과값 2015-01-16 01:03:45.721
	 */
	public static String getDateTime(String pattern, long time){
		dateFormat.applyPattern(pattern);		
		return dateFormat.format(time);
	}
}
