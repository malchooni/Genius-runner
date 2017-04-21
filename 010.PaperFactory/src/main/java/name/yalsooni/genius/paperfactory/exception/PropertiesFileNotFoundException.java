package name.yalsooni.genius.paperfactory.exception;

import name.yalsooni.genius.paperfactory.definition.Code;

import java.io.FileNotFoundException;

/**
 * 프로퍼티 파일을 찾을 수 없는 예외.
 * Created by ijyoon on 2017. 4. 19..
 */
public class PropertiesFileNotFoundException extends FileNotFoundException {
    public PropertiesFileNotFoundException() {
        super(Code.G_010_0001);
    }
}
