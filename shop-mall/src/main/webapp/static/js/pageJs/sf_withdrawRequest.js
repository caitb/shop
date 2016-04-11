/**
 * 确认提现
 */
function withdraw(userId){
    var extractableFee = $("#extractableFee").val();
    var inputAccount = $("#inputAccount").val();
    if (inputAccount > extractableFee){
        alert("输入金额大于可提现金额");
    }else {

    }
    alert(extractableFee);
    alert(inputAccount);
}