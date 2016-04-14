<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<footer>
    <div>
        <a href="<%=basePath%>index">
            <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
            <span>首页</span>
        </a>
        <a class="active" href="<%=basePath%>shop/manage/index">
            <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
            <span>我的店铺</span>
        </a>
        <a href="<%=basePath%>personalInfo/personalHomePageInfo.html">
            <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
            <span>个人中心</span>
        </a>
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