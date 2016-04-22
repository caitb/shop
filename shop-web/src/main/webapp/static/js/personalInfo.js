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
                $("#identityAuthId").bind("click",function(){
                    if($("#auditStatusId").val() == 1){
                        alert("您的实名认证正在审核，请耐心等待");
                    }else{
                        validateCodeJS.applyTrial("identityAuth");
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
        personalInfoJS.init();
        validateCodeJS.initPage();
    })
})();