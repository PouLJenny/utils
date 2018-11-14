package top.poul.utils;

/**
 * 接口的返回实体类
 * @author 杨霄鹏  
 * @since 2017年7月2日 上午11:36:43
 * @param <T>
 */
public class ResponseBody<T> {

	
	private Integer code;
	private String message;
	private T data;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
