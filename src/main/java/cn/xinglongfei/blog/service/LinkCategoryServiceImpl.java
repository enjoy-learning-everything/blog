package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.LinkCategoryResposiory;
import cn.xinglongfei.blog.po.LinkCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
@Service
public class LinkCategoryServiceImpl implements LinkCategoryService {

    @Autowired
    private LinkCategoryResposiory linkCategoryResposiory;

    @Override
    public Long countLinkCategory() {
        return linkCategoryResposiory.count();
    }

    @Override
    public Page<LinkCategory> listLinkCategory(Pageable pageable) {
        return linkCategoryResposiory.findAll(pageable);
    }

    @Override
    public List<LinkCategory> listLinkCategory() {
        return linkCategoryResposiory.findAll();
    }

    @Override
    public LinkCategory getLinkCategoryByName(String name) {
        return linkCategoryResposiory.findByName(name);
    }

    @Override
    public LinkCategory getLinkCategoryByPriority(Long priority) {
        return linkCategoryResposiory.findByPriority(priority);
    }


    @Override
    public LinkCategory saveLinkCategory(LinkCategory linkCategory) {
        return linkCategoryResposiory.save(linkCategory);
    }

    @Override
    public LinkCategory getLinkCategory(Long id) {
        return linkCategoryResposiory.getOne(id);
    }

    @Override
    public LinkCategory updateLinkCategory(Long id, LinkCategory linkCategory) {
        LinkCategory linkCategoryTemp = linkCategoryResposiory.getOne(id);
        if (linkCategoryTemp == null) {
            throw new NotFoundException("该外链分类不存在");
        }
        BeanUtils.copyProperties(linkCategory, linkCategoryTemp);
        return linkCategoryResposiory.save(linkCategoryTemp);
    }

    @Override
    public void deleteLinkCategory(Long id) {
        linkCategoryResposiory.deleteById(id);
    }
}
