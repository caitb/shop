<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>Document</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/fenxiaojilu.css">
    <link rel="stylesheet" href="${path}/static/css/common/common.css">
</head>
<body>
    <header>
            <a href="zhifu.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>订单详情</p>  
    </header>
    <div class="wrap">
       <div class="index_login">
            <p><b>1233</b><span>参与人次</span></p>
            <ul>
                <li><p>总销售额</p><h1>￥<span>10,000,000</span>.00</h1></li>
                <li><p>发放佣金</p><h1>￥<span>10,000,000</span>.00</h1></li>
            </ul>
       </div>
       <div class="sec1" id="sec1">
                <p>分销记录：<label for="beginTime"><b>2016</b>年<b>1</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
                <div id="divall">
                    <div>
                        <p><span><b>18</b>人参加</span><span>抗引力-瘦脸精华</span><span>查看订单></span></p>
                        <h1><span>22日</span><span>购买人：张三</span><span>￥228.00</span></h1>
                        <h1><span><b>3</b>人分佣</span><span>￥2580.00</span><span>奋勇明细></span></h1>
                    </div>
                    <div>
                        <p><span><b>18</b>人参加</span><span>抗引力-瘦脸精华</span><span>查看订单></span></p>
                        <h1><span>22日</span><span>购买人：张三</span><span>￥228.00</span></h1>
                        <h1><span><b>3</b>人分佣</span><span>￥2580.00</span><span>奋勇明细></span></h1>
                    </div>
                </div>
            </div>
    </div>
    <div id="datePlugin"></div>
    <div class="back"></div>
    <div class="back_f">
        <div>
            <p>王平:</p>
            <p>$123.00</p>
        </div>
        <div>
            <p>jaliikkdll:</p>
            <p>$123.00</p>
        </div>
        <div>
            <p>晴晴晴，天:</p>
            <p>$123.00</p>
        </div>
        <button>知道了</button>
    </div>
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${path}/static/js/plugins/date.js" ></script>
    <script type="text/javascript" src="${path}/static/js/plugins/iscroll.js" ></script>
    <script>
            $(function(){
                $('#beginTime').date();
                $('#endTime').date({theme:"datetime"});
            });
    </script>
</body>
</html>