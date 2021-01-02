package cn.xinglongfei.blog.vo;

/**
 * Created by Phoenix on 2020/11/18
 */
public class BlogQuery {

    private String title;
    private Long categoryId;
    private boolean recommend;
    private boolean noPublished;

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

    public boolean isNoPublished() {
        return noPublished;
    }

    public void setNoPublished(boolean noPublished) {
        this.noPublished = noPublished;
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", recommend=" + recommend +
                ", noPublished=" + noPublished +
                '}';
    }
}
