(function () {
  $.definedAlertWindow = {
    Alert: function (msg,path) {
      GenerateHtml(msg,path);
    }
  }
  //生成Html
  var GenerateHtml = function (msg,path) {
      alert(window.location.host);
    var _html = "";
    _html += '<div class="alert"><h1><img src="'+path+'static/images/alert.png" alt="">提示</h1>';
      alert(_html);
    _html += '<h2>' + msg + '</h2></div>';
    $("body").append(_html);
      hideWindow();
  },
    hideWindow = function(){
        $('.alert').fadeIn("slow").delay(3000).fadeOut("slow");
    }
})();