package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.UserMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.UserService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * <p>
  * 用户表 Service 接口实现
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    JsonBuilder jsonBuilder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteBookService noteBookService;




    public int insert(User user) {
        return userMapper.insert(user);
    }

    /**
     * 检查是否是有效登陆信息
     * @param username 用户名
     * @param pwd 密码
     * @return 有效返回true，否则返回false
     */
    public boolean isValidLogin(String username, String pwd) {
        User user = userMapper.select(username, pwd);
        return !(user==null);
    }

    public User getUser(String username, String pwd){
        return userMapper.select(username, pwd);
    }

    public User getUser(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }


    /**
     * 用户注册
     * @param username 用户名 非空
     * @param pwd 密码 非空
     * @param phone 手机 非空
     * @param email 邮箱
     * @param gender 性别
     * @return 返回json {"msg":"消息内容","isSuccessful":"0|1"}
     */
    public String register(String username,String pwd,String phone,String email,String gender) {
        try {
            if (CheckerUtil.checkNull(username))
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "用户名非空").build();
            if (CheckerUtil.checkNull(pwd))
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "密码非空").build();
            if (CheckerUtil.checkNull(phone))
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "手机非空").build();
            if (CheckerUtil.checkNull(gender))
                gender = "0";

            if (isValidLogin(username, pwd))
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "不要重复注册").build();

            User user = new User(null, username, pwd, phone, email, Integer.valueOf(gender), null, null);
            userMapper.insert(user);
            noteBookService.insert(new NoteBook(null, user.getId(), "默认", 0));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("用户注册抛出异常：" + e.getMessage());
            e.printStackTrace();
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "抛出异常").build();
        }
        return jsonBuilder.createConstructor("isSuccessful", "1").build();
    }
}