package top.poul.utils.http;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * http请求信息
 * @author 杨霄鹏
 * @since 2019/4/23
 */
@Getter
@Setter
public class RequestInfo {

	/**
	 * 请求的uri
	 */
	private String uri;

	/**
	 * 请求头信息
	 */
	private Map<String,Object> header;

	/**
	 * 查询的参数
	 */
	private String query;

	/**
	 * 请求的参数
	 */
	private Map<String,Object> params;

	/**
	 * 请求体
	 */
	private byte[] body;


	public RequestInfo() {

	}


//	public RequestInfo() {
//
//	}






}
