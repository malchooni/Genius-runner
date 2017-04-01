package name.yalsooni.genius.common.inout.output;

import genius.rulebreaker.delegate.vo.DelegateDTO;
import genius.rulebreaker.delegate.vo.EntryDTO;
import genius.rulebreaker.repository.DelegateList;
import genius.rulebreaker.util.Log;

/**
 * 화면에 출력
 * Created by yoon-iljoong on 2016. 11. 2..
 */
public class OutputManager {

    /**
     * 딜리게이트 목록 출력
     * @param delegateList
     */
    public static void printDelegateList(DelegateList delegateList){
        Log.console(" - Genius 2.0 Service List");
        String[] listArr = delegateList.getList();
        DelegateDTO dto;

        for(String delegateID : listArr){
            dto = delegateList.getDelegateDTO(delegateID);
            Log.console("["+ dto.getId() +"] "+dto.getName() + " - " + dto.getServiceType());
        }
    }

    /**
     * 엔트리 목록 출력
     * @param delegateDTO
     */
    public static void printEntryList(DelegateDTO delegateDTO){
        Log.console(" - ["+delegateDTO.getId()+" "+delegateDTO.getName() + "] entry list.");

        EntryDTO[] entryList = delegateDTO.getEntrys();
        for(EntryDTO entryDTO : entryList){
            Log.console(entryDTO.getName());
        }
    }

    /**
     * 파라미터의 데이터 타입을 출력한다.
     * @param entryDTO
     */
    public static void printParameters(EntryDTO entryDTO){
        Class[] parameters = entryDTO.getMethod().getParameterTypes();
        if(parameters != null && parameters.length > 0){
            Log.console(" - ["+entryDTO.getName()+"] parameters" );
            for(int i=0; i < parameters.length; i++){
                Log.console((i+1) + " : " +parameters[i].getName());
            }
        }
    }
}
