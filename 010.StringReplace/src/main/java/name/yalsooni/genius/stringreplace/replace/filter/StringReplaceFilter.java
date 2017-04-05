package name.yalsooni.genius.stringreplace.replace.filter;

/**
 * String Pattern Filter
 * 
 * 
 * @author ijyoon
 *
 */
public interface StringReplaceFilter {

	/**
	 * 사용자 정의 필터.
	 * 
	 * @param data 변환할 데이터.
	 * @return 변환된 데이터.
	 */
	String replace(String data) throws Exception;
}
