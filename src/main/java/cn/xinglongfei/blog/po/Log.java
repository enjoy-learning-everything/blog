package cn.xinglongfei.blog.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Phoenix on 2020/11/28
 */
@Data
@Entity
@Table(name = "t_syslog")
public class Log {
    @Id
    @GeneratedValue
    private Long id;    //日志ID
    private Long userId;        //用户ID
    private String username;    //用户名
    private String operation;   //操作
    private String method;      //操作的方法
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String args;        //方法参数
    private String ip;          //操作者ip地址
    private String type;           //操作类型(登录、查询、修改)
    @Temporal(TemporalType.TIMESTAMP)
    private Date operaTime;     //操作时间
}
