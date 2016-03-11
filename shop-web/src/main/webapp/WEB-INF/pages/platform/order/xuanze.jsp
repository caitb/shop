<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/guanli.css">
</head>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(document).ready(function () {
        var  selectedAddressId = $("#addressId").val();
        $.post("/userAddress/getUserAddressByUserId.do",
                {
                },function(data) {
                    if(data!=null){
                        $(".pp").hide();
                        var jsonData=eval(data);
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
                            address +="</h3><p><b>【默认】</b><span>";
                            address +=jsonData[i].provinceName +"  ";
                            address +=  jsonData[i].regionName +"  ";
                            address +=  jsonData[i].address +"  ";;
                            address +="</span></p></div></section>";
                            $(".box").append(address);
                        });
                    }else{
                        $(".box").hide();
                        $(".pp").show();
                    }
                });
    })
</script>
<body>
   <main>
       <div class="wrap">
             
           <div class="box">
                <header class="xq_header">
                   <a onclick="returnPage()"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>选择收货地址</p>
                        <input id="addressId" style="display:none;" value="${addressId}"/>
                        <input id="pfCorderId" style="display:none;" value="${pfCorderId}"/>
                        <a href="<%=path%>/userAddress/toManageAddressPage.html?addressId=${addressId}&pfCorderId=${pfCorderId}"><h2 class="gl">管理</h2></a>
                </header>
                <div class="xinz">
                    <p><a href="<%=path%>/userAddress/toAddAddressPage.html">新增收货地址</a></p>
                </div>
                 <section class="pp">
                        <h1>您还没有收货地址</h1>
                        <p>请添加新地址</p>
                </section>
<%--                <section class="sec1">
                   <div class="on">
                        <h2>王平</h2>
                        <h3>18611536163</h3>
                        <p><b>【默认】</b><span>北京市 朝阳区 丰联广场A座809A</span></p>
                   </div>
                </section>
                <section class="sec1">
                   <div>
                        <h2>王平</h2>
                        <h3>18611536163</h3>
                        <p><b>【默认】</b><span>北京市 朝阳区 丰联广场A座809A</span></p>
                   </div>
                </section>--%>
                
            </div>
            
        </div>
    </main>
    <script>
/*        $(".sec1").on("click",function(){
            alert("aaaaaa");
            $(".sec1").find("div").removeClass("on")
            $(this).find("div").eq(0).addClass("on")
        })*/


        function returnPage(){
            alert("aaaa");
            var  selectedAddressId = $("#addressId").val();
            var  pfCorderId = $("#pfCorderId").val();
            alert(selectedAddressId);
            alert(pfCorderId);
            window.location.href = "<%=path%>/corder/confirmOrder.do?orderId?"+pfCorderId+"&selectedAddressId="+selectedAddressId;
        }

        function selectClick(id){
            var  pfCorderId = $("#pfCorderId").val();
            window.location.href = "<%=path%>/corder/confirmOrder.do?orderId?"+pfCorderId+"&selectedAddressId="+id;

/*            $.post("/userAddress/settingDefaultAddress.do",{
                "id":i
            },function(data){
                if (data){
                    $('div[id^="div_"]').removeClass("on");
                    $("#div_"+i+"").addClass("on");
                }else{
                    alert("设置默认地址失败");
                }
            })*/
        }
    </script>
</body>
</html>