<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/xinjianka.css">
    <link rel="stylesheet" href="${path}/static/css/loading.css">
</head>
<body>
<input type="hidden" id="userId" name="userId" value="${userId}"/>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:history.back(-1)" onClick=""><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>添加银行卡</p>
    </header>
    <main>
        <p>新增银行卡信息</p>
        <input id="returnJumpTypeId" value="${returnJumpType}" style="display: none" />
        <h1>银行卡号：<input type="text" id="bankcard" placeholder="填写您的卡号"></h1>
        <h1>银行名称：<select id="bankid" name="bankid">
            <option value="">请选择银行</option>
            <c:forEach var="bank" items="${bankList}">
                <option value="${bank.id}">${bank.bankName}</option>
            </c:forEach>
        </select></h1>
        <h1>开户行名称 ：<input type="text" id="depositbankname" placeholder="输入您的开户行名称"></h1>
        <h1>持卡人姓名：<input type="text" id="cardownername" placeholder="输入持卡人姓名"></h1>
        <botton onclick="submitClick()">
            提交
        </botton>
    </main>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/commonAjax.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script>
    var path = "${path}";
    var basePath = "${basePath}";
    <%--function backLastPage(){--%>
        <%--fullShow();//跳转页面钱展示全屏遮罩loading...--%>
        <%--window.location.href="<%=basePath%>extractwayinfo/findExtractwayInfo.shtml";--%>
    <%--}--%>
    function submitClick() {
        var bankcard = $("#bankcard").val();
        var bankid = $("#bankid").val();
        var depositbankname = $("#depositbankname").val();
        var cardownername = $("#cardownername").val();
        if (bankcard == "") {
            alert("请输入银行卡号");
            return;
        }
        if(bankid == ""){
            alert("请选择银行名称");
            return;
        }
        if (depositbankname == "") {
            alert("请输入开户行名称");
            return;
        }
        if (cardownername == "") {
            alert("请输入持卡人姓名");
            return;
        }
        if(luhmCheck(bankcard)==false){
            alert("请输入正确银行卡号");
            return;
        }
        var returnJumpType = $("#returnJumpTypeId").val();
        $.ajax({
            type:"POST",
            async:false,
            url : basePath + "extractwayinfo/add.do",
            data:{bankcard:bankcard,bankid:bankid,depositbankname:depositbankname,cardownername:cardownername,returnJumpType:returnJumpType},
            dataType:"Json",
            success:function(data){
                if(data.isTrue == "false"){
                    alert(data.message);
                }else {
                    fullShow();//跳转页面钱展示全屏遮罩loading...
                    if (data.returnJumpType == 0){
                        window.location.href = basePath + "extractwayinfo/findExtractwayInfo.shtml";
                    }else if (data.returnJumpType == 1){
                        window.location.href = basePath + "personalInfo/toBankCardPage.html";
                    }
                }
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }

    /**
     * 校验银行卡号是否正确
     * @param bankno
     * @returns {boolean}
     */
    function luhmCheck(bankno){
        if (bankno.length < 16 || bankno.length > 19) {
            //$("#banknoInfo").html("银行卡号长度必须在16到19之间");
            return false;
        }
        var num = /^\d*$/;  //全数字
        if (!num.exec(bankno)) {
            //$("#banknoInfo").html("银行卡号必须全为数字");
            return false;
        }
        //开头6位
        var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
        if (strBin.indexOf(bankno.substring(0, 2))== -1) {
            //$("#banknoInfo").html("银行卡号开头6位不符合规范");
            return false;
        }
        var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

        var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
        var newArr=new Array();
        for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
            newArr.push(first15Num.substr(i,1));
        }
        var arrJiShu=new Array();  //奇数位*2的积 <9
        var arrJiShu2=new Array(); //奇数位*2的积 >9

        var arrOuShu=new Array();  //偶数位数组
        for(var j=0;j<newArr.length;j++){
            if((j+1)%2==1){//奇数位
                if(parseInt(newArr[j])*2<9)
                    arrJiShu.push(parseInt(newArr[j])*2);
                else
                    arrJiShu2.push(parseInt(newArr[j])*2);
            }
            else //偶数位
                arrOuShu.push(newArr[j]);
        }

        var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
        var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
        for(var h=0;h<arrJiShu2.length;h++){
            jishu_child1.push(parseInt(arrJiShu2[h])%10);
            jishu_child2.push(parseInt(arrJiShu2[h])/10);
        }

        var sumJiShu=0; //奇数位*2 < 9 的数组之和
        var sumOuShu=0; //偶数位数组之和
        var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
        var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
        var sumTotal=0;
        for(var m=0;m<arrJiShu.length;m++){
            sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
        }

        for(var n=0;n<arrOuShu.length;n++){
            sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
        }

        for(var p=0;p<jishu_child1.length;p++){
            sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
            sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
        }
        //计算总和
        sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);

        //计算Luhm值
        var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;
        var luhm= 10-k;

        if(lastNum==luhm){
            $("#banknoInfo").html("Luhm验证通过");
            return true;
        }
        else{
            $("#banknoInfo").html("银行卡号必须符合Luhm校验");
            return false;
        }
    }
</script>
</body>
</html>