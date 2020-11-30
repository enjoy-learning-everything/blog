package cn.xinglongfei.blog.po;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
@Data
@Entity
@Table(name = "t_lcategory")
public class LinkCategory {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空！")
    private String name;

    private Long priority;

    @OneToMany(mappedBy = "linkCategory")
    private List<Link> blogs = new ArrayList<>();
}
