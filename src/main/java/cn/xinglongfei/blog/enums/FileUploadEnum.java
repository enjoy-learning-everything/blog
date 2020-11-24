package cn.xinglongfei.blog.enums;

/**
 * Created by Phoenix on 2020/11/23
 */
public enum FileUploadEnum {

    FILE_IS_NULL("file_is_null","文件为空"),
    FILE_NAME_IS_NULL("file_name_is_null","文件名为空"),
    NOT_A_IMAGE_FILE("not_a_image_file","不是图片文件"),
    FILE_SIZE_EXCEEDS_1MB("file_size_exceeds_1MB","文件大小超过1MB"),
    FILE_DEAL_FAIL("file_deal_fail","文件处理异常"),
    UPLOAD_FILE_SUCCESS("upload_file_success","文件上传成功"),
    UPLOAD_FILE_FAIL("upload_file_fail","文件上传失败"),
    TEMP_FILE_TEMP_DELETE_FIAL("temp_file_temp_delete_fial","文件上传成功，但临时文件删除失败，可忽略此消息");


    private String value;
    private String desc;

    FileUploadEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getValueByDesc(String desc){
        FileUploadEnum[] fileUploadEnums = values();
        for (FileUploadEnum fileUploadEnum : fileUploadEnums) {
            if (fileUploadEnum.getDesc().equals(desc)) {
                return fileUploadEnum.getValue();
            }
        }
        return null;
    }


    public static String getDescByValue(String value){
        FileUploadEnum[] fileUploadEnums = values();
        for (FileUploadEnum fileUploadEnum : fileUploadEnums) {
            if (fileUploadEnum.getValue().equals(value)) {
                return fileUploadEnum.getDesc();
            }
        }
        return null;
    }


}
