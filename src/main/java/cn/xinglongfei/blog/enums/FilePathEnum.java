package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum FilePathEnum {

    BANNER("Banner"),
    COVER_PICTURE("CoverPicture"),
    OTHERS("Others"),
    USER("User"),
    PAGE("Page"),
    LINKAVATAR("LinkAvatar");


    private String path;

    FilePathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
