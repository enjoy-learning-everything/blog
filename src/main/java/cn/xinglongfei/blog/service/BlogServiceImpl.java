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
    public Page<Blog> listPublishedBlog(Pageable pageable, BlogQuery blogQuery) {
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
                //只能查询到发布了的博客
                predicates.add(cb.equal(root.<Boolean>get("published"),Boolean.TRUE));
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
    public Page<Blog> listPublishedBlog(Pageable pageable) {
        return blogResposiory.findAllPublishedBlog(pageable);
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
    public Page<Blog> listPublishedBlog(Long tagId, Pageable pageable) {
        return blogResposiory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join join = root.join("tags");
                predicates.add(cb.equal(join.get("id"),tagId));
                //只能查询到发布了的博客
                predicates.add(cb.equal(root.<Boolean>get("published"),Boolean.TRUE));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogResposiory.findByQuery(query,pageable);
    }


    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        //根据创建时间降序排序
        Sort sort =Sort.by(Sort.Direction.DESC,"createTime");
        //根据传入的大小和排序方式构建分页查询对象
        Pageable pageable =PageRequest.of(0,size,sort);
        //根据分页查询对象查询出需要查询的结果
        return blogResposiory.findTop(pageable);
    }

    /**
     * 将所有博客按照年份分组，组与组之间按照年份倒序排序，组内的所有博客按照创建时间倒序排序
     * @return 排序后的Map
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //获取所有博客的年份，并把按倒序排序
        List<String> years = blogResposiory.findGroupYear();
        years.sort(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                return o2.compareTo(o1);
            }
        });
        //TreeMap默认按Key的字典升序排序，由于年份需要降序，所以需要自定义排序
        Map<String,List<Blog>> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        for(String year:years){
            //获取某一个年份对应的所有的博客
            List<Blog> blogList = blogResposiory.findByYear(year);
            //如果当年有发布了的博客，就显示当年（因为有的年份的博客可能全部只是保存）
            if(blogList.size()>0){
                //将博客按创建时间降序排序
                Collections.sort(blogList);
                map.put(year,blogList);
            }
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
        //只有修改了博客内容才更新博客更新日期
        if(!blog.getContent().equals(blogTemp.getContent())){
            blog.setUpdateTime(new Date());
        }
        BeanUtils.copyProperties(blog, blogTemp, MyBeanUtils.getNullPropertyNames(blog));

        return blogResposiory.save(blogTemp);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogResposiory.deleteById(id);
    }


}
