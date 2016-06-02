<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>

<div id="modal-audit" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <span class="white">&times;</span>
                    </button>
                    会员信息审核
                </div>
            </div>

            <div class="modal-body no-padding">
                <div>
                    <div id="user-profile-1" class="user-profile row">
                        <div class="col-xs-12 col-sm-12 col-sm-offset-0">

                            <!-- #section:pages/profile.info -->
                            <div class="profile-user-info profile-user-info-striped">

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 会员信息 </div>

                                    <div class="profile-info-value">
                                        <span class="" id="nickName">${auditMap.comUser.wxNkName}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 姓名 </div>

                                    <div class="profile-info-value">
                                        <span class="editable editable-click" id="realName">${auditMap.comUser.realName}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 上级信息 </div>

                                    <div class="profile-info-value">
                                        <table class="table table-bordered table-striped">
                                            <thead class="thin-border-bottom">
                                            <tr>
                                                <th>
                                                    <i class="ace-icon fa fa-caret-right blue"></i>上级人员
                                                </th>

                                                <th>
                                                    <i class="ace-icon fa fa-caret-right blue"></i>电话
                                                </th>

                                                <th class="hidden-480">
                                                    <i class="ace-icon fa fa-caret-right blue"></i>所属等级
                                                </th>

                                                <th class="hidden-480">
                                                    <i class="ace-icon fa fa-caret-right blue"></i>代理产品
                                                </th>
                                            </tr>
                                            </thead>

                                            <tbody>
                                            <c:forEach items="${auditMap.pUserMaps}" var="pUserMap">
                                            <tr>
                                                <td>${pUserMap.userName}</td>
                                                <td>${pUserMap.mobile}</td>
                                                <td>${pUserMap.agentLevelName}</td>
                                                <td>${pUserMap.agentSkuName}</td>
                                            </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 身份证号 </div>

                                    <div class="profile-info-value">
                                        <span class="" id="linkmanIDCard">${auditMap.comUser.idCard}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 身份证扫描件 </div>

                                    <div class="profile-info-value" style="height: 200px;">
                                        <img data-action="zoom" class="img-thumbnail" id="idCardF" src="${auditMap.comUser.idCardFrontUrl}" alt="200x200" src="#" data-holder-rendered="true" style="width: 245px;height: 200px;">
                                        <img data-action="zoom" class="img-thumbnail" id="idCardB" src="${auditMap.comUser.idCardBackUrl}" alt="200x200" src="#" data-holder-rendered="true" style="width: 245px;height: 200px">
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name" id="jjT"> 审核备注 </div>

                                    <div class="profile-info-value" id="jjF">
                                        <form id="auditForm">
                                            <input type="hidden" name="id" id="userId" value="${auditMap.comUser.id}" />
                                            <input type="hidden" name="mobile" id="mobile" value="${auditMap.comUser.mobile}" />
                                            <input type="hidden" name="auditStatus" id="auditStatus" value="2" />
                                            <textarea name="auditReason" id="auditReason" placeholder="请填写审核记录" rows="3" cols="50"></textarea>
                                        </form>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer no-margin-top">
                <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                    <input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5">
                    <button class="btn btn-sm btn-danger pull-left audit" audit-status="3">
                        拒绝
                    </button>
                    <button class="btn btn-sm btn-info pull-left audit" audit-status="2">
                        通过
                    </button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- PAGE CONTENT ENDS -->