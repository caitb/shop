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
        $.post("/userAddress/getUserAddressByUserId.do",
                {
                },function(data) {
                    if(data!=null){
                        var jsonData=eval(data);
                        $.each(jsonData, function(i, item) {
                            var  address = "<selection class=\"sec1\" id=\"selection_"+jsonData[i].id+"\" >";
                            address +="<div><h2>";
                            address +=jsonData[i].name;
                            address +="</h2><h3>";
                            address +=jsonData[i].mobile;
                            address +="</h3><p><b>【默认】</b><span>";
                            address +=jsonData[i].provinceName +"  ";
                            address +=jsonData[i].cityName +"  ";
                            address +=  jsonData[i].regionName +"  ";
                            address +=  jsonData[i].address +"  ";;
                            address +="</span></p></div>";
                            address +=" <div class=\"setting\">";
                            if (jsonData[i].isDefault==1){
                                address +="<p class=\"pon\" onclick='settingDefaultAddress("+jsonData[i].id+")'  id=\"p_"+jsonData[i].id+"\" >默认地址<span></span></p>";
                            }else{
                                address +="<p id=\"p_"+jsonData[i].id+"\"  onclick='settingDefaultAddress("+jsonData[i].id+")'  >默认地址<span></span></p>";
                            }
                            address +="<h2 onclick='editAddress("+jsonData[i].id+")'>编辑地址<span></span></h2>";
                            address +="<h3 class=\"end\" onclick='deleteAddress("+jsonData[i].id+")' >删除地址</h3>";
                            address +="</div></section>";
                            $(".box").append(address);
                        })
                    }else{

                    }
                })

    })
</script>
<body>
   <main>
       <div class="wrap">
             
           <div class="box">
                <header class="xq_header">
                   <a href="guanli.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>管理收货地址</p>
                        <h2 class="gl">完成</h2>          
                </header>
                <div class="xinz">
                    <p><a href="<%=path%>/userAddress/toAddAddressPage.html">新增收货地址</a></p>
                </div>
                 <section class="pp">
                        <h1>您还没有收货地址</h1>
                        <p>请添加新地址</p>
                </section>
               <%-- <section class="sec1">
                   <div>
                        <h2>王平</h2>
                        <h3>18611536163</h3>
                        <p><b>【默认】</b><span>北京市 朝阳区 丰联广场A座809A</span></p>
                   </div>
                   <div class="setting">
                       <p class="pon">默认地址<span></span></p>
                       <h2>编辑地址<span></span></h2>
                       <h3 class="end">删除地址</h3>
                   </div>
                </section>
                <section class="sec1">
                   <div>
                        <h2>王平</h2>
                        <h3>18611536163</h3>
                        <p><b>【默认】</b><span>北京市 朝阳区 丰联广场A座809A</span></p>
                   </div>
                   <div class="setting">
                       <p>默认地址<span></span></p>
                       <h2>编辑地址<span></span></h2>
                       <h3 class="end">删除地址</h3>
                   </div>
                </section>--%>
            </div>
        </div>
    </main>
    <script>
/*        $(".setting").on("click","p",function(){
            $(".setting").find("p").removeClass("pon");
            $(this).addClass("pon")
        })*/
/*        $(".end").on("click",function(){
            $.post("/userAddress/getUserAddressByUserId.do",{

        },function(data){
             if (data == true){
            $(this).parents(".sec1").remove();
         }else{
            alert("删除失败");
             }
             })
        })*/
        function settingDefaultAddress(ids){
            $.post("/userAddress/settingDefaultAddress.do",{
                "id":ids
            },function(data){
                if (data){
                    $('p[id^="p_"]').removeClass("pon");
                    $("#p_"+ids+"").addClass("pon");
                }else{
                    alert("设置默认地址失败");
                }
            })
        }
        function deleteAddress(id){
            $.post("/userAddress/deleteUserAddressById.do",{
                "id":id
            },function(data){
                if (data){
                    $("#selection_"+id+"").remove();
                }else{
                    alert("删除失败");
                }
            })
        }
        function editAddress(id){
            window.location.href = "<%=path%>/userAddress/toEditAddress.html?id="+id;
        }
    </script>
</body>
</html>