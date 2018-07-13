package com.flyingwillow.springbootproptype.spring.cache;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodCacheAspectAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(cacheConfig)")
    public Object invoke(ProceedingJoinPoint joinPoint, CacheConfig cacheConfig) throws Throwable {

        String key = createCacheKey(joinPoint,cacheConfig.key());
        logger.debug(key);
        int seconds = cacheConfig.expired();

        //Object result = RedisUtil.getObject(key);
        Object result = null;
        if(null==result){
            result = joinPoint.proceed();
            if(null!=result){
                //RedisUtil.setObjectEx(key,seconds,result);
            }
        }

        return result;
    }

    private String createCacheKey(ProceedingJoinPoint joinPoint, String key){

        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotBlank(key)){
            sb.append(key);
        }else {
            String signature = joinPoint.getSignature().toLongString();
            sb.append(signature);
        }

        Object[] params = joinPoint.getArgs();
        if(null!=params&&params.length>0){
            sb.append("#");
            StringBuilder parameter = new StringBuilder();
            for(Object param : params){
                parameter.append(",").append(object2String(param));
            }
            sb.append(parameter.substring(1));
        }


        return sb.toString();
    }

    private String object2String(Object object){

        //判断是否为primitive 基本类型
        if(object.getClass().isPrimitive()){
            return object.toString();
        }else if(object instanceof Integer||
                object instanceof Float||
                object instanceof Double||
                object instanceof Character ||
                object instanceof Boolean ||
                object instanceof Byte ||
                object instanceof Long ||
                object instanceof String){ // 是否为封装类型
            return object.toString();
        }else { //自定义类型
            return JSON.toJSONString(object);
        }

    }
}
