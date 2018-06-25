package cn.sy;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setFileSizeThreshold(33554432);//当数据量大于该值时，内容将被写入文件。
        factory.setMaxFileSize(-1);//允许上传的文件最大值。默认值为 -1，表示没有限制。
        factory.setMaxRequestSize(-1);//针对该 multipart/form-data 请求的最大数量，默认值为 -1，表示没有限制。
        return factory.createMultipartConfig();
    }


//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
////        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
////                SerializerFeature.WriteMapNullValue, // 设置null值也要输出
//                SerializerFeature.WriteDateUseDateFormat); // 设置文本格式输出日期
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fastConverter;
//        return new HttpMessageConverters(converter);
//    }
    
}
