package top.poul.utils;


import top.poul.utils.annotation.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * 校验工具
 * @author Poul.Y
 * @since 2017年11月7日 下午1:49:22
 */
public class ValidateUtil {

    
    
    /**
     * 校验对象里面的属性是否有{@code null}值 需要配合{@code NotNull}注解才生效
     * @param e
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <E> void checkParamsNotNull(E e) throws IllegalArgumentException, IllegalAccessException {
        
        // 获取所有属性
        Field[] declaredFields = e.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            // 设置属性可访问
            f.setAccessible(true);
            String name = f.getName();
            
            if (f.isAnnotationPresent(NotNull.class)) {
                Annotation[] annotations = f.getAnnotations();
                
                for (Annotation a : annotations) {
                    if (a.annotationType().equals(NotNull.class)) {
                        
                    	if (f.getType().equals(String.class)) {
                    		String value = (String)f.get(e);
                    		if (value == null || value.trim().length() <= 0) {
                    			throw new IllegalArgumentException(name + "不能为空");
                    		}
                    	} else {
                            if (f.get(e) == null) {
                                throw new IllegalArgumentException(name + "不能为空");
                            }
                    	}
                        ((NotNull)a).msg();
                    }
                }
                
            }
        }
    }
    
    
}
