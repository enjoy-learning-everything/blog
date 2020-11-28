package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.dao.LogResposiory;
import cn.xinglongfei.blog.po.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Phoenix on 2020/11/28
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogResposiory logResposiory;

    @Override
    public Long countLog() {
        return logResposiory.count();
    }

    @Override
    public Log saveLog(Log log) {
        return logResposiory.save(log);
    }

    @Override
    public Page<Log> listLog(Pageable pageable) {
        return logResposiory.findAll(pageable);
    }
}
