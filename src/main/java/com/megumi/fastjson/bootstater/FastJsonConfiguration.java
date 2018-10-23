package com.megumi.fastjson.bootstater;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 将fastjson设置为springmvc默认解析器
 *
 * @author megumi
 * @date 2018/10/15
 */
@Configuration
@ConditionalOnClass(value = JSONObject.class)
public class FastJsonConfiguration implements WebMvcConfigurer {

    /**
     * httpMessageConverter集合操作
     * @param converters 当前有效的httpMessageConverter集合
     * */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while(iterator.hasNext()) {
            HttpMessageConverter<?> httpMessageConverter = iterator.next();
            if (StringHttpMessageConverter.class == httpMessageConverter.getClass()) {
                iterator.remove();
                continue;
            }
            if (MappingJackson2HttpMessageConverter.class == httpMessageConverter.getClass()) {
                iterator.remove();
            }
        }
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //加入FastJson转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        ///自定义配置...
        //FastJsonConfig config = new FastJsonConfig();
        ///config.set ...
        //converter.setFastJsonConfig(config);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(fastConverter);
    }

}
