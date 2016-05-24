(function () {
    window.personalInfoJS = window.personalInfoJS || {
            basePath : "http://"+ window.location.host,
            init:function(){
                personalInfoJS.initClick();
            },
            initClick:function(){
                /*$("#weChatNumberId").bind("click",function(){
                    window.location.href = personalInfoJS.basePath + "/personalInfo/selectSkuWeChatInfo.do";
                })*/
                $("#bindPhoneId").bind("click",function(){
                    if ($("#mobileId").val()==null||$("#mobileId").val()==""){
                        validateCodeJS.applyTrial("personalInfo");
                    }
                })

                $("#identityAuthId").bind("click",function(){
                    if ($("#mobileId").val()==null||$("#mobileId").val()==""){
                        alert("请先绑定手机号");
                        return false;
                    }
                    if($("#auditStatusId").val() == 1){
                        alert("您的实名认证正在审核，请耐心等待");
                    }else{
                        window.location.href = personalInfoJS.basePath + "/identityAuth/toInentityAuthPage.html?returnPageIdentity=0&auditStatus="+$("#auditStatusId").val();
                    }
                })

                $("#bankCardId").bind("click",function(){
                    window.location.href = personalInfoJS.basePath + "/personalInfo/toBankCardPage.html";
                })
                $("#capitalId").bind("click",function(){
                    window.location.href = personalInfoJS.basePath + "/account/home";
                })
                $("#addressManageId").bind("click",function(){
                    window.location.href =personalInfoJS.basePath + "/userAddress/toManageAddressPage.html?manageAddressJumpType=1&addAddressJumpType=1";
                })
            }
        }
    $(document).ready(function(){
        validateCodeJS.initPage();
        personalInfoJS.init();
    })
})();