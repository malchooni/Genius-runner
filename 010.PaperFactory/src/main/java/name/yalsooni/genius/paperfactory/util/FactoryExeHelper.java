package name.yalsooni.genius.paperfactory.util;

import name.yalsooni.genius.paperfactory.definition.Code;
import name.yalsooni.genius.paperfactory.definition.ExcelReadIdx;
import name.yalsooni.genius.paperfactory.definition.Process;
import name.yalsooni.genius.paperfactory.execute.PaperFactoryService;
import name.yalsooni.genius.paperfactory.filter.PaperFactoryFilter;
import name.yalsooni.genius.util.Log;
import name.yalsooni.genius.util.file.FileSupport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 페이퍼팩토리 실행 도우미
 *
 * Created by ijyoon on 2017. 4. 19..
 */
public class FactoryExeHelper {

    private static final FileSupport FILE_SUPPORT = new FileSupport();

    /**
     * temp 디렉토리의 파일 목록을 반환한다.
     * @param tempDirectoryPath temp 디렉토리 경로
     * @return temp 디렉토리의 파일 목록
     * @throws Exception
     */
    public static List<String> templateFilesToList(String tempDirectoryPath) throws Exception {

        File tempDirectory = new File(tempDirectoryPath);
        if(!tempDirectory.isDirectory()) throw new Exception(Code.G_010_0006 + " - " + tempDirectory);

        File[] files = tempDirectory.listFiles();

        List<String> fileList = new ArrayList<String>();

        for(File file : files){
            if(!file.isFile()) continue;
            fileList.add(file.getName());
        }

        return fileList;
    }

    /**
     * Template을 기반으로 새로운 파일을 생성한다.
     * @param service
     * @param tempDir
     * @param tempData
     * @throws Exception
     */
    public static void  createNewFileFromTemplate(PaperFactoryService service, String tempDir, List<String> tempFileList, List<List<String>> tempData) throws Exception {

        List<String> subject = tempData.get(ExcelReadIdx.TEMP_DATA_SUBJECT_IDX);
        List<String> values = null;

        for(int rowNum = ExcelReadIdx.TEMP_DATA_START_IDX; rowNum < tempData.size(); rowNum++){

            values = tempData.get(rowNum);

            Log.console("ROW NUMBER : "+ (rowNum+1) + ", Start Replace.");

            if(values.size() < ExcelReadIdx.DATA_COLS_TARGET_IDX){
                continue;
            }

            if(!values.get(ExcelReadIdx.DATA_COLS_TARGET_IDX).equals("T")){
                continue;
            }

            for(String tempFileName : tempFileList){

                try{
                    String filePath = null;
                    String fileName;

                    // @ 기준으로 왼쪽은 아웃풋 패스 + 하위 디렉토리, 오른쪽은 파일 이름이 됨.
                    String[] splitFileName = tempFileName.split("@");

                    // 파일 경로 및 이름 치환
                    if (splitFileName.length > 1) {
                        filePath = splitFileName[0].replace("_", "/");
                        filePath = replaceFileName(service, filePath, subject, values);
                        fileName = replaceFileName(service, splitFileName[1], subject, values);
                    } else {
                        fileName = replaceFileName(service, splitFileName[0], subject, values);
                    }

                    // 아웃풋 파일 경로 생성
                    if (filePath != null) {
                        filePath = values.get(1) + "/" + filePath;
                    } else {
                        filePath = values.get(1);
                    }

                    // 아웃풋 디렉토리 생성
                    FILE_SUPPORT.mkdir(filePath);

                    Map<String,String> dataMap = FactoryExeUtil.listToMapMerge(subject, values);
                    // 아웃풋 파일 생성.
                    replaceFileData(service, tempDir + "/" + tempFileName, filePath + "/" + fileName, dataMap);

                }catch (Exception e){
                    Log.console(Code.G_010_0009, e);
                    continue;
                }
            }
            Log.console(values);
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
    private static String replaceFileName(PaperFactoryService service, String targetTemp, List<String> subject, List<String> values) throws Exception{

        String key = null;
        String value = null;
        PaperFactoryFilter filter = null;

        for(int j=ExcelReadIdx.DATA_COLS_START_IDX; j<values.size(); j++){

            key = subject.get(j);

            filter = (PaperFactoryFilter) service.getFilterInstanceMap().get(key);
            if(filter == null){
                value = values.get(j);
            }else{
                try{
                    value = filter.replace(values.get(j));
                }catch(Exception e){
                    throw new Exception(Code.G_010_0007 + " : " + e.getMessage(), e);
                }
            }

            try{
                targetTemp = targetTemp.replace("["+key+"]", value);
            }catch (Exception e){
                throw new Exception(Code.G_010_0008 + "COLUMN NUMBER : "+(j+1)+", KEY : "+key+", VALUE : "+ value, e);
            }
        }

        return targetTemp;
    }

    /**
     * 새로운 파일 생성
     * @param templateFilePath
     * @param newFilePath
     * @param dataMap
     * @throws Exception
     */
    private static void replaceFileData(PaperFactoryService service, String templateFilePath , String newFilePath, Map<String,String> dataMap) throws Exception {

        FileInputStream tempInput = null;
        FileOutputStream targetOutput = null;

        try {
            tempInput = new FileInputStream(new File(templateFilePath));
            targetOutput = new FileOutputStream(new File(newFilePath));

            if (FactoryExeUtil.isCompressedXML(templateFilePath)) {
                replaceOnZipStream(service, tempInput, targetOutput, dataMap);
            } else {
                replaceOnStream(service, tempInput, targetOutput, dataMap);
            }
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{ targetOutput.close(); } catch (Exception e){}
            try{ tempInput.close(); } catch (Exception e){}
        }
    }

    /**
     * 압축 파일
     * @param is
     * @param os
     * @param dataMap
     * @throws IOException
     */
    private static void replaceOnZipStream(PaperFactoryService service, InputStream is, OutputStream  os, Map<String,String> dataMap) throws IOException {

        ZipInputStream zipInputStream = null;
        ZipOutputStream zipOutputStream = null;

        try{
            zipInputStream = new ZipInputStream(is);
            zipOutputStream = new ZipOutputStream(os);

            ZipEntry zentry = null;
            ZipEntry newZentry = null;

            while ((zentry = zipInputStream.getNextEntry()) != null) {
                newZentry = new ZipEntry(zentry.getName());
                zipOutputStream.putNextEntry(newZentry);

                if (!zentry.isDirectory()) {
                    replaceOnStream(service, zipInputStream, zipOutputStream, dataMap);
                }
            }
        }catch (IOException ioe){
            throw ioe;
        }finally {
            try{ zipOutputStream.close(); } catch (Exception e){}
            try{ zipInputStream.close(); } catch (Exception e){}
        }
    }

    private static void replaceOnStream(PaperFactoryService service, InputStream is, OutputStream  os, Map<String,String> dataMap) throws IOException {

        byte[] data = new byte[Process.BYTE_PACKET_SIZE];
        byte[] tempSubject = new byte[Process.LIMIT_SUBJECT_BYTE_SIZE + 2];

        int readSize;
        int dataStartIdx = 0;
        int tempSubjectIdx = 0;

        boolean scanTempSubject = false;



        while ((readSize = is.read(data)) != -1) {

            for(int idx=0; idx < readSize; idx++){

                if (scanTempSubject == false && idx == readSize -1){
                    os.write(data, dataStartIdx, readSize - dataStartIdx);
                    dataStartIdx = 0;
                }else if(scanTempSubject && data[idx] == Process.POSTFIX_MARK) {
                    tempSubject[tempSubjectIdx] = data[idx];

                    String key = new String(tempSubject, 1, tempSubjectIdx - 1);
                    String value = dataMap.get(key);

                    if(value != null){

                        PaperFactoryFilter filter = (PaperFactoryFilter) service.getFilterInstanceMap().get(key);

                        if(filter != null){
                            try {
                                value = filter.replace(value);
                            } catch (Exception e) {
                                Log.console(Code.G_010_0010, e);
                            }
                        }

                        os.write(value.getBytes());
                    }else{
                        os.write(tempSubject, 0, tempSubjectIdx + 1);
                    }

                    dataStartIdx = (readSize == idx + 1) ? 0 : idx + 1;
                    tempSubjectIdx = 0;
                    scanTempSubject = false;

                }else if( scanTempSubject && tempSubjectIdx == Process.LIMIT_SUBJECT_BYTE_SIZE + 1 ){
                    tempSubject[tempSubjectIdx] = data[idx];
                    os.write(tempSubject, 0, tempSubject.length);

                    dataStartIdx = (readSize == idx + 1) ? 0 : idx + 1;

                    tempSubjectIdx = 0;
                    scanTempSubject = false;

                }else if (scanTempSubject && data[idx] == Process.PREFIX_MARK){
                    os.write(tempSubject, 0, tempSubjectIdx);

                    dataStartIdx = (readSize == idx + 1) ? 0 : idx + 1;
                    tempSubjectIdx = 0;
                    tempSubject[tempSubjectIdx++] = data[idx];

                }else if (data[idx] == Process.PREFIX_MARK){
                    os.write(data, dataStartIdx, idx-dataStartIdx);

                    scanTempSubject = true;
                    tempSubject[tempSubjectIdx++] = data[idx];

                    dataStartIdx = idx;

                }else if (scanTempSubject) {
                    tempSubject[tempSubjectIdx++] = data[idx];
                }
            }
        }

        os.flush();
    }
}
