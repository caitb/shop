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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/guanli.css">
</head>
<script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
<script>
    document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
        WeixinJSBridge.call('hideToolbar');
        WeixinJSBridge.call('hideOptionMenu');
    });
</script>
<script>
    $(document).ready(function () {
        $.post("/userAddress/getUserAddressByUserId.do",
                {
                },function(data) {
                    if(data!=null&&data!=""){
                        var jsonData=eval(data);
                        if (jsonData!=null&&jsonData!=""){
                            $(".pp").attr("style","display:none");
                            $.each(jsonData, function(i, item) {
                                var  address = "<selection class=\"sec1\" id=\"selection_"+jsonData[i].id+"\" >";
                                address +="<div><h2>";
                                address +=jsonData[i].name;
                                address +="</h2><h3>";
                                address +=jsonData[i].mobile;
                                address +="</h3><p><span>";
                                address +=jsonData[i].provinceName +"  ";
                                address +=jsonData[i].cityName +"  ";
                                address +=  jsonData[i].regionName +"  ";
                                address +=  jsonData[i].address +"  ";;
                                address +="</span></p></div>";
                                address +=" <div class=\"setting\">";
                                if (jsonData[i].isDefault==1){
                                    $("#defaultAddressId").val(jsonData[i].id);
                                    address +="<p class=\"pon\" onclick='settingDefaultAddress("+jsonData[i].id+")'  id=\"p_"+jsonData[i].id+"\" >默认地址<span></span></p>";
                                }else{
                                    address +="<p id=\"p_"+jsonData[i].id+"\"  onclick='settingDefaultAddress("+jsonData[i].id+")'  >默认地址<span></span></p>";
                                }
                                address +="<h2 onclick='editAddress("+jsonData[i].id+")'>编辑地址<span></span></h2>";
                                address +="<h3 class=\"end\" onclick='deleteAddress("+jsonData[i].id+")' >删除地址</h3>";
                                address +="</div></section>";
                                $(".box").append(address);
                            })
                            $(".sec1").attr("style","display:block");
                        }else{
                            //显示没有收获地址的提示信息
                            $(".pp").css("display","-webkit-box");
                        }
                    }else{
                        //显示没有收获地址的提示信息
                        $(".pp").css("display","-webkit-box");
                    }
                })
    })

    function complete(){
        window.location.href="/userAddress/manageAddressPageToChooseAddressPage.html";
    }
</script>
<body>
   <main>
       <div class="wrap">
           <div class="box">
                <header class="xq_header">
                   <a onclick="returnToPage()" ><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>管理收货地址</p>
                        <h2 class="gl" onclick="returnToPage()">完成</h2>
                </header>
                <div class="xinz">
                    <p><a href="<%=path%>/userAddress/toAddAddressPage.html?addAddressJumpType=${addAddressJumpType}">新增收货地址</a></p>
                    <input id="defaultAddressId" style="display:none;" value=""/>
                </div>
               <input id="addAddressJumpTypeId" style="display: none" value="${addAddressJumpType}"/>
               <input id="manageAddressJumpTypeId" style="display: none" value="${manageAddressJumpType}"/>
                 <section class="pp">
                        <h1>您还没有收货地址</h1>
                        <p>请添加新地址</p>
                </section>
            </div>
        </div>
    </main>
    <script>
        function settingDefaultAddress(ids){
            $.post("/userAddress/settingDefaultAddress.do",{
                "id":ids
            },function(data){
                if (data){
                    $('p[id^="p_"]').removeClass("pon");
                    $("#p_"+ids+"").addClass("pon");
                    $("#defaultAddressId").val(ids);
                }else{
                    alert("设置默认地址失败");
                }
            })
        }
        function deleteAddress(id){
            var defaultAddressId = $("#defaultAddressId").val();
            if(confirm("确定要删除地址?")){
                $.post("/userAddress/deleteUserAddressById.do",{
                    "id":id,
                    "defaultAddressId":defaultAddressId
                },function(data){
                    if(data==-1){
                        //删除的不是默认地址
                        $("#selection_"+id+"").remove();
                    }else  if (data!=0&&data!=-1){
                        //删除的是默认地址，将最新的地址设置为默认地址
                        $("#selection_"+id+"").remove();
                        $("#p_"+data+"").addClass("pon");
                    }else{
                        //删除地址失败
                        alert("删除失败");
                    }
                    if ($(".sec1").length==0){
                        //显示没有收获地址的提示信息
                        $(".pp").css("display","-webkit-box");
                    }
                })
            }
        }
        function editAddress(id){
            var addAddressJumpType = $("#addAddressJumpTypeId").val();
            var manageAddressJumpType = $("#manageAddressJumpTypeId").val();
            window.location.href = "<%=path%>/userAddress/toEditAddress.html?id="+id+"&addAddressJumpType="+addAddressJumpType+"&manageAddressJumpType="+manageAddressJumpType;
        }
        function returnToPage(){
            var manageAddressJumpType = $("#manageAddressJumpTypeId").val();
            window.location.href ="<%=path%>/userAddress/manageAddressPageToChooseAddressPage.html?manageAddressJumpType="+manageAddressJumpType;
        }
    </script>
</body>
</html>