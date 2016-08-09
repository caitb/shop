<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <%@include file="/WEB-INF/pages/common/commonhead.jsp" %>
    <title>"链"在七夕秒杀会场</title>
    <style type="text/css">
        img{
            width: 100%;
            display: block;
        }
        main{
            background:url(${path}/static/images/activity/qixi/tobg.jpg) no-repeat;
            background-size: 100% 100%;
            padding: 0 5px;
            padding-bottom: 10%;
        }
        main img{
            width: 100%;
        }
        main .two{
            margin: 0 10%;
            padding: 20px 0;
        }
        main .two img{
            width: 55%;
            margin: 0 auto;
        }
        main .two p{
            color: #FFF;
            margin: 10% 0 2% 0;
        }
        main .floor{
            margin: 0 10px;
            background: #FFF;
            padding: 15px 20px;
            border-radius: 5px;
        }
        main .floor h1{
            font-size: 1.2rem;
            color: #323232;
        }
        main .floor p{
            color: #323232;
            font-size: 12px;
            margin-top: 5px;
            line-height: 25px;
        }
    </style>
</head>
<body>
<img src="${path}/static/images/activity/qixi/toshop.jpg"/>
<main>
    <img src="${path}/static/images/activity/qixi/tolist.jpg" alt="" />
    <div class="two">
        <img src="${path}/static/images/activity/qixi/one.jpg" alt="" />
        <p>存储该二维码，微信扫一扫扫描该张二维码图片，进入店铺页面选择商品下单，发货类型务必选择“平台发货”哦。</p>
    </div>
    <div class="floor">
        <h1>活动须知：</h1>
        <p>*本活动顾客在活动页推荐二维码的店铺以外的店铺购买不予补贴。</p>
        <p>*本活动顾客按原价下单，麦链会在活动结束后通过线下方式返现补贴给消费者。</p>
        <p>*本活动中一人多次下单或一次购买多件商品的，按1单予以补贴，每名消费者补贴不超过80元。</p>
        <p>*中奖的幸运顾客以麦链电话通知为准，麦链将在活动结束后7个工作日内公示中奖名单。</p>
    </div>
</main>
</body>
</html>

