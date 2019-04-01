package website.liujie.common.spring;

import org.apache.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description	: 解决spring3.1与jquery返回json编码问题
 * @Copyright	: Excenon. ALL Rights Reserved
 * @Company		: 深圳市华磊移动设备科技有限公司 
 * @author		: Cosmo
 * @version		: 1.0
 * @Date		: 2015年11月27日 下午8:07:36
 */
public class UTF8StringHttpMessageConverter extends
		AbstractHttpMessageConverter<String> {

	private Logger logger = Logger.getRootLogger();
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private final List<Charset> availableCharsets;

	public UTF8StringHttpMessageConverter() {
		this(DEFAULT_CHARSET);
	}

	public UTF8StringHttpMessageConverter(Charset defaultCharset) {
		super(new MediaType("text", "plain", defaultCharset), MediaType.ALL);
		this.availableCharsets = new ArrayList<Charset>(Charset
				.availableCharsets().values());
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	protected String readInternal(Class<? extends String> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		MediaType contentType = inputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType
				.getCharSet() : DEFAULT_CHARSET;
				
		return FileCopyUtils.copyToString(new InputStreamReader(inputMessage
				.getBody(), charset));
	}

	@Override
	protected void writeInternal(String t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		MediaType contentType = outputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType
				.getCharSet() : DEFAULT_CHARSET;
				
		logger.info("-----------");
		logger.info("返回数据："+t);
		FileCopyUtils.copy(t, new OutputStreamWriter(outputMessage.getBody(),
				charset));
	}

	protected List<Charset> getAcceptedCharsets() {
		return this.availableCharsets;
	}

	@Override
	protected Long getContentLength(String s, MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			Charset charset = contentType.getCharSet();
			try {
				return (long) s.getBytes(charset.name()).length;
			} catch (UnsupportedEncodingException ex) {
				throw new InternalError(ex.getMessage());
			}
		} else {
			return null;
		}
	}
}