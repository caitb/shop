<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wotuijianderen.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="<%=path%>/myRecommend/feeList"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>我推荐的人</p>            
        </header>
        <main>
            <div class="floor">
                <div id="find">
                    <span>商品：</span>
                    <label for="goods" class="goods">
                        <b></b>
                        <select id="goods" class="myValue">
                            <option value="">全部</option>
                            <c:forEach items="${skuList}" var="skuList">
                                <option value="${skuList.id}">${skuList.name}</option>
                            </c:forEach>
                        </select>
                    </label>

                    <span>等级：</span>
                    <label for="level" class="level">
                        <b></b>
                        <select id="level" class="myValue">
                            <option value="">全部</option>
                            <c:forEach items="${agentLevels}" var="agent">
                                <option value="${agent.id}">${agent.name}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <%--<button >查询</button>--%>
            </div>
            <div class="floor2">
                <c:forEach items="${sumByUserPid}" var="sumByUser">
                    <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>myRecommend/myRecommend?userId=${sumByUser.userId}&skuId=${sumByUser.skuId}')">
                        <img src="${sumByUser.wxHeadImg}" alt="">
                        <div>
                            <p>${sumByUser.name} <b>${sumByUser.agentName}</b></p>
                            <p>${sumByUser.skuName}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        $(document).ready(function(){
            var goodsWidth=$(".goods").width();
            var levelWidth=$(".level").width();
            $(".goods b").html($("#goods option:selected").text());
            $(".level b").html($("#level option:selected").text());
            $("#goods").width(goodsWidth);
            $("#level").width(levelWidth);
        })
        $("#goods").on("change",function(){
            var tabVal=$("#goods option:selected").text();
            $(".goods b").html(tabVal);
        })
        $("#level").on("change",function(){
            var tabVal=$("#level option:selected").text();
            $(".level b").html(tabVal);
        })

        $(".myValue").on("change",function(){
            var skuId = $("#goods").val()
            var agentLevelIdLong = $("#level").val();
            $(".floor2").html("");
            $.ajax({
                type:"POST",
                url : "<%=path%>/myRecommend/myRecommendLike.do",
                data:{skuId:skuId,agentLevelIdLong:agentLevelIdLong},
                dataType:"Json",
                success:function(data){
                    var trHtml = "";
                    $.each(data, function(i, userRecommend) {
                        trHtml+="<div class=\"sec1\" ";
                        trHtml+="onclick=\"javascript:window.location.replace('<%=basePath%>myRecommend/myRecommend?userId="+userRecommend.userId+"&skuId="+userRecommend.skuId+"')\" >";
                        trHtml+="<img src=\""+userRecommend.wxHeadImg+"\" alt=\"\">";
                        trHtml+="<div> <p>"+userRecommend.name+" <b>"+userRecommend.agentName+"</b></p>";
                        trHtml+="<p>"+userRecommend.skuName+"</p> </div> </div>";
                    })
                    $(".floor2").html(trHtml);
                }
            })
        })
    </script>
</body>
</html>