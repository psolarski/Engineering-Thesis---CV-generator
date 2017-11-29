package pl.beng.thesis.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@ControllerAdvice(basePackages = "pl.beng.thesis.controller")
public class JSONResponseWrapper implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (body instanceof List) {
            return new Wrapper<>((List<Object>) body);
        }
        return body;
    }

    private class Wrapper<T> {
        private final List<T> list;

        public Wrapper(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }
    }
}
