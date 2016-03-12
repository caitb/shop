(function() {
	window.addressJS = window.addressJS || {
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
					"operateType":"update"
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