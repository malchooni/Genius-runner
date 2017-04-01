package name.yalsooni.genius.util.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * 확장자 파일 필터
 * Created by ijyoon on 2015. 12. 17..
 */
public class FileExtNameFilter implements FileFilter {

    private String ext = "";

    /**
     * 보관하고 있는 확장자 명을 반환한다.
     * @return
     */
    public String getExtName() {
        return ext;
    }

    /**
     * 확장자명을 삽입한다.
     * @param ext
     */
    public void setExtName(String ext) {
        this.ext = ext.toLowerCase();
    }

    /**
     * 파일 확장자명이 맞는지 확인한다.
     * 숨겨진 파일 제외.
     * @param pathname
     * @return
     */
    public boolean accept(File pathname) {
        if (pathname.isFile() && !pathname.isHidden()) {
            return pathname.getName().toLowerCase().endsWith(ext);
        }

        return false;
    }
}
