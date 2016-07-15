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
    <link rel="stylesheet" href="${path}/static/css/radio.css">
    <script type="application/javascript">
        var path = "${path}";
    </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:void(0)" onclick="javascript:window.history.back()"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>新建群发</p>
    </header>
    <main>
        <div class="floor">
            <h1>推送给：</h1>
            <div>
                <p id="p_1">
                    <span id="fanId">0</span>
                    <span>全部粉丝</span>
                    <img src="${path}/static/images/message/message_4.jpg" alt="">
                </p>
                <p id="p_2">
                    <span id="spId">0</span>
                    <span>全部代言人</span>
                    <img src="${path}/static/images/message/message_4.jpg" alt="">
                </p>
            </div>
        </div>
        <div class="floor2">
            <h1>消息内容：</h1>
            <div>
                <textarea id="textarea" onkeydown="LimitTextArea(this)" onkeyup="LimitTextArea(this)" onkeypress="LimitTextArea(this)"></textarea>
                <button onclick="clickShow()">
                    <b id="url_show">跳转网址(<span style='text-decoration:underline;color: blue;'>点击设置</span>)</b>
                </button>
            </div>
        </div>
        <h1>您还可以输入<span class="textlength">140</span>/140字</h1>
        <p>*禁止发送与政治、色情、暴力、等违法内容，违者必究！</p>
        <button id="submit" class="btn">
            发送
        </button>
        <p id="disable_p" style="display: none;align-self: center">暂无粉丝或代言人，无法群发消息</p>
    </main>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="see">
        <h1>设置“点击查看”网址</h1>
        <p>点击查看网址设置后，粉丝在查看信息的时候可以点击链接访问。为了保证良好的体验，目前跳转地址只能设置为麦链商城站内地址。</p>
        <div class="s_b">
            <div class="b_l">
                设置跳转地址：
            </div>
            <div class="b_r">
                <p id="url_setting">
                    <span id="-1" class="on">不设置</span>
                    <span id="0">您的店铺首页</span>
                </p>
            </div>
        </div>
        <h2>
            <span onclick="clickHide()">取消</span>
            <span id="url_select">确定</span>
        </h2>
    </div>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script src="${path}/static/js/commonAjax.js"></script>
<script>
    $(function(){
        // 初始化页面数据
        init();

        $(".b_r p").on("click","span",function(){
            $(this).addClass("on").siblings().removeClass("on");
        });

        $(".floor div").on("click","p",function(){
            $(this).addClass("on").siblings().removeClass("on");
            var num = +$(this).children("span").first().html();
            if(num <= 0){
                $(this).removeClass("on");
                alert("请不要选择没人的部分");
            }
        });

        $("#url_select").unbind("click").on("click", function(){
            var thisSpan = $("#url_setting .on")[0];
            urlFlag = +thisSpan.id;
            if(urlFlag != -1){
                $("#url_show").html("跳转网址(已设置，跳转到\"" + thisSpan.innerHTML.toLocaleString() + "\")");
            } else {
                $("#url_show").html("跳转网址(" + "<span style='text-decoration:underline;color: blue;'>点击设置</span>" + ")");
            }
            clickHide();
        });

        // 提交按钮
        $("#submit").unbind("click").on("click", message_submit);

    });

    var fansNum = 0;
    var spNum = 0;
    var urlFlag = -1;

    function init(){
        // 初始化方法
        var options = {
            url:"${path}/shopmessage/toNewData.do",
            type:"post",
            dataType:"json",
            async:true,
            success:function(data){
                if(data.resCode == "success"){
                    fansNum = data.fansNum;
                    spNum = data.spokeManNum;
                    $("#fanId").html(fansNum);
                    $("#spId").html(spNum);
                    for(var i=0; i < data.skus.length; i++) {
                        var sendType = "";
                        if(data.skus[i].isOwnShip == 0){
                            sendType = "平台代发";
                        } else if(data.skus[i].isOwnShip == 1) {
                            sendType = "自己发货";
                        }
                        $("#url_setting").append($("<span id='" + data.skus[i].id + "'>"
                                + data.skus[i].skuName + "_" + sendType + "_详情页</span>"));
                    }
                    if(fansNum > 0){
                        $("#fanId").parent().addClass("on");
                    } else if(spNum > 0){
                        $("#spId").parent().addClass("on");
                    } else {
                        // 增加不能发送页面变化
                        $("#submit").css("background", "#E6E6E6");
                        $("#submit").attr("disabled", "disabled");
                        $("#disable_p").show();
                    }
                } else {
                    // 请求错误
                    alert(data.resMsg);
                }
            }
        };
        $.ajax(options);
    }


    function LimitTextArea(field){
        var maxlimit = 140;
        var Length = field.value.length;
        if (Length > maxlimit) {
            field.value = field.value.substring(0, maxlimit);
        }

        if(Length >= maxlimit){
            Length = maxlimit;
        }
        $(".textlength").html(maxlimit-Length);
    }

    function clickHide(){
        $(".black").hide();
    }
    function clickShow(){
        $(".black").show();
    }

    /**
     * 创建消息提交
     */
    function message_submit(){
        var num = $(".floor div .on").size();
        if(num <= 0){
            alert("您未选择发送人群");
            return;
        }
        if($("#textarea")[0].value.length <= 0){
            alert("您未输入消息内容");
            return;
        }
        var options = {
            url:"${path}/shopmessage/newmessage.do",
            type:"post",
            dataType:"json",
            async:true,
            data:{
                message:$("#textarea")[0].value,
                urlType:urlFlag,
                sendType:$(".floor div .on")[0].id.split("_")[1]
            },
            success:function(data){
                if(data.resCode == "success"){
                    // 创建群发消息成功
                    window.location.href = "${path}/shopmessage/success.shtml";
                } else {
                    // 请求错误
                    alert(data.resMsg);
                }
            },
            complete:function(){
                $("#submit").unbind("click").on("click", message_submit);
            }
        };

        $("#submit").unbind("click");
        $.ajax(options);
    }
</script>
</body>
</html>
