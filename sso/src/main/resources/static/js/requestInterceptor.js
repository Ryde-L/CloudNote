$.ajaxSetup({
    type: "POST",
    // headers:{'token':storage.getItem("token")},
    headers:{'token':"233"},
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
// (function(send) {
//
//     XMLHttpRequest.prototype.send = function(data) {
//
//         var _valuToAdd = "666";
//         this.setRequestHeader('777', _valuToAdd);
//         send.call(this, data);
//     };
// })(XMLHttpRequest.prototype.send);

window.onbeforeunload=function(a) {
    var r=Request;
    console.log(r);
    // r.prototype.setHeaderValue("888","999");
    // r.prototype.headers.set("888","999");
    // console.log(window.location.href);
};



