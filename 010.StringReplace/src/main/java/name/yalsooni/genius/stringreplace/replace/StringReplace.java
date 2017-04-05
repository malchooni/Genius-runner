package name.yalsooni.genius.stringreplace.replace;

import name.yalsooni.genius.stringreplace.replace.code.StringReplaceErrCode;
import name.yalsooni.genius.stringreplace.replace.filter.StringReplaceFilter;
import name.yalsooni.genius.stringreplace.replace.vo.StringReplaceInfo;
import name.yalsooni.genius.util.ClassUtil;
import name.yalsooni.genius.util.Log;
import name.yalsooni.genius.util.excel.ExcelReader;
import name.yalsooni.genius.util.file.FileSupport;
import name.yalsooni.genius.util.reader.PropertyReader;

import java.io.File;
import java.util.*;

/**
 * 엑셀 기반의 문자 치환.
 *
 * Created by yoon-iljoong on 2015. 11. 2..
 */
public class StringReplace {

	private final String PROPERTY_MAKER = "MAKER";
	private final String LIST_SHEETNAME = "TemplateList";
	private final String LIST_FILTER = "FilterList";
	private final String TARGET = "T";
	private final int START_IDX = 3;
	private final int TARGET_IDX = START_IDX-1;

	private Properties makerProperty = null;
	private Properties filterList = null;
	private PropertyReader pr = null;

	private FileSupport fSupport = null;
	private ExcelReader eReader = null;
	private ClassUtil classUtil = null;

	private StringReplaceInfo defaultInfo = null;

	private List<List<String>> tempIDList = null;
	private Map<String, Object> filterInstanceMap = null;
	
	public StringReplace(StringReplaceInfo defaultInfo) throws NullPointerException{
		if(defaultInfo == null){
			throw new NullPointerException(StringReplaceErrCode.G_011_0001);
		}else{
			this.defaultInfo = defaultInfo;
		}
	}

	/**
	 * 초기화.
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		pr = new PropertyReader();
		makerProperty = pr.read(PROPERTY_MAKER, defaultInfo.getPropertyFilePath());

		defaultInfo.setExcelDataFilePath(makerProperty.getProperty("STRINGREPLACE.DATAEXCELFILEPATH"));
		defaultInfo.setFilterListFilePath(makerProperty.getProperty("STRINGREPLACE.FILTERLIST"));
		
		String excelFilePath = defaultInfo.getExcelDataFilePath();
		if(excelFilePath == null){
			throw new NullPointerException(StringReplaceErrCode.G_011_0002);
		}

		fSupport = new FileSupport();
		eReader = new ExcelReader(excelFilePath, "");
		classUtil = new ClassUtil();
		tempIDList = eReader.getData(LIST_SHEETNAME, 1,1);

		// STRINGPATTERN.FILTERLIST 
		String filterListFilePath = defaultInfo.getFilterListFilePath();
		if( filterListFilePath != null){
			filterList = pr.read(LIST_FILTER, filterListFilePath);
			filterInstanceMap = classUtil.getInstanceMap(filterList);
		}
	}

	/**
	 * 실행.
	 * @throws Exception
	 */
	public void execute() throws Exception {

		String tempID = null;
		String tempDir = null;
		String target = null;

		for(List<String> row : tempIDList){

			target = row.get(2);

			if(target.equals(TARGET)){
				tempID = row.get(0);
				tempDir = row.get(1);

				try{
					this.replaceExecute(tempID, tempDir);
				}catch(Exception e){
					Log.console(e.getMessage(), e);
					continue;
				}
			}

		}
	}

	/**
	 * 변환 작업.
	 *
	 * @param tempID
	 * @param tempDir
	 * @throws Exception
	 */
	private void replaceExecute(String tempID, String tempDir) throws Exception{

		Map<String, String> fileListData = this.templateFileToMap(new File(tempDir));

		if(fileListData.size() < 1) return;

		List<List<String>> tempData = eReader.getData(tempID);

		if(tempData == null) return ;

		List<String> subject = tempData.get(0);

		Log.console(tempID +  " :  Metadata.");
		Log.console(subject);

		this.replaceTempFile(fileListData, tempData);

	}

	/**
	 * temp file 데이터를 map으로 반환
	 * @param tempDirectory
	 * @return
	 * @throws OutOfMemoryError
	 */
	private Map<String, String> templateFileToMap(File tempDirectory) throws OutOfMemoryError, Exception {

		if(!tempDirectory.isDirectory()) throw new Exception(StringReplaceErrCode.G_011_0003 + tempDirectory);

		File[] files = tempDirectory.listFiles();

		Map<String, String> result = new HashMap<String, String>();

		for(File file : files){
			if(!file.isFile()) continue;

			result.put(file.getName(), fSupport.getData(file));
		}

		return result;
	}

	/**
	 * Template 데이터의 값을 치환한다
	 * @param dataGroup
	 * @throws Exception 
	 */
	private void replaceTempFile(Map<String, String> dataGroup, List<List<String>> tempData) throws Exception {

		List<String> values = null;
		Iterator<String> fileListKey = null;
		
		List<String> subject = tempData.get(0);

		for(int i=1; i < tempData.size(); i++){
			
			values = tempData.get(i);
			
			Log.console("ROW NUMBER : "+ (i+1));
			
			if(values.size() < TARGET_IDX){
				continue;
			}
			
			if(!values.get(TARGET_IDX).equals("T")){
				continue;
			}
						
			fileListKey = dataGroup.keySet().iterator();
			
			while (fileListKey.hasNext()) {

				String fileKey = null;
				String filePath = null;
				String fileName = null;

				fileKey = fileListKey.next();

				// @ 기준으로 왼쪽은 아웃풋 패스 + 하위 디렉토리, 오른쪽은 파일 이름이 됌.
				String[] splitFileName = fileKey.split("@");

				try{
					
					// 파일 경로 및 이름 치환
					if (splitFileName.length > 1) {
						filePath = splitFileName[0].replaceAll("_", "/");
						filePath = this.replace(filePath, subject, values);
						fileName = this.replace(splitFileName[1], subject, values);
					} else {
						fileName = this.replace(splitFileName[0], subject, values);
					}

					// 아웃풋 파일 경로 생성
					if (filePath != null) {
						filePath = values.get(1) + "/" + filePath;
					} else {
						filePath = values.get(1);
					}

					// 데이터 치환.
					String resultData = this.replace(dataGroup.get(fileKey), subject, values);

					// 아웃풋 디렉토리 생성
					fSupport.mkdir(filePath);

					// 아웃풋 파일 생성.
					String resultFile = filePath + "/" + fileName;
					fSupport.dataToFile(new File(resultFile), resultData);

					Log.console(resultFile + " created.");
					
				}catch (Exception e){
					Log.console(e);
					continue;
				}
			}
		}
	}

	/**
	 * 해당 메타값을 데이터로 치환한다.
	 * @param targetTemp
	 * @param subject
	 * @param values
	 * @return
	 * @throws Exception 
	 */
	private String replace(String targetTemp, List<String> subject, List<String> values) throws Exception{

		String key = null;
		String value = null;
		Object obj = null;
		
		for(int j=START_IDX; j<values.size(); j++){

			key = subject.get(j);
			
			obj = filterInstanceMap.get(key);
			if(obj == null){
				value = values.get(j);
			}else{
				try{
					value = ((StringReplaceFilter) obj).replace(values.get(j));
				}catch(Exception e){
					throw new Exception(StringReplaceErrCode.G_011_0005 + " : " + e.getMessage(), e);
				}
				obj = null;
			}
			
			try{
				targetTemp = targetTemp.replaceAll("\\["+key+"\\]", value);
			}catch (Exception e){
				throw new Exception(StringReplaceErrCode.G_011_0004 + "COLUMN NUMBER : "+(j+1)+", KEY : "+key+", VALUE : "+ value, e);
			}
		}

		return targetTemp;
	}
}
