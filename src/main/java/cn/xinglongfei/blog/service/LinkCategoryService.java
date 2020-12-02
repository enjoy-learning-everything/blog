package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.po.LinkCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
public interface LinkCategoryService {

    Long countLinkCategory();

    Page<LinkCategory> listLinkCategory(Pageable pageable);

    List<LinkCategory> listLinkCategory();

    LinkCategory getLinkCategoryByName(String name);

    LinkCategory saveLinkCategory(LinkCategory linkCategory);

    LinkCategory getLinkCategory(Long id);

    LinkCategory updateLinkCategory(Long id,LinkCategory linkCategory);

    void deleteLinkCategory(Long id);
}
