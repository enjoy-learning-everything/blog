package cn.xinglongfei.blog.Controller.admin;

import cn.xinglongfei.blog.config.AliyunOSSConfigConstant;
import cn.xinglongfei.blog.enums.FilePathEnum;
import cn.xinglongfei.blog.enums.FileUploadEnum;
import cn.xinglongfei.blog.enums.ImageLinkEnum;
import cn.xinglongfei.blog.log.MyLog;
import cn.xinglongfei.blog.po.Link;
import cn.xinglongfei.blog.po.User;
import cn.xinglongfei.blog.service.FileUploadService;
import cn.xinglongfei.blog.service.LinkCategoryService;
import cn.xinglongfei.blog.service.LinkService;
import cn.xinglongfei.blog.util.AliyunOSSUtil;
import cn.xinglongfei.blog.util.URLUtil;
import cn.xinglongfei.blog.vo.LinkQuery;
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
import java.util.UUID;

/**
 * Created by Phoenix on 2020/11/30
 */
@Controller
@RequestMapping("/admin")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkCategoryService linkCategoryService;

    @Autowired
    private FileUploadService fileUploadService;

    @MyLog(operation = "【管理端】跳转页面：外链列表")
    @GetMapping("/links")
    public String links(@PageableDefault(size = 10, sort = {"priority"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        model.addAttribute("linkCategories", linkCategoryService.listLinkCategory());
        model.addAttribute("page", linkService.listLink(pageable));
        return "admin/links";
    }


    @MyLog(operation = "【管理端】加载碎片：外链搜索结果列表")
    @PostMapping("/links/search")
    public String search(@PageableDefault(size = 10, sort = {"priority"},
            direction = Sort.Direction.ASC) Pageable pageable, LinkQuery link, Model model) {
        model.addAttribute("page", linkService.listLink(pageable, link));
        return "admin/links :: linkListTable";
    }

    @MyLog(operation = "【管理端】跳转页面：新增外链", type = "跳转")
    @GetMapping("/links/newLink")
    public String newTag(Model model) {
        model.addAttribute("linkCategories", linkCategoryService.listLinkCategory());
        model.addAttribute("link", new Link());
        return "admin/newLink";
    }

    @MyLog(operation = "【管理端】跳转页面：修改外链")
    @GetMapping("/links/{id}/edit")
    public String editBlog(@PathVariable Long id, Model model) {
        model.addAttribute("linkCategories", linkCategoryService.listLinkCategory());
        Link link = linkService.getLink(id);
        model.addAttribute("link", link);
        return "admin/newLink";
    }


    @MyLog(operation = "【管理端】访问接口：新增外链", type = "新增")
    @PostMapping("/links")
    public String newBlogPost(Link link, @RequestParam(value = "file", required = false) MultipartFile file,
                              @RequestParam String uploadType, @RequestParam(value = "imageUrl", required = false) String imageUrl,
                              RedirectAttributes attributes, HttpSession session) throws IOException {
        //获取session中的用户信息
        link.setUser((User) session.getAttribute("user"));
        //根据外链分类ID设置外链分类
        link.setLinkCategory(linkCategoryService.getLinkCategory(link.getLinkCategory().getId()));
        Link linkTemp = null;
        //如果首图是本地上传
        if (uploadType.equals("upLoad")) {
            //随机文件名
            String randomName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
            link.setAvatar("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.LINKAVATAR.getPath() + "/"
                    + randomName);
            System.out.println(link.getAvatar());
            //判断文件是否合法，合法则直接上传，不合法则返回错误信息
            String result = fileUploadService.upLoadFileToALiYunOSS(file, FilePathEnum.LINKAVATAR.getPath(), randomName);
            if (result.equals(FileUploadEnum.UPLOAD_FILE_SUCCESS.getValue())) {
                linkTemp = linkService.saveLink(link);
                if (linkTemp == null) {
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
            }
            //随机文件名
            String randomName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
            link.setAvatar("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.LINKAVATAR.getPath() + "/"
                    + randomName);
            String result = fileUploadService.upLoadFileToALiYunOSS(imageUrl, FilePathEnum.LINKAVATAR.getPath(), randomName);
            if (result.equals(ImageLinkEnum.UPLOAD_BY_URL_SUCCESS.getValue())) {
                linkTemp = linkService.saveLink(link);
                if (linkTemp == null) {
                    attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库新增失败");
                } else {
                    attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库新增成功");
                }
            } else {
                attributes.addFlashAttribute("message", ImageLinkEnum.getDescByValue(result));
            }
        }
        return "redirect:/admin/links";
    }

    @MyLog(operation = "【管理端】访问接口：修改外链", type = "修改")
    @PostMapping("/links/{id}")
    public String editLink(Link link, @RequestParam(value = "file", required = false) MultipartFile file,
                              @RequestParam String uploadType, @RequestParam(value = "imageUrl", required = false) String imageUrl,
                              RedirectAttributes attributes, HttpSession session) throws IOException {
        //获取session中的用户信息
        link.setUser((User) session.getAttribute("user"));
        //根据外链分类ID设置外链分类
        link.setLinkCategory(linkCategoryService.getLinkCategory(link.getLinkCategory().getId()));
        Link linkTemp = null;
        //如果需要更新的首图是本地上传，说明肯定需要更改首图（因为回显的是网址，本地上传的input是没有数据的）
        if (uploadType.equals("upLoad")) {
            //获取原首图的网络链接地址
            String webUrl = linkService.getLink(link.getId()).getAvatar();
            //随机文件名
            String randomName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
            link.setAvatar("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.LINKAVATAR.getPath() + "/"
                    + randomName);
            //判断文件是否合法，合法则直接上传，不合法则返回错误信息
            String result = fileUploadService.upLoadFileToALiYunOSS(file, FilePathEnum.LINKAVATAR.getPath(), randomName);
            if (result.equals(FileUploadEnum.UPLOAD_FILE_SUCCESS.getValue())) {
                linkTemp = linkService.updateLink(link.getId(), link);
                if (linkTemp == null) {
                    attributes.addFlashAttribute("message", "上传本地文件成功，数据库更新失败");
                } else {
                    //数据库修改成功后要删除原来的图片
                    AliyunOSSUtil.deleteFile(webUrl);
                    attributes.addFlashAttribute("message", "上传本地文件成功，数据库更新成功，原图片删除成功");
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
                //获取原首图的网络链接地址
                String webUrl = linkService.getLink(link.getId()).getAvatar();
                if (!webUrl.equals(imageUrl)) {
                    //随机文件名
                    String randomName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
                    //设置博客首图地址为上传文件成功后，在阿里云oss中，该文件的访问地址
                    link.setAvatar("http://" + AliyunOSSConfigConstant.getDomainName() + "/" + FilePathEnum.LINKAVATAR.getPath() + "/"
                            + randomName);
                    String result = fileUploadService.upLoadFileToALiYunOSS(imageUrl, FilePathEnum.LINKAVATAR.getPath(), randomName);
                    if (result.equals(ImageLinkEnum.UPLOAD_BY_URL_SUCCESS.getValue())) {
                        linkTemp = linkService.updateLink(link.getId(), link);
                        if (linkTemp == null) {
                            attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库更新失败");
                        } else {
                            //数据库修改成功后要删除原来的图片
                            AliyunOSSUtil.deleteFile(webUrl);
                            attributes.addFlashAttribute("message", "根据地址上传文件成功，数据库更新成功，原图片删除成功");
                        }
                    } else {
                        attributes.addFlashAttribute("message", ImageLinkEnum.getDescByValue(result));
                    }
                }else{
                    linkTemp = linkService.updateLink(link.getId(), link);
                    if (linkTemp == null) {
                        attributes.addFlashAttribute("message", "您未更改首图地址，且数据库更新失败");
                    } else {
                        attributes.addFlashAttribute("message", "您未更改首图地址，数据库更新成功");
                    }

                    //数据库中保存的首图网址和接收到的不一致，说明需要更改首图并且删除原来的首图
                }
            }
        }
        return "redirect:/admin/links";
    }

    @MyLog(operation = "【管理端】访问接口：删除外链", type = "删除")
    @GetMapping("/links/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        Link linkTemp = linkService.getLink(id);
        if(linkTemp!=null){
            //从阿里云删除文件
            AliyunOSSUtil.deleteFile(linkTemp.getAvatar());
            linkService.deleteLink(id);
        }
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/links";
    }

}
