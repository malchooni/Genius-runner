package name.yalsooni.genius.util.file;

import java.io.*;

/**
 * 파일 관련 유틸.
 * @author yoon-iljoong
 *
 */
public class FileSupport {

	/**
	 * 파일의 데이터를 String으로 반환한다.
	 *
	 * @param file
	 * @return
	 * @throws IOException, OutOfMemoryError
	 */
	public String getData(File file) throws IOException{
		
		String line = null;
		StringBuilder result = new StringBuilder();
		
		BufferedReader br = null;
		
		try{
			br = new BufferedReader(new FileReader(file));
			
			while( (line = br.readLine() ) != null){
				result.append(line);
			}
			
		}catch(IOException e){
			throw e;
		}catch(NullPointerException ne){
			throw ne;
		}finally{			
			if(br != null) br.close();
		}
		
		return result.toString();
	}
	
	public String getData(String filePath) throws IOException{
		return this.getData(new File(filePath));
	}
	
	/**
	 * 데이터를 파일에 출력한다.
	 * 
	 * @param output
	 * @param data
	 * @throws IOException
	 */
	public void dataToFile(File output, String data) throws IOException{
		
		if(output.isFile() || output.exists()){
			output.delete();
		}
		
		output.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(output);
		fos.write(data.getBytes());
		fos.flush();
		
		if(fos != null) try{ fos.close(); } catch (Exception e) {}
	}

	/**
	 * 디렉토리 생성.
	 * 
	 * @param dirPath
	 */
	public void mkdir(String dirPath){
		File dir = new File(dirPath);

		if(!dir.isDirectory()){
			dir.mkdirs();
		}
	}
}
