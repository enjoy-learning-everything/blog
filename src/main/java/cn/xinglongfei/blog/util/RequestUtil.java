package cn.xinglongfei.blog.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Phoenix on 2020/12/20
 */
public class RequestUtil {


    /**
     * 由于使用了nginx作为代理，在后台应用服务中直接采用request.getRemoteAddr()，无法获取到请求所处的真实ip，需要通过下面的代码获取
     * @param request http请求
     * @return 真实Ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
