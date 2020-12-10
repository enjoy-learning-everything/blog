package cn.xinglongfei.blog.util;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;


/**
 * Created by Phoenix on 2020/12/11
 */
public class SslUtils {
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }


        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
        }


        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
        }
    }


    public static void ignoreSsl()
            throws Exception {
        javax.net.ssl.HostnameVerifier hv = new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String urlHostName, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}