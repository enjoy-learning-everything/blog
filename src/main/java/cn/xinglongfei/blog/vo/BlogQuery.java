package cn.xinglongfei.blog.vo;

/**
 * Created by Phoenix on 2020/11/18
 */
public class BlogQuery {

    private String title;
    private Long categoryId;
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", recommend=" + recommend +
                '}';
    }
}
