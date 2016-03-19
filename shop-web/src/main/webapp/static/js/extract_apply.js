$(function(){
    $("#extractBtnId").on("click", function(){
        alert("sddf" + $("#extractMoneyId").val());
    });
});

function choiceBank(){
    window.location.href = basepath + "extractwayinfo/findByUserId.do";
}