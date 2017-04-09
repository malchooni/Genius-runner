package name.yalsooni.genius.common.exception;

import name.yalsooni.genius.common.definition.ErrCode;

/**
 * 입력받은 데이터가 특정 데이터 타입으로 변환에 실패 했을 때의 예외.
 * Created by yoon-iljoong on 2016. 11. 3..
 */
public class InvalidParameterValueTypeException extends Exception{
    public InvalidParameterValueTypeException(Throwable cause) {
        super(ErrCode.G_003_0006, cause);
    }
}
