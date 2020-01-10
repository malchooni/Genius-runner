package name.yalsooni.genius.runner.inout.input;


import name.yalsooni.boothelper.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 사용자로부터 입력값을 받는다.
 * Created by yoon-iljoong on 2016. 11. 2..
 */
public class InputManager {

    private static String getInputValue() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    /**
     * 서비스아이디를 입력받는다.
     * @return
     * @throws IOException
     */
    public static String getServiceID() throws IOException {
        Log.console("Please enter service ID.");
        return getInputValue();
    }

    /**
     * 엔트리 이름을 입력받는다.
     * @return
     * @throws IOException
     */
    public static String getEntryName() throws IOException {
        Log.console("Please enter entry name.");
        return getInputValue();
    }

    /**
     * 파라미터값을 입력받는다.
     * @return
     * @throws IOException
     */
    public static String getParameterValues() throws IOException {
        Log.console("Please enter parameter values. Separator is comma. (,)");
        return getInputValue();
    }
}
