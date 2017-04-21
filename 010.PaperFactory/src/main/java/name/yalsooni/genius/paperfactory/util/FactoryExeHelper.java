package name.yalsooni.genius.paperfactory.util;

import name.yalsooni.genius.paperfactory.definition.Code;
import name.yalsooni.genius.paperfactory.definition.ExcelReadIdx;
import name.yalsooni.genius.paperfactory.definition.Process;
import name.yalsooni.genius.paperfactory.execute.PaperFactoryService;
import name.yalsooni.genius.stringreplace.replace.filter.StringReplaceFilter;
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

            Log.console("ROW NUMBER : "+ (rowNum+1));

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

                    // @ 기준으로 왼쪽은 아웃풋 패스 + 하위 디렉토리, 오른쪽은 파일 이름이 됌.
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
                    replaceFileData(tempDir + "/" + tempFileName, filePath + "/" + fileName, dataMap);

                }catch (Exception e){
                    Log.console(Code.G_010_0009, e);
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
    private static String replaceFileName(PaperFactoryService service, String targetTemp, List<String> subject, List<String> values) throws Exception{

        String key = null;
        String value = null;
        Object obj = null;

        for(int j=ExcelReadIdx.DATA_COLS_START_IDX; j<values.size(); j++){

            key = subject.get(j);

            obj = service.getFilterInstanceMap().get(key);
            if(obj == null){
                value = values.get(j);
            }else{
                try{
                    value = ((StringReplaceFilter) obj).replace(values.get(j));
                }catch(Exception e){
                    throw new Exception(Code.G_010_0007 + " : " + e.getMessage(), e);
                }
            }

            try{
                targetTemp = targetTemp.replace("\\["+key+"\\]", value);
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
     * @param subject
     * @param values
     * @throws FileNotFoundException
     */
    private static void replaceFileData(String templateFilePath , String newFilePath, Map<String,String> dataMap) throws Exception {

        try {
            FileInputStream tempInput = new FileInputStream(new File(templateFilePath));
            FileOutputStream targetOutput = new FileOutputStream(new File(newFilePath));

            if (FactoryExeUtil.isCompressedXML(templateFilePath)) {
                replaceOnZipStream(tempInput, targetOutput, dataMap);
            } else {
                replaceOnStream(tempInput, targetOutput, dataMap);
            }
        }catch (FileNotFoundException fnfe){

        }catch (IOException ioe){

        }catch (Exception e){

        }
    }

    /**
     * 압축 파일
     * @param is
     * @param os
     * @param subject
     * @param values
     * @throws IOException
     */
    private static void replaceOnZipStream(InputStream is, OutputStream  os, Map<String,String> dataMap) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(is);
        ZipOutputStream zipOutputStream = new ZipOutputStream(os);

        ZipEntry zentry = null;
        ZipEntry newZentry = null;

        while ((zentry = zipInputStream.getNextEntry()) != null) {
            newZentry = new ZipEntry(zentry.getName());
            zipOutputStream.putNextEntry(newZentry);

            if (!zentry.isDirectory()) {
                replaceOnStream(zipInputStream, zipOutputStream, dataMap);
            }

        }

    }

    private static void replaceOnStream(InputStream is, OutputStream  os, Map<String,String> dataMap) throws IOException {

        byte[] data = new byte[Process.BYTE_PACKET_SIZE];
        int readSize;
        int offset = 0;
        int length = Process.BYTE_PACKET_SIZE;

        byte[] targetSubject = new byte[0];
        int vaildLength = 0;

        while ((readSize = is.read(data, offset, length)) != -1) {

            for(int i=offset; i < readSize; i++){

                if(data[i] == Process.PREFIX_MARK){

                    /**
                     * todo: 테스트 필요
                     */
                    if( readSize > Process.LIMIT_SUBJECT_BYTE_SIZE + 1 && readSize - i >= Process.LIMIT_SUBJECT_BYTE_SIZE + 1){

                        offset = readSize - i - 1;
                        length = readSize - offset;

                        System.arraycopy(data, offset, data, 0, length);
                        break;
                    }

                    int j = i+1;
                    targetSubject = new byte[Process.LIMIT_SUBJECT_BYTE_SIZE];
                    vaildLength=0;

                    for( ; j < readSize; j++, vaildLength++){
                        if(data[j] == Process.POSTFIX_MARK){
                            byte[] key = new byte[vaildLength];
                            System.arraycopy(targetSubject, 0, key, 0, vaildLength);

                            String value = dataMap.get(new String(key));

                            if(value != null){
                                os.write(value.getBytes());
                            }else{
                                os.write(data, i, vaildLength + 2);
                            }
                            break;
                        }
                        targetSubject[vaildLength] = data[j];
                    }
                    i = j + 1;
                }
                os.write(data[i]);
            }
        }
        
        if(vaildLength != 0){
            os.write(Process.PREFIX_MARK);
            os.write(targetSubject, 0, vaildLength);
        }

        os.flush();
    }

    private static boolean searchSubject(List<byte[]> subjectByteArr, int targetIdx, byte target){

        for(byte[] subject : subjectByteArr){
            if(targetIdx < subject.length && target == subject[targetIdx]){
                return true;
            }
        }

        return false;
    }

    private static int getSubjectIdx(List<String> subjects, String target){
        for(int i=0; i<subjects.size(); i++){
            if(subjects.get(i).equals(target)){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args){

        List<String> subject = new ArrayList<String>();
        List<String> values = new ArrayList<String>();

        subject.add("NAME");
        subject.add("AGE");
        values.add("윤일중");
        values.add("33");

        Map<String, String> dataMap = FactoryExeUtil.listToMapMerge(subject, values);

        try {
            replaceFileData("/Users/ijyoon/Desktop/1.txt","/Users/ijyoon/Desktop/2.txt", dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("done");
    }
}
