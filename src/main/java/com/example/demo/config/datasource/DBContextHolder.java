package com.example.demo.config.datasource;


/**
 * @author Djh
 */
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void set(DBTypeEnum dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    public static DBTypeEnum get() {
        return CONTEXT_HOLDER.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        System.out.println("切换到master");
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE);
        System.out.println("切换到slave");
    }
}
