package top.poul.utils.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * excel的列属性
 * @author poul
 * @since 2020/12/27 下午8:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelColumn {

    /**
     * 列的index 从0开始
     */
    private int index;


    /**
     * 列的name A,B,C etc.
     */
    public String getName() {
        return ExcelUtil.getColumnName(index);
    }

}
