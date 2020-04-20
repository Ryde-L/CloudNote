//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
//    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    var r = decodeURI(decodeURI(window.location.search.substr(1))).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

/*判断当前设备是手机还是电脑*/
function phoneOrPc() {
    var system = {};
    var p = navigator.platform;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
    if (system.win || system.mac || system.xll) {
        return "pc";
    } else {
        return "phone";
    }
}
