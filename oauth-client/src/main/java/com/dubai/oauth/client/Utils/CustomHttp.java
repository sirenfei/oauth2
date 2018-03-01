package com.dubai.oauth.client.Utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 返回值，请求地址
 * @author xueyongfei01 xueyongfei01@baidu.com
 * @date 2014年8月30日 下午2:26:00 
 */
@Component("customHttp")
public class CustomHttp {
    private static final Logger logger = LoggerFactory.getLogger(CustomHttp.class);

    private static final int CONNECT_TIMEOUT = 8000;
    private static final int READ_TIMEOUT = 8000;
    private static final int REQUEST_RETRY_TIMES = 8;

    protected CloseableHttpClient httpClient;
    private RequestConfig requestConfig;

    /**
     * 
     * 拼接http入参，基数为key，偶数为value
     * @param params
     * @author xueyongfei01 xueyongfei01@baidu.com
     * @date 2014年8月7日 下午2:10:41
     */
    public List<NameValuePair> buildRequestParmas(String... params) throws RuntimeException {
        if (params.length % 2 > 0) {
            throw new RuntimeException("params count must be event!");
        }
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (int i = 0; i < params.length; i = i + 2) {
            nvps.add(new BasicNameValuePair(params[i], params[i + 1]));
        }
        return nvps;
    }

    public String manualGet(String url, Header[] headers) {
        long t1 = System.currentTimeMillis();
        String result = null;
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(requestConfig);
        httpget.setHeaders(headers);
        
        try {
            result = httpClient.execute(httpget, responseHandler);
        }
        catch (ClientProtocolException e) {
            logger.error("response failed...ClientProtocolException:", e);
        } catch (SocketTimeoutException e) {
            logger.error("response failed...SocketTimeoutException:", e.getMessage());
        } catch (IOException e) {
            logger.error("response failed...IOException:", e.getMessage());
        } catch (Exception e) {
            logger.error("response failed...Exception:", e.getMessage());
        }
        logger.info("manualGet $cost:{}$url:{}$result:{}", System.currentTimeMillis() - t1, url,result);
        return result;
    }



    
    
    /**
     * 
     */
    public String manualPost(String url, String reqCharset, List<NameValuePair> nvps,Header[] headers) {
        long t1 = System.currentTimeMillis();
        if (StringUtils.isEmpty(url)) {
            logger.error("post url is null");
            return null;
        }

        HttpPost httpPost = new HttpPost(url);
        String result = null;

        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(nvps, reqCharset);
        } 
        catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncoding:" + reqCharset, e);
        }
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(entity);
        httpPost.setHeaders(headers);
        try {
            result = httpClient.execute(httpPost, responseHandler);
        } catch (ClientProtocolException e) {
            logger.error("response failed...ClientProtocolException:", e);
            return result;
        } catch (IOException e) {
            logger.error("response failed...IOException:", e);
            return result;
        }catch (Exception e) {
            logger.error("response failed...Exception:", e);
            return result;
        }
        logger.info("manualPost url:{}$cost:{}$result:{}", url, System.currentTimeMillis() - t1 ,result);
        return result;
    }
    
    

    /**
     * 注册或者登录
     * @param url
     * @param reqCharset 可以写Consts.UTF_8
     * @param respCharset Consts.UTF_8
     * @param nvps 参数列表
     * @author xueyongfei01 xueyongfei01@baidu.com
     * @date 2014年8月7日 下午3:09:09
     */
    public Header[] cookiePost(String url, String reqCharset, List<NameValuePair> nvps) {
        long t1 = System.currentTimeMillis();

        HttpPost httpPost = new HttpPost(url);
        Header[] result = null;

        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(nvps, reqCharset);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncoding:" + reqCharset, e);
        }
        httpPost.setEntity(entity);
        try {
            result = httpClient.execute(httpPost, cookieResponseHandler);
        } catch (ClientProtocolException e) {
            logger.error("response failed...ClientProtocolException:", e);
        } catch (IOException e) {
            logger.error("response failed...IOException:", e);
        }
        logger.info("call service completed.Total_ms={}. url={}", (System.currentTimeMillis() - t1), url);
        return result;
    }

    private ResponseHandler<Header[]> cookieResponseHandler = new ResponseHandler<Header[]>() {
        @Override
        public Header[] handleResponse(final HttpResponse response) throws IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }

            Header[] headers = response.getHeaders("Set-Cookie");
            return headers;
        }
    };

    private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        @Override
        public String handleResponse(final HttpResponse response) throws IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            String body = EntityUtils.toString(response.getEntity(), charset);
            return body;
        }
    };

    /**
     * 重试机制
     */
    HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= REQUEST_RETRY_TIMES) {
                logger.error("manual_error:httprequest retry over {} times", REQUEST_RETRY_TIMES);
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                logger.error("manual_error:httprequest ioException");
                return true;
            }
            if (exception instanceof UnknownHostException) {
                logger.error("manual_error:httprequest UnknownHostException");
                return true;
            }
            if (exception instanceof ConnectTimeoutException) {
                // 连接被拒绝
                logger.error("manual_error:httprequest connection rejected!");
                return true;
            }
            if (exception instanceof SSLException) {
                logger.error("manual_error:httprequest ssl handshake rejected!");
                return true;
            }
            if (exception instanceof Exception) {
            	logger.error("manual_error:httprequest ssl handshake rejected!");
            	return true;
            }
            return false;
        }
    };

    @PostConstruct
    public void initParams() {
        //设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(CONNECT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(READ_TIMEOUT).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(600); //将最大连接数增加到600
        connManager.setDefaultMaxPerRoute(3);//将每个路由基础的连接增加到6
        connManager.setDefaultSocketConfig(socketConfig);
        httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(myRetryHandler).build();
    }

}
