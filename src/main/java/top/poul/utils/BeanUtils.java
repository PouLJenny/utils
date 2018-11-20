package top.poul.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {

    /**
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) throws Exception{
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();

            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);

                map.put(key, value);
            }

        }
        return map;

    }

	/**
	 * 给bean list的属性添加值
	 * @param o
	 * @param propertyName
	 * @param value
	 * @param <T>
	 */
    public static <T> void  addListValue(Object o, String propertyName, T value) {
        Object fieldValue = ReflectionUtils.getFieldValue(o, propertyName);
        if (fieldValue == null) {
            synchronized (o) {
                if (fieldValue == null) {
                    fieldValue = new ArrayList<T>();
                }
            }
        }
        if (!(fieldValue instanceof List)) {
	        throw new IllegalArgumentException("bean的属性不是list");
        }
	    List fieldValueL = (List) fieldValue;
	    // 不做重复校验
	    fieldValueL.add(value);
        ReflectionUtils.setFieldValue(o,propertyName,fieldValueL);
    }

}
