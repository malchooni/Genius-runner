package name.yalsooni.genius.runner.definition;

/**
 * 지니어스 에러코드 정의
 * @author ijyoon
 *
 */
public interface ErrCode {

	String GR_INIT = "initialization error.";
	String GR_I001 = "[GR-I001] invalid 'GeniusLibPath' system property.";
    String GR_I002 = "[GR-I002] GeniusClassLoader error.";
    String GR_I003 = "[GR-I003] not found 'GeniusLibPath' directory.";

	String GR_EXEC = "execution error.";
	String GR_E001 = "[GR-E001] Invalid Service ID.";
    String GR_E002 = "[GR-E002] Not found Service ID.";
    String GR_E003 = "[GR-E003] Invalid entry name.";
    String GR_E004 = "[GR-E004] Not found entry name.";
    String GR_E005 = "[GR-E005] Invalid parameter value.";

}
