package graduation.project.sgu.cloudnote.eureka.client.web.bean;

import graduation.project.sgu.cloudnote.eureka.client.web.utils.JsonUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope("prototype")
public class JsonBuilder {
    private HashMap<String,Object> json;

    /**
     * @return 生成的json字符串
     */
    public String build(){
        return JsonUtil.objectToJson(json);
    }

    /**
     * 添加单个参数
     * @param key 键
     * @param value 值
     * @return 本身
     */
    public JsonBuilder add(String key,Object value){
        json.put(key,value);
        return this;
    }

    /**
     * 一个键一个值的形式，参数长度需为2的倍数
     * @param keysAndValues 键或值
     * @throws IllegalArgumentException 参数需为2的倍数
     */
    public JsonBuilder createConstructor(String ...keysAndValues) throws IllegalArgumentException{
        json=new HashMap<String, Object>();
        if (keysAndValues.length % 2 !=0) throw new IllegalArgumentException("参数长度需为2的倍数");
        for (int i = 0; i < keysAndValues.length; i+=2)
            json.put(keysAndValues[i],keysAndValues[i+1]);
        return this;
    }

    /**
     * 一个键一个值的形式，参数长度需为2的倍数
     * @param isSuccessful 成功与否，0或1
     * @param msg 消息
     */
    public JsonBuilder setValuesIntoTemplate(String isSuccessful,String msg){
        json=new HashMap<String, Object>();
        json.put("isSuccessful",isSuccessful);
        json.put("msg",msg);
        return this;
    }

}
