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
            <c:forEach var="entry" items="${turnTablelInfo.turnTableGiftInfo}">
                <img id="giftImg_${entry.sort}" src="${entry.imgUrl}"/>
                <input id="giftName_${entry.sort}" type="hidden" value="${entry.giftName}"/>
                <input id="giftId_${entry.sort}" type="hidden" value="${entry.giftId}"/>
                <input id="turnTableGiftId_${entry.sort}" type="hidden" value="${entry.turnTableGiftId}"/>
            </c:forEach>
        </div>
        <div class="wrap">
            <header class="xq_header">
                <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>抽奖</p>
            </header>
            <main>
                <div class="floor">
                    <img src="<%=path%>/static/images/z.png" alt="" />
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
                        <p>您今天还有<span id="receiveGiftTimesId">${noUsedTimes}</span>次机会</p>
                        <button onclick="javascript:window.location.replace('<%=basePath%>turnTableGiftRecord/getPromotionGorderPageInfo.html');">我的中奖记录</button>
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
                    <input id="giftId" type="hidden" value=""/>
                    <input id="userTurnTableRecordId" type="hidden" value=""/>
                    <input id="turnTableGiftId" type="hidden" value=""/>
                    <button onclick="skipToReceiveGiftPage()">
                        立即领取
                    </button>
                </div>
            </div>
        </div>
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
    var clickNum = ${noUsedTimes};
    //旋转次数
    var rotNum = 0;
    //中奖公告
    var notice = "";
    //转盘初始化
    var color = ["#ffd821","#ffd821","rgba(0,0,0,0)","#333333","rgba(0,0,0,0)","rgba(0,0,0,0)"];
    canvasRun();
    $('#tupBtn').on('click',function(){
        if (clickNum >= 1) {
            //通过数据库验证抽奖次数
            validateCondition();
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
                $("#receiveGiftTimesId").html(clickNum);
                $('#tupBtn').removeAttr("disabled", true);
            },6000);
            updateTimesAndQuantity();
        }
        else{
            alert("亲，抽奖次数已用光！");
        }
    });
    function validateCondition(){
        var paramData = {};
        paramData.turnTableId = "${turnTableId}";
        paramData.giftId = $("#giftId").val();
        $.ajax({
            type: "POST",
            url: "/turnTableGorder/validateReceiveGiftCondition.json",
            async:false,
            data: paramData,
            dataType: "Json",
            success: function (result) {
                if (result==3){
                    alert("亲，抽奖次数已用光！");
                    return;
                }
            }
        })
    }
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
        var num = null;
        var turnTableId = ${turnTableId};
        $.ajax({
            type: "POST",
            url: "/turnTableDetailShow/getRandomByGiftRate.json",
            async:false,
            data: {turnTableId:turnTableId},
            dataType: "Json",
            success: function (result) {
                num = result;
            }
        })

        var giftId = null;
        var turnTableGiftId = null;
        //概率
        if ( num == 0 ) {
            angles = 2160 * rotNum + 1800;
            notice =$("#giftName_0").val();
            giftId = $("#giftId_0").val();
            turnTableGiftId = $("#turnTableGiftId_0").val();
        }
        //概率
        else if ( num == 1 ) {
            angles = 2160 * rotNum + 1845;
            notice =$("#giftName_1").val();
            giftId = $("#giftId_1").val();
            turnTableGiftId = $("#turnTableGiftId_1").val();
        }
        //概率
        else if ( num == 2 ) {
            angles = 2160 * rotNum + 1890;
            notice =$("#giftName_2").val();
            giftId = $("#giftId_2").value;
            turnTableGiftId = $("#turnTableGiftId_2").val();
        }
        //概率
        else if ( num == 3 ) {
            angles = 2160 * rotNum + 1935;
            notice =$("#giftName_3").val();
            giftId = $("#giftId_3").val();
            turnTableGiftId = $("#turnTableGiftId_3").val();
        }
        //概率
        else if ( num == 4 ) {
            angles = 2160 * rotNum + 1980;
            notice =$("#giftName_4").val();
            giftId = $("#giftId_4").val();
            turnTableGiftId = $("#turnTableGiftId_4").val();
        }
        //概率
        else if ( num == 5 ) {
            angles = 2160 * rotNum + 2025;
            notice =$("#giftName_5").val();
            giftId = $("#giftId_5").val();
            turnTableGiftId = $("#turnTableGiftId_5").val();
        }
        //概率
        else if ( num == 6 ) {
            angles = 2160 * rotNum + 2070;
            notice =$("#giftName_6").val();
            giftId = $("#giftId_6").val();
            turnTableGiftId = $("#turnTableGiftId_6").val();
        }
        //概率
        else if ( num == 7 ) {
            angles = 2160 * rotNum + 2115;
            notice =$("#giftName_7").val();
            giftId = $("#giftId_7").val();
            turnTableGiftId = $("#turnTableGiftId_7").val();
        }
        $("#receiveGiftNameId").html("获得"+notice);
        $("#giftId").val(giftId);
        $("#turnTableGiftId").val(turnTableGiftId);
    }
 
    //绘制转盘
    function canvasRun(){
        var canvas=document.getElementById('xttblog');
        var ctx=canvas.getContext('2d');
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
            	img.src=$("#giftImg_"+i).attr("src");
                ctx.save();
                ctx.beginPath();
                ctx.translate(140,140);
                ctx.rotate(i*step);
                ctx.font = " 20px Microsoft YaHei";
                ctx.fillStyle = color[3];
				ctx.drawImage(img,0,0,130,130,-28,-115,60,60);
                ctx.font = " 14px Microsoft YaHei";
                ctx.closePath();
                ctx.restore();
            }
        }
        $(".x").on("click",function(){
            $(".black").hide();
        })
    }
    function updateTimesAndQuantity(){
        var paramData = {};
        paramData.turnTableId = "${turnTableId}";
        paramData.giftId = $("#giftId").val();
        paramData.turnTableRuleId =  "${turnTableRule.id}";
        paramData.turnTableGiftId = $("#turnTableGiftId").val();
        $.ajax({
            type: "POST",
            url: "/turnTableGorder/receiveGiftUpdateTimesAndQuantity.json",
            async:false,
            data: paramData,
            dataType: "Json",
            success: function (result) {
                if (result!=""){
                    $("#userTurnTableRecordId").val(result);
                }
            }
        })
    }
});
function skipToReceiveGiftPage(){
    var giftId = $("#giftId").val();
    var userTurnTableRecordId = $("#userTurnTableRecordId").val();
    window.location.href="<%=path%>/turnTableGorder/getTurnTableGiftInfo.html?turnTableId=${turnTableId}&giftId="+giftId+"&userTurnTableRecordId="+userTurnTableRecordId;
}
</script>
</body>
</html>
