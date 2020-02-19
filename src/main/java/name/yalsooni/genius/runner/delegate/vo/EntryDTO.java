package name.yalsooni.genius.runner.delegate.vo;


import name.yalsooni.genius.runner.definition.ErrCode;

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
     * @param parameterTypesClass parameterTypesClass
     */
    private void setParameterTypes(Class[] parameterTypesClass) {

        this.parameterTypes = new String[parameterTypesClass.length];

        for(int i=0; i < parameterTypesClass.length; i++){
            this.parameterTypes[i] = parameterTypesClass[i].getName();
        }
    }

    /**
     * 엔트리(메소드)의 이름을 반환한다.
     * @return method name
     */
    public String getName() {
        return this.method.getName();
    }

    /**
     * 메소드를 반환한다.
     * @return method name
     */
    public Method getMethod() {
        return method;
    }

    /**
     * 엔트리(메소드)의 파라미터 존재 유무를 반환한다.
     * @return isParameter
     */
    public boolean isParameter(){
        Class[] parameters = this.method.getParameterTypes();
        return parameters != null && parameters.length > 0;
    }

    /**
     * 엔트리(메소드)의 파라미터 값을 입력한다.
     * @param parameterValues parameter value
     * @throws Exception ErrCode.GR_E005
     */
    public void setParameterValues(String parameterValues) throws Exception {

        List<Object> result = new ArrayList<>();
        String[] values = parameterValues.split(",");

        try{
            for(int i=0; i < values.length; i++){
                switch (this.parameterTypes[i]) {
                    case "java.lang.String":
                        result.add(values[i]);
                        break;
                    case "boolean":
                        result.add(Boolean.parseBoolean(values[i]));
                        break;
                    case "char":
                        result.add(values[i].charAt(0));
                        break;
                    case "byte":
                        result.add(Byte.parseByte(values[i], 0));
                        break;
                    case "short":
                        result.add(Short.parseShort(values[i]));
                        break;
                    case "int":
                        result.add(Integer.parseInt(values[i]));
                        break;
                    case "long":
                        result.add(Long.parseLong(values[i]));
                        break;
                    case "float":
                        result.add(Float.parseFloat(values[i]));
                        break;
                    case "double":
                        result.add(Double.parseDouble(values[i]));
                        break;
                }
            }
        }catch (Exception e){
            throw new Exception(ErrCode.GR_E005,e);
        }

        this.parameterObject = result.stream().toArray(n -> new Object[n]);
    }

    /**
     * 엔트리(메소드)의 파라미터 값을 Object 배열로 반환한다.
     * @return parameter array
     */
    public Object[] getParameterObject() {
        return this.parameterObject;
    }

    /**
     * 인자값 설명이 담긴 배열
     * @return argu description
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
     * @param arguments array
     */
    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    /**
     * 엔트리(메소드) 정보를 반환한다.
     * @return method name
     */
    public String toStringMethod(){
        return "method name : "+method.toString();
    }

    /**
     * 엔트리(인수값) 정보를 반환한다.
     * @return entry info
     */
    public String toString(){
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
