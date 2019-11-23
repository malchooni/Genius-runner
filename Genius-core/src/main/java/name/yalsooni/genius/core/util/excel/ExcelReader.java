package name.yalsooni.genius.core.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 엑셀파일을 읽어 정보를 반환한다.
 * Created by yoon-iljoong on 15. 10. 18..
 */
public class ExcelReader {

    private File excelFile;
    private FileInputStream fis;
    private XSSFWorkbook book;

    private String defaultStr = null;

    /**
     * 엑셀리더 객체생성
     *
     * @param filePath 해당 엑셀의 파일 경로
     * @throws IOException
     */
    public ExcelReader(String filePath) throws IOException {
        this.init(filePath);
    }

    /**
     * 엑셀리더 객체생성
     *
     * @param filePath   해당 엑셀의 파일 경로
     * @param defaultStr 해당 컬럼이 null 일 경우 기본 값
     * @throws IOException
     */
    public ExcelReader(String filePath, String defaultStr) throws IOException {
        this.defaultStr = defaultStr;
        this.init(filePath);
    }

    private void init(String filePath) throws IOException {
        this.excelFile = new File(filePath);

        if (this.excelFile == null || !this.excelFile.isFile()) {
            throw new FileNotFoundException("Excel file not found.");
        }

        this.fis = new FileInputStream(excelFile);
        this.book = new XSSFWorkbook(this.fis);
    }

    /**
     * 해당 컬럼이 null 일 경우 기본 값을 설정한다.
     *
     * @param defaultStr
     */
    public void setDefaultStr(String defaultStr) {
        this.defaultStr = defaultStr;
    }

    /**
     * 대상 시트를 반환한다.
     *
     * @param sheetName 대상 시트 이름
     * @return
     */
    public XSSFSheet getSheet(String sheetName) {
        return this.book.getSheet(sheetName);
    }

    /**
     * 대상 시트의 전체 행과 열의 데이터를 반환하다.
     *
     * @param sheetName 대상 시트 이름
     * @return
     */
    public List<List<String>> getData(String sheetName) {

        XSSFSheet sheet = this.getSheet(sheetName);

        if (sheet == null) {
            return null;
        }

        return this.getData(sheet, 0, 0);
    }

    /**
     * 대상 시트의 전체 행과 열의 데이터를 반환하다.
     *
     * @param sheetName   대상 시트 이름
     * @param startRowIdx 시작 행 번호
     * @param startColIdx 시작 열 번호
     * @return
     */
    public List<List<String>> getData(String sheetName, int startRowIdx, int startColIdx) {

        XSSFSheet sheet = this.getSheet(sheetName);

        if (sheet == null) {
            return null;
        }

        return this.getData(sheet, startRowIdx, startColIdx);
    }

    public LinkedList<Map<String,String>> getDataMap(String sheetName, int startRowIdx, int startColIdx, int subjectRowIdx){

        LinkedList<Map<String,String>> result = new LinkedList<>();
        List<List<String>> allData = getData(sheetName, startRowIdx, startColIdx);
        List<String> subjectData = allData.get(subjectRowIdx);

        for(int i=0; i < allData.size(); i++){

            if( i == subjectRowIdx ){
                continue;
            }

            List<String> data = allData.get(i);

            int dataIdx = 0;
            Map<String, String> row = new HashMap<>();
            for(String column: subjectData){
                row.put(column, data.get(dataIdx));
                dataIdx++;
            }

            result.add(row);
        }

        return result;
    }

    /**
     * 대상 시트의 전체 행과 열의 데이터를 반환하다.
     *
     * @param sheet       대상 시트 이름
     * @param startRowIdx 시작 행 번호
     * @param startColIdx 시작 열 번호
     * @return
     */
    public List<List<String>> getData(XSSFSheet sheet, int startRowIdx, int startColIdx) {

        if (sheet == null) {
            return null;
        }

        XSSFRow xRow;

        List<List<String>> rowsList = new LinkedList<List<String>>();

        int rowNum = sheet.getPhysicalNumberOfRows();
        int lastColNum = 0;

        for (int row = startRowIdx; row < rowNum; row++) {

            xRow = sheet.getRow(row);

            if (xRow != null) {
                if (row == startRowIdx) {
                    lastColNum = xRow.getLastCellNum();
                }
                rowsList.add(this.getRowData(xRow, startColIdx, lastColNum));
            } else {
                rowsList.add(new LinkedList<String>());
            }
        }

        return rowsList;
    }

    /**
     * 대상 시트의 해당 열과 행에 대한 데이터를 반환한다.
     *
     * @param sheetName   대상 시트 이름
     * @param startRowIdx 시작 행 번호
     * @param endRowIdx   시작 행 번호
     * @param startColIdx 시작 열 번호
     * @param endColIdx   종료 열 번호
     * @return
     */
    public List<List<String>> getData(String sheetName, int startRowIdx, int endRowIdx, int startColIdx, int endColIdx) {

        XSSFSheet sheet = this.getSheet(sheetName);

        if (sheet == null) {
            return null;
        }

        return this.getData(sheet, startRowIdx, endRowIdx, startColIdx, endColIdx);
    }

    /**
     * 대상 시트의 해당 열과 행에 대한 데이터를 반환한다.
     *
     * @param sheet       대상 시트 이름
     * @param startRowIdx 시작 행 번호
     * @param endRowIdx   시작 행 번호
     * @param startColIdx 시작 열 번호
     * @param endColIdx   종료 열 번호
     * @return
     */
    public List<List<String>> getData(XSSFSheet sheet, int startRowIdx, int endRowIdx, int startColIdx, int endColIdx) {

        if (sheet == null) {
            return null;
        }

        XSSFRow xRow;

        List<List<String>> rowsList = new LinkedList<List<String>>();

        int rowNum = sheet.getPhysicalNumberOfRows();

        for (int row = startRowIdx; row < endRowIdx; row++) {

            xRow = sheet.getRow(rowNum);

            rowsList.add(this.getRowData(xRow, startColIdx, endColIdx));
        }

        return rowsList;
    }

    /**
     * 특정 행의 지정된 열 데이터를 반환한다.
     *
     * @param xRow        대상 로우
     * @param startColIdx 시작 열 번호
     * @param endColIdx   종료 열 번호
     * @return
     */
    public List<String> getRowData(XSSFRow xRow, int startColIdx, int endColIdx) {

        if (xRow == null) return null;

        XSSFCell xCol;
        List<String> colsList = new LinkedList<String>();

        for (int col = startColIdx; col < endColIdx; col++) {
            xCol = xRow.getCell(col);

            if (xCol != null) {
                colsList.add(getValueOnlyString(xCol));
            } else {
                colsList.add(this.defaultStr);
            }

        }

        return colsList;
    }

    /**
     * 여러 타입의 결과값을 String 으로 변환한다.
     *
     * @param xCol
     * @return
     */
    private String getValueOnlyString(XSSFCell xCol) {

        String value = "";

        switch (xCol.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(xCol)) {
                    value = xCol.getDateCellValue().toString();
                } else {
                    value = String.valueOf(xCol.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                value = xCol.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = xCol.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(xCol.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                value = xCol.getErrorCellString();
                break;
        }

        return value;
    }

    /**
     * 대상 시트의 전체 행에서 원하는 열 들의 데이터를 반환한다.
     *
     * @param sheetName 대상 시트 이름
     * @param cols      원하는 열의 배열
     * @return
     */
    public List<List<String>> getData(String sheetName, int[] cols) {

        XSSFSheet sheet = this.getSheet(sheetName);

        if (sheet == null) {
            return null;
        }

        int rowNum = sheet.getPhysicalNumberOfRows();

        return this.getData(sheet, 1, rowNum, cols);
    }

    /**
     * 대상 시트의 부분적인 행과 원하는 열 들의 데이터를 반환한다.
     *
     * @param sheetName   대상 시트 이름
     * @param startRowIdx 시작 행 인덱스 번호
     * @param EndRowIdx   종료 행 인덱스 번호
     * @param cols        원하는 열의 배열
     * @return
     */
    public List<List<String>> getData(String sheetName, int startRowIdx, int EndRowIdx, int[] cols) {

        XSSFSheet sheet = this.getSheet(sheetName);

        if (sheet == null) {
            return null;
        }

        return this.getData(sheet, startRowIdx, EndRowIdx, cols);
    }

    /**
     * 대상 시트의 부분적인 행과 원하는 열 들의 데이터를 반환한다.
     *
     * @param sheet       대상시트
     * @param startRowIdx 시작 행 인덱스 번호
     * @param EndRowIdx   종료 행 인덱스 번호
     * @param cols        원하는 열의 배열
     * @return
     */
    public List<List<String>> getData(XSSFSheet sheet, int startRowIdx, int EndRowIdx, int[] cols) {

        if (sheet == null) {
            return null;
        }

        XSSFRow xRow;

        List<List<String>> rowsList = new LinkedList<List<String>>();

        for (int row = startRowIdx; row < EndRowIdx; row++) {

            xRow = sheet.getRow(row);

            rowsList.add(this.getRowData(xRow, cols));
        }

        return rowsList;
    }


    /**
     * 특정 행의 부분적인 열의 데이터를 반환한다.
     *
     * @param xRow 대상 로우
     * @param cols 원하는 열의 배열
     * @return
     */
    public List<String> getRowData(XSSFRow xRow, int[] cols) {

        if (xRow == null) return null;

        XSSFCell xCol;
        List<String> colsList = new LinkedList<String>();

        int colsPsc = cols.length;

        for (int colIdx = 0; colIdx < colsPsc; colIdx++) {
            xCol = xRow.getCell(cols[colIdx]);

            if (xCol != null) {
                colsList.add(xCol.getStringCellValue());
            } else {
                colsList.add(this.defaultStr);
            }
        }

        return colsList;
    }

    /**
     * 엑셀파일스트림 객체를 close 한다.
     */
    public void close() {
        if (this.fis != null) {
            try {
                this.fis.close();
            } catch (IOException e) {
            }
        }
    }

}
