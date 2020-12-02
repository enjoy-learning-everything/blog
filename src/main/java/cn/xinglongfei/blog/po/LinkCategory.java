package cn.xinglongfei.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phoenix on 2020/11/30
 */
@Setter
@Getter
@Entity
@Table(name = "t_lcategory")
public class LinkCategory implements Comparable<LinkCategory>{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空！")
    private String name;

    private Long priority = 999L;       //链接优先级，默认为999

    @OneToMany(mappedBy = "linkCategory")
    private List<Link> links = new ArrayList<>();

    @Override
    public int compareTo(LinkCategory linkCategory) {
        int i = (int) (this.getPriority() - linkCategory.getPriority());//先按照优先级排序
        if(i == 0){
            return (int) (this.getId() - linkCategory.getId());//如果优先级相等了再用ID进行排序
        }
        return i;
    }
}
