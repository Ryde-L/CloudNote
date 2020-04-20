package com.cloudnote.note.utils;

/**
 * 对象检查工具
 */
public class CheckerUtil {
    /**
     * 单个空对象检查
     * @param obj
     * @return 如果对象是null或空字符串，则返回true；否则返回false
     */
    static public boolean checkNull(Object obj){
        if (obj==null)
            return true;
        if (obj instanceof String&& ((String)obj).equals(""))
            return true;
        return false;
    }

    /**
     * 不定长参数对象空值检查
     * @param objs 对象
     * @return 如果有对象是null或空字符串，则返回true；否则返回false
     */
    static public boolean checkNulls(Object ...objs){
        for (Object obj:objs){
            if (obj==null)
                return true;
            if (obj instanceof String&& ((String)obj).equals(""))
                return true;
            else if (obj instanceof String[]){
                System.out.println("String数组检查");
                String[] ss= (String[]) obj;
                for (String s:ss)
                    if (s==null||"".equals(s))
                        return true;
            }
        }
        return false;
    }
}
