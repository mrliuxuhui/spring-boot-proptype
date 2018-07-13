package com.flyingwillow.springbootproptype.spring.jsonp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;

public class FastJsonpSurpportHttpMessageConverter extends FastJsonHttpMessageConverter{

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		if (obj instanceof JSONPObject) {
		    JSONPObject jsonp = (JSONPObject) obj;
		    OutputStream out = outputMessage.getBody();
		    String text = jsonp.getFunction() + "(" + JSON.toJSONString(jsonp.getJson(), getFeatures()) + ")";
		    
		    byte[] bytes = text.getBytes(getCharset());
		    out.write(bytes);
		} else {
		    super.writeInternal(obj, outputMessage);
		}
	}
	
}
