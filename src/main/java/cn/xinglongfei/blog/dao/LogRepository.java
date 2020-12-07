package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Phoenix on 2020/11/28
 */
public interface LogRepository extends JpaRepository<Log,Long>{
}
