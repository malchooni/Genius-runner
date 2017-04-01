package name.yalsooni.genius.common.definition;

/**
 * 지니어스 에러코드 정의
 * @author ijyoon
 *
 */
public interface ErrCode {

	String G_002_0001 = "[G-002-0001] 초기화 오류 입니다.";
	String G_002_0002 = "[G-002-0002] 실행 오류 입니다.";
	String G_002_0006 = "[G-002-0006] 파라미터 값의 데이터 타입이 유효하지 않습니다.";

	/**
	 * todo : Common 프로젝트와는 성격이 다르다고 판단하여 타 프로젝트로 분리 방안 판단 중.
	 */
	// megaware design tree -> ref project : 012, 015
	String G_002_0003 = "[G-002_0003] 유효하지 않은 입력값입니다. ( 유효 값 : 'TRANSFORM', 'ORCHESTRATE', 'MEDIATE', 'ROUTE' )"; 
	String G_002_0004 = "[G-002_0004] 메가웨어 사용자 ID가 Null 입니다.";
	String G_002_0005 = "[G-002_0004] 메가웨어 서비스그룹명이 Null 입니다.";


}
