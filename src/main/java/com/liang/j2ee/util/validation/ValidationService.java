package com.liang.j2ee.util.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.liang.j2ee.util.exception.ExceptionFactory;
import com.liang.j2ee.util.exception.ServiceException;
import com.liang.j2ee.util.exception.ServiceExceptionCode;

/**
 * 类ValidationService.java的实现描述：TODO 类实现描述 
 * @author liangxing 2016年4月13日 下午7:20:01
 */
public class ValidationService implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(ValidationService.class);
    /**
     *  <pre>
     * 验证服务，能够支持Fast-failed式的验证
     * 
     * </pre>
     * 验证入口
     */
    private Validator validator;
    
    
    /**
     * <pre>
     * 验证参数，如果验证没有通过会抛出异常
     * 
     * </pre>
     * 
     * @param param
     * @throws ServiceException
     */
    public void validateParam(Object param) throws ServiceException {
        if (param == null) {
            logger.error(ServiceExceptionCode.INVALID_PARAM.getMessage(param));
            throw ExceptionFactory.makeFault(ServiceExceptionCode.INVALID_PARAM);
        }


        ValidationResult vr = validate(param);

        if (!vr.isPass()) {
            logger.error(ServiceExceptionCode.INVALID_PARAM.getMessage(param));
            throw ExceptionFactory.makeFault(ServiceExceptionCode.INVALID_PARAM,vr.getFailedReason());
        }
    }
    
    
    /**
     * <pre>
     * 给定一个参数，验证其是否符合约束，通过JSR303加以实现
     * 
     * </pre>
     * 
     * @param param
     * @return
     */
    private ValidationResult validate(Object param) {
        ValidationResult result = new ValidationResult();

        Set<ConstraintViolation<Object>> violations = validator.validate(param);
        boolean isEmpty = violations.isEmpty();
        result.setPass(isEmpty);

        // 这里设置违反的约束，设定了快速失败，其实只有一个失败项
        if (!isEmpty) {
            for (ConstraintViolation<Object> violation : violations) {
                StringBuilder sb = new StringBuilder(violation.getPropertyPath().toString());
                sb.append(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
                String reason = sb.toString();
                result.setFailedReason(reason);
                break;
            }
        }
        return result;
    }

    public void afterPropertiesSet() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
        ValidatorFactory factory = configuration.failFast(true).buildValidatorFactory();
        validator = factory.getValidator();
    }
}
