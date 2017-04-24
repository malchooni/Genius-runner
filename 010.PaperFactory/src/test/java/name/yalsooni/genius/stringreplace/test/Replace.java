package name.yalsooni.genius.stringreplace.test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by ijyoon on 2017. 4. 18..
 */
public class Replace {

    public void zip(String sourcePath, String targetPath) throws IOException {
        System.out.println("start");

        byte[] searchWord = "[NAME]".getBytes();
        byte[] replaceWord = "일중".getBytes();

        File tempFile = new File(sourcePath);
        File targetFile = new File (targetPath);

        byte[] data = new byte[4096];

        FileInputStream tempInput = new FileInputStream(tempFile);
        FileOutputStream targetOutput = new FileOutputStream(targetFile);

        ZipInputStream zipInputStream = new ZipInputStream(tempInput);
        ZipOutputStream zipOutputStream = new ZipOutputStream(targetOutput);

        ZipEntry zentry = null;
        ZipEntry newZentry = null;
        int readSize;

        while ((zentry = zipInputStream.getNextEntry()) != null) {

            newZentry = new ZipEntry(zentry.getName());

            zipOutputStream.putNextEntry(newZentry);

            if (!zentry.isDirectory()) {

                while ((readSize = zipInputStream.read(data, 0, 4096)) != -1) {
                    for(int i=0; i < readSize; i++){
                        for(int j = 0; j < searchWord.length; ++j) {
                            if (data[i+j] != searchWord[j]) {
                                break;
                            }else if( j == searchWord.length -1 ){
                                System.out.println("searched , Idx : "+i);
                                zipOutputStream.write(replaceWord);
                                i += searchWord.length;
                            }
                        }
                        zipOutputStream.write(data[i]);
                    }
                }
                zipOutputStream.flush();
            }
        }

        zipOutputStream.close();
        zipInputStream.close();

        System.out.println("done");
    }

    public void run(String sourcePath, String targetPath) throws IOException {
        System.out.println("start");

        byte[] searchWord = "[NAME]".getBytes();
        byte[] replaceWord = "일중".getBytes();

        File tempFile = new File(sourcePath);
        File targetFile = new File (targetPath);

        byte[] data = new byte[4096];

        FileInputStream tempInput = new FileInputStream(tempFile);
        FileOutputStream targetOutput = new FileOutputStream(targetFile);


        int readSize = 0;

        do{
            readSize = tempInput.read(data);

            for(int i=0; i < readSize; i++){

                for(int j = 0; j < searchWord.length; ++j) {
                    if (data[i+j] != searchWord[j]) {
                        break;
                    }else if( j == searchWord.length -1 ){
                        System.out.println("searched , Idx : "+i);
                        targetOutput.write(replaceWord);
                        i += searchWord.length;
                    }
                }
                targetOutput.write(data[i]);
            }
//            targetOutput.write(data, 0, readSize);
        }while(readSize == 4096);

        targetOutput.flush();

        targetOutput.close();
        tempInput.close();

        System.out.println("done");
    }

    public void test(){
        byte[] targetWord = "일중이메롱".getBytes();

        System.out.println(targetWord.length);

        for(int i=0; i < targetWord.length; i++){
            System.out.print(targetWord[i]+ " ");
        }
    }

    public static void main(String[] args) throws IOException {

        Replace r = new Replace();
        r.zip(args[0], args[1]);

    }
}
