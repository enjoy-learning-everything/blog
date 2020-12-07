package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Phoenix on 2020/11/17
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 根据用户名和密码查询出用户
     * @param username 用户名
     * @param password 密码
     * @return 对应的用户
     */
    User findByUsernameAndPassword(String username,String password);
}
