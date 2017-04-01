package name.yalsooni.genius.util.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.List;

/**
 * 파일 찾기.
 * @author ijyoon
 *
 */
public abstract class Search {

	/**
	 * 필터 적용, 하위디렉토리 포함한 파일 검색
	 * @param result 결과 목록
	 * @param path 경로
     * @param filter 사용필터
     */
	protected void searchFileIncludeSubDir(List<File> result, File path, FileFilter filter){

		File[] allFiles = path.listFiles();

		if (allFiles == null && 1 > allFiles.length) return;

		searchFile(result, path, filter);

		for(File file : allFiles){
			if(file.isDirectory()){
				searchFileIncludeSubDir(result, file, filter);
			}
		}
	}

	/**
	 * 필터적용, 파일 검색
	 * @param result 결과 목록
	 * @param path 경로
	 * @param filter 사용필터
     */
	protected void searchFile(List<File> result, File path, FileFilter filter){

		File targetFiles[] = path.listFiles(filter);

		if(targetFiles.length > 0){
			Collections.addAll(result, targetFiles);
		}
	}

	/**
	 * 구현 메소드 - 결과 파일 리턴
	 * @param path 경로
     * @return 결과 목록
     */
	public abstract List<File> getFileList(File path);
}
