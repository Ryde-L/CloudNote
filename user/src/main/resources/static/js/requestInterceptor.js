var token=null;
function getToken(){
    return token;
}
function setToken(t){
    token=t
}
$.ajaxSetup({
    type: "POST",
    headers:{'user-ajax':"true"},
    xhrFields   : {withCredentials: true},
    error: function (jqXHR, textStatus, errorMsg) {
        // jqXHR 是经过jQuery封装的XMLHttpRequest对象

        // textStatus 可能为： null、"timeout"、"error"、"abort"或"parsererror"

        // errorMsg 可能为： "Not Found"、"Internal Server Error"等

        switch (jqXHR.status) {
            case(500):
                alert("服务器系统内部错误");
                break;
            case(401):
                alert("未登录");
                break;
            case(403):
                alert("当前用户没有权限");
                break;
            case(408):
                alert("请求超时");
                break;
            default:
                alert("未知错误");
        }
    }
});
