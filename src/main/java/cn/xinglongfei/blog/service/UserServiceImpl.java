package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.dao.UserResposiory;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Phoenix on 2020/11/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserResposiory userResposiory;

    @Override
    public User checkUser(String username, String password) {
        User user = userResposiory.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }
}
