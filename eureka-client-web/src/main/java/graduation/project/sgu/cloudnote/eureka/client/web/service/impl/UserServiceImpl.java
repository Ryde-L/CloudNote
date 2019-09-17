package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.UserMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.UserService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteBookService noteBookService;

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

    /**
     * 用户注册
     * @param username 用户名 非空
     * @param pwd 密码 非空
     * @param phone 手机 非空
     * @param email 邮箱
     * @param gender 性别
     * @return ResponseDto
     */
    public ResponseDto register(String username, String pwd, String phone, String email, String gender) {
        if (CheckerUtil.checkNulls(username, pwd, phone)) return ResultUtil.error("缺少必填参数", null);
        if (CheckerUtil.checkNull(gender)) gender = "0";
        if (isValidLogin(username, pwd)) return ResultUtil.error("不要重复注册", null);

        User user = new User(null, username, pwd, phone, email, Integer.valueOf(gender), null, 1);
        userMapper.insert(user);
        noteBookService.insert(new NoteBook(null, user.getId(), "默认", 0));
        return ResultUtil.success("", null);
    }
}