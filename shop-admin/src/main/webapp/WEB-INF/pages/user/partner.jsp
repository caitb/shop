<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>麦士商城 - 后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- Latest compiled and minified CSS -->
    <link href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-table.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-editable.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/buttons.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/examples.css">


    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js"></script>

    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
    <!-- Latest compiled and minified Locales -->
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=basePath%>static/js/date-util.js"></script>
    <style>
        .row{

            display: -webkit-box;
        }
        .col-lg-3:nth-child(2){
            width:300px;
        }
        .col-lg-3:nth-child(3){
            width:500px;
        }
    </style>
</head>
<body>
    <div class="container">
        <input type="hidden" id="approveId" name ="id" value="${pfUserSku.id}"/>
        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>代理编号:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.code}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>联系人姓名:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.comUser.realName}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>联系人手机号:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.comUser.mobile}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>联系人身份证:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.comUser.idCard}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>


        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>身份证扫描:</h4>
            </div>

            <div class="col-lg-3">
                <p><a href="#" data-toggle="modal" data-target="#cardModal" onclick="card('${pfUserSku.comUser.idCardFrontUrl}')">正面</a>&nbsp;<a href="#" data-toggle="modal" data-target="#cardModal" onclick="card('${pfUserSku.comUser.idCardBackUrl}')">反面</a></p>
            </div>
            <div class="col-lg-3"></div>
        </div>
        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>合伙商品:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.skuName}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>
        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>合伙人等级:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.comAgentLevel.name}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>
        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>上级合伙人:</h4>
            </div>

            <div class="col-lg-3">
                <p>${pfUserSku.pRealName}</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>是否交代理款:</h4>
            </div>

            <div class="col-lg-3">
                <p><c:if test="${pfUserSku.isPay==0}" >
                    未完成
                </c:if>
                    <c:if test="${pfUserSku.isPay==1}"  >
                        已完成
                    </c:if></p>
            </div>
            <div class="col-lg-3"></div>
        </div>
        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>授权证书:</h4>
            </div>

            <div class="col-lg-3">
                <p><c:if test="${pfUserSku.isCertificate==0}" >
                    未完成
                </c:if>
                    <c:if test="${pfUserSku.isCertificate==1}"  >
                        已完成&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="certificate('${pfUserSku.id}')">点击查看</a>
                    </c:if></p>
            </div>
            <div class="col-lg-3"></div>
        </div>

    </div> <!-- /container -->
    <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true"  style="width: auto;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        授权证书
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group" id="certificate">


                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit">
                        提交
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="cardModal" tabindex="-1" role="dialog"
         aria-labelledby="orderModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="orderModalLabel">
                        身份证复印件
                    </h4>
                </div>
                <div class="modal-body" >
                    <div id="bodyimg"></div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="Submit">
                        提交
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->

    </div>
    <div>
        <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
        <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
        <!-- Latest compiled and minified JavaScript -->
        <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js"></script>

        <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
        <!-- Latest compiled and minified Locales -->
        <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-zh-CN.min.js"></script>
    </div>
    <script>
        //身份证
        function card(url){
            //alert(url)
            $("#bodyimg").html('<img src="' + url + '" />');
            $("#cardModal").show;
        }
       /* //更改上级
        function changeLeader(){
            var approveId = $("#approveId").val();
            $.ajax({
                url: '<%=basePath%>certificate/listUpper.do',
                data: {id: approveId},
                success: function (data) {
                    $('#myModal').modal({
                        show: true,
                        backdrop: true,
                        upperList:data
                    });
                }
            });
        }*/
        //授权证书
        function certificate(id){
            $.ajax({
                url: '<%=basePath%>/certificate/findById.do',
                data: {id: id},
                success: function (data) {
                    $("#certificate").html('<img src="' + data + '" />');

                    $('#myModal').modal({
                        width: 'auto',
                        show: true,
                        backdrop: true,
                    });
                }
            });
        }

    </script>

</body>
</html>
