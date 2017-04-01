package name.yalsooni.genius.common.repository;

import genius.rulebreaker.delegate.vo.DelegateDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 서비스 클래스의 목록을 보관하는 맵 레파지토리
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class DelegateList {

    private Map<String, DelegateDTO> list = new HashMap<String, DelegateDTO>();

    /**
     * 딜리게이트 목록을 String 배열로 반환한다.
     * @return
     */
    public String[] getList(){
        return list.keySet().toArray(new String[list.size()]);
    }

    /**
     * 딜리게이트를 추가한다.
     * @param delegateDTO
     */
    public void addDelegate(DelegateDTO delegateDTO){
        list.put(delegateDTO.getId(), delegateDTO);
    }

    /**
     * 특정 딜리게이트의 정보를 반환한다.
     * @param id
     * @return
     */
    public DelegateDTO getDelegateDTO(String id){
        return list.get(id);
    }
}
