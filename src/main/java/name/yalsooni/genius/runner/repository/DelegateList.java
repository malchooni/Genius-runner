package name.yalsooni.genius.runner.repository;

import name.yalsooni.genius.runner.delegate.vo.DelegateDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 서비스 클래스의 목록을 보관하는 맵 레파지토리
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class DelegateList {

    private Map<String, DelegateDTO> list = new HashMap<>();

    /**
     * 딜리게이트 목록을 String 배열로 반환한다.
     * @return delegate list
     */
    public String[] getList(){
        return list.keySet().stream().toArray(n -> new String[n]);
    }

    /**
     * 딜리게이트를 추가한다.
     * @param delegateDTO delegate info
     */
    public void addDelegate(DelegateDTO delegateDTO){
        list.put(delegateDTO.getId(), delegateDTO);
    }

    /**
     * 특정 딜리게이트의 정보를 반환한다.
     * @param id delegate id
     * @return delegate
     */
    public DelegateDTO getDelegateDTO(String id){
        return list.get(id);
    }
}
