function showMenu() {
    var href=window.location.href;
    var host=window.location.host;
    var index=href.indexOf(host);
    var path=href.substring(index+host.length);
    var contextPath="${APP_PATH}";
    var pathAddress=path.substring(contextPath.length);

    var link=$(".list-group a[href*='"+pathAddress+"']");
    link.css("color","red");
    link.parent().parent().parent().removeClass("tree-closed");
    link.parent().parent().show();

}