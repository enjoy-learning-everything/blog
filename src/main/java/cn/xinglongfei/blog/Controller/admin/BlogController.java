package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.config.AliyunOSSConfigConstant;
import cn.xinglongfei.blog.enums.FilePathEnum;
import cn.xinglongfei.blog.enums.FileUploadEnum;
import cn.xinglongfei.blog.enums.ImageLinkEnum;
import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.Blog;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.service.BlogService;
import cn.xinglongfei.blog.service.CategoryService;
import cn.xinglongfei.blog.service.FileUploadService;
import cn.xinglongfei.blog.service.TagService;
import cn.xinglongfei.blog.util.URLUtil;
import cn.xinglongfei.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Phoenix on 2020/11/17
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String NEWBLOGPAGE = "admin/newBlog";
    private static final String LISTBLOGPAGE = "admin/blogs";
    private static final String REDIRECT_LISTBLOGPAGE = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private FileUploadService fileUploadService;

    @MyLog(operation = "【管理端】跳转页面：博客列表")
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"createTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        //传输分类列表信息
        model.addAttribute("categories", categoryService.listCategory());
        //传输博客分页信息
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return LISTBLOGPAGE;
    }

    @MyLog(operation = "【管理端】加载碎片：博客搜索结果列表")
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"createTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        //传输博客分页信息
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs :: blogListTable";
    }

    /**
     * 传输分类和标签列表信息
     *
     * @param model 页面模型层
     */
    private void setTypeAndTag(Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());
    }

    @MyLog(operation = "【管理端】跳转页面：新增博客")
    @GetMapping("/blogs/newBlog")
    public String newBlog(Model model) {
        setTypeAndTag(model);
        Blog blog = new Blog();
        //初始化博客图片目录
        blog.setImageFolder(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + UUID.randomUUID().toString().substring(0, 4));
        model.addAttribute("blog", blog);
        return NEWBLOGPAGE;
    }

    @MyLog(operation = "【访客端】跳转页面：博客详情")
    @GetMapping("/blogs/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }


    @MyLog(operation = "【管理端】跳转页面：修改博客")
    @GetMapping("/blogs/{id}/edit")
    public String editBlog(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        //获取原博客信息
        Blog blog = blogService.getBlog(id);
        blog.init();
        //传入原博客信息
        model.addAttribute("blog", blog);
        return NEWBLOGPAGE;
    }


    @MyLog(operation = "【管理端】访问接口：新增博客", type = "新增")
    @PostMapping("/blogs")
    public String newBlogPost(Blog blog, @RequestParam(value = "file", required = false) MultipartFile file,
                              @RequestParam String uploadType, @RequestParam(value = "imageUrl", required = false) String imageUrl,
                              RedirectAttributes attributes, HttpSession session) {
        //获取session中的用户信息
        blog.setUser((User) session.getAttribute("user"));
        //根据博客分类ID和tagsID设置博客分类和标签
        blog.setCategory(categoryService.getCategory(blog.getCategory().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blogTemp = null;
        try {
            //如果首图是本地上传
            if (uploadType.equals("upLoad")) {
                //随机文件名
                String randomName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
                blog.setCoverPicture("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.COVER_PICTURE.getPath() + "/"
                        + randomName);
                //判断文件是否合法，合法则直接上传，不合法则返回错误信息
                String result = fileUploadService.upLoadFileToALiYunOSS(file, FilePathEnum.COVER_PICTURE.getPath(), randomName);
                if (result.equals(FileUploadEnum.UPLOAD_FILE_SUCCESS.getValue())) {
                    blogTemp = blogService.saveBlog(blog);
                    if (blogTemp == null) {
                        attributes.addFlashAttribute("message", "上传本地文件成功，数据库新增失败");
                    } else {
                        attributes.addFlashAttribute("message", "上传本地文件成功，数据库新增成功");
                    }
                } else {
                    attributes.addFlashAttribute("message", FileUploadEnum.getDescByValue(result));
                }
                //如果首图是网络地址上传
            } else if (uploadType.equals("netLink")) {
                if (!URLUtil.verifyImageUrl(imageUrl).equals(ImageLinkEnum.URL_IS_VERIFIED.getDesc())) {
                    attributes.addFlashAttribute("message", URLUtil.verifyImageUrl(imageUrl));
                } else {
                    //检测是否阿里云OSS当前文件夹中已经有这个文件地址了
                    // (其实函数只能判断是否是oss路径，但是上面已经判断是有效的图片路径了，两者都通过就可以认为是存在此文件)
                    if (URLUtil.isOSSFile(imageUrl)) {
                        blog.setCoverPicture(imageUrl);
                        blogTemp = blogService.saveBlog(blog);
                        if (blogTemp == null) {
                            attributes.addFlashAttribute("message", "检测到此图片已存在，无需上传到OSS，但数据库新增失败");
                        } else {
                            attributes.addFlashAttribute("message", "检测到此图片已存在，无需上传到OSS，数据库新增成功");
                        }
                        //如果不是oss端文件
                    } else {
                        //随机文件名
                        String randomName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
                        //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
                        blog.setCoverPicture("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.COVER_PICTURE.getPath() + "/"
                                + randomName);
                        String result = fileUploadService.upLoadFileToALiYunOSS(imageUrl, FilePathEnum.COVER_PICTURE.getPath(), randomName);
                        if (result.equals(ImageLinkEnum.UPLOAD_BY_URL_SUCCESS.getValue())) {
                            blogTemp = blogService.saveBlog(blog);
                            if (blogTemp == null) {
                                attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库新增失败");
                            } else {
                                attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库新增成功");
                            }
                        } else {
                            attributes.addFlashAttribute("message", ImageLinkEnum.getDescByValue(result));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", FileUploadEnum.FILE_DEAL_FAIL.getDesc());
        }
        return REDIRECT_LISTBLOGPAGE;
    }


    @MyLog(operation = "【管理端】访问接口：修改博客", type = "修改")
    @PostMapping("/blogs/{id}")
    public String editBlogPost(Blog blog, @RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestParam String uploadType, @RequestParam(value = "imageUrl", required = false) String imageUrl,
                               RedirectAttributes attributes, HttpSession session) {
        //获取session中的用户信息
        blog.setUser((User) session.getAttribute("user"));
        //根据博客分类ID和tagsID设置博客分类和标签
        blog.setCategory(categoryService.getCategory(blog.getCategory().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blogTemp = null;
        try {
            //如果需要更新的首图是本地上传，说明肯定需要更改首图（因为回显的是网址，本地上传的input是没有数据的）
            if (uploadType.equals("upLoad")) {
                //获取原首图的网络链接地址
                String webUrl = blogService.getBlog(blog.getId()).getCoverPicture();
                //随机文件名
                String randomName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
                blog.setCoverPicture("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.COVER_PICTURE.getPath() + "/"
                        + randomName);
                //判断文件是否合法，合法则直接上传，不合法则返回错误信息
                String result = fileUploadService.upLoadFileToALiYunOSS(file, FilePathEnum.COVER_PICTURE.getPath(), randomName);
                if (result.equals(FileUploadEnum.UPLOAD_FILE_SUCCESS.getValue())) {
                    blogTemp = blogService.updateBlog(blog.getId(), blog);
                    if (blogTemp == null) {
                        attributes.addFlashAttribute("message", "上传本地文件成功，数据库更新失败");
                    } else {
                        //数据库修改成功后要删除原来的图片
                        //AliyunOSSUtil.deleteFile(webUrl);
                        attributes.addFlashAttribute("message", "上传本地文件成功，数据库更新成功，原图片暂不删除");
                    }
                } else {
                    attributes.addFlashAttribute("message", FileUploadEnum.getDescByValue(result));
                }
                //如果首图是网络地址上传
            } else if (uploadType.equals("netLink")) {
                //图片验证不通过
                if (!URLUtil.verifyImageUrl(imageUrl).equals(ImageLinkEnum.URL_IS_VERIFIED.getDesc())) {
                    attributes.addFlashAttribute("message", URLUtil.verifyImageUrl(imageUrl));
                    //图片验证通过
                } else {
                    //检测是否阿里云OSS当前文件夹中已经有这个文件地址了
                    // (其实函数只能判断是否是oss路径，但是上面已经判断是有效的图片路径了，两者都通过就可以认为是存在此文件)
                    if (URLUtil.isOSSFile(imageUrl)) {
                        blog.setCoverPicture(imageUrl);
                        blogTemp = blogService.updateBlog(blog.getId(), blog);
                        if (blogTemp == null) {
                            attributes.addFlashAttribute("message", "检测到此图片已存在，无需上传到OSS，但数据库更新失败");
                        } else {
                            attributes.addFlashAttribute("message", "检测到此图片已存在，无需上传到OSS，数据库更新成功");
                        }
                        //如果不是oss端文件
                    } else {
                        //获取原首图的网络链接地址
                        String webUrl = blogService.getBlog(blog.getId()).getCoverPicture();
                        //随机文件名
                        String randomName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
                        //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
                        blog.setCoverPicture("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.COVER_PICTURE.getPath() + "/"
                                + randomName);
                        String result = fileUploadService.upLoadFileToALiYunOSS(imageUrl, FilePathEnum.COVER_PICTURE.getPath(), randomName);
                        if (result.equals(ImageLinkEnum.UPLOAD_BY_URL_SUCCESS.getValue())) {
                            blogTemp = blogService.updateBlog(blog.getId(), blog);
                            if (blogTemp == null) {
                                attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库更新失败");
                            } else {
                                //数据库修改成功后要删除原来的图片
                                //AliyunOSSUtil.deleteFile(webUrl);
                                attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库更新成功，原图片暂不删除");
                            }
                        } else {
                            attributes.addFlashAttribute("message", ImageLinkEnum.getDescByValue(result));
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", FileUploadEnum.FILE_DEAL_FAIL.getDesc());
        }
        return REDIRECT_LISTBLOGPAGE;
    }

    @MyLog(operation = "【管理端】访问接口：删除博客", type = "删除")
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        Blog blogTemp = blogService.getBlog(id);
        if (blogTemp != null) {
//            AliyunOSSUtil.deleteFile(blogTemp.getCoverPicture());
            blogService.deleteBlog(id);
        }
        attributes.addFlashAttribute("message", "删除成功，首图暂不删除");
        return REDIRECT_LISTBLOGPAGE;
    }
}
