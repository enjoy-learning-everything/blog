package cn.xinglongfei.blog.dao;

import cn.xinglongfei.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Phoenix on 2020/11/18
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * 根据pageable对象查询推荐的博客List
     * @param pageable 分页查询，封装了当前页，每页显示的条数，排序方式
     * @return 符合要求的博客List
     */
    @Query("select b from Blog b where b.recommend = true and b.published = true")
    List<Blog> findTop(Pageable pageable);

    /**
     * 根据pageable对象查询已发布的博客
     * @param pageable 分页查询，封装了当前页，每页显示的条数，排序方式
     * @return 已发布的博客
     */
    @Query("select b from Blog b where b.published = true")
    Page<Blog> findAllPublishedBlog(Pageable pageable);



    /**
     * 条件查询，根据标题和内容查询已发布的博客
     * @param query 需要查询的字段
     * @param pageable 分页查询，封装了当前页，每页显示的条数，排序方式
     * @return 标题或内容包含了该字段的已发布的博客
     */
    @Query("select b from Blog b where (b.title like ?1 or b.content like ?1)and b.published = true ")
    Page<Blog> findByQuery(String query,Pageable pageable);

    /**
     * 浏览次数加1
     * @param id 当前浏览量
     * @return 浏览量+1后的浏览量
     */
    @Transactional
    @Modifying
    @Query("update Blog b set b.viewCounts = b.viewCounts+1 where b.id = ?1")
    int updateViews(Long id);

    /**
     * 获取所有博客的年份，并把按倒序排序
     * @return 所有博客的年份倒序信息
     */
    @Query("select function('date_format',b.createTime,'%Y') as year from Blog b  group by year order by function('date_format',b.createTime,'%Y') desc")
    List<String> findGroupYear();

    /**
     * 查询当前年的所有博客
     * @param year 年份
     * @return 对应年份下的所有博客
     */
    @Query("select b from Blog b where function('date_format',b.createTime,'%Y') = ?1 and b.published = true ")
    List<Blog> findByYear(String year);
}
