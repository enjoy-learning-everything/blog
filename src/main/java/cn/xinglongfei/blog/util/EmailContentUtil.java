package cn.xinglongfei.blog.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Phoenix on 2020/12/29
 */
public class EmailContentUtil {

    /**
     * 将文件内容读出
     * @param fileName 相对目录+文件名，resources为根目录
     * @return 文件内容
     */
    public static String readToString(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = classPathResource.getInputStream();

        String encoding = "UTF-8";
        byte[] filecontent = new byte[inputStream.available()];
        try {
            inputStream.read(filecontent);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 向新的评论模板中添加信息
     * @param template 评论模板
     * @param formUser 评论人
     * @param commentContent 评论的内容
     * @param blogUrl 博客评论链接地址
     * @return 修改后的内容
     */
    public static String setNewCommentEmailContent(String template, String formUser,String commentContent,String blogUrl) {
        //添加评论人信息
        Pattern formUserFilter = Pattern.compile("<span class=\"comment-user\".*?>[\\s\\S]*?</span>", Pattern.CASE_INSENSITIVE);
        Matcher formUserFilterMatcher = formUserFilter.matcher(template);
        String formUserFind = null;
        if(formUserFilterMatcher.find()) {
            formUserFind = formUserFilterMatcher.group();
        }
        StringBuffer formUserStringBuffer = new StringBuffer(formUserFind);
        formUserStringBuffer.insert(formUserFind.lastIndexOf("</span>"), formUser);
        template = template.replaceAll("<span class=\"comment-user\".*?>[\\s\\S]*?</span>", formUserStringBuffer.toString());

        //添加评论内容
        Pattern commentContentFilter = Pattern.compile("<span class=\"comment-content\".*?>[\\s\\S]*?</span>", Pattern.CASE_INSENSITIVE);
        Matcher commentContentFilterMatcher = commentContentFilter.matcher(template);
        String commentContentFind = null;
        if(commentContentFilterMatcher.find()) {
            commentContentFind = commentContentFilterMatcher.group();
        }
        StringBuffer commentContentStringBuffer = new StringBuffer(commentContentFind);
        commentContentStringBuffer.insert(commentContentFind.lastIndexOf("</span>"), commentContent);
        template = template.replaceAll("<span class=\"comment-content\".*?>[\\s\\S]*?</span>", commentContentStringBuffer.toString());

        //添加博客链接
        Pattern blogUrlFilter = Pattern.compile("<a class=\"comment-link\" href=\"#comment-container\".*?>*?</a>", Pattern.CASE_INSENSITIVE);
        Matcher blogUrlFilterMatcher = blogUrlFilter.matcher(template);
        String blogUrlFind = null;
        if(blogUrlFilterMatcher.find()) {
            blogUrlFind = blogUrlFilterMatcher.group();
        }
        StringBuffer blogUrlStringBuffer = new StringBuffer(blogUrlFind);
        blogUrlStringBuffer.insert(blogUrlFind.lastIndexOf("#comment-container"), blogUrl);
        template = template.replaceAll("<a class=\"comment-link\" href=\"#comment-container\".*?>*?</a>", blogUrlStringBuffer.toString());

        return template;
    }

    /**
     * 向新的评论回复模板中添加信息
     * @param template 评论模板
     * @param commentUser 原评论人
     * @param commentContent 原评论的内容
     * @param commentReplyUser 新的回复的评论人
     * @param commentReplyContent  新的回复的评论内容
     * @param blogUrl 博客评论链接地址
     * @return 修改后的内容
     */
    public static String setNewCommentReplyEmailContent(String template, String commentUser,String commentContent,
                                                        String commentReplyUser,String commentReplyContent,String blogUrl){
        //添加原评论人信息
        Pattern formUserFilter = Pattern.compile("<span class=\"comment-user\"></span>", Pattern.CASE_INSENSITIVE);
        Matcher formUserFilterMatcher = formUserFilter.matcher(template);
        String formUserFind = null;
        if(formUserFilterMatcher.find()) {
            formUserFind = formUserFilterMatcher.group();
        }
        StringBuffer formUserStringBuffer = new StringBuffer(formUserFind);
        formUserStringBuffer.insert(formUserFind.lastIndexOf("</span>"), commentUser);
        template = template.replaceAll("<span class=\"comment-user\"></span>", formUserStringBuffer.toString());


        //添加原评论人评论内容
        Pattern commentContentFilter = Pattern.compile("<p class=\"comment-content\"></p>", Pattern.CASE_INSENSITIVE);
        Matcher commentContentFilterMatcher = commentContentFilter.matcher(template);
        String commentContentFind = null;
        if (commentContentFilterMatcher.find()) {
            commentContentFind = commentContentFilterMatcher.group();
        }
        StringBuffer commentContentStringBuffer = new StringBuffer(commentContentFind);
        commentContentStringBuffer.insert(commentContentFind.lastIndexOf("</p>"), commentContent);
        template = template.replaceAll("<p class=\"comment-content\"></p>", commentContentStringBuffer.toString());

        //添加评论回复者信息
        Pattern toUserFilter = Pattern.compile("<span class=\"comment-reply-user\"></span>", Pattern.CASE_INSENSITIVE);
        Matcher toUserFilterMatcher = toUserFilter.matcher(template);
        String toUserFind = null;
        if (toUserFilterMatcher.find()) {
            toUserFind = toUserFilterMatcher.group();
        }
        StringBuffer toUserStringBuffer = new StringBuffer(toUserFind);
        toUserStringBuffer.insert(toUserFind.lastIndexOf("</span>"), commentReplyUser);
        template = template.replaceAll("<span class=\"comment-reply-user\"></span>", toUserStringBuffer.toString());

        //添加原评论人评论内容
        Pattern commentReplyContentFilter = Pattern.compile("<span class=\"comment-reply-content\"></span></p>", Pattern.CASE_INSENSITIVE);
        Matcher commentReplyContentFilterMatcher = commentReplyContentFilter.matcher(template);
        String commentReplyContentFind = null;
        if (commentReplyContentFilterMatcher.find()) {
            commentReplyContentFind = commentReplyContentFilterMatcher.group();
        }
        StringBuffer commentReplyContentStringBuffer = new StringBuffer(commentReplyContentFind);
        commentReplyContentStringBuffer.insert(commentReplyContentFind.lastIndexOf("</p>"), commentReplyContent);
        template = template.replaceAll("<span class=\"comment-reply-content\"></span></p>", commentReplyContentStringBuffer.toString());


        //添加博客链接
        Pattern blogUrlFilter = Pattern.compile("<a class=\"comment-link\" href=\"#comment-container\".*?>*?</a>", Pattern.CASE_INSENSITIVE);
        Matcher blogUrlFilterMatcher = blogUrlFilter.matcher(template);
        String blogUrlFind = null;
        if (blogUrlFilterMatcher.find()) {
            blogUrlFind = blogUrlFilterMatcher.group();
        }
        StringBuffer blogUrlStringBuffer = new StringBuffer(blogUrlFind);
        blogUrlStringBuffer.insert(blogUrlFind.lastIndexOf("#comment-container"), blogUrl);
        template = template.replaceAll("<a class=\"comment-link\" href=\"#comment-container\".*?>*?</a>", blogUrlStringBuffer.toString());

        return template;
    }


}
