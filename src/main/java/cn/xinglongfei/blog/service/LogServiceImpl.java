package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.dao.LogRepository;
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
    private LogRepository logRepository;

    @Override
    public Long countLog() {
        return logRepository.count();
    }

    @Override
    public Log saveLog(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Page<Log> listLog(Pageable pageable) {
        return logRepository.findAll(pageable);
    }
}
