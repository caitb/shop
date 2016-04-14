<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<footer>
    <div>
        <p class="active" onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
            <span><img src="<%=path%>/static/images/footer_x%20(3).png" alt=""></span>
            <span>首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${shopId}');">
            <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
            <span>分享计划</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/borderManagement.html');" >
            <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
<script>
    var oAs = document.getElementsByTagName('a');
    for(var i in oAs){
        oAs[i].onclick = function(){
            for(var j in oAs){
                oAs[j].class = '';
            }

            oAs[i].class = 'active';
        }
    }
</script>