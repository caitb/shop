(function() {
    window.addressJS = window.addressJS || {
            patrn:/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
            initBlur: function(){
                $(".name").on("blur",function(){
                    addressJS.checkName();
                })
                $(".tel").on("blur",function(){
                    addressJS.checkPhone();
                })
                $(".dizhi").on("focus",function(){
                    addressJS.checkDetailAddress();
                })
            },
            checkName:function(){
                if ($(".name").val()==""){
                    $(".tishi").show().html("姓名不能为空");
                    $(".name").focus();
                    return false;
                }
                if (!isCardName($(".name").val())){
                    $(".tishi").show().html("姓名格式错误")
                    $(".name").focus().css("color","red");
                    return false;
                }else{
                    $(".tishi").hide();
                }
                //检验汉字
                function isChinese(s){
                    var patrn = /^\s*[\u4e00-\u9fa5]{1,15}\s*$/;
                    if(!patrn.exec(s))
                    {
                        return false;
                    }
                    return true;
                }
                //检验姓名：姓名是2-15字的汉字
                function isCardName(s){
                    var patrn = /^\s*[\u4e00-\u9fa5]{1,}[\u4e00-\u9fa5.·]{0,15}[\u4e00-\u9fa5]{1,}\s*$/;
                    if(!patrn.exec(s))
                    {
                        return false;
                    }
                    return true;
                }
            },
            checkPhone:function(){
                if ($(".tel").val()==""){
                    $(".tishi").show().html("手机号不能为空")
                    $(".tel").focus();
                    return false;
                }
                if (!addressJS.isMobile($(".tel").val()))
                {
                    $(".tishi").show().html("手机号输入格式有误");
                    $(".tel").focus();
                    $(".tel").focus().css("color","red");
                    return false;
                }else{
                    $(".tishi").hide()
                }
            },
            isMobile:function(s){
                if(!addressJS.patrn.exec(s))
                {
                    return false;
                }
                return true;
            },
            checkDetailAddress:function(){
                var cityId = $("#s_city").val();
                var provinceId = $("#s_province").val();
                var countyId = $("#s_county").val();
                var bl = true;
                if (provinceId==-1||provinceId==null||provinceId==""){
                    bl = false;
                }
                if (cityId==-1||cityId==null||cityId==""){
                    bl = false;
                }
                if (countyId==-1||countyId==null||countyId==""){
                    bl = false;
                }
               if (!bl){
                   $(".tishi").show().html("省市区不能为空");
               }else{
                   $(".tishi").hide();
               }
            },
            getJsonParam:function(){
                var addressId = $("#addressId").val();
                var name = $("#name").val();
                var phone = $("#phone").val();
                var postcode = $("#postcode").val();
                var provinceId = $("#s_province").val();
                var provinceName = $("#s_province  option:selected").text();
                var cityId = $("#s_city").val();
                var cityName = $("#s_city  option:selected").text();
                var countyId = $("#s_county").val();
                var countyName = $("#s_county  option:selected").text();
                var detailAddress = $("#detailAddress").val();
                var isDefault = $("#isDefaultId").val();
                var operateType = $("#operateTypeId").val();

                var paramJson = {
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
                    "operateType":operateType
                }
                return paramJson;
            },
            validateAddressInfo:function (paramJson){
                if (paramJson.name==null||paramJson.name==""){
                    alert("姓名不能为空");
                    return false;
                }
                if (paramJson.phone==null||paramJson.phone==""){
                    alert("手机号不能为空");
                    return false;
                }else if(!checkPhone(paramJson.phone)){
                    alert("手机号格式不对");
                    return false;
                }
                if(paramJson.postcode==null||paramJson.postcode==""){
                    alert("邮政编码不能为空");
                    return false;
                }
                if (paramJson.provinceId==-1||paramJson.provinceId==null||paramJson.provinceId==""){
                    alert("请选择省份");
                    return false;
                }
                if (paramJson.cityId==-1||paramJson.cityId==null||paramJson.cityId==""){
                    alert("请选择地级市");
                    return false;
                }
                if (paramJson.countyId==-1||paramJson.countyId==null||paramJson.countyId==""){
                    alert("请选择县区");
                    return false;
                }
                return true;
            }
        }
})();