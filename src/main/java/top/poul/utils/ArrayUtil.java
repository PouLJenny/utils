package top.poul.utils;

public class ArrayUtil {

	
	
	/**
	 * 查找一个数组在另一个数组的位置
	 * @param source
	 * @param target
	 * @return
	 * @since 2017年11月22日 下午3:56:22
	 */
	public static <E> int indexOf(E[] source, E[] target) {

		// 判断类型是否一样
		if (!source.getClass().equals(target.getClass())) {
			throw new IllegalArgumentException("参数类型不一致");
		}

		int sl = source.length;
		int tl = target.length;

		if (tl > sl || tl <= 0) {
			return -1;
		}

		int max = sl - tl;

		E first = target[0];

		for (int i = 0; i <= max; i++) {

			if (source[i] == first) {

				int k = 1;
				// 比较之后的
				for (int j = i + 1; k < tl && source[j].equals(target[k]); j++, k++);

				if (k == tl) {
					return i;
				}
			}
		}

		return -1;
	}

}
