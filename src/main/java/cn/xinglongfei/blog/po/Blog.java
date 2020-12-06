package cn.xinglongfei.blog.po;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Phoenix on 2020/11/16
 */
@Entity
@Table(name = "t_blog")
public class Blog implements Comparable<Blog> {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String description;
    private String coverPicture;
    private String flag;
    private String imageFolder;
    private boolean appreciateStatement;
    private boolean shareStatement;
    private boolean commentStatement;
    private boolean published;
    private boolean recommend;
    private Long viewCounts;
    private Long commentCounts;
    private Long likeCounts;
    private Long wordCounts;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    public Blog() {
        this.imageFolder = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + UUID.randomUUID().toString().substring(0, 4);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isAppreciateStatement() {
        return appreciateStatement;
    }

    public void setAppreciateStatement(boolean appreciateStatement) {
        this.appreciateStatement = appreciateStatement;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentStatement() {
        return commentStatement;
    }

    public void setCommentStatement(boolean commentStatement) {
        this.commentStatement = commentStatement;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Long getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(Long viewCounts) {
        this.viewCounts = viewCounts;
    }

    public Long getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(Long commentCounts) {
        this.commentCounts = commentCounts;
    }

    public Long getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Long likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Long getWordCounts() {
        return wordCounts;
    }

    public void setWordCounts(Long wordCounts) {
        this.wordCounts = wordCounts;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
        this.imageFolder = imageFolder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void init() {
        this.tagIds = this.tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public int compareTo(Blog blog) {
        //先按照优先级排序
        int i = Integer.parseInt(String.valueOf(blog.getCreateTime().getTime()).substring(0, 10))
                - Integer.parseInt(String.valueOf(this.getCreateTime().getTime()).substring(0, 10));
        if (i == 0) {
            return (int) (this.getId() - blog.getId());//如果优先级相等了再用ID进行排序
        }
        return i;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", appreciateStatement=" + appreciateStatement +
                ", shareStatement=" + shareStatement +
                ", commentStatement=" + commentStatement +
                ", published=" + published +
                ", recommend=" + recommend +
                ", viewCounts=" + viewCounts +
                ", commentCounts=" + commentCounts +
                ", likeCounts=" + likeCounts +
                ", wordCounts=" + wordCounts +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", category=" + category +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }

}
