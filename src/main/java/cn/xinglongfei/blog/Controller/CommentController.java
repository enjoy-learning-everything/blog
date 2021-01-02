package cn.xinglongfei.blog.Controller;

import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.Blog;
import cn.xinglongfei.blog.po.Comment;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.service.BlogService;
import cn.xinglongfei.blog.service.CommentService;
import cn.xinglongfei.blog.thread.SendEmailThread;
import cn.xinglongfei.blog.util.MosaicAvatarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Phoenix on 2020/11/19
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    SendEmailThread sendEmailThread;

    @Value("${blog.index}")
    private String blogIndex;

    @Value("${blog.authoremail}")
    private String blogAuthorEmail;

    @Value("${comment.avatar}")
    private String avatar;

    @MyLog(operation = "【访客端】加载碎片：评论列表")
    @GetMapping("/comments/{blogId}")
    public String commentList(@PathVariable Long blogId, Model model) {
        //传入评论列表
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        Blog blog = blogService.getBlog(blogId);
        model.addAttribute("commentCount", commentService.countCommentByBlog(blog));
        return "blog :: commentList";
    }

    /**
     * 添加评论
     *
     * @param comment 评论对象
     * @param session session对象
     * @return 调用/comments/{blogId}接口
     */
    @MyLog(operation = "【访客端】访问接口：添加评论", type = "新增")
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        //获取博客信息
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        //用户名不为空则设置为管理员身份和管理员头像
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            //如果邮箱有历史评论，则获得上次的头像
            String lastAvatar = commentService.getLastAvatar(comment.getEmail());
            if (lastAvatar == null) {
                try {
                    comment.setAvatar(MosaicAvatarUtil.createBase64Avatar());
                } catch (IOException e) {
                    comment.setAvatar(avatar);
                    e.printStackTrace();
                }
            } else {
                comment.setAvatar(lastAvatar);
            }
        }
        //保存评论
        Comment resultComment = commentService.saveComment(comment);
        //发送邮件
        sendEmailThread = new SendEmailThread(resultComment, blogAuthorEmail, blogIndex);
        Thread thread = new Thread(sendEmailThread);
        thread.start();
        return "redirect:/comments/" + blogId;
    }

}
