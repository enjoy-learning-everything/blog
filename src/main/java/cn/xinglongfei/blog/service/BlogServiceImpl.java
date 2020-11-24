package cn.xinglongfei.blog.service;

import cn.xinglongfei.blog.NotFoundException;
import cn.xinglongfei.blog.dao.BlogResposiory;
import cn.xinglongfei.blog.po.Blog;
import cn.xinglongfei.blog.po.Category;
import cn.xinglongfei.blog.util.MarkdownUtils;
import cn.xinglongfei.blog.util.MyBeanUtils;
import cn.xinglongfei.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by Phoenix on 2020/11/18
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogResposiory blogResposiory;

    @Override
    public Blog getBlog(Long id) {
        return blogResposiory.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogResposiory.getOne(id);
        if(blog == null ){
            throw new NotFoundException("该博客不存在");
        }
        blogResposiory.updateViews(id);
        Blog blogTemp = new Blog();
        BeanUtils.copyProperties(blog,blogTemp);
        String content = blogTemp.getContent();
        blogTemp.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blogTemp;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogResposiory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                if(blogQuery.getCategoryId() !=null){
                    predicates.add(cb.equal(root.<Category>get("category").get("id"),blogQuery.getCategoryId()));
                }
                if(blogQuery.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blogQuery.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogResposiory.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogResposiory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogResposiory.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort =Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable =PageRequest.of(0,size,sort);
        return blogResposiory.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogResposiory.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for(String year:years){
            map.put(year,blogResposiory.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogResposiory.count();
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViewCounts(0L);
            blog.setCommentCounts(0L);
            blog.setLikeCounts(0L);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogResposiory.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blogTemp = blogResposiory.getOne(id);
        if (blogTemp == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, blogTemp, MyBeanUtils.getNullPropertyNames(blog));
        blogTemp.setUpdateTime(new Date());
        return blogResposiory.save(blogTemp);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogResposiory.deleteById(id);
    }


}
