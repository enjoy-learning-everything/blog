package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.TagResposiory;
import cn.xinglongfei.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/17
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagResposiory tagResposiory;

    @Override
    public Long countTag() {
        return tagResposiory.count();
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        ;
        return tagResposiory.save(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagResposiory.findByName(name);
    }

    @Override
    public Tag getTag(Long id) {
        return tagResposiory.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagResposiory.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagResposiory.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort =Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagResposiory.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagResposiory.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new Long(idArray[i]));
            }
        }

        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tagTemp = tagResposiory.getOne(id);
        if (tagTemp == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, tagTemp);
        return tagResposiory.save(tagTemp);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagResposiory.deleteById(id);
    }
}
