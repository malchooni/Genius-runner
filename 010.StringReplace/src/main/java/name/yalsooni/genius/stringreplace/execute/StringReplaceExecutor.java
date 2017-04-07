package name.yalsooni.genius.stringreplace.execute;


import name.yalsooni.genius.stringreplace.replace.StringReplace;
import name.yalsooni.genius.stringreplace.replace.vo.StringReplaceInfo;
import name.yalsooni.genius.util.Log;

/**
 * 엑셀파일을 기반으로 특정 탬플릿 파일의 단어를 치환시켜 준다.
 * 1. 지니어스 프로퍼티 파일 경로.
 *
 * @author ijyoon
 */
public class StringReplaceExecutor {

	public static void main(String[] args) {

		if( args.length < 1 || args[0] == null ){
			Log.console("파라미터 값을 입력하세요.");
			Log.console("1. genius property file path");
			return;
		}

		long startTime = System.currentTimeMillis();

		try{

			StringReplaceInfo info = new StringReplaceInfo();

			info.setPropertyFilePath(args[0]);

			Log.console(" ** StringPattern Start. **  Elasped Time : ");

			StringReplace sp = new StringReplace(info);
			sp.initialize();
			Log.console(" StringPattern initialize done.");

			sp.execute();
			Log.console(" StringPattern execute done.");

		}catch(Exception e){
			Log.console(e.getMessage(), e);
		}finally{
			Log.console(" ** StringPattern End. **  Elasped Time : " + (System.currentTimeMillis() - startTime) + " ms");
		}

	}

}
