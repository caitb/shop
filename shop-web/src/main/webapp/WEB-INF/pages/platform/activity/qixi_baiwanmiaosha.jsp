<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <%@ include file="/WEB-INF/pages/common/head.jsp"%>
    <title>七夕百万秒杀活动专场</title>
    <style type="text/css">
        body > img{
            width: 100%;
            display: block;
        }
        main{
            background: url(${path}/static/images/activity/qixi/tobg.jpg) no-repeat;
            background-size: 100% 100%;
            padding-bottom: 20px;
            padding-top: 20px;
        }
        .sec1{
            padding:10px 10px 10px 10px;
            background: #FFF url(${path}/static/images/activity/qixi/tobg2.jpg) no-repeat;
            background-size: 100% 100%;
            border-radius: 5px;
            margin:0 10px;
            padding-bottom: 15px;
        }
        .sec1 li{
            color: #333;
            padding-left: 34%;
            margin-bottom: 10px;
        }
        .sec1 li span{
            font-size: 12px;
        }
        .sec1 h1{
            color: #333;
            font-size: 1.2rem;
            padding: 3% 0 8% 25%;
        }
        .sec1 p{
            font-size: 1rem;
            padding-left: 25%;
            margin-top: 5px;
            color: #333;
            line-height: 25px;
        }
        .sec2{
            padding: 0 10px;
        }
        .sec2 img{
            width: 100%;
            display: block;
            display: -webkit-box;
        }
        .sec3{
            margin: 0 15px;
        }
        .sec3 h1{
            padding: 25px 0;
            color: #FFF;
            font-size: 1.2rem;
        }
        .sec3 p{
            color: #fff;
            font-size: 12px;
            line-height: 25px;
            margin-bottom: 2%;
        }
        button{
            background: #E7134E;
            display: block;
            width: 80%;
            padding: 10px 0;
            border-radius: 5px;
            color: #FFF;
            margin: 10% auto 6% auto;
        }
        button+p{
            color: #FFF;
            font-size: 12px;
            text-align: center;
        }
    </style>
</head>
<body>
<img src="${path}/static/images/activity/qixi/tobanner.png" alt="" />
<img src="${path}/static/images/activity/qixi/tonav.jpg"/>
<main>
    <div class="sec1">
        <div>
            <h1>通知中心</h1>
            <div id="hottitle" class="hot">
                <ul id="ulid">
                    <li><span class='name'>131xxxxx555</span><span class='gift-type'>已经卖出了</span><span>1万</span></li>
                    <li><span class='name'>138xxxxx431</span><span class='gift-type'>已经卖出了</span><span>3万</span></li>
                    <li><span class='name'>132xxxxx781</span><span class='gift-type'>已经卖出了</span><span>2万</span></li>
                    <li><span class='name'>182xxxxx860</span><span class='gift-type'>已经卖出了</span><span>1万</span></li>
                    <li><span class='name'>183xxxxx544</span><span class='gift-type'>已经卖出了</span><span>2万</span></li>
                    <li><span class='name'>133xxxxx578</span><span class='gift-type'>已经卖出了</span><span>1万</span></li>
                    <li><span class='name'>155xxxxx328</span><span class='gift-type'>已经卖出了</span><span>1万</span></li>
                    <li><span class='name'>177xxxxx031</span><span class='gift-type'>已经卖出了</span><span>1万</span></li>
                </ul>
            </div>
            <!--<p>
                王平在迎客开始了自己的直播，可搜索房间号1233
            </p>-->
        </div>
    </div>
    <div class="sec2">
        <img src="${path}/static/images/activity/qixi/toimg.jpg" alt="" />
    </div>
    <div class="sec3">
        <h1>活动须知</h1>
        <p>
            1.本活动仅统计零售订单，代理订单不计算在内。<br />

            2.本活动时间为2016年8月9日0：00—2016年8月25日24：00。<br />

            3.奖品中的现货价值为零售价价格，奖品的品类由平台决定。<br />

            4.二等奖特斯拉预定资格特指特斯拉model 3，中奖代理商可选择兑换现金8000元。<br />

            5.活动时间以外的零售流水不计算在本活动要求范围内。<br />

            6.本活动所统计的零售订单必须是平台发货，代理商自己发货的订单不计算在内。<br />

            7.现货类奖品要求中奖代理商或中奖代理商其代理层级中的最高级代理商从平台补充对应奖项要求门槛的零售价值的货品后才予以兑现。<br />

            8.中奖商家以麦链电话通知为准，麦链将在活动结束后7个工作日内公示中奖名单

        </p>
    </div>
    <button id="btn">我要成为零售皇帝</button>
</main>
<script src="${path}/static/js/activity/msclass.js"></script>
<script type="text/javascript" src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script>
    new Marquee(["hottitle","ulid"],0,2,"100%","35%",150,0,0);

    document.getElementById("btn").onclick = function (){
        window.location.href = "${path}/shop/manage/index";
    }
</script>
</body>
</html>

