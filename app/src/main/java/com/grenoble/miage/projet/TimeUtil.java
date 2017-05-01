package com.grenoble.miage.projet;

public abstract class TimeUtil {
    public static final int SECOND = 0;
    public static final int MINUTE = 1;

    public static Integer[] getMinSec(long timeInMs){
        int secs = (int) (timeInMs / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return new Integer[]{secs, mins};
    }

    public static long toMs(int minute, int seconde){
        return (minute * 60 + seconde) * 1000;
    }
}
