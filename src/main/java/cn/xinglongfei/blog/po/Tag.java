package cn.xinglongfei.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/16
 */
@Entity
@Table(name="t_tag")
public class Tag  implements Comparable<Tag>{

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public int compareTo(Tag o) {
        return o.blogs.size()-this.blogs.size(); // 根据博客数量降序排序
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
