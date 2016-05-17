(function () {
    $(document).ready(function () {
        bankCardJS.getBankCardInfo();
    })
    window.bankCardJS = window.bankCardJS || {
            getBankCardInfo: function () {
                $.ajax({
                    type: "POST",
                    url: "/personalInfo/findBankCardInfoByUserId.do",
                    async: false,
                    dataType: "Json",
                    success: function (result) {
                        var jsonData = eval(result);
                        if (jsonData != null) {
                            var appendString = "";
                            var bankCard = "";
                            $.each(jsonData, function (i, item) {
                                bankCard = jsonData[i].bankCard;
                                appendString += "<div class=\"sec1\" id=\"sec1_" + jsonData[i].id + "\">";
                                appendString += "<span><img src= '" + jsonData[i].cardImg + "' alt=\"\"></span>";
                                appendString += "<p><span><em>" + jsonData[i].bankName + "</em></span>";
                                appendString += " <span>持卡人:<b>" + jsonData[i].cardOwnerName + "</b>卡号:<b>" + bankCard.substr(0,4);
                                appendString += "***********";
                                appendString += bankCard.substr(bankCard.length -4)+"</b></span></p>";
                                appendString += "<h1 onclick=bankCardJS.showDeleteDialog(" + jsonData[i].id + ") class=\"remove\"><img src=\"\\static\\images\\delete.png \" alt=\"\"></h1></div>";
                            })
                            $("#chooseBankCardId").empty();
                            $("#chooseBankCardId").append(appendString);
                        }
                    },
                    error: function () {
                        alert("查询银行卡信息失败");
                    }
                })
            },
            showDeleteDialog: function (id) {
                $("#confirmBankCardId").val(id);
                $(".back").show();
                $(".back_que").show();
            },
            hideDeleteDialog: function () {
                $(".back_que").hide();
                $(".back").hide();
            },
            deleteBankCard:function(){
                var id =  $("#confirmBankCardId").val();
                $.ajax({
                    type: "POST",
                    url: "/personalInfo/deleteBankCardInfoById.do",
                    data: "id="+id,
                    dataType: "text",
                    success: function (result) {
                        if (result == "true") {
                            $("#sec1_" + id).remove();
                        }
                    },
                    error: function () {
                        alert("删除银行卡失败");
                    }
                })
                $(".back_que").hide();
                $(".back").hide();
            }
        }
})();