package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.LinkResposiory;
import cn.xinglongfei.blog.po.Link;
import cn.xinglongfei.blog.po.LinkCategory;
import cn.xinglongfei.blog.util.MyBeanUtils;
import cn.xinglongfei.blog.vo.LinkQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
@Service
public class LinkServiceImpl implements LinkService{

    @Autowired
    private LinkResposiory linkResposiory;

    @Override
    public Long countLink() {
        return linkResposiory.count();
    }

    @Override
    public Page<Link> listLink(Pageable pageable) {
        return linkResposiory.findAll(pageable);
    }

    @Override
    public Page<Link> listLink(Pageable pageable, LinkQuery link) {
        return linkResposiory.findAll(new Specification<Link>() {
            @Override
            public Predicate toPredicate(Root<Link> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(link.getTitle()) && link.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"),"%"+link.getTitle()+"%"));
                }
                if(link.getLinkCategoryId() !=null){
                    predicates.add(cb.equal(root.<LinkCategory>get("linkCategory").get("id"),link.getLinkCategoryId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<Link> listLink() {
        return linkResposiory.findAll();
    }

    @Override
    public Link saveLink(Link link) {
        if(link.getId() == null){
            link.setCreateTime(new Date());
        }
        return linkResposiory.save(link);
    }

    @Override
    public Link getLink(Long id) {
        return linkResposiory.getOne(id);
    }

    @Override
    public Link updateLink(Long id, Link link) {
        Link linkTemp = linkResposiory.getOne(id);
        if (linkTemp == null) {
            throw new NotFoundException("该外链不存在");
        }
        BeanUtils.copyProperties(link, linkTemp, MyBeanUtils.getNullPropertyNames(link));
        return linkResposiory.save(linkTemp);
    }

    @Override
    public void deleteLink(Long id) {
        linkResposiory.deleteById(id);
    }
}
