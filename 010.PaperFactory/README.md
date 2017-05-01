# Genius - 010. PaperFactory

템플릿 문서와 데이터 엑셀을 기반으로 새로운 파일을 생성하는 유틸이다.
* [Download](https://github.com/yalsooni/Genius/releases/tag/v1.0)

~~~
지원 확장자 : xlsx, docx, pptx, 기타 텍스트 파일 (txt, xml, html ....)
~~~

## 1. 프로퍼티 설정

 - 010_paperfactory.properties - 기본설정 파일

~~~
 - PAPERFACTORY.DATAEXCELFILEPATH : 데이터 엑셀의 경로
 - PAPERFACTORY.FILTERLIST : 필터 목록 파일의 경로
~~~

 - 010_paperfactory.list - 필터 목록 파일
  
> name.yalsooni.genius.paperfactory.filter.PaperFactoryFilter 인터페이스를 구현한 클래스.
~~~
 #Column Name = Filter class
 FILTER_COLUMN = ex.test.MyFilter
~~~

## 2. 엑셀 양식

- ﻿[TemplateList] Sheet

![TemplateList](https://github.com/yalsooni/Genius/blob/master/op/img/010/010_temp.png)

~~~
 - No.
    순번
 - TemplateID
    데이터가 저장되어있는 Sheet 이름
 - TemplateDirectoryPath
    템플릿 파일의 루트 디렉토리 경로
 - TARGET
    'T' 일 경우 수행, 나머지 값은 무시.
~~~ 

- [TemplateID에 정의된 이름] Sheet

![TemplateID-Data](https://github.com/yalsooni/Genius/blob/master/op/img/010/010_data.png)

~~~
 - No.
    순번
 - OUTPUTPATH
    새로 생성될 파일의 루트 디렉토리 경로
 - TARGET
    'T' 일 경우 수행, 나머지 값은 무시.
~~~

## 3. 필터 구현

- 010_paperfactory.list 파일에 정의된 필터 목록을 지정한다.

![TemplateID-Data](https://github.com/yalsooni/Genius/blob/master/op/img/010/010_filter.png)

> Column Name = Filter class

~~~
﻿ADDRESS_F = name.yalsooni.genius.pf.filter.AddressFilter
~~~

> 예제 소스

~~~java
package name.yalsooni.genius.pf.filter;

import name.yalsooni.genius.paperfactory.filter.PaperFactoryFilter;
import name.yalsooni.genius.util.Log;

/**
 * 테스트 필터
 * Created by ijyoon on 2017. 4. 25..
 */
public class AddressFilter implements PaperFactoryFilter {

    /**
     * data + " return"
     * @param data 변환할 데이터.
     * @return
     * @throws Exception
     */
    public String replace(String data) throws Exception {
        Log.console("receive data : " + data);
        return data + " return";
    }
}
~~~

구현된 필터는 jar로 압축하여 $GENIUS_HOME/lib/genius/ 디렉토리 밑으로 옮긴다.

## 4. 템플릿 파일 만들기

- 파일 이름

~~~
'@' 기준으로
 - 왼쪽 : 아웃풋 경로 + 하위 디렉토리, _ 구분자로 하위디렉토리 경로 설정.
 - 오른쪽 : 파일이름

'@' 없으면 파일이름 
~~~

- 바꿀 단어 정의

~~~
[ 정의 컬럼명 ] => [NAME] , [AGE] .....
~~~

![txt](https://github.com/yalsooni/Genius/blob/master/op/img/010/010_temp_txt.png)

![pptx](https://github.com/yalsooni/Genius/blob/master/op/img/010/010_temp_pptx.png)


## 5. 실행

- 인코딩 설정
~~~
genius.sh OR genius.cmd vi 혹은 메모장으로 열기
-Dfile.encoding=UTF-8 옵션을 알맞은 인코딩으로 변경
~~~

- 일반 실행
~~~
$GENIUS_HOME/bin/genius.sh OR genius.cmd 실행
Project ID : 010
Entry List 
 - replace(String propertyPath) - 기본설정 파일 경로
 - replaceDefault() - 기본값 ../property/010_paperfactory.properties
~~~

- 빠른 실행 : 실행 명령어에 argument를 미리 넣는다.
~~~
$GENIUS_HOME/bin/genius.sh 010 replaceDefault
$GENIUS_HOME/bin/genius.cmd 010 replaceDefault
~~~


## 6. 결과

![result](https://github.com/yalsooni/Genius/blob/master/op/img/010/010_result.png)



[시연화면](https://youtu.be/TkhFibd4DhQ)