<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/upGrade/shengji.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                    <p>升级</p>            
        </header>
        <main>
            <div class="floor">
                <p>
                    <span>产品名：</span>
                    <label for="name"><b>请选择产品</b></label>
                    <select id="name" onchange="changeSku()">
                        <option value="">请选择产品</option>
                        <c:forEach items="${userSkuAgents}" var="userSkuAgent">
                            <option value="${userSkuAgent.skuId}_${userSkuAgent.skuName}_${userSkuAgent.agentLevelId}_${userSkuAgent.agentName}_${userSkuAgent.userPid}">${userSkuAgent.skuName}</option>
                        </c:forEach>
                    </select>
                </p>
                <p>
                    <span>当前级别：</span>
                    <span id="currentLevel">请选择产品</span>
                </p>
                <p>
                    <span>申请级别</span>
                </p>
            </div>
            <div class="floor2" id="upGradePackage">
                <p>
                    请选择产品
                </p>
            </div>
            <button onclick="blackShow()">
                下一步
            </button>
        </main>
    </div>
    <div class="black">
        <input type="hidden" id="skuId" name="skuId" value=""/>
        <input type="hidden" id="curAgentLevel" name="curAgentLevel" value=""/>
        <input type="hidden" id="upgradeLevel" name="upgradeLevel" value=""/>
        <input type="hidden" id="userPid" name="userPid" value=""/>
        <div class="backb"></div>
        <div class="back_que">
                    <p>您确定升级?</p>
                    <h4><b>产品名:</b><span id="productName"></span></h4>
                    <h4><b>当前级别:</b><span id="curLevel"></span></h4>
                    <h4><b>申请级别:</b><span id="upLevel"></span></h4>
                    <h3>
                        <span class="que_qu" onclick="blackHide()">我再想想</span>
                        <span class="que_que">确定</span>
                    </h3>
                </div>
    </div>
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
    <script src="${path}/static/js/commonAjax.js"></script>
    <script src="${path}/static/js/definedAlertWindow.js"></script>
    <script src="${path}/static/js/upGrade.js"></script>
    <script>
        var path = "${path}";
        var basePath = "${basePath}";
    </script>
</body>
</html>