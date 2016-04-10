/** 检查手机，邮箱格式的js */

/**
 * 检验手机格式
 */
function checkPhone(phone) {
    var tphone = /^1(([3][0-9])|([4][7])|([5][01236789])|([8][0256789]))[0-9]{8}$/;
    var partten = /^\d{7,8}$/;

    if (phone == null || phone == "") {
        return false;
    }

    if (tphone.test(phone) == false) {
        return false;
    }
    return true;
}

/**
 * 检验邮箱格式
 * @param mail
 * @returns {Boolean}
 */
function checkMail(mail) {
    var myreg = /^([a-za-z0-9]+[_|_|.]?)*[a-za-z0-9]+@([a-za-z0-9]+[_|_|.]?)*[a-za-z0-9]+.[a-za-z]{2,3}$/;
    if (mail == null || mail == "") {
        return false;
    }
    if (!myreg.test(mail)) {
        return false;
    }
    return true;
}

function checkNumber(num) {
    var reg = /^\d+$/;
    if (num == null || num == "") {
        return false;
    }
    if (!reg.test(num)) {
        return false;
    }
    return true;
}
//判断长度
function checkLength(str, length) {
    var flag = true;
    if (str.length > length) {
        flag = false;
    }
    return flag;
}
//判断是否是英文和数字
function isWordAndNum(str) {
    var patern = /[^0-9a-zA-Z]/;
    return patern.test(str);
}
//得到字符串的长度
function getStrLen(str){
    var len = 0;
    if (str!=null && str!=""){
        for (var i = 0 ; i < str.length; i++){
            var  c = str.charCodeAt(i);
            if (c>127||c==94){
                len += 2;
            }else{
                len++;
            }
        }
    }
    return len;
}