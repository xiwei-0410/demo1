package com.wxw.repeatedsubmit;

import java.lang.annotation.*;

/**
 * 自定义防重复提交注解
 * @author wxw
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

}
