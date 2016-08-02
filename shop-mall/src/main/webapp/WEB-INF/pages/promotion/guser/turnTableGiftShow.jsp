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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/choujiang.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">
</head>
<script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
<body>
    <c:forEach items="${turnTablelInfos}" var="turnTablelInfo" >
        <div style="display: none;">
            <img src="<%=path%>/static/images/a (1).png" alt="" />
            <img src="<%=path%>/static/images/a (2).png" alt="" />
            <img src="<%=path%>/static/images/a (3).png" alt="" />
            <img src="<%=path%>/static/images/a (4).png" alt="" />
            <img src="<%=path%>/static/images/a (5).png" alt="" />
            <img src="<%=path%>/static/images/a (6).png" alt="" />
            <img src="<%=path%>/static/images/a (7).png" alt="" />
            <img src="<%=path%>/static/images/a (8).png" alt="" />
        </div>
        <div class="wrap">
            <header class="xq_header">
                <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>抽奖</p>
            </header>
            <main>
                <div class="floor">
                    <img src="<%=path%>/static/images/z.png" alt="" />
                    <p>分享</p>
                    <div class="xttblog_box">
                        <canvas id="xttblog" width="280px" height="280px">抱歉！浏览器不支持。</canvas>
                        <canvas id="xttblog01" width="280px" height="280px">抱歉！浏览器不支持。</canvas>
                        <canvas id="xttblog03" width="180px" height="180px">抱歉！浏览器不支持。</canvas>
                        <canvas id="xttblog02" width="130px" height="130px">抱歉！浏览器不支持。</canvas>
                        <button id="tupBtn" class="taoge_btn">
                            <img src="<%=path%>/static/images/button.png" alt=""/>
                        </button>
                    </div>
                </div>
                <div class="floor2">
                    <div class="f-one">
                        <p>您今天还有<span>3</span>次机会</p>
                        <button>我的中奖记录</button>
                    </div>
                    <div class="list">
                        <img src="<%=path%>/static/images/zbg.png" alt="" />
                        <div id="hottitle" class="hot">
                            <ul id="ulid">
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                                <li><span class='name'>123xxxxx123</span><span class='gift-type'>获得</span><span>123</span></li>
                            </ul>
                        </div>
                    </div>
                    <div class="floor3">
                        <h1><img src="<%=path%>/static/images/i.png" alt="" />活动规则</h1>
                        <p>活动时间：<span>${turnTablelInfo.beginTimeString}-${turnTablelInfo.endTimeString}</span></p>
                        <p>活动说明：
	        			<span>
                            ${turnTablelInfo.describe}
	        			</span>
                        </p>
                    </div>
                </div>
            </main>
        </div>
        <div class="black">
            <div class="backb"></div>
            <div class="backj">
                <img src="<%=path%>/static/images/jyes.jpg" alt=""/>
                <img src="<%=path%>/static/images/x.png" class="x" />
                <div>
                    <img src="<%=path%>/static/images/xiaol.jpg" alt="" />
                    <p>恭喜你</p>
                    <h1 id="receiveGiftNameId"></h1>
                    <button>
                        立即领取
                    </button>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:forEach var="entry" items="${giftNameMap}">
        <input id="giftName_${entry.key}" type="hidden" value="${entry.value}"/>
    </c:forEach>
    <c:forEach var="entry" items="${giftIdMap}">
        <input id="giftId_${entry.key}" type="hidden" value="${entry.value}"/>
    </c:forEach>
    <script src="/static/js/plugins/msclass.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<script>
	new Marquee(["hottitle","ulid"],0,2,"80%","40%",150,0,0);
</script>
<script>
$(function(){
    //旋转角度
    var angles;
    //可抽奖次数
    var clickNum = 3;
    //旋转次数
    var rotNum = 0;
    //中奖公告
    var notice = "";
    //转盘初始化
    var color = ["#ffd821","#ffd821","rgba(0,0,0,0)","#333333","rgba(0,0,0,0)","rgba(0,0,0,0)"];
    var info = [
        "<%=path%>/static/images/a (1).png",
        "<%=path%>/static/images/a (2).png",
        "<%=path%>/static/images/a (3).png",
        "<%=path%>/static/images/a (4).png",
        "<%=path%>/static/images/a (5).png",
        "<%=path%>/static/images/a (6).png",
        "<%=path%>/static/images/a (7).png",
        "<%=path%>/static/images/a (8).png"
    ];
    canvasRun();
    $('#tupBtn').on('click',function(){
        if (clickNum >= 1) {
            //可抽奖次数减一
            clickNum = clickNum-1;
            //转盘旋转
            runCup();
            //转盘旋转过程“开始抽奖”按钮无法点击
            $('#tupBtn').attr("disabled", true);
            //旋转次数加一
            rotNum = rotNum + 1;
            //“开始抽奖”按钮无法点击恢复点击
            setTimeout(function(){
                $(".black").show();
                $('#tupBtn').removeAttr("disabled", true);
            },6000);
        }
        else{
            alert("亲，抽奖次数已用光！");
        }
    });
 
    //转盘旋转
    function runCup(){
        probability();
        var degValue = 'rotate('+angles+'deg'+')';
        $('#xttblog').css('-o-transform',degValue);           //Opera
        $('#xttblog').css('-ms-transform',degValue);          //IE浏览器
        $('#xttblog').css('-moz-transform',degValue);         //Firefox
        $('#xttblog').css('-webkit-transform',degValue);      //Chrome和Safari
        $('#xttblog').css('transform',degValue);
    }
 
    //各奖项对应的旋转角度及中奖公告内容
    function probability(){
        //获取随机数
        var num = parseInt(Math.random()*(7 - 0 + 0) + 0);
        var giftId = null;
        //概率
        if ( num == 0 ) {
            angles = 2160 * rotNum + 1800;
            notice =$("#giftName_0").val();
            giftId = $("#giftId_0").val();
        }
        //概率
        else if ( num == 1 ) {
            angles = 2160 * rotNum + 1845;
            notice =$("#giftName_1").val();
            giftId = $("#giftId_1").val();
        }
        //概率
        else if ( num == 2 ) {
            angles = 2160 * rotNum + 1890;
            notice =$("#giftName_2").val();
            giftId = $("#giftId_2").value;
        }
        //概率
        else if ( num == 3 ) {
            angles = 2160 * rotNum + 1935;
            notice =$("#giftName_3").val();
            giftId = $("#giftId_3").val();
        }
        //概率
        else if ( num == 4 ) {
            angles = 2160 * rotNum + 1980;
            notice =$("#giftName_4").val();
            giftId = $("#giftId_4").val();
        }
        //概率
        else if ( num == 5 ) {
            angles = 2160 * rotNum + 2025;
            notice =$("#giftName_5").val();
            giftId = $("#giftId_5").val();
        }
        //概率
        else if ( num == 6 ) {
            angles = 2160 * rotNum + 2070;
            notice =$("#giftName_6").val();
            giftId = $("#giftId_6").val();
        }
        //概率
        else if ( num == 7 ) {
            angles = 2160 * rotNum + 2115;
            notice =$("#giftName_7").val();
            giftId = $("#giftId_7").val();
        }
        $("#receiveGiftNameId").html("获得"+notice);
    }
 
    //绘制转盘
    function canvasRun(){
        var canvas=document.getElementById('xttblog');
        var canvas01=document.getElementById('xttblog01');
        var canvas03=document.getElementById('xttblog03');
        var canvas02=document.getElementById('xttblog02');
        var ctx=canvas.getContext('2d');
        var ctx1=canvas01.getContext('2d');
        var ctx3=canvas03.getContext('2d');
        var ctx2=canvas02.getContext('2d');
        createCircle();
		setTimeout(function(){
        	createCirText();
		},0)
        //外圆
        function createCircle(){
            var startAngle = 0;//扇形的开始弧度
            var endAngle = 0;//扇形的终止弧度
            //画一个8等份扇形组成的圆形
            for (var i = 0; i< 8; i++){
                startAngle = Math.PI*(i/4-1/8);
                endAngle = startAngle+Math.PI*(1/4);
                ctx.save();
                ctx.beginPath();
                ctx.arc(140,140,100, startAngle, endAngle, false);
                ctx.lineWidth = 120;
                if (i%2 == 0) {
                    ctx.strokeStyle =  color[0];
                }else{
                    ctx.strokeStyle =  color[1];
                }
                ctx.stroke();
                ctx.restore();
            }
        }
        //各奖项
        function createCirText(){   
            ctx.textAlign='start';
            ctx.textBaseline='middle';
            ctx.fillStyle = color[3];
            var step = 2*Math.PI/8;
            var img=new Image();
            for ( var i = 0; i < 8; i++) {
            	img.src=info[i];
                ctx.save();
                ctx.beginPath();
                ctx.translate(140,140);
                ctx.rotate(i*step);
                ctx.font = " 20px Microsoft YaHei";
                ctx.fillStyle = color[3];
				ctx.drawImage(img,0,0,90,90,-20,-125,60,60);
                ctx.font = " 14px Microsoft YaHei";
                ctx.closePath();
                ctx.restore();
            }
        }

        $(".x").on("click",function(){
            $(".black").hide();
        })
    }
});
</script>
</body>
</html>
