package top.poul.utils;

import java.util.Arrays;
import java.util.Collection;

/**
 * 集合工具类
 * @author Poul.Y
 * @since 2017年11月2日 下午2:38:15
 */
public class CollectionsUitl {

    
    
    /**
     * 判断集合是否是空或者null
     * @param <E>
     * @return
     */
    public static <E> boolean isEmpty(Collection<E> c) {
        
        return c == null || c.isEmpty();
        
    }

    /**
     * 集合转数组
     * @param c
     * @param <E>
     * @return
     */
    public static <E> E[] toArray(Collection<E> c,Class<? extends E[]> cls) {
        if (isEmpty(c)) {
            return null;
        }
        Object[] result = new Object[c.size()];
        int i = 0;
        for (E item : c) {
            result[i] = item;
            i++;
        }
        return Arrays.copyOf(result, c.size(), cls);
    }



}
