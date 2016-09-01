package com.uuzz.android.util.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//在字段上使用
@Target(ElementType.FIELD)
//在代码运行期，需要读取这个注解的内容
@Retention(RetentionPolicy.RUNTIME)
public @interface TablePropertyExtra {
    String value();
}
