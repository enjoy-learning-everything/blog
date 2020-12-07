package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.TagRepository;
import cn.xinglongfei.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/17
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Long countTag() {
        return tagRepository.count();
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        List<Tag> tags = tagRepository.findAll();
        for (int i = 0; i < tags.size(); i++) {
            for (int j = 0; j < tags.get(i).getBlogs().size(); j++) {
                if (!tags.get(i).getBlogs().get(j).isPublished()) {
                    tags.get(i).getBlogs().remove(j);
                    //删除了后需要重置游标，防止漏掉
                    j--;
                }
            }
        }
        Collections.sort(tags);
        return tags.subList(0, Math.min(tags.size(), size));
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                boolean isNum=false;
                //判断传来的是否数字
                isNum = idArray[i].matches("[0-9]+");
                if (isNum){
                list.add(new Long(idArray[i]));
                }else{
                    //不是数字就表明是新标签，先添加到数据库
                    Tag tag = new Tag();
                    tag.setName(idArray[i]);
                    saveTag(tag);
                    Long id= tagRepository.findByName(idArray[i]).getId();
                    list.add(id);
                }
            }
        }

        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tagTemp = tagRepository.getOne(id);
        if (tagTemp == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, tagTemp);
        return tagRepository.save(tagTemp);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
