<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/material/productimage.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:history.go(-1)"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>素材图片</p>
    </header>
    <main id="divall">
        <c:forEach var="mat" items="${materials}">
            <div class="floor">
                <h1>${mat.title}</h1>
                <div class="sec1" id="imagelist">
                    <c:forEach items="${mat.comSkuMaterialItems}" var="img">
                        <a href="#"><img data="${img.fileUrl}" /></a>
                    </c:forEach>
                </div>
                <p>${mat.content}</p>
            </div>
        </c:forEach>
    </main>
    <div class="nobady" style="display: none">
        <img src="${path}/static/images/material/nodady.png" alt="">
        <p>暂无上传素材</p>
    </div>
    <img src="${path}/static/images/material/FAB.png" alt="" onclick="clickShow()">
    <c:if test="${countSkuMaterial>3}">
        <div class="downloading"><img src="${path}/static/images/material/downloading.png" alt=""></div>
    </c:if>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="b_t">
        <h1>亲爱的代理，</h1>

        <p>
            线下素材的图片像素比较大，请您留下您的邮箱地址，系统稍后会将线下素材的下载链接发到您的邮箱，请注意查收！
        </p>
        <input type="text" placeholder="请输入邮箱地址" id="email">
        <button onclick="saveEmail()">留下邮箱</button>
        <b class="off" onclick="clickHide()">×</b>
    </div>
</div>
<div class="bigphp">
    <div class="backb"></div>
    <div class="b_p">
        <img src="" alt="">
    </div>
    <b class="off" onclick="bigphpHide()">×</b>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script>
    function clickShow(){
        $(".black").show();
    }
    function clickHide(){
        $(".black").hide();
    }
    function bigphpHide(){
        $(".bigphp").hide();
    }
    $(".sec1 img").on("click",function(){
        var imgSrc=$(this).attr("src");
        $(".bigphp").show();
        $(".bigphp img").attr("src",imgSrc);
    })
    //判断浏览器
    var Browser=new Object();
    Browser.userAgent=window.navigator.userAgent.toLowerCase();
    Browser.ie=/msie/.test(Browser.userAgent);
    Browser.Moz=/gecko/.test(Browser.userAgent);

    function Imagess(url,imgid,callback){
        var val=url;
        var img=new Image();
        if(Browser.ie){
            img.onreadystatechange =function(){
                if(img.readyState=="complete"||img.readyState=="loaded"){
                    callback(img,imgid);
                }
            }
        }else if(Browser.Moz){
            img.onload=function(){
                if(img.complete==true){
                    callback(img,imgid);
                }
            }
        }
        //如果因为网络或图片的原因发生异常，则显示该图片
        img.onerror=function(){img.src="${path}/static/images/material/Failed.png"}
        img.src=val;
    }
    //显示图片
    function checkimg(obj,imgid){
        document.getElementById(imgid).style.cssText="";
        document.getElementById(imgid).src=obj.src;
    }
    window.onload=function(){
        var imageList=$("#imagelist");
        $(".sec1 img").each(function(i,n){
            $(".floor").find("img").eq(i).attr("id","img0"+i);
            $(".floor").find("img").eq(i).attr("src","${path}/static/images/material/imgloading.gif");
            Imagess($(".floor").find("img").eq(i).attr("data"),$(".floor").find("img").eq(i).attr("id"),checkimg);
        })
    }
    var pagelimit = 2;
    $(".downloading").on("click", function () {
        var _contain = $("#divall");
        var pageData = {};
        pageData.mgId = '${mgId}';
        pageData.currentPage = pagelimit;
        $.ajax({
            type: 'post',
            url: '${path}/materielList/materialInfoBPage',
            data: pageData,
            dataType: 'json',
            success: function(data){
                if(data.materials==null || data.materials.length <=0){
                    alert("没有更多了");
                }
                if(data.isError==false && data.materials.length>0){
                    $.each(data.materials, function (i, mat) {
                        var imgHtml ="";
                        $.each(mat.comSkuMaterialItems, function (i, img){
                            imgHtml += "<a href=\"#\"><img data=\""+img.fileUrl+"\" /></a>";
                        });
                        _contain.append("<div class=\"floor\">" +
                        "<h1>"+mat.title+"</h1>" +
                        "<div class=\"sec1\" id=\"imagelist\">"+imgHtml+"</div>" +
                        "<p>"+mat.content+"</p>" +
                        "</div>");
                    });
                    pagelimit++;
                }
            }
        });
    })

    $(function(){
        var bWidth=$(".sec1").width()/3-5;
        $(".sec1 a").css({
            "display":"inline-block",
            "width":bWidth+'px',
            "height":bWidth+'px'
        })
    })
    function saveEmail() {
        var email = $("#email").val();
        var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
        if (email == null || email == "") {
            alert("邮箱不能为空");
            return false;
        }
        if (!reg.test(email)) {
            alert("请输入正确的邮箱格式");
            return false;
        }
        $.ajax({
            url: '${basePath}materielApply/addEmail.do',
            type: 'post',
            data: {email: email},
            dataType: 'json',
            success: function (data) {
                if (data.isError == false) {
                    alert("邮箱上传成功，请注意查收邮件");
                    $(".black").hide();
                }
            }
        });
    }
</script>
</body>
</html>