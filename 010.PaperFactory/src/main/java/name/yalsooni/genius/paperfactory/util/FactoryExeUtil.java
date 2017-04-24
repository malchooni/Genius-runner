package name.yalsooni.genius.paperfactory.util;

import name.yalsooni.genius.paperfactory.definition.ExtType;
import name.yalsooni.genius.paperfactory.definition.Process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 페어퍼팩토리 헬퍼의 유틸
 * Created by ijyoon on 2017. 4. 20..
 */
public class FactoryExeUtil {

    /**
     * 파일 확장자 확인
     * @param templateFilePath 탬플릿 파일 패스
     * @return
     */
    public static boolean isCompressedXML(String templateFilePath){

        String lowerTemplateFilePath = templateFilePath.toLowerCase();
        for(String ext : ExtType.compressedXML){
            if(lowerTemplateFilePath.endsWith(ext)){
                return true;
            }
        }
        return false;
    }

    public static Map<String, String> listToMapMerge(List<String> keys, List<String> values){
        Map<String, String> resultMap = new HashMap<String, String>();

        for(int i=0; i<keys.size(); i++){
            resultMap.put(keys.get(i), values.get(i));
        }

        return resultMap;
    }

}
