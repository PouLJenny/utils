package top.poul.utils;


import top.poul.utils.codec.CharacterEncode;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * 发送http请求的工具类
 * 
 * @author 杨霄鹏
 * @version 1.0, 2016-11-25
 * @since
 */
public final class HttpRequest {

    public static final String FORM = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String JSON = "application/json;charset=UTF-8";

    private static final String HTTP = "http";
    private static final String HTTPS = "https";


    /**
     * 超时时间
     */
    private static int timeout = 30 * 1000;

    private static String charsetName = "UTF-8";

    // 私有构造器 无法实例化
    private HttpRequest() {
        super();
    }
    
    /**
     * 发送TRACE请求  此方法待实际验证
     * @param requestUrl
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static HttpResponse sendTrace(final String requestUrl) throws UnsupportedEncodingException, IOException {
        HttpResponse response = new HttpResponse();
        URL url = new URL(requestUrl);

        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置连接属性
        // conn.setDoOutput(true);// 使用 URL 连接进行输出 默认是false TRACE请求不支持 output
        conn.setDoInput(true);// 使用 URL 连接进行输入 默认是true
        conn.setUseCaches(false);// 不使用缓存 默认是true
        conn.setRequestMethod("TRACE"); // 默认是GET

        conn.setConnectTimeout(timeout);// 设置连接建立的超时时间
        conn.setReadTimeout(timeout);// 设置读取的超时时间

        // 设置请求属性
        //conn.setRequestProperty("Content-Type", contentType);// 请求携带参数的方式
        conn.setRequestProperty("Charset", charsetName);// 设置编码

        // 建立实际的连接
        conn.connect();
        // 获取所有的相应头
        response.setHead(getResponseHead(conn));
        // 获取URL的响应体
        response.setBody(getResponseBody(conn));

        return response;
    }
    
    
    /**
     * 发送OPTIOINS请求  此方法待实际验证
     * @param requestUrl
     * @param params
     * @param contentType
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static HttpResponse sendOptions(final String requestUrl,final String params,final String contentType) throws UnsupportedEncodingException, IOException {
        return sendPPD(requestUrl, params, contentType, "OPTIONS",null);
    }
    
    
    /**
     * 发送DELETE请求  测方法待实际验证
     * @param requestUrl
     * @param params
     * @param contentType
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static HttpResponse sendDelete(final String requestUrl,final String params, final String contentType) throws UnsupportedEncodingException, IOException {
        return sendPPD(requestUrl, params, contentType, "DELETE",null);
    }
    

    /**
     * 发送PUT请求 测方法待实际验证
     * @param requestUrl
     * @param params
     * @param contentType
     * @return 
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static HttpResponse sendPut(final String requestUrl,final String params,final String contentType) throws UnsupportedEncodingException, IOException {
        return sendPPD(requestUrl, params, contentType, "PUT",null);
    }
    
    
    /**
     * 发送HEAD请求
     * @param requestUrl
     * @return 
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String sendHead(final String requestUrl) throws UnsupportedEncodingException, IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时时间
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        // 设置请求方法
        conn.setRequestMethod("HEAD");
        // 建立实际的连接
        conn.connect();
        // 获取所有的相应头字段
        return getResponseHead(conn);
    }

    public static HttpResponse sendGet(final String requestUrl, final String params,final Map<String,String> headers) throws Exception {
        HttpResponse response = new HttpResponse();
        String finalUrl = null;
        if (CommonUtils.isEmpty(params)) {
            finalUrl = requestUrl;
        } else {
            finalUrl = requestUrl + "?" + params;
        }
//        URL url = new URL(finalUrl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        HttpURLConnection conn = getConnection(finalUrl);
        setHeaders(conn,headers);
        // 设置超时时间
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        // 建立实际的连接
        conn.connect();
        // 获取所有的相应头
        response.setHead(getResponseHead(conn));
        // 获取URL的响应体
        response.setBody(getResponseBody(conn));
        return response;
    }
    /**
     * 发送GET请求
     * 
     * @param requestUrl
     * @param params
     * @return 使用时map的每个value进行非空校验
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public static HttpResponse sendGet(final String requestUrl, final String params) throws Exception {
        return sendGet(requestUrl,params,null);
    }

    /**
     * 发送POST请求
     * @param requestUrl
     * @param params
     * @param contentType
     * @return 使用时map的每个value进行非空校验
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static HttpResponse sendPost(final String requestUrl,final String params,final String contentType,final Map<String,String> header) throws UnsupportedEncodingException, IOException {
        return sendPPD(requestUrl, params, contentType, "POST",header);
    }


    private static void setHeaders(HttpURLConnection connection,Map<String,String> headers) {
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k,v)->{
                connection.setRequestProperty(k,v);
            });
        }
    }


    /**
     * @Descriptionmap 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @author temdy
     * @Date 2015-01-26
     * @param path
     *            图片路径
     * @return
     */
    public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将map转换成key-value的参数格式
     * 
     * @param map
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String mapToParams(Map<String, Object> map) throws UnsupportedEncodingException {
    	return mapToParamsCo(map,false);
    }
    
    public static String mapToParamsWithUrlencodeValue(Map<String, Object> map) throws UnsupportedEncodingException{
    	return mapToParamsCo(map,true);
    }
    
    
    /**
     * 
     * 转换值之后根据参数判断是否再进行Urlencode编码
     * 
     * @param map
     * @param urlEncode
     * @return
     * @throws UnsupportedEncodingException
     * @since 2017年6月8日 下午11:52:27
     */
    private static String mapToParamsCo(Map<String, Object> map,boolean urlEncode){
    	StringBuffer sb = new StringBuffer();
        Set<String> key = map.keySet();
        int i = 0;
        Object value;
        for (String str : key) {
            ++i;
            value = map.get(str);
            if (value == null){
                continue;
            }
            
            if (urlEncode) {
            	try {
					sb.append(str).append("=").append(URLEncoder.encode(value.toString(), CharacterEncode.UTF_8));
				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
					// ignore
				}
            } else {
            	sb.append(str).append("=").append(value);
            }
            
            if (i != key.size()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串先进行base64 在进行urlencode
     * 
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64AUrlen(final String params) throws UnsupportedEncodingException {
        return URLEncoder.encode(Base64.getEncoder().encodeToString(params.getBytes("UTF-8")), "UTF-8");
    }

    /**
     * 获取连接请求之后返回的消息头
     * @param conn
     * @return
     */
    public static String getResponseHead(URLConnection conn) {
        StringBuffer sb = new StringBuffer();
        boolean inFlag = false;
        // When only the keys from a map are needed in a loop, iterating the keySet makes sense. 
        // But when both the key and the value are needed, it's more efficient to iterate the entrySet, 
        // which will give access to both the key and value, instead.
        // Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
        for(Entry<String, List<String>> entry : conn.getHeaderFields().entrySet()) {
            if (inFlag) {
                sb.append("\r\n");
            }
            sb.append(entry.getKey() + " : " + entry.getValue().toString());
            if (!inFlag) {
                inFlag = true;
            }
        }
        return sb.toString();
    }
    
    /**
     * 获取连接请求之后返回的消息体
     * 
     * @param conn
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String getResponseBody(URLConnection conn) throws UnsupportedEncodingException, IOException {
        StringBuffer sb = new StringBuffer();
        return IOUtil.getString(conn.getInputStream());
    }

    /**
     * 发送post或者delete或者put请求
     * @param requestUrl
     * @param params
     * @param contentType
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static HttpResponse sendPPD (final String requestUrl,
    		                             final String params,
    		                             final String contentType,
    		                             final String requestMethodName,
    		                             final Map<String,String> requestHeader) throws UnsupportedEncodingException, IOException{
        OutputStreamWriter osw = null;
        HttpResponse response = new HttpResponse();
        try {
            URL url = new URL(requestUrl);
            
            
            // 打开和URL之间的连接
            HttpURLConnection conn = getConnection(requestUrl);
            // 设置连接属性
            conn.setDoOutput(true);// 使用 URL 连接进行输出 默认是false
            conn.setDoInput(true);// 使用 URL 连接进行输入 默认是true
            conn.setUseCaches(false);// 不使用缓存 默认是true
            conn.setRequestMethod(requestMethodName); // 默认是GET

            conn.setConnectTimeout(timeout);// 设置连接建立的超时时间
            conn.setReadTimeout(timeout);// 设置读取的超时时间

            // 设置请求属性
            conn.setRequestProperty("Content-Type", contentType);// 请求携带参数的方式
            // conn.setRequestProperty("Connection", "Keep-Alive");//
            // 维持长连接
            conn.setRequestProperty("Charset", charsetName);// 设置编码
            
            // 添加请求头
            if (requestHeader != null && !requestHeader.isEmpty()) {
            	requestHeader.forEach((k,v) -> {
            		conn.setRequestProperty(k, v);
            	});
            }

            // 获取URLConnection对象对应的输出流
            osw = new OutputStreamWriter(conn.getOutputStream(), charsetName);
            // 发送请求参数
            if(params != null) {
            	osw.write(params);
            }
            // flush输出流的缓冲
            osw.flush();
            // 建立实际的连接
            conn.connect();
            
            // 获取所有的相应头
            response.setHead(getResponseHead(conn));
            // 获取URL的响应体
            response.setBody(getResponseBody(conn));


        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            // ignore
            e.printStackTrace();
        } finally {
            if (osw != null) {
                osw.close();
            }
        }
        return response;
    }

    private static HttpURLConnection getConnection(final String requestUrl) throws IOException,KeyManagementException,NoSuchAlgorithmException {
        String schema = requestUrl.split("://")[0];
        if (HTTP.equalsIgnoreCase(schema)) {
            URL url = new URL(requestUrl);
            // 打开和URL之间的连接
            return (HttpURLConnection) url.openConnection();
        } else  if (HTTPS.equalsIgnoreCase(schema)) {

            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            // don't check
                        }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            // don't check
                        }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            HttpsURLConnection connection = (HttpsURLConnection)new URL(requestUrl).openConnection();
            connection.setSSLSocketFactory(ssf);

            return connection;
        } else {
            throw new IllegalArgumentException("schema错误，requestUrl = " + requestUrl);
        }
    }


    /**
     * HttpRequest请求返回的信息实体类
     * @author Poul.Y
     * @since 2017年7月5日 下午7:27:42
     */
    public static class HttpResponse {

        /**
         * 响应头
         */
        private String head;
        
        /**
         * 响应体
         */
        private String body;


        /**
         * @return the head
         */
        public String getHead() {
            return head;
        }


        /**
         * @return the body
         */
        public String getBody() {
            return body;
        }


        /**
         * @param head the head to set
         */
        void setHead(String head) {
            this.head = head;
        }


        /**
         * @param body the body to set
         */
        void setBody(String body) {
            this.body = body;
        }


        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "HttpResponse [head=" + head + ", body=" + body + "]";
        }
        
    }
    
    
    /**
	 * 获取请求体里面的字符串
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestBody(HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("UTF-8");
		return IOUtil.getString(request.getInputStream());
	}
	
	
	/**
	 * 
	 * form表单式的请求参数设置
	 * 
	 * @author 杨霄鹏
	 * @since 2018-01-08 14:45:28
	 */
	public static class RequestFormParams{
		private Map<String,Object> params = new HashMap<>();
		
		public void setParam(String key,String value) {
			params.put(key, value);
		}
		
		public String toParam() {
			return mapToParamsCo(this.params, true);
		}
	}
	
	
}
