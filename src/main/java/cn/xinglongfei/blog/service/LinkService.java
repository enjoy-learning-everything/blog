package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.po.Link;
import cn.xinglongfei.blog.vo.LinkQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
public interface LinkService {

    Long countLink();

    Page<Link> listLink(Pageable pageable);

    Page<Link> listLink(Pageable pageable, LinkQuery link);

    List<Link> listLink();

    Link saveLink(Link link);

    Link getLink(Long id);

    Link updateLink(Long id,Link link);

    void deleteLink(Long id);
}
