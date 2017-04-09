package name.yalsooni.genius.common.delegate.vo;

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
    private Map<String, EntryDTO> entryMap = new HashMap<String, EntryDTO>();
    private Class klass;

    public DelegateDTO(String id) {
        this.id = id;
    }

    /**
     * 엔트리를 추가한다.
     * @param entry
     */
    public void addEntry(EntryDTO entry){
        this.entryMap.put(entry.getName(), entry);
    }

    /**
     * 엔트리 정보 반환
     * @param entryName
     * @return
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
     * @return
     */
    public EntryDTO[] getEntrys(){
        return entryMap.values().toArray(new EntryDTO[entryMap.size()]);
    }

    /**
     * 딜리게이트의 아이디를 반환한다.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 딜리게이트 이름을 반환한다.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 딜리게이트의 서비스타입을 반환한다.
     * @return
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * 딜리게이트의 서비스 타입을 입력한다.
     * @param serviceType
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * 딜리게이트의 클래스를 반환한다.
     * @return
     */
    public Class getKlass() {
        return klass;
    }

    /**
     * 딜리게이트 클래스를 입력한다.
     * @param klass
     */
    public void setKlass(Class klass) {
        this.klass = klass;
        this.name = this.klass.getSimpleName();
    }
}
