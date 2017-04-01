package name.yalsooni.genius.common.definition;

/**
 * 지니어스 서비스 정의
 * @author ijyoon
 *
 */
public interface Service {

	String RUNTIME 	= "Runtime";
	String UTIL 	= "Util";

	/**
	 * 초기화
	 * @throws Exception
	 */
	void initialize() throws Exception;
	
	/**
	 * 실행
	 * @throws Exception
	 */
	void execute() throws Exception;
	
}
