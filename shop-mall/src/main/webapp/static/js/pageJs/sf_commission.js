/**
 * 用户提现
 */
function withdraw(userId,extractableFee){
    alert(extractableFee);
    if (extractableFee == 0){
        alert("暂无可提现额度");
        return;
    }
    //判断该用户是否已经绑定
    $.ajax({
        type:"POST",
        async:true,
        url : basepath+"/sfuser/checkBinding.do",
        data:{userId:userId},
        dataType:"Json",
        success:function(data){
            if(data.isTrue == "false"){
                alert(data.message);
            }else {

            }
        },
        error: function(){
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}