package top.poul.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 获取请求体里面的字符串
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestBody(final HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("UTF-8");
		return IOUtil.getString(request.getInputStream());
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getParameters(final HttpServletRequest req) {

		// req.setCharacterEncoding("UTF-8");

		Map<String, String[]> parameterMap = req.getParameterMap();

		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();

		if (!entrySet.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			int size = entrySet.size();
			int i = 0;
			for (Entry<String, String[]> enty : entrySet) {
				i++;
				String key = enty.getKey();
				String[] value = enty.getValue();
				System.out.println(key + " : " + Arrays.toString(value));
				sb.append(key + "=" + Arrays.toString(value));
				if (i != size) {
					sb.append("&");
				}
			}

			return sb.toString();
		} else {
			return null;
		}

	}

	/**
	 * 获取请求头信息
	 * 
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getRequestHead(final HttpServletRequest req) {
		// req.setCharacterEncoding("UTF-8");

		logger.info("the request header:");

		Map<String, String> result = new HashMap<String, String>();

		Enumeration<String> headerNames = req.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = req.getHeader(key);
			logger.info(key + " : " + value);
			result.put(key, value);
		}

		return result.toString();
	}

	public static Map<String, String> getResponseHeaders(final HttpServletResponse response) {
		Collection<String> headerNames = response.getHeaderNames();
		final Map<String, String> result = new LinkedHashMap<>();
		Optional.ofNullable(headerNames).ifPresent((headerNs)->{
			headerNs.forEach(headerName->{
				result.put(headerName,response.getHeader(headerName));
			});
		});
		return result;
	}

	public static void logResponseDetail(final HttpServletResponse response) {
		Map<String, String> responseHeaders = getResponseHeaders(response);
		responseHeaders.forEach((k,v)->{
			logger.info("response header => {} : {}",k,v);
		});
	}

	/**
	 * 打印请求详情
	 * 
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	public static void logRequestDetail(final HttpServletRequest request) throws UnsupportedEncodingException {

		request.setCharacterEncoding("UTF-8");

		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();
		String remoteAddr = request.getRemoteAddr();
		String remoteUser = request.getRemoteUser();
		String method = request.getMethod();

		logger.info("remoteAddr=" + remoteAddr);
		logger.info("remoteUser=" + remoteUser);
		logger.info("requestURL=" + requestURL);
		logger.info("requestMethod=" + method);
		logger.info("queryString=" + queryString);

		getRequestHead(request);
		getParameters(request);

	}


	/**
	 * 获取url的请求host+端口号的地址
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getHost(final String url) throws MalformedURLException {
		int i = url.indexOf("://");
		if (i > 0) {
			String scheme = url.substring(0, i + 3);
			URL url1 = new URL(url);
			String host = url1.getHost();
			int port = url1.getPort();
			if (port == 80 || port == -1) {
				return scheme + host;
			} else {
				return scheme + host + ":" + port;
			}
		}
		return null;
	}

	/**
	 * 获取url的host
	 * @param url
	 * @return
	 */
	public static String determineHost(final String url) {
		int i = url.indexOf("://");
		String scheme = url.substring(0,i);
		String substring = url.substring(i + 3);
		String domain = substring.substring(0, substring.indexOf("/"));
		return scheme + "://" + domain;
	}


	/**
	 * 获取url中scheme后面的路径
	 * @param url
	 * @return
	 */
	public static String determineUri(String url) {
		int i = url.indexOf("://");
		if (i > -1) {
			String substring = url.substring(i + 3);
			i = substring.indexOf("/");
			if (i > -1) {
				return substring.substring(i + 1);
			}
		}

		return "";
	}



}
