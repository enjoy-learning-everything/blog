package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Phoenix on 2020/11/17
 */
public interface UserResposiory extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}
