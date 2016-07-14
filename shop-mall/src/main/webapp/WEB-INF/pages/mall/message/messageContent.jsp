<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/common/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/message.css">
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js" ></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js" ></script>
    <script type="application/javascript">
        var path = "${path}";
        var cur = 0;

        $(function(){
            listMessage();
        });
        function listMessage(){
            $("#msgList").empty();
            $.ajax({
                type: 'get',
                url: path + '/mallmessage/contentList.shtml?userId=' + ${userId} + "&cur=" + cur++,
                dataType: "json",
                success: function(data) {
                    if(data.resCode == "success"){
                        if(data.hasData == true){
                            $("#nameP").html(data.fromUserName);
                            var tempHtml = '';
                            var messageList = data.messageList;
                            for(var i = 0; i < messageList.length; i++){
                                tempHtml += '<div class="sec1">' +
                                                '<div class="s_b">' +
                                                    '<div class="b_b">' +
                                                        '<img src="${path}/static/images/massage_r1_c1.png" alt="">' +
                                                        '<h1>' + messageList[i].content + '</h1>' +
                                                        '<p>' +
                                                            '<a href="' + messageList[i].contentUrl + '">点击查看</a>' +
                                                            '<a>' + messageList[i].createTime + '</a>' +
                                                        '</p>' +
                                                    '</div>' +
                                                '</div>' +
                                            '</div>';
                            }
                            $("#msgList").append(tempHtml);
                        }else{
                            $("#more").hide();
                        }
                        if(data.isLast == true){
                            $("#more").hide();
                        } else {
                            $("#more").show();
                        }
                    }
                }
            });
        }
    </script>
</head>
<body>

<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p id="nameP"></p>
    </header>
    <main id="msgList">
        <div class="sec1">
            <div class="s_b">
                <div class="b_b">
                    <img src="${path}/static/images/massage_r1_c1.png" alt="">
                    <h1>阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的</h1>
                    <p>
                        <a href="">点击查看</a>
                        <a>2016-4-44 14:23</a>
                    </p>
                </div>
            </div>
        </div>
    </main>
    <div id="more" class="downloading" onclick="listMessage();"><img src="${path}/static/images/downloading.png" alt=""></div>
</div>
</body>
</html>