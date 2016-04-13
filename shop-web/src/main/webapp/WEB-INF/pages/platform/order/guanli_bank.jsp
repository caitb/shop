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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/guanli.css">
</head>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<body>
   <main>
       <div class="wrap">
             
           <div class="box">
                <header class="xq_header">
                   <a href="zhifu.html"><img src="<%=path%>/static/images/xq_rwo0qwt.png" alt=""></a>
                        <p>管理收货地址</p>            
                </header>
                <a href="<%=path%>/userAddress//toAddAddressPage.html" class="tianjia">
                    添加新地址
                </a>
                 <section class="pp">
                        <h1>您还没有收货地址</h1>
                        <p>请添加新地址</p>
                </section>
<%--                <section class="sec1">
                   <div>
                        <a href="#"><h2><b>王平</b> <span>18611536163</span></h2></a>
                        <a href="#"><p class="on"><b>【默认】</b><span>北京市 朝阳区 丰联广场A座809A</span></p></a>
                   </div>
                </section>
                <section class="sec1">
                   <div>
                        <a href="#"><h2><b>王平</b> <span>18611536163</span></h2></a>
                        <a href="#"><p><b>【默认】</b><span>北京市 朝阳区 丰联广场A座809A</span></p></a>
                   </div>
                </section>--%>
            </div>
            
        </div>
    </main>
    <script>
        $(document).ready(function () {
            $.post("/userAddress/getUserAddressByUserId.do",
                    {
                    },function(data) {
                        if(data!=null){
                            $(".pp").hide();
                            var jsonData=eval(data);
                            $.each(jsonData, function(i, item) {
                                var  address = "<section class=\"sec1\" onclick='selectClick("+i+")'>";
                                address +="<div><a href=\"#\"><h2><b>";
                                address +=jsonData[i].name;
                                address +="</b> <span>";
                                address +=jsonData[i].mobile;
                                address +="</span></h2></a> <a href=\"#\">";
                                if (jsonData[i].isDefault==1){
                                    address +="<p  id='"+i+"'  class=\"on\">";
                                }else{
                                    address +="<p id='"+i+"'>";
                                }
                                address +="<b>【默认】</b><span>";
                                address +=jsonData[i].provinceName +"  ";
                                address +=  jsonData[i].regionName +"  ";
                                address +=  jsonData[i].address +"  ";;
                                address +="</span></p></a></div></section>";
                                $(".box").append(address);
                            });
                        }else{

                        }

                    });
        })
    </script>
<script>
    function selectClick(i){
        $(".sec1").find("p").removeClass("on");
        $(this).find("p").attr("class","on");
        $("#"+i+"").attr("class","on");
       // $(this).find("p").addClass("on");
    }
/*    $(".sec1").on("click",function(){
        alert("11111");
        $(".sec1").find("p").removeClass("on")
        $(this).find("p").addClass("on")
    })*/
</script>
</body>
</html>