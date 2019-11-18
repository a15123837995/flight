package xyz.xfcloud.flight.util.http;

/**
 * 基础配置
 */
public class ConstantUtils {

	/****************** http 请求成功状态码 **************/
	public static final int HTTP_SUCCESS_CODE = 200;
	/** 向网关请求默认失败重试次数 */
	public static int DEFAULT_RESEND = 2;
	/************** 是否为长连接，默认为长连接 **************/
	public static final boolean IS_KEEP_ALIVE = true;
	/************** 长连接 **************/
	public static final String KEEP_ALIVE = "Keep-Alive";
	/************** 请求超时时间(毫秒) 5秒 **************/
	public static final int HTTP_REQUEST_TIMEOUT = 5 * 1000;

	/************** 默认http连接池大小 **************/
	public static final int POOL_NUMBER = 2;

	/************** 响应超时时间(毫秒) 30秒 **************/
	public static int HTTP_RESPONSE_TIMEOUT = 30 * 1000;


}
