package name.yalsooni.genius.runner.delegate.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Delegate 어노테이션이 정의된 클래스의 정보를 담는다.
 * Created by yoon-iljoong on 2016. 11. 1..
 */
public class DelegateDTO {

    private String id;
    private String name;
    private String serviceType;
    private Map<String, EntryDTO> entryMap = new HashMap<>();
    private Class klass;

    public DelegateDTO(String id) {
        this.id = id;
    }

    /**
     * 엔트리를 추가한다.
     * @param entry 엔트리 정보
     */
    public void addEntry(EntryDTO entry){
        this.entryMap.put(entry.getName(), entry);
    }

    /**
     * 엔트리 정보 반환
     * @param entryName 엔트리 이름
     * @return EntryDTO 엔트리 정보
     */
    public EntryDTO getEntry(String entryName){
        EntryDTO[] entryList = getEntrys();
        for(EntryDTO entryDTO : entryList){
            if(entryDTO.getName().equals(entryName)){
                return entryDTO;
            }
        }
        return null;
    }

    /**
     * 엔트리 배열을 반환한다.
     * @return EntryDTO 목록
     */
    public EntryDTO[] getEntrys(){
        return this.entryMap.values().toArray(new EntryDTO[entryMap.size()]);
    }

    /**
     * 딜리게이트의 아이디를 반환한다.
     * @return delegate id
     */
    public String getId() {
        return id;
    }

    /**
     * 딜리게이트 이름을 반환한다.
     * @return delegate name
     */
    public String getName() {
        return name;
    }

    /**
     * 딜리게이트의 서비스타입을 반환한다.
     * @return delegate service type
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * 딜리게이트의 서비스 타입을 입력한다.
     * @param serviceType 서비스 타입
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * 딜리게이트의 클래스를 반환한다.
     * @return delegate class
     */
    public Class getKlass() {
        return klass;
    }

    /**
     * 딜리게이트 클래스를 입력한다.
     * @param klass set delegate class
     */
    public void setKlass(Class klass) {
        this.klass = klass;
        this.name = this.klass.getSimpleName();
    }
}
