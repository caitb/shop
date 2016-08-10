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
    <title>麦链商城</title>
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
                <input id="giftName_${entry.sort}" type="hidden" value="${entry.giftName}"/>
                <input id="giftId_${entry.sort}" type="hidden" value="${entry.giftId}"/>
                <input id="turnTableGiftId_${entry.sort}" type="hidden" value="${entry.turnTableGiftId}"/>
                <input id="isGift_${entry.sort}" type="hidden" value="${entry.isGift}"/>
            </c:forEach>
            <img id="giftImg_0" src="<%=path%>/static/images/turnTable/char.png"/>
            <img id="giftImg_1" src="<%=path%>/static/images/turnTable/thankyouPart.png"/>
            <img id="giftImg_2" src="<%=path%>/static/images/turnTable/flat.png"/>
            <img id="giftImg_3" src="<%=path%>/static/images/turnTable/makePersistentEfforts.png"/>
            <img id="giftImg_4" src="<%=path%>/static/images/turnTable/vocalConcert.png"/>
            <img id="giftImg_5" src="<%=path%>/static/images/turnTable/noLoseHeart.png"/>
            <img id="giftImg_6" src="<%=path%>/static/images/turnTable/faceLift.png"/>
            <img id="giftImg_7" src="<%=path%>/static/images/turnTable/goodLuck.png"/>
        </div>
        <div class="wrap">
            <header class="xq_header">
                <a onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');">
                    <img src="<%=path%>/static/images/xq_rt.png" alt="">
                </a>
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
                                <c:forEach items="${turnTablelInfo.userTurnTableRecordInfos}" var="recordInfo">
                                    <li><span class='name'>${recordInfo.phoneFormat}</span><span class='gift-type'>获得</span><span>${recordInfo.turnTableGiftName}</span></li>
                                </c:forEach>
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
                <img class="giftFail" src="<%=path%>/static/images/jno.jpg" alt=""/>
                <img class="giftSuccess" src="<%=path%>/static/images/jyes.jpg" alt=""/>
                <img src="<%=path%>/static/images/x.png" class="x" />
                <div>
                    <img src="<%=path%>/static/images/xiaol.jpg" alt="" />
                    <p class="congratulate">恭喜你</p>
                    <h1 id="receiveGiftNameId"></h1>
                    <input id="giftId" type="hidden" value=""/>
                    <input id="isGiftId" type="hidden" value=""/>
                    <input id="userTurnTableRecordId" type="hidden" value=""/>
                    <input id="turnTableGiftId" type="hidden" value=""/>
                    <button class="receiveGift" onclick="skipToReceiveGiftPage()">
                        立即领取
                    </button>
                    <button class="goToPurchaseSku" onclick="goToPurchaseSku()">
                        立即下单
                    </button>
                </div>
            </div>
        </div>
    </c:forEach>
    <script src="/static/js/plugins/msclass.js"></script>
    <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>

<script>
	new Marquee(["hottitle","ulid"],0,2,"80%","18%",150,0,0);
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
            var info= ["<%=path%>/static/images/turnTable/char.png","<%=path%>/static/images/turnTable/thankyouPart.png","<%=path%>/static/images/turnTable/flat.png","<%=path%>/static/images/turnTable/makePersistentEfforts.png","<%=path%>/static/images/turnTable/vocalConcert.png","<%=path%>/static/images/turnTable/noLoseHeart.png","<%=path%>/static/images/turnTable/faceLift.png","<%=path%>/static/images/turnTable/goodLuck.png"]
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
                        var isGiftId = $("#isGiftId").val();
                        if (isGiftId==0){
                            //有奖品
                            $(".giftSuccess").show();
                            $(".giftFail").hide();
                            $(".receiveGift").show();
                            $(".goToPurchaseSku").hide();
                            $(".congratulate").show();
                        }else{
                            //无奖品
                            $(".giftSuccess").hide();
                            $(".giftFail").show();
                            $(".receiveGift").hide();
                            $(".goToPurchaseSku").hide();
                            $(".congratulate").hide();
                            $("#receiveGiftNameId").html("很遗憾，未中奖！");
                        }
                        updateTimesAndQuantity();
                        $("#receiveGiftTimesId").html(clickNum);
                        $('#tupBtn').removeAttr("disabled", true);
                    },6000);
                }
                else{
                    var isPurchaseSku = "${isPurchaseSku}";
                    $(".black").show();
                    if (isPurchaseSku=="true"){
                        $(".giftSuccess").hide();
                        $(".giftFail").show();
                        $(".receiveGift").hide();
                        $(".goToPurchaseSku").hide();
                        $(".congratulate").hide();
                        $("#receiveGiftNameId").html("您的抽奖次数已用完，再次下单后来吧！");
                        $(".goToPurchaseSku").show();
                        //alert("亲，抽奖次数已用光！");
                    }else{
                        $(".giftSuccess").hide();
                        $(".giftFail").show();
                        $(".receiveGift").hide();
                        $(".goToPurchaseSku").show();
                        $(".congratulate").hide();
                        $("#receiveGiftNameId").html("亲，您还没有抽奖机会，请先下单后再来哦！");
                    }

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
                var isGift = null;
                //概率
                if ( num == 0 ) {
                    angles = 2160 * rotNum + 1080;
                    notice =$("#giftName_0").val();
                    giftId = $("#giftId_0").val();
                    turnTableGiftId = $("#turnTableGiftId_0").val();
                    isGift = $("#isGift_0").val();
                }
                //概率
                else if ( num == 1 ) {
                    angles = 2160 * rotNum + 1035;
                    notice =$("#giftName_1").val();
                    giftId = $("#giftId_1").val();
                    turnTableGiftId = $("#turnTableGiftId_1").val();
                    isGift = $("#isGift_1").val();
                }
                //概率
                else if ( num == 2 ) {
                    angles = 2160 * rotNum + 990;
                    notice =$("#giftName_2").val();
                    giftId = $("#giftId_2").val();
                    turnTableGiftId = $("#turnTableGiftId_2").val();
                    isGift = $("#isGift_2").val();
                }
                //概率
                else if ( num == 3 ) {
                    angles = 2160 * rotNum + 945;
                    notice =$("#giftName_3").val();
                    giftId = $("#giftId_3").val();
                    turnTableGiftId = $("#turnTableGiftId_3").val();
                    isGift = $("#isGift_3").val();
                }
                //概率
                else if ( num == 4 ) {
                    angles = 2160 * rotNum + 900;
                    notice =$("#giftName_4").val();
                    giftId = $("#giftId_4").val();
                    turnTableGiftId = $("#turnTableGiftId_4").val();
                    isGift = $("#isGift_4").val();
                }
                //概率
                else if ( num == 5 ) {
                    angles = 2160 * rotNum + 855;
                    notice =$("#giftName_5").val();
                    giftId = $("#giftId_5").val();
                    turnTableGiftId = $("#turnTableGiftId_5").val();
                    isGift = $("#isGift_5").val();
                }
                //概率
                else if ( num == 6 ) {
                    angles = 2160 * rotNum + 810;
                    notice =$("#giftName_6").val();
                    giftId = $("#giftId_6").val();
                    turnTableGiftId = $("#turnTableGiftId_6").val();
                    isGift = $("#isGift_6").val();
                }
                //概率
                else if ( num == 7 ) {
                    angles = 2160 * rotNum + 765;
                    notice =$("#giftName_7").val();
                    giftId = $("#giftId_7").val();
                    turnTableGiftId = $("#turnTableGiftId_7").val();
                    isGift = $("#isGift_7").val();
                }
                if (isGift==0){
                    $("#receiveGiftNameId").html("获得"+notice);
                }else if(isGift==1){
                    $("#receiveGiftNameId").html(notice);
                }
                $("#giftId").val(giftId);
                $("#turnTableGiftId").val(turnTableGiftId);
                $("#isGiftId").val(isGift);
            }

            //绘制转盘
            function canvasRun(){
                var canvas=document.getElementById('xttblog');
                var ctx=canvas.getContext('2d');
                createCircle();
                setInterval(function(){
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
                var img=new Image();
                function createCirText(){
                    ctx.textAlign='start';
                    ctx.textBaseline='middle';
                    ctx.fillStyle = color[3];
                    var step = 2*Math.PI/8;
                    for ( var i = 0; i < 8; i++) {
                        img.src=info[i];
                        ctx.save();
                        ctx.beginPath();
                        ctx.translate(140,140);
                        ctx.rotate(i*step);
                        ctx.drawImage(img,0,0,130,130,-28,-115,60,60);
//                        ctx.closePath();
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
        function goToPurchaseSku(){
            window.location.href = "<%=basePath%>${shopId}/0/shop.shtml";
        }
        function skipToReceiveGiftPage(){
            var giftId = $("#giftId").val();
            var userTurnTableRecordId = $("#userTurnTableRecordId").val();
            window.location.href="<%=path%>/turnTableGorder/getTurnTableGiftInfo.html?turnTableId=${turnTableId}&giftId="+giftId+"&userTurnTableRecordId="+userTurnTableRecordId;
        }
    </script>
</body>
</html>
