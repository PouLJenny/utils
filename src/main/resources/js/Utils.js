/**
 * 自己总结的一些原生js方法
 */

// 告诉浏览器启用严格模式
"use strict"

/**
 * 此方法可快速并稳定的删除字符串两端的空格
 */
String.prototype.trim = function () {
    var str = this.replace(/^\s+/, ""), end = str.length - 1, ws = /\s/;
    while (ws.test(str.charAt(end))) {
        end--;
    }
    return str.slice(0, end + 1);
};

/**
 * 获取当前的主机的地址
 */
var getLocalHostPath = function () {
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localHostPath = curWwwPath.substring(0, pos);
    return localHostPath;
};

var getRootPath = function () {
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    // 获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName
        .substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);

};

/* 获取cookie */
var getCookie = function (c_name) {
    if (document.cookie.length > 0) {
        var c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            var c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = document.cookie.length;
            }
            return decodeURIComponent(document.cookie.substring(c_start, c_end));
        }
    }
    return ""
}

/**
 * 设置cookie
 * @param c_name
 * @param value
 * @param expiredays
 * @returns
 */
function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + encodeURIComponent(value)
        + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

/**
 * 获取ajax对象
 */
var createXHR = function () {
    if (typeof XMLHttpRequest != "undefined") {
        return new XMLHttpRequest();
    } else if (typeof ActiveXObject != "undefined") {
        if (typeof arguments.callee.activeXString != "String") {
            var versions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.3.0",
                "MSXML2.XMLHttp"], i, len;
            for (i = 0, len = versions.length; i < len; i++) {
                try {
                    new ActiveXObject(varsions[i]);
                    arguments.callee.activeXString = versions[i];
                } catch (e) {
                    // 跳过
                }
            }
        }
        return new ActiveXObject(arguments.callee.activeXString);
    } else {
        throw new Error("No XHR object available.")
    }

}

/**
 * 获取lowerValue ~ upperValue 之间的整数
 */
var selectForm = function (lowerValue, upperValue) {
    var choices = upperValue - lowerValue;
    return Math.floor(Math.random() * choices + lowerValue);

}

/**获取文件的后缀名，并转为小写 */
const getFileSuffix = function (fileName) {
    let suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
    return suffix.toLocaleLowerCase();
}