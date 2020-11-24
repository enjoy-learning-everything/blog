package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.po.User;

/**
 * Created by Phoenix on 2020/11/17
 */
public interface UserService {

    User checkUser(String username, String password);
}
