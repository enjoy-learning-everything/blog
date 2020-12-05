package cn.xinglongfei.blog.po;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Phoenix on 2020/11/16
 */
@Entity
@Table(name="t_category")
public class Category implements Comparable<Category>{

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空！")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Blog> blogs = new ArrayList<>();

    public Category() {
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
    public int compareTo(Category o) {
        return o.blogs.size()-this.blogs.size(); // 根据博客数量降序排序
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
