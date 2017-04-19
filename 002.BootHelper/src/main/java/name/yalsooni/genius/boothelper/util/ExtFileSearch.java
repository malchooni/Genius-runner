package name.yalsooni.genius.boothelper.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 모든 파일(하위 디렉토리를 포함)에 대한 확장자 검색
 * Created by ijyoon on 2015. 12. 17..
 */
public class ExtFileSearch extends Search {

    private FileExtNameFilter filter = null;

    public ExtFileSearch(String extName) {
        this.filter = new FileExtNameFilter();
        this.filter.setExtName(extName);
    }

    /**
     * 검색 확장자 이름을 변경한다.
     *
     * @param extName 확장자 명
     */
    public void changeExtName(String extName) {
        this.filter.setExtName(extName);
    }

    /**
     * 현재 지정된 검색 확장자 이름을 반환한다.
     *
     * @return 현재 확장자 이름
     */
    public String getExtName() {
        return this.filter.getExtName();
    }

    /**
     * 해당 디렉토리의 지정된 확장자를 가진 파일 목록을 반환한다.
     *
     * @param path 경로
     * @return 검색된 파일 목록
     */
    public List<File> getFileList(String path) {
        return getFileList(new File(path));
    }

    /**
     * 해당 디렉토리의 지정된 확장자를 가진 파일 목록을 반환한다.
     *
     * @param path 경로
     * @return 검색된 파일 목록
     */
    public List<File> getFileList(File path) {
        List<File> result = new ArrayList<File>();
        searchFile(result, path, filter);
        return result;
    }

    /**
     * 하위 디렉토리를 포함하고 지정된 확장자를 가진 파일 목록을 반환한다.
     *
     * @param path 경로
     * @return 검색된 파일 목록
     */
    public List<File> getFileListIncludeSubDir(String path) {
        return getFileListIncludeSubDir(new File(path));
    }

    /**
     * 하위 디렉토리를 포함하고 지정된 확장자를 가진 파일 목록을 반환한다.
     *
     * @param path 경로
     * @return 검색된 파일 목록
     */
    public List<File> getFileListIncludeSubDir(File path) {
        List<File> result = new ArrayList<File>();
        searchFileIncludeSubDir(result, path, filter);
        return result;
    }

    /**
     * 지정된 확장자를 가진 파일 목록을 반환한다.
     * @param path 경로
     * @param includeSubDir 하위 디렉토리 포함여부
     * @return 검색된 파일 목록
     */
    public List<File> getFile(String path, boolean includeSubDir){
        return getFile(new File(path), includeSubDir);
    }

    /**
     * 지정된 확장자를 가진 파일 목록을 반환한다.
     * @param path 경로
     * @param includeSubDir 하위 디렉토리 포함여부
     * @return 검색된 파일 목록
     */
    public List<File> getFile(File path, boolean includeSubDir){
        List<File> targetFile;
        if(includeSubDir){
            targetFile = getFileListIncludeSubDir(path);
        }else{
            targetFile = getFileList(path);
        }
        return targetFile;
    }

}
