package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.LinkCategoryRepository;
import cn.xinglongfei.blog.po.LinkCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
@Service
public class LinkCategoryServiceImpl implements LinkCategoryService {

    @Autowired
    private LinkCategoryRepository linkCategoryRepository;

    @Override
    public Long countLinkCategory() {
        return linkCategoryRepository.count();
    }

    @Override
    public Page<LinkCategory> listLinkCategory(Pageable pageable) {
        return linkCategoryRepository.findAll(pageable);
    }

    @Override
    public List<LinkCategory> listLinkCategory() {
        return linkCategoryRepository.findAll();
    }

    @Override
    public LinkCategory getLinkCategoryByName(String name) {
        return linkCategoryRepository.findByName(name);
    }

    @Transactional
    @Override
    public LinkCategory saveLinkCategory(LinkCategory linkCategory) {
        return linkCategoryRepository.save(linkCategory);
    }

    @Override
    public LinkCategory getLinkCategory(Long id) {
        return linkCategoryRepository.getOne(id);
    }

    @Transactional
    @Override
    public LinkCategory updateLinkCategory(Long id, LinkCategory linkCategory) {
        LinkCategory linkCategoryTemp = linkCategoryRepository.getOne(id);
        if (linkCategoryTemp == null) {
            throw new NotFoundException("该外链分类不存在");
        }
        BeanUtils.copyProperties(linkCategory, linkCategoryTemp);
        return linkCategoryRepository.save(linkCategoryTemp);
    }

    @Transactional
    @Override
    public void deleteLinkCategory(Long id) {
        linkCategoryRepository.deleteById(id);
    }
}
