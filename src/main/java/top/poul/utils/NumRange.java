package top.poul.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 数字区间
 */
@Data
@Accessors(chain = true)
public class NumRange {

    private Integer from;
    private Integer to;

    public NumRange(int from,int to) {
        this.from = from;
        this.to = to;
    }

}
