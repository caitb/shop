<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/guanli.css">
</head>
<body>
   <main>
       <div class="wrap">
           <div class="box">
                <header class="xq_header">
                   <a onclick="returnPage()" style="position: absolute;top: 9px;left: 10px;"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                        <p>选择收货地址</p>
                        <input id="addressId" style="display:none;" value="${addressId}"/>
                        <a href="${path}/userAddress/toManageAddressPage.html" style="position: absolute;right: 10px;
                        display: block;width: 50px;height: 100%;"><h2 class="gl">管理</h2></a>
                </header>
                <div class="xinz">
                    <p><a href="${path}/userAddress/toAddAddressPage.html">新增收货地址</a></p>
                </div>
                 <section class="pp">
                        <h1>您还没有收货地址</h1>
                        <p>请添加新地址</p>
                </section>
            </div>
        </div>
    </main>
   <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
   <script>
       $(document).ready(function () {
           var  selectedAddressId = $("#addressId").val();
           $.post("/userAddress/getUserAddressByUserId.do",
                   {
                   },function(data) {
                       if(data!=null&&data!=""){
                           $(".pp").hide();
                           var jsonData=eval(data);
                           if (jsonData!=null&&jsonData!=""){
                               $(".pp").attr("style","display:none");
                               $.each(jsonData, function(i, item) {
                                   var  address = "<section class=\"sec1\" onclick='selectClick("+jsonData[i].id+")'>";
                                   if (jsonData[i].id==selectedAddressId){
                                       address +="<div class=\"on\" id=\"div_"+jsonData[i].id+"\"><h2>";
                                   }else{
                                       address +="<div id=\"div_"+jsonData[i].id+"\"><h2>";
                                   }
                                   address +=jsonData[i].name;
                                   address +="</h2><h3>";
                                   address +=jsonData[i].mobile;
                                   address +="</h3><p>";
                                   if(jsonData[i].isDefault==1){
                                       address += "<b style='display:inline-block;color: #F74A11;' >【默认】</b><span>";
                                   }else{
                                       address += "<b></b><span>";
                                   }
                                   address +=jsonData[i].provinceName +"  ";
                                   address +=  jsonData[i].regionName +"  ";
                                   address +=  jsonData[i].address +"  ";;
                                   address +="</span></p></div></section>";
                                   $(".box").append(address);
                               });
                               $(".sec1").attr("style","display:block");
                           }else{
                               $(".pp").css("display","-webkit-box");
                           }
                       }else{
                           $(".pp").css("display","-webkit-box");
                       }
                   });
       })
   </script>
    <script>
        function returnPage(){
            var  selectedAddressId = $("#addressId").val();
            window.location.href = "${path}/userAddress/clickAddressOrReturnToPage.do?selectedAddressId="+selectedAddressId;
        }
        function selectClick(id){
            window.location.href = "${path}/userAddress/clickAddressOrReturnToPage.do?selectedAddressId="+id;
        }
    </script>
</body>
</html>