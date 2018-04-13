package name.yalsooni.genius.core.boothelper.repository;


import name.yalsooni.genius.core.boothelper.execute.BootHelper;

/**
 * Created by yoon-iljoong on 2016. 11. 6..
 */
public class BootHelperRepository {

    private static BootHelper bootHelper = null;

    public static void setBootHelper (BootHelper bootHelper_){
        bootHelper = bootHelper_;
    }

    public static BootHelper getBootHelper(){
        return bootHelper;
    }

}
