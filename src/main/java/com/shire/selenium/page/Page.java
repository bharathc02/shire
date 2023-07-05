package com.shire.selenium.page;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.CLASS)
//@Target(ElementType.TYPE)
//@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Target(ElementType.TYPE)
public @interface Page {

  String previous();
//   next();
//  Class<? extends Annotation>[] previous();

  String name();
}
