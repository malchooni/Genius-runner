package name.yalsooni.genius.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * System.out 출력.
 * Created by yoon-iljoong on 2015. 11. 2..
 */
public class Log {

    /**
     * 해당 메세지를 System.out 으로 출력.
     * @param msg
     */
    public static void console(String msg){
        System.out.println("["+Date.getDateTime()+"] " + msg);
    }
    
    /**
     * 해당 메세지를 System.out.printf 로 출력.
     * @param t
     */
    public static void console(Throwable t){
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
    	PrintStream ps = new PrintStream(os);
    	t.printStackTrace(ps);
    	System.out.println("["+Date.getDateTime()+"] " + os.toString());
    	
    	ps.close();
    	try { os.close(); } catch (IOException e) {}
    }
    
    /**
     * 해당 메세지를 System.out.printf 로 출력.
     * @param msg
     * @param t
     */
    public static void console(String msg, Throwable t){
    	Log.console(msg);
    	Log.console(t);
    }

    /**
     * 해당 메세지를 System.out.printf 로 출력.
     * @param format 출력 포맷
     * @param msg
     */
    public static void consolef(String format, Object... msg){
        System.out.printf("["+Date.getDateTime()+"] " + format, msg);
    }

    /**
     * List<String> 메세지를 System.out 으로 출력
     * @param list
     */
    public static void console(List<String> list){
    	System.out.print("["+Date.getDateTime()+"] ");
    	for(String str : list){
    		System.out.print(" ["+str+"] ");
        }
        System.out.println();
    }
}
