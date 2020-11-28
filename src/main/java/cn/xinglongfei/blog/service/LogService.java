package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.po.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Phoenix on 2020/11/28
 */
public interface LogService {

    Long countLog();

    Log saveLog(Log log);

    Page<Log> listLog(Pageable pageable);
}
