package cn.xinglongfei.blog.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Phoenix on 2020/11/30
 */
@Setter
@Getter
@Entity
@Table(name = "t_link")
public class Link implements Comparable<Link> {

    @Id
    @GeneratedValue
    private Long id;
    private String title;       //网站名
    private String url;       //网站地址
    private String description;       //网站介绍
    private String avatar;       //网站图标
    private String email = null;        //站长邮箱，默认为null
    private Long priority = 999L;       //链接优先级，默认为999
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;       //创建时间


    @ManyToOne
    private LinkCategory linkCategory;

    @ManyToOne
    private User user;

    @Override
    public int compareTo(Link link) {
        int i = (int) (this.getPriority() - link.getPriority());//先按照优先级排序
        if (i == 0) {
            return (int) (this.getId() - link.getId());//如果优先级相等了再用ID进行排序
        }
        return i;
    }
}
