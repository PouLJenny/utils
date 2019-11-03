package top.poul.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * 集合工具类
 * @author Poul.Y
 * @since 2017年11月2日 下午2:38:15
 */
public class CollectionUtil {

    
    
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


    /**
     *
     * @param cols
     * @param split
     * @return
     */
    public static String join(Collection<?> cols,String split) {
        if (split == null) {
            throw new NullPointerException();
        }
        if (CollectionUtils.isEmpty(cols)) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        for (Object obj : cols) {
            sb.append(Objects.toString(obj,StringUtils.EMPTY)).append(split);
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


}
