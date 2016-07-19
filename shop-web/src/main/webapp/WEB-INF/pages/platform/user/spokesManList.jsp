<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/daiyanren.css">
</head>
<body>
<input type="hidden" id="pageNums" name="pageNums" value="${totalPage}"/>
<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="totalNum" name="totalNum" value="${totalCount}">
<input type="hidden" id="hiddenID" name="hiddenID" value="">
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.location.href='${basePath}shop/manage/index'"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>代言人粉丝</p>
    </header>
    <main>
        <div class="top">
            <div>
                <h1>${fansNum}</h1>
                <h2>粉丝数</h2>
            </div>
            <div>
                <h1>${spokesManNum}</h1>
                <h2>代言人数</h2>
            </div>
        </div>
        <div class="t_b">
            <p>代言人ID</p>
            <input type="tel" id="ID">
            <b onclick="checkInfo()">查找</b>
        </div>
        <div class="main" id="disSpokesMan">

            <%--<c:forEach items="${infos}" var="info">
                <div class="s_t">
                    <p onclick="toDetail(${info.userId})" style="background:url('${info.headImg}');background-size:100% 100%;"></p>
                    <div>
                        <p><span>${info.wxName}</span><span>${info.isBuyView}</span></p>
                        <p><span>ID：${info.ID}</span><span>${info.createTimeView}</span></p>
                    </div>
                </div>
                <p class="s_b">
                    <b>代言人数：${info.spokesManNum}</b>
                    <b>粉丝数：${info.fansNum}</b>
                </p>
            </c:forEach>--%>
            </div>
        </div>
    </main>
    <div class="next">
        <button onclick="lastPage()">上一页</button>
        <h1 id="lable">
            <label>${currentPage}/${totalPage}</label>
        </h1>
        <button onclick="nextPage()">下一页</button>
    </div>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/commonAjax.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script src="${path}/static/js/spokesManList.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/pageJs/hideWXShare.js"></script>
<script>
    var path = "${path}";
    var basePath = "${basePath}";
    gohistory();
</script>
</body>
</html>
