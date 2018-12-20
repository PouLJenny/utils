package top.poul.utils.excel;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Getter
@Setter
public class Excel {


    private ExcelType excelType;

    private ByteArrayOutputStream outputStream;

}
