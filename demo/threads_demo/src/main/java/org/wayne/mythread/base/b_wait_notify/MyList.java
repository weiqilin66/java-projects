package org.wayne.mythread.base.b_wait_notify;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class MyList {
    private static List<String> list = new ArrayList<String>();

    public static void add() {
        list.add("anyString");
    }

    public static int size() {
        return list.size();
    }
}
