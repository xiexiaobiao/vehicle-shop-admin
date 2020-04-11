package com.biao.shop.common.aspectj;

import com.biao.shop.common.annotation.AutoIdempotent;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.common.service.IdempotentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @ClassName IdempotentInterceptor
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/11
 * @Version V1.0
 **/
public class IdempotentInterceptor implements HandlerInterceptor {

    private IdempotentService idempotentService;

    @Autowired
    public IdempotentInterceptor(IdempotentService idempotentService) {
        this.idempotentService = idempotentService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ((handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有注解
        AutoIdempotent autoIdempotent = method.getAnnotation(AutoIdempotent.class);
        if (!Objects.isNull(autoIdempotent)){
            PrintWriter printWriter = null;
            try {
                return idempotentService.checkToken(request);
            }catch (Exception e){
                ObjectResponse<String> objectResponse = new ObjectResponse();
                objectResponse.setCode(RespStatusEnum.FAIL.getCode());
                objectResponse.setMessage(RespStatusEnum.FAIL.getMessage());
                objectResponse.setData(null);
                printWriter = response.getWriter();
                printWriter.println(objectResponse);
                // 抛出异常，让统一异常机制来处理
                throw e;
            }finally {
                assert printWriter != null;
                printWriter.close();
            }
        }
        // 无注解需返回true，
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
