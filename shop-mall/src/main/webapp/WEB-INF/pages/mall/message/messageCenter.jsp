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
    <link rel="stylesheet" href="${path}/static/css/messagecenter.css">
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js" ></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js" ></script>
    <script type="application/javascript">
        var path = "${path}";
    </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>消息中心</p>
    </header>
    <!--      消息list-->
    <main>
        <c:forEach items="${messageList}" var="info">
            <div class="sec1">
                <%--<h1 id="imagelist"><img src="${path}/static/images/admin.png" alt=""><span>22</span></h1>--%>
                <h1 id="imagelist"><img src="${info.shopLogo}" alt=""><span>${info.num}</span></h1>
                <div>
                    <h2>${info.shopName}</h2>
                    <p>${info.msgContent}</p>
                </div>
            </div>
        </c:forEach>
    </main>
    <!--
           <div class="nobady">
                <img src="../img/nodady.png" alt="">
                <p>暂无消息</p>
            </div>
    -->
    <!--        点击加载-->
    <div class="downloading"><img src="${path}/static/images/downloading.png" alt=""></div>
</div>
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
        img.onerror=function(){img.src="images/default.png"}
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
            $(".sec1").eq(i).find("#imagelist img").attr("src","http://www.86y.org/images/loading.gif");
            $(".sec1").eq(i).find("#imagelist img").css("background","url(http://www.86y.org/images/loading.gif) no-repeat center center");
            Imagess($(".sec1").eq(i).find("#imagelist img").attr("data"),$(".sec1").eq(i).find("#imagelist img").attr("id"),checkimg);
        })
    }*/
</script>
</body>
</html>