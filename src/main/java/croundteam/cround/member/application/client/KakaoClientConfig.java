package croundteam.cround.member.application.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class KakaoClientConfig {

    @Bean
    Logger.Level kakaoLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor kakaoRequestInterceptor() {
        return requestInterceptor -> requestInterceptor.header("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    @Bean
    public feign.codec.Encoder kakaoEncoder() {
        return new SpringEncoder(() -> new HttpMessageConverters(camelCaseMessageConverter()));
    }

    @Bean
    public feign.codec.Decoder kakaoDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters(camelCaseMessageConverter())));
    }

    private MappingJackson2HttpMessageConverter camelCaseMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}