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

        var cur = 0;

        $(function(){
            shopMsgList();

            $(document).on("click", "#shopList .sec1", viewDetail);

        });

        function shopMsgList(){
            $("#shopList").empty();
            $.ajax({
                type: 'get',
                url: path + '/mallmessage/shopMsgList.shtml?cur=' + cur++,
                dataType: "json",
                success: function(data) {
                    if(data.resCode == "success"){
                        var messageList = data.messageList;
                        if(messageList != undefined){
                            var tempHtml = '';
                            for (var i = 0; i < messageList.length; i++) {
                                tempHtml += '<div class="sec1" id="' + messageList[i].id + '">' +
                                        '<h1 id="imagelist"><img src="' + messageList[i].logo + '" alt="">';
                                if(messageList[i].notSeeNum > 0){
                                    tempHtml += '<span>' + messageList[i].notSeeNum + '</span>';
                                }
                                tempHtml += '</h1>' +
                                        '<div>' +
                                        '<h2>' + messageList[i].name + '</h2>' +
                                        '<p>' + messageList[i].content + '</p>' +
                                        '</div>' +
                                        '</div>';
                            }
                            $("#shopList").append(tempHtml);
                        }
                        if(data.isLast == true){
                            $("#more").hide();
                        } else {
                            $("#more").show();
                        }
                        if(data.hasData == false){
                            $("#shopList").hide();
                            $("#nomore").show();
                            $("#more").hide();
                        }
                    }
                }
            });
        }

        function viewDetail(){
            window.location.href = path + "/mallmessage/toDetail.shtml?userId=" + $(this).attr("id");
        }

        </script>
                    </head>
                    <body>
                    <div class="wrap">
                        <header class="xq_header">
                            <a href="#" onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                            <p>消息中心</p>
                        </header>
                        <!--      消息list-->
                        <main id="shopList">
                            <%--<c:forEach items="${messageList}" var="info">
                                <div class="sec1">--%>
                                    <%--<h1 id="imagelist"><img src="${path}/static/images/admin.png" alt=""><span>22</span></h1>--%>
                                    <%--<h1 id="imagelist"><img src="${info.logo}" alt=""><span>${info.notSeeNum}</span></h1>
                                    <div>
                                        <h2>${info.name}</h2>
                                        <p>${info.content}</p>
                                    </div>
                                </div>
                            </c:forEach>--%>
                        </main>
        <c:if test="${hasData}">
           <div class="nobady">
                <img src="${path}/static/images/material/nodady.png" alt="">
                <p>暂无消息</p>
            </div>
        </c:if>
        <div id="nomore" style="display: none">
            <br/>
            <h3 align="center">暂无更多数据</h3>
        </div>
    <!--        点击加载-->
    <%--<c:if test="${isLast}">--%>
        <div id="more" class="downloading" onclick="shopMsgList();"><img src="${path}/static/images/downloading.png" alt=""></div>
    <%--<</c:if>--%>
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