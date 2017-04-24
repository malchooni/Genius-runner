package name.yalsooni.genius.paperfactory.util;

import name.yalsooni.genius.paperfactory.definition.Code;
import name.yalsooni.genius.paperfactory.definition.PropertyName;
import name.yalsooni.genius.paperfactory.execute.PaperFactoryService;
import name.yalsooni.genius.util.ClassUtil;
import name.yalsooni.genius.util.excel.ExcelReader;
import name.yalsooni.genius.util.reader.PropertyReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 페이퍼팩토리 초기화 도우미
 *
 * Created by ijyoon on 2017. 4. 19..
 */
public class FactoryInitHelper {

    private final static ClassUtil CLASSUTIL = new ClassUtil();

    /**
     * 프로퍼티 파일을 읽어 해당 속성값을 바인딩한다.
     *
     * @param service 페이퍼팩토리 객체
     * @throws Exception
     */
    public static void propertiesRead(PaperFactoryService service) throws Exception {

        // 프로퍼티 파일 읽기
        PropertyReader pr = new PropertyReader();
        Properties properties = null;

        try{
            properties = pr.read(PropertyName.PROPERTYNAME, service.getPropertyFilePath());
        } catch (FileNotFoundException fe) {
            throw new Exception(Code.G_010_0001, fe);
        } catch (Exception e) {
            throw new Exception(Code.G_010_0002, e);
        }

        service.setExcelDataFilePath(properties.getProperty(PropertyName.DATAEXCELFILEPATH));
        service.setFilterListFilePath(properties.getProperty(PropertyName.FILTERLIST));
    }

    /**
     * 필터 속성 파일을 읽어 필터객체 맵을 바인딩한다.
     *
     * @param service 페이퍼팩토리 객체
     * @throws Exception
     */
    public static void makeFilterMap(PaperFactoryService service) throws Exception {

        String filterListFilePath = service.getFilterListFilePath();
        if( filterListFilePath != null){
            PropertyReader pr = new PropertyReader();
            try{
                Properties filterList = pr.read(PropertyName.LIST_FILTER, filterListFilePath);
                service.setFilterInstanceMap(CLASSUTIL.getInstanceMap(filterList));
            } catch (FileNotFoundException fe) {
                throw new Exception(Code.G_010_0003, fe);
            } catch (Exception e) {
                throw new Exception(Code.G_010_0004, e);
            }
        }
    }

    /**
     * 엑셀 파일을 읽어 변환 대상을 보관한다.
     *
     * @param service 페이퍼팩토리 객체
     * @throws IOException
     */
    public static void excelRead(PaperFactoryService service) throws IOException {

        try {
            ExcelReader eReader = new ExcelReader(service.getExcelDataFilePath(), "");
            service.setTempIDList(eReader.getData(PropertyName.LIST_SHEETNAME, 1,1));
            service.setExcelReader(eReader);
        } catch (IOException e) {
            throw new IOException(Code.G_010_0005, e);
        }
    }
}
