package name.yalsooni.genius.runner.execute;

import name.yalsooni.boothelper.util.Log;
import name.yalsooni.genius.runner.definition.ErrCode;
import name.yalsooni.genius.runner.definition.Version;
import name.yalsooni.genius.runner.definition.property.GeniusProperties;
import name.yalsooni.genius.runner.delegate.helper.GeniusDelegateEjector;
import name.yalsooni.genius.runner.delegate.vo.DelegateDTO;
import name.yalsooni.genius.runner.delegate.vo.EntryDTO;
import name.yalsooni.genius.runner.inout.input.InputManager;
import name.yalsooni.genius.runner.inout.output.OutputManager;
import name.yalsooni.genius.runner.repository.DelegateList;

import java.lang.reflect.Method;

/**
 * 지니어스 유틸.
 * Created by yoon-iljoong on 2016. 10. 26..
 */
public class GeniusExecutor {

    public static final String GENIUS = "GENIUS";

    private DelegateList delegateList = null;
    private String[] arguments = null;

    /**
     * 초기화
     * @param args 속성값
     */
    private void initialization(String[] args) throws Exception {
        Log.console(" - Genius "+ Version.INFO+" Initializing...");

        GeniusProperties geniusProperties = new GeniusProperties();
        GeniusDelegateEjector ejector = new GeniusDelegateEjector();
        this.delegateList = ejector.eject(geniusProperties);
        this.arguments = args;

        Log.console(" - Genius "+ Version.INFO+" Initialization done.");
    }

    /**
     * 실행
     */
    private void execute() throws Exception {

        int SERVICE_ID = 0;
        int ENTRY_NAME = 1;
        int PARAMETER_VALUE = 2;

        String serviceID;
        String entryName;
        String parameterValue;

        if(this.arguments == null || this.arguments.length < 1){
            OutputManager.printDelegateList(this.delegateList);
            serviceID = InputManager.getServiceID();
        }else{
            serviceID = this.arguments[SERVICE_ID];
        }

        if(serviceID == null || serviceID.length() != 3){
            Log.console(ErrCode.GR_E001);
            return;
        }

        DelegateDTO target = this.delegateList.getDelegateDTO(serviceID);
        if(target == null){
            Log.console(ErrCode.GR_E002);
            return;
        }

        if(this.arguments == null || this.arguments.length < 2){
            OutputManager.printEntryList(target);
            entryName = InputManager.getEntryName();
        }else{
            entryName = this.arguments[ENTRY_NAME];
        }

        if(entryName == null || entryName.length() < 1){
            Log.console(ErrCode.GR_E003);
            return;
        }

        EntryDTO targetEntry = target.getEntry(entryName);
        if(targetEntry == null){
            Log.console(ErrCode.GR_E004);
            return;
        }

        Class targetKlass = target.getKlass();
        Method targetMethod = targetEntry.getMethod();

        if(targetEntry.isParameter()){
            if(this.arguments == null || this.arguments.length < 3){
                OutputManager.printParameters(targetEntry);
                parameterValue = InputManager.getParameterValues();
            }else{
                parameterValue = this.arguments[PARAMETER_VALUE];
            }
            targetEntry.setParameterValues(parameterValue);
            targetMethod.invoke(targetKlass.newInstance(), targetEntry.getParameterObject());
        }else{
            targetMethod.invoke(targetKlass.newInstance());
        }

        Log.console(" - Genius "+ Version.INFO+" Execute done.");
    }

    /**
     * 실행.
     * @param args 필요 속성 값.
     */
    public static void main(String[] args) {
        GeniusExecutor executor = new GeniusExecutor();
        try {
            executor.initialization(args);
        } catch (Exception e) {
            Log.console(ErrCode.GR_INIT,e);
            return;
        }

        try {
            executor.execute();
        } catch (Exception e) {
            Log.console(ErrCode.GR_EXEC,e);
        }
    }
}
