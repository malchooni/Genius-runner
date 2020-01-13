package name.yalsooni.genius.runner.delegate.vo;


import name.yalsooni.genius.runner.exception.InvalidParameterValueTypeException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 특정 Delegate의 엔트리 정보.
 * Created by yoon-iljoong on 2016. 11. 1..
 */
public class EntryDTO {

    private String[] parameterTypes;
    private Method method;
    private Object[] parameterObject;
    private String[] arguments;

    public EntryDTO(Method method) {
        this.method = method;
        this.setParameterTypes(method.getParameterTypes());
    }

    /**
     * 엔트리(메소드)의 파라미터 타입을 입력한다.
     * @param parameterTypesClass
     */
    public void setParameterTypes(Class[] parameterTypesClass) {

        this.parameterTypes = new String[parameterTypesClass.length];

        for(int i=0; i < parameterTypesClass.length; i++){
            this.parameterTypes[i] = parameterTypesClass[i].getName();
        }
    }

    /**
     * 엔트리(메소드)의 이름을 반환한다.
     * @return
     */
    public String getName() {
        return this.method.getName();
    }

    /**
     * 엔트리(메소드)의 파라미터 데이터 타입을 반환한다.
     * @return
     */
    public String[] getParameterTypes() {
        return parameterTypes;
    }

    /**
     * 메소드를 반환한다.
     * @return
     */
    public Method getMethod() {
        return method;
    }

    /**
     * 엔트리(메소드)의 파라미터 존재 유무를 반환한다.
     * @return
     */
    public boolean isParameter(){
        Class[] parameters = this.method.getParameterTypes();

        if(parameters != null && parameters.length > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 엔트리(메소드)의 파라미터 값을 입력한다.
     * @param parameterValues
     * @throws InvalidParameterValueTypeException
     */
    public void setParameterValues(String parameterValues) throws InvalidParameterValueTypeException {

        List<Object> result = new ArrayList<Object>();
        String[] values = parameterValues.split(",");

        try{
            for(int i=0; i < values.length; i++){
                if(this.parameterTypes[i].equals("java.lang.String")){
                    result.add(values[i]);
                }else if(this.parameterTypes[i].equals("boolean")){
                    result.add(Boolean.parseBoolean(values[i]));
                }else if(this.parameterTypes[i].equals("char")){
                    result.add(values[i].charAt(0));
                }else if(this.parameterTypes[i].equals("byte")){
                    result.add(Byte.parseByte(values[i], 0));
                }else if(this.parameterTypes[i].equals("short")){
                    result.add(Short.parseShort(values[i]));
                }else if(this.parameterTypes[i].equals("int")){
                    result.add(Integer.parseInt(values[i]));
                }else if(this.parameterTypes[i].equals("long")){
                    result.add(Long.parseLong(values[i]));
                }else if(this.parameterTypes[i].equals("float")){
                    result.add(Float.parseFloat(values[i]));
                }else if(this.parameterTypes[i].equals("double")){
                    result.add(Double.parseDouble(values[i]));
                }
            }
        }catch (Exception e){
            throw new InvalidParameterValueTypeException(e);
        }

        parameterObject = result.toArray(new Object[result.size()]);
    }

    /**
     * 엔트리(메소드)의 파라미터 값을 Object 배열로 반환한다.
     * @return
     */
    public Object[] getParameterObject() {
        return parameterObject;
    }

    /**
     * 인자값 설명이 담긴 배열
     * @return
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * 인자값 설명이 담긴 배열
     * @return
     */
    public String getArguments(int arrIdx) {
        String msg;
        try{
            msg = arguments[arrIdx];
        }catch (Exception ae){
            msg = "none";
        }

        return msg;
    }

    /**
     * 인자값 설명 적재
     * @param arguments
     */
    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    /**
     * 엔트리(메소드) 정보를 반환한다.
     * @return
     */
    public String toStringMethod(){
        return "method name : "+method.toString();
    }

    /**
     * 엔트리(인수값) 정보를 반환한다.
     * @return
     */
    public String toStringArguments(){
        StringBuilder methodInfo = new StringBuilder();
        int argumentLength = arguments.length;

        methodInfo.append("arguments : ");

        for(int i=0; i < argumentLength; i++){
            methodInfo.append("[").append(i+1).append("]").append(arguments[i]);
            if(i != argumentLength -1){
                methodInfo.append(", ");
            }
        }

        return methodInfo.toString();
    }
}
