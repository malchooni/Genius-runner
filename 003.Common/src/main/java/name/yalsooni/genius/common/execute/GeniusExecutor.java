package name.yalsooni.genius.common.execute;

import name.yalsooni.genius.common.definition.ErrCode;
import name.yalsooni.genius.common.definition.property.GeniusProperties;
import name.yalsooni.genius.common.delegate.helper.GeniusDelegateEjector;
import name.yalsooni.genius.common.delegate.vo.DelegateDTO;
import name.yalsooni.genius.common.delegate.vo.EntryDTO;
import name.yalsooni.genius.common.exception.InvalidParameterValueTypeException;
import name.yalsooni.genius.common.inout.input.InputManager;
import name.yalsooni.genius.common.inout.output.OutputManager;
import name.yalsooni.genius.common.repository.DelegateList;
import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 지니어스 유틸.
 * Created by yoon-iljoong on 2016. 10. 26..
 */
public class GeniusExecutor {

    public static final String GENIUS = "GENIUS";

    private GeniusProperties geniusProperties = null;
    private GeniusDelegateEjector ejector = null;
    private DelegateList delegateList = null;

    private String serviceID = null;
    private String entryName = null;
    private String parameterValue = null;

    private String[] arguments = null;
    private final int SERVICE_ID = 0;
    private final int ENTRY_NAME = 1;
    private final int PARAMETER_VALUE = 2;

    /**
     * 초기화
     * @param args
     * @throws Exception
     */
    private void initialization(String[] args) throws Exception {

        Log.console(" - Genius 2.0 Initializing...");

        try{
            this.arguments = args;
            this.geniusProperties = new GeniusProperties();
            this.ejector = new GeniusDelegateEjector();
            this.delegateList = this.ejector.eject(this.geniusProperties);
        }catch (Exception e){
            throw new Exception(ErrCode.G_002_0001,e);
        }

        Log.console(" - Genius 2.0 Initialization done.");
    }

    /**
     * 실행
     */
    private void execute() throws Exception {
        try {

            if(this.arguments == null || this.arguments.length < 1){
                OutputManager.printDelegateList(this.delegateList);
                serviceID = InputManager.getServiceID();
            }else{
                serviceID = this.arguments[SERVICE_ID];
            }

            if(serviceID == null || serviceID.length() != 3){
                Log.console("Invalid Service ID.");
                return;
            }

            DelegateDTO target = this.delegateList.getDelegateDTO(serviceID);
            if(target == null){
                Log.console("Not found Service ID.");
                return;
            }

            if(this.arguments == null || this.arguments.length < 2){
                OutputManager.printEntryList(target);
                entryName = InputManager.getEntryName();
            }else{
                entryName = this.arguments[ENTRY_NAME];
            }

            if(entryName == null || entryName.length() < 1){
                Log.console("Invalid entry name.");
                return;
            }

            EntryDTO targetEntry = target.getEntry(entryName);
            if(targetEntry == null){
                Log.console("Not found entry name.");
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InvalidParameterValueTypeException e) {
            Log.console(e);
            throw e;
        }

        Log.console(" - Genius 2.0 Execute done.");
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
            Log.console(ErrCode.G_002_0001,e);
            return;
        }

        try {
            executor.execute();
        } catch (Exception e) {
            Log.console(new Exception(ErrCode.G_002_0002,e));
        }
    }
}
