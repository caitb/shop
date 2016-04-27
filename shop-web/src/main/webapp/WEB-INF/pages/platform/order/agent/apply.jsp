<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/shenqing.css">
</head>
<body>
<div class="fakeloader"></div>
<c:if test="${isUserForcus==false}">
    <div class="na">
        <p>关注麦链公众微信号“<span class="add">麦链商城</span>”，查佣金，查订单。</p>
        <label class="close">×</label>
    </div>
</c:if>
<div class="wrap">
    <header class="xq_header">
        <a href="${basePath}product/${skuId}">
            <img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>合伙人申请</p>
    </header>

    <c:if test="${isQueuing==true}">
        <div class="paidan">
            <h1><img src="${path}/static/images/loading.png" alt=""><b>在您前面还有<span>${count}</span>人排单</b></h1>
            <p style="color: #FF5200">*由于商品火爆导致库存不足,本次申请将进入排单系统,待产能提升,我们会按付款顺序发货</p>
        </div>
    </c:if>
    <div class="biao">
        <h1>申请条件</h1>
        <table>
            <tr>
                <td>申请条件</td>
                <td>是否完成</td>
                <td>任务入口</td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${user.isBinding==1}">
                        <td>绑定手机号</td>
                        <td><img src="${path}/static/images/dui.png" alt=""></td>
                        <td>--</td>
                    </c:when>
                    <c:otherwise>
                        <td>绑定手机号</td>
                        <td><img src="${path}/static/images/cuo.png" alt=""></td>
                        <td><a href="javascript:void(0);" onclick="validateCodeJS.applyTrial('agent')"
                               style="color: #FF5200;text-decoration: underline">去完成</a></td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td>实名认证</td>
                <c:choose>
                    <c:when test="${user.auditStatus == 2}">
                        <td><img src="${path}/static/images/dui.png" alt=""></td>
                        <td>--</td>
                    </c:when>
                    <c:otherwise>
                        <td><img src="${path}/static/images/cuo.png" alt=""></td>
                        <c:if test="${user.auditStatus == 1}">
                            <td><span style="color: #FF5200">审核中</span></td>
                        </c:if>
                        <c:if test="${user.auditStatus == 3}">
                            <td><a href="javascript:void(0);" onclick="reSubmitIdentityAuth();"
                                   style="color: #FF5200;text-decoration: underline">已拒绝重新提交</a></td>
                        </c:if>
                        <c:if test="${user.auditStatus == 0}">
                            <td><a href="javascript:void(0);" onclick="goVerified();"
                                   style="color: #FF5200;text-decoration: underline">去完成</a></td>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </tr>
        </table>
    </div>

    <main>
        <div class="he">
            <h1>成为合伙人后，您将获得以下特权</h1>
        </div>
        <section class="sec1">
            <img src="${path}/static/images/shenqing_3.png" alt="">
            <div>
                <h2>独立店铺</h2>
                <p>拥有自己的独立店铺进行推广装修等</p>
            </div>
            <img src="${path}/static/images/down.png" alt="" class="down">
        </section>
        <div>
            <p></p>
            <h1>
                <span>还没有数据</span>
                <span>还没有数据</span>
            </h1>
        </div>
        <section class="sec1">
            <img src="${path}/static/images/shenqing_4.png" alt="">
            <div>
                <h2>寻找合伙人</h2>
                <p>可以用采购价格购买商品，赚取利差</p>
            </div>
            <img src="${path}/static/images/down.png" alt="" class="down">
        </section>
        <div>
            <p></p>
            <h1>
                <span>还没有数据</span>
                <span>还没有数据</span>
            </h1>
        </div>
        <section class="sec1">
            <img src="${path}/static/images/shenqing_5.png" alt="">
            <div>
                <h2>推广渠道</h2>
                <p>平台提供多样的推广渠道编辑推广自己商品</p>
            </div>
            <img src="${path}/static/images/down.png" alt="" class="down">
        </section>
        <div>
            <p></p>
            <h1>
                <span>还没有数据</span>
                <span>还没有数据</span>
            </h1>
        </div>
        <section class="sec1">
            <img src="${path}/static/images/shenqing_6.png" alt="">
            <div>
                <h2>团队管理</h2>
                <p>提供完善的售后和团队管理工具</p>
            </div>
            <img src="${path}/static/images/down.png" alt="" class="down">
        </section>
        <div>
            <p></p>
            <h1>
                <span>还没有数据</span>
                <span>还没有数据</span>
            </h1>
        </div>
        <section class="sec1">
            <img src="${path}/static/images/shenqing_7.png" alt="">
            <div>
                <h2>平台补助</h2>
                <p style="margin-right:10px;">消费者分享商品可获得佣金，佣金来自于平台的补助</p>
            </div>
            <img src="${path}/static/images/down.png" alt="" class="down">
        </section>
        <div>
            <p></p>
            <h1>
                <span>还没有数据</span>
                <span>还没有数据</span>
            </h1>
        </div>
        <section class="sec1">
            <img src="${path}/static/images/shouquan.png" alt="">
            <div>
                <h2>授权证书</h2>
                <p style="margin-right:10px;">官方的授权证书证明你的身份</p>
            </div>
            <img src="${path}/static/images/down.png" alt="" class="down">
        </section>
        <div>
            <p></p>
            <h1>
                <span>还没有数据</span>
                <span>还没有数据</span>
            </h1>
        </div>
        <input id="skuId" value="${skuId}" style="display: none"/>
        <%--<input id="skipPageId" value="register" style="display: none"/>--%>
        <input id="type" value="${type}" style="display: none"/>
    </main>
    <section class="sec2">
        <p><a id="goToNext" href="javascript:void(0);">继续</a></p>
    </section>
</div>
<div class="back_box">
    <div class="back"></div>
    <div class="back_j">
        <p class="biao">绑定账号</p>
        <div>
            <p>手机号：<input type="tel" class="phone" id="phoneId"></p>
        </div>
        <div class="d">
            <p>验证码：<input type="tel" id="validateNumberDataId">
                <button id="validateNumberId">获取验证码</button>
            </p>
        </div>
        <p class="tishi" id="errorMessageId"></p>
        <h1 class="j_qu" id="nextPageId">下一步</h1>
    </div>
    <div class="back_f">
        <p>关注公众账号查佣金、查订单</p>
        <span class="close">×</span>
        <img src="${path}/static/images/b.png" alt="">
    </div>
</div>
<div id="realNameVerifyDiv" class="back_login" style="display:none;">
    <p>您的账户还未通过实名认证,无法继续申请合伙人,请去认证!</p>
    <h1><span id="quxiao">取消</span><span id="goMark" onclick="goVerified(this);">去认证</span></h1>
</div>
</body>
<%--<%@ include file="/WEB-INF/pages/common/foot.jsp" %>--%>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<%--<script src="${path}/static/js/commonAjax.js"></script>--%>
<script src="${path}/static/js/iscroll.js"></script>
<script src="${path}/static/js/validateCode.js"></script>
<script>
    $(document).ready(function () {
        validateCodeJS.initPage();
    });
    function goVerified(para) {
        if (${user.isBinding!=1}) {
            alert("请先绑定手机号");
            return false;
        }
        $(para).html("正在提交...");
        var para = "?";
        para += "goToURL=" + encodeURIComponent("${basePath}userApply/apply.shtml?skuId=${skuId}");
        window.location.href = "${basePath}user/userVerified.shtml" + para;
    }

    function reSubmitIdentityAuth() {
        if (${user.isBinding!=1}) {
            alert("请先绑定手机号");
            return false;
        }
        window.location.href = "${basePath}identityAuth/toInentityAuthPage.html?auditStatus=3";
    }

    $("#goToNext").on("click", function () {
        var isBinding = "${user.isBinding}";
        var auditStatus = "${user.auditStatus}";
        if (isBinding == 0) {
            alert("请绑定手机号");
            return;
        }
        if (auditStatus == 1) {
            alert("您的实名认证正在审核中,请耐心等候!");
            return;
        } else if (auditStatus == 0) {
            $("#realNameVerifyDiv").show();
            return;
        } else if (auditStatus == 3) {
            alert("您的实名认证未通过,请重新提交!");
            return;
        } else if (auditStatus == 2) {
            $(this).html("请稍后...");
            window.location.href = "${path}/userApply/register.shtml?skuId=${skuId}";
        }
    });

    $("#quxiao").on("click", function () {
        $("#realNameVerifyDiv").hide();
    });

    $(".down").toggle(function () {
        $(this).attr("src", "${path}/static/images/top.png")
        $(this).parent().css("borderBottom", "none")
        $(this).parent().next().css("display", "-webkit-box");
    }, function () {
        $(this).attr("src", "${path}/static/images/down.png")
        $(this).parent().next().hide();
        $(this).parent().css("borderBottom", "1px solid #f6f6f6")
    })
</script>
</html>