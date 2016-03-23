<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<style>
    * {
        box-sizing: border-box;
    }
    .box {
        /*background-color: #fafafa;*/
        color: #666;
        margin-bottom: 20px;
        padding: 5px;
        border-radius: 4px;
        /*border: 1px solid #e1e8ed;*/
    }
    .box.-radius-all {
        border-radius: 4px;
    }
    .meta-attributes {
        color: #999;
    }
    table {
        border-collapse: collapse;
        border-spacing: 0;
        margin: 0 auto;
        width: 500px;
    }
    .meta-attributes tr td {
        font-size: 14px;
        padding-bottom: 15px;
        vertical-align: top;
    }
    .meta-attributes__attr-name {
        color: #454545;
        font-weight: bold;
        padding-right: 10px;
        width: 140px;
    }

    .my-table th, .my-table td {
        text-align: center;
        height:38px;
    }
</style>
<div class="box -radius-all">
    <div class="meta-attributes">

        <c:if test="${extractApply.extractWay==1}" >
            <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
                <tbody>

                <input type="hidden" id="extractApplyId" value="${extractApply.id}" name="extractApplyId">
                <tr>
                    <td class="meta-attributes__attr-name">申请日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.applyTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">申请人</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.comUser.realName}
                    </td>
                </tr>


                <tr>
                    <td class="meta-attributes__attr-name">申请金额</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.extractFee}

                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">账户余额</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.comUserAccount.extractableFee}

                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">提现方式</td>
                    <td class="meta-attributes__attr-detail">
                        微信
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">微信绑定状态</td>
                    <td class="meta-attributes__attr-detail">
                        <c:if test="${extractApply.comUser.openid != null}">已绑定</c:if>
                        <c:if test="${extractApply.comUser.openid == null}">未绑定</c:if>
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">审核通过日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.extractTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>
                <tr>
                    <td class="meta-attributes__attr-name">付款日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.payTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>

                </tbody>
            </table>
        </c:if>

        <c:if test="${extractApply.extractWay==2}" >
            <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
                <tbody>

                <tr>
                    <td class="meta-attributes__attr-name">申请日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.applyTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">申请人</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.comUser.realName}
                    </td>
                </tr>


                <tr>
                    <td class="meta-attributes__attr-name">申请金额</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.extractFee}

                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">账户余额</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.comUserAccount.extractableFee}

                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">提现方式</td>
                    <td class="meta-attributes__attr-detail">
                            <%-- ${extractApply.extractWay}--%>
                        支付宝
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">支付宝账号</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.bankCard}
                    </td>
                </tr>
                <tr>
                    <td class="meta-attributes__attr-name">审核通过日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.extractTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>
                <tr>
                    <td class="meta-attributes__attr-name">付款日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.payTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>

                </tbody>
            </table>
        </c:if>

        <c:if test="${extractApply.extractWay==3}" >
            <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
                <tbody>

                <tr>
                    <td class="meta-attributes__attr-name">申请日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.applyTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">申请人</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.comUser.realName}
                    </td>
                </tr>


                <tr>
                    <td class="meta-attributes__attr-name">申请金额</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.extractFee}

                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">账户余额</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.comUserAccount.extractableFee}

                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">提现方式</td>
                    <td class="meta-attributes__attr-detail">
                            <%--${extractApply.extractWay}--%>
                        银行卡
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">银行卡号</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.bankCard}
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">银行名称</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.bankName}
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">开户行名称</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.depositBankName}
                    </td>
                </tr>

                <tr>
                    <td class="meta-attributes__attr-name">持卡人姓名</td>
                    <td class="meta-attributes__attr-detail">
                            ${extractApply.cardOwnerName}
                    </td>
                </tr>
                <tr>
                    <td class="meta-attributes__attr-name">审核通过日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.extractTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>
                <tr>
                    <td class="meta-attributes__attr-name">付款日期</td>
                    <td class="meta-attributes__attr-detail">
                        <fmt:formatDate value="${extractApply.payTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                    </td>
                </tr>

                </tbody>
            </table>
        </c:if>

        <%--<div class="modal-footer">
            <button type="button" class="btn btn-success" onclick="pass(${order})">通过申请</button>
            <button type="button" class="btn btn-danger">拒绝申请</button>
            &lt;%&ndash;<button type="button" class="btn btn-primary" id="userSubmit">
                提交
            </button>&ndash;%&gt;
        </div>--%>
    </div>
</div>