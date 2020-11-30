package cn.xinglongfei.blog.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Phoenix on 2020/11/18
 */
@Getter
@Setter
public class LinkQuery {

    private String title;
    private Long linkCategoryId;

    public LinkQuery() {
    }
}
