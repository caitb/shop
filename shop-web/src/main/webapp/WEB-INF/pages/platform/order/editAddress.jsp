<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/xinjiandizhi.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
</head>
<script>
    function updateAddress() {
        var addressId = $("#addressId")[0].value;
        var name = $("#name")[0].value;
        var phone = $("#phone")[0].value;
        var postcode = $("#postcode")[0].value;
        var provinceId = $("#s_province").val();
        var provinceName = $("#s_province  option:selected").text();
        var cityId = $("#s_city").val();
        var cityName = $("#s_city  option:selected").text();
        var countyId = $("#s_county").val();
        var countyName = $("#s_county  option:selected").text();
        var detailAddress = $("#detailAddress")[0].value;
        var isDefault = $("#isDefaultId")[0].value;

        $.post("/userAddress/addOrUpdateAddress.do",
                {
                    "id":addressId,
                    "name":name,
                    "phone":phone,
                    "postcode":postcode,
                    "provinceId":provinceId,
                    "provinceName":provinceName,
                    "cityId":cityId,
                    "cityName":cityName,
                    "countyId":countyId,
                    "countyName":countyName,
                    "detailAddress":detailAddress,
                    "isDefault":isDefault,
                    "operateType":"update"
                },function(data) {
                    if (data == "success"){
                        window.location.href = "<%=path%>/userAddress/toManageAddressPage.html";
                    }
                });
    }
</script>
<body>
<main>
    <div class="wrap">
        <div class="box">
            <header class="xq_header">
                <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>编辑收货地址</p>
            </header>
            <div class="sf">
                收货人姓名
                <input type="text" id="addressId" style="display:none;" value="${comUserAddress.id}">
                <input type="text" id="isDefaultId" style="display:none;" value="${comUserAddress.isDefault}">
                <input type="text" id="name" value="${comUserAddress.name}">
            </div>
            <div class="sf">
                手机号码
                <input type="tel" id="phone" value="${comUserAddress.mobile}">
            </div>
            <div class="sf">
                邮政编码
                <input type="tel" id="postcode" value="${comUserAddress.zip}">
            </div>
            <div class="address">
                联系地址
                <select id="s_province" name="s_province"></select>
                <select id="s_city" name="s_city" ></select>
                <select id="s_county" name="s_county"></select>
            </div>
            <div class="sf">
                详细地址
                <input type="text" id="detailAddress" value="${comUserAddress.address}">
            </div>

        </div>
        <a href="" onclick="updateAddress()" class="baocun">
            保存
        </a>
    </div>
</main>
<script>
    var categories = window.eval('(' + '${comAreas}' + ')');
    var c1 = {};//一级类别
    var c2 = {};//二级类别
    var c3 = {};//三级类别
    c1['sub'+0] = [];
    for(var i in categories){
        if(categories[i].level == 0){
            if (categories[i].pid==1){
                c1['sub'+0].push(categories[i]);
            }
            c2['sub'+categories[i].id] = [];
            for(var sub in categories){
                if(categories[sub].pid == categories[i].id) c2['sub'+categories[i].id].push(categories[sub]);
            }
            for(var sub in c2['sub'+categories[i].id]){
                c3['sub'+c2['sub'+categories[i].id][sub].id] = [];
                for(var ss in categories){
                    if(categories[ss].pid == c2['sub'+categories[i].id][sub].id) c3['sub'+c2['sub'+categories[i].id][sub].id].push(categories[ss]);
                }
            }
        }
    }
    var $skuC1 = $('#s_province');
    var $skuC2 = $('#s_city');
    var $skuC3 = $('#s_county');
    $skuC1.html("<option value=\'-1\'>省份</option>");
    for(var sub in c1['sub'+0]){
        if (c1['sub'+0][sub].id == ${comUserAddress.provinceId}){
            $skuC1.append('<option selected value="' + c1['sub'+0][sub].id + '">' + c1['sub'+0][sub].name + '</option>');
        }else{
            $skuC1.append('<option value="' + c1['sub'+0][sub].id + '">' + c1['sub'+0][sub].name + '</option>');
        }
    }
    var  id = ${comUserAddress.provinceId};
    for(var sub in c2['sub'+id]){
        if (c2['sub'+id][sub].id ==${comUserAddress.cityId}){
            $skuC2.append('<option selected value="'+ c2['sub'+id][sub].id +'">'+ c2['sub'+id][sub].name+'</option>');
        }else{
            $skuC2.append('<option value="'+ c2['sub'+id][sub].id +'">'+c2['sub'+id][sub].name+'</option>');
        }
    }
    var cityId = ${comUserAddress.cityId}
    for(var sub in c3['sub'+cityId]){
        if ( c3['sub'+cityId][sub].id == ${comUserAddress.regionId}){
            $skuC3.append('<option selected value="'+ c3['sub'+cityId][sub].id +'">'+ c3['sub'+cityId][sub].name+'</option>');
        }else{
            $skuC3.append('<option value="'+ c3['sub'+cityId][sub].id +'">'+ c3['sub'+cityId][sub].name+'</option>');
        }
    }
    $skuC1.change(function(){
        $skuC2.empty().html('<option value="-1">地级市</option>');
        $skuC3.empty().html('<option value="-1">县/区</option>');
        for(var sub in c2['sub'+$(this).val()]){
            $skuC2.append('<option value="'+ c2['sub'+$(this).val()][sub].id +'">'+ c2['sub'+$(this).val()][sub].name+'</option>');
        }
    });
    $skuC2.change(function(){
        $skuC3.empty().html('<option value="-1">县/区</option>');
        for(var sub in c3['sub'+$(this).val()]){
            $skuC3.append('<option value="'+ c3['sub'+$(this).val()][sub].id +'">'+ c3['sub'+$(this).val()][sub].name+'</option>');
        }
    });
</script>
</body>
</html>