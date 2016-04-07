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
                            $.each(jsonData, function (i, item) {
                                appendString += "<div class=\"sec1\" id=\"sec1_" + jsonData[i].id + "\">";
                                appendString += "<span><img src= '" + jsonData[i].cardImg + "' alt=\"\"></span>";
                                appendString += "<p><span><em>" + jsonData[i].bankName + "</em></span>";
                                appendString += " <span>持卡人:<b>" + jsonData[i].cardOwnerName + "</b>卡号:<b>" + jsonData[i].bankCard + "</b></span></p>";
                                appendString += "<h1 onclick=bankCardJS.deleteBankCard(" + jsonData[i].id + ") class=\"remove\"><img src=\"\\static\\images\\delete.png \" alt=\"\"></h1></div>";
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
            deleteBankCard: function (id) {
                if (confirm("确定要删除银行卡")) {
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
                }
            }
        }
})();