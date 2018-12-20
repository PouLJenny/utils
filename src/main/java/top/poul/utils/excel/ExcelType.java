package top.poul.utils.excel;


import lombok.Getter;

import java.util.Objects;

/**
 * excel文件类型
 */
@Getter
public enum ExcelType {
	EXCEL_03("03","xls"),
	EXCEL_07("07","xlsx");

	private String code;

	private String extension;

	ExcelType(String code, String extension) {
		this.code = code;
		this.extension = extension;
	}

	public static ExcelType getByExtension(String extension) {
		for (ExcelType type : values()) {
			if (Objects.equals(type.extension,extension)) {
				return type;
			}
		}

		throw new IllegalArgumentException("错误的extension："  + extension);
	}


	public static ExcelType getByCode(String code) {
		for (ExcelType type : values()) {
			if (Objects.equals(type.code,code)) {
				return type;
			}
		}

		throw new IllegalArgumentException("错误的code："  + code);
	}

}
