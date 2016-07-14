<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/messagecenter.css">
    <script>
        var path = "${path}";
    </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>消息中心</p>
        <a href="${path}/message/tonew.shtml"><img src="${path}/static/images/message/xiaoxi_1.jpg" alt=""></a>
    </header>
    <!--      消息list-->
    <main id="mlist">
        <c:if test="${content != null}">
            <div id="${myId}" class="sec1">
                <h1 id="imagelist">
                    <img src="${myHeadUrl}" alt="">
                </h1>
                <div>
                    <h2>我</h2>
                    <p>${content.content}</p>
                </div>
            </div>
        </c:if>
    </main>
    <div id="more" style="display: none">
        <br/>
        <h3 align="center">加载更多</h3>
    </div>
    <div id="nomore" style="display: none">
        <br/>
        <h3 align="center">暂无更多数据</h3>
    </div>
    <div id="remore" style="display: none">
        <br/>
        <h3 align="center">点击重新加载</h3>
    </div>
    <br/>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/commonAjax.js"></script>
<script src="${path}/static/js/message_center_platform.js"></script>
<script>
    /*var Browser=new Object();
    Browser.userAgent=window.navigator.userAgent.toLowerCase();
    Browser.ie=/msie/.test(Browser.userAgent);
    Browser.Moz=/gecko/.test(Browser.userAgent);

    function Imagess(url,imgid,callback){
        var val=url;
        var img=new Image();
        if(Browser.ie){
            img.onreadystatechange =function(){
                if(img.readyState=="complete"||img.readyState=="loaded"){
                    callback(img,imgid);
                }
            }
        }else if(Browser.Moz){
            img.onload=function(){
                if(img.complete==true){
                    callback(img,imgid);
                }
            }
        }
        //如果因为网络或图片的原因发生异常，则显示该图片
        img.onerror=function(){img.src="${path}/static/images/message/default.png"}
        img.src=val;
    }

    //显示图片
    function checkimg(obj,imgid){
        document.getElementById(imgid).style.cssText="";
        document.getElementById(imgid).src=obj.src;
    }
    window.onload=function(){
        var imageList=$("#imagelist");
        $(".sec1").each(function(i,n){
            $(".sec1").eq(i).find("#imagelist img").attr("id","img0"+i);
            $(".sec1").eq(i).find("#imagelist img").attr("src","${path}/static/images/message/imgloading.gif");
            Imagess($(".sec1").eq(i).find("#imagelist img").attr("data"),$(".sec1").eq(i).find("#imagelist img").attr("id"),checkimg);
        })
    }*/
</script>
</body>
</html>
