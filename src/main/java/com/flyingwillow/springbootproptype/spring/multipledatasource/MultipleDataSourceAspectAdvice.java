package com.flyingwillow.springbootproptype.spring.multipledatasource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by liuxuhui on 2016/6/15.
 */
@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.flyingwillow.*.*(..)) && @annotation(dataSourceKey)")
    public Object invoke(ProceedingJoinPoint joinPoint, DataSourceKey dataSourceKey) throws Throwable {

        if(null!=dataSourceKey){//没有注解的用默认值
            String value = dataSourceKey.value();
            if(StringUtils.isNotBlank(value)){
                MultipleDataSource.setDataSourceKey(value);
                logger.debug("Data Source Key :"+value);
            }
        }

        return joinPoint.proceed();
    }

}
