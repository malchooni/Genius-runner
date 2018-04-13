package name.yalsooni.genius.core.boothelper;


import name.yalsooni.genius.core.boothelper.execute.BootHelper;

/**
 * Created by ijyoon on 2016. 8. 1..
 */
public class Run {

    public static void main(String[] args) throws Exception {
        BootHelper bootHelper = new BootHelper();
        bootHelper.exe(args);
    }
}
