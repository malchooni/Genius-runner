package name.yalsooni.genius.pf.filter;

import name.yalsooni.genius.paperfactory.filter.PaperFactoryFilter;
import name.yalsooni.genius.util.Log;

/**
 * 테스트 필터
 * Created by ijyoon on 2017. 4. 25..
 */
public class AddressFilter implements PaperFactoryFilter {

    /**
     * data + " return"
     * @param data 변환할 데이터.
     * @return
     * @throws Exception
     */
    public String replace(String data) throws Exception {
        Log.console("receive data : " + data);
        return data + " return";
    }
}
