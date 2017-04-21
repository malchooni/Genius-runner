package name.yalsooni.genius.paperfactory.execute;

import name.yalsooni.genius.paperfactory.definition.ExcelReadIdx;
import name.yalsooni.genius.paperfactory.util.FactoryExeHelper;
import name.yalsooni.genius.paperfactory.util.FactoryInitHelper;
import name.yalsooni.genius.util.Log;
import name.yalsooni.genius.util.excel.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by ijyoon on 2017. 4. 19..
 */
public class PaperFactoryService {

    private String propertyFilePath;

    private String excelDataFilePath;
    private String filterListFilePath;

    private Map<String, Object> filterInstanceMap = null;

    private List<List<String>> tempIDList = null;
    private ExcelReader eReader = null;

    public PaperFactoryService(String propertyFilePath) {
        this.propertyFilePath = propertyFilePath;
    }

    public void initialize() throws Exception{

        // 프로퍼티 속성 값 바인딩
        FactoryInitHelper.propertiesRead(this);

        // 필터목록 객체화
        FactoryInitHelper.makeFilterMap(this);

        // 엑셀파일 읽기
        FactoryInitHelper.excelRead(this);
    }

    public void execute() throws Exception{

        List<String> templateFileList;

        for(List<String> row : tempIDList){
            if(row.get(ExcelReadIdx.TEMP_TARGET).equals(ExcelReadIdx.TARGET)){
                try {

                    templateFileList = FactoryExeHelper.templateFilesToList(row.get(ExcelReadIdx.TEMP_DIR));

                    if(templateFileList.size() < 1) continue;

                    List<List<String>> tempData = eReader.getData(row.get(ExcelReadIdx.TEMP_ID));

                    if(tempData == null) continue ;

                    List<String> subject = tempData.get(ExcelReadIdx.TEMP_DATA_SUBJECT_IDX);

                    Log.console(row.get(ExcelReadIdx.TEMP_ID) +  " :  Metadata.");
                    Log.console(subject);

                    FactoryExeHelper.createNewFileFromTemplate(this, row.get(ExcelReadIdx.TEMP_DIR), templateFileList, tempData);

                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    public void setExcelDataFilePath(String excelDataFilePath) {
        this.excelDataFilePath = excelDataFilePath;
    }

    public String getExcelDataFilePath() {
        return excelDataFilePath;
    }

    public void setFilterListFilePath(String filterListFilePath) {
        this.filterListFilePath = filterListFilePath;
    }

    public String getFilterListFilePath() {
        return filterListFilePath;
    }

    public String getPropertyFilePath() {
        return propertyFilePath;
    }

    public void setFilterInstanceMap(Map<String, Object> filterInstanceMap) {
        this.filterInstanceMap = filterInstanceMap;
    }

    public Map<String, Object> getFilterInstanceMap() {
        return filterInstanceMap;
    }

    public void setTempIDList(List<List<String>> tempIDList) {
        this.tempIDList = tempIDList;
    }

    public List<List<String>> getTempIDList() {
        return tempIDList;
    }

    public void setExcelReader(ExcelReader eReader) {
        this.eReader = eReader;
    }

    public ExcelReader geteReader() {
        return eReader;
    }
}
