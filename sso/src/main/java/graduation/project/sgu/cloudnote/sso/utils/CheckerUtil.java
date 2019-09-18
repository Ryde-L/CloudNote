package graduation.project.sgu.cloudnote.sso.utils;

/**
 * 对象检查工具
 */
public class CheckerUtil {

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
