package name.yalsooni.genius.paperfactory.filter;

/**
 * 사용자 정의 필터 인터페이스
 * Created by ijyoon on 2017. 4. 22..
 */
public interface PaperFactoryFilter {

        /**
         * 사용자 정의 필터.
         *
         * @param data 변환할 데이터.
         * @return 변환된 데이터.
         */
        String replace(String data) throws Exception;
}
