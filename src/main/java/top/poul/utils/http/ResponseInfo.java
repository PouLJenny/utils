package top.poul.utils.http;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * http 响应信息
 * @author 杨霄鹏
 * @since 2019/4/23
 */
@Getter
@Setter
public class ResponseInfo {

	private Map<String,Object> header;

	private byte[] body;

}
