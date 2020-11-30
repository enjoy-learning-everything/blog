package cn.xinglongfei.blog.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Phoenix on 2020/11/30
 */
@Data
@Entity
@Table(name = "t_link")
public class Link {

    @Id
    @GeneratedValue
    private Long id;
    private String title;       //网站名
    private String url;       //网站地址
    private String description;       //网站介绍
    private String avatar;       //网站图标
    private String email;        //站长邮箱
    private Long priority;       //链接优先级
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;       //创建时间

    @ManyToOne
    private LinkCategory linkCategory;

    @ManyToOne
    private User user;
}
