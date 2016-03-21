(function() {
	window.comAreaJS = window.comAreaJS || {
			isAdd:true,
			isEdit:false,
			init:function(addOrEdit){
				switch(addOrEdit){
					case "add":
						comAreaJS.isAdd=true;
						comAreaJS.isEdit=false;
						break;
					case "edit":
						comAreaJS.isAdd=false;
						comAreaJS.isEdit=true;
						break;
					default:
						break;
				}
				comAreaJS.bindChangeEvent();
				if (comAreaJS.isAdd){
					comAreaJS.getAllProvinces();
				}else if (comAreaJS.isEdit){
					comAreaJS.getAllProvinces()?(comAreaJS.getCity()?(comAreaJS.getCounty()?true:false):false) :false;
				}
			},
			getAllProvinces:function(){
				var bl =true;
				$.ajax({
					type: "POST",
					url: "/comArea/queryAllProvince.do",
					async:false,
					dataType: "Json",
					success: function (result) {
						var jsonData = eval(result);
						var oldProvinceId = $("#oldProvinceId").val();
						if (jsonData!=null){
							var appendString ="<option value='-1'>--省份--</option>";
							$.each(jsonData,function(i,item){
								if(oldProvinceId!=null&&oldProvinceId!=""&&oldProvinceId==jsonData[i].id){
									appendString +='<option selected value='+jsonData[i].id+'>' +  jsonData[i].name + '</option>';
								}else{
									appendString +='<option value='+jsonData[i].id+'>' +  jsonData[i].name + '</option>';
								}

							})
							$("#s_province").empty();
							$("#s_province").html(appendString);
						}
					},
					error:function(){
						bl = false;
					}
				})
				return bl;
			},
			bindChangeEvent:function(){
				$("#s_province").bind("change",function(){
					comAreaJS.getCity();
					$("#s_county").empty();
					$("#s_county").html("<option value='-1'>--县/区--</option>");
				})
				$("#s_city").bind("change",function(){
					comAreaJS.getCounty();
				})
			},
			getCity:function(){
				var bl=true;
				var oldCityId = $("#oldCityId").val();
				$.ajax({
					type: "POST",
					async:false,
					url: "/comArea/queryCityByProviceId.do",
					data:"proviceId="+ $("#s_province").val(),
					dataType: "Json",
					success: function (result) {
						var jsonData = eval(result);
						if (jsonData!=null){
							var appendString ="<option value='-1'>--地级市--</option>";
							$.each(jsonData,function(i,item){
								if(oldCityId!=null&&oldCityId!=""&&oldCityId==jsonData[i].id){
									appendString +='<option selected value='+jsonData[i].id+'>' +  jsonData[i].name + '</option>';
								}else{
									appendString +='<option value='+jsonData[i].id+'>' +  jsonData[i].name + '</option>';
								}
							})
							$("#s_city").empty();
							$("#s_city").html(appendString);
						}
					},
					error:function(){
						bl = false;
					}
				})
				return bl;
			},
			getCounty:function(){
				var oldConntyId = $("#oldConntyId").val();
				$.ajax({
					type: "POST",
					url: "/comArea/queryCountyByCityId.do",
					data:"cityId="+ $("#s_city").val(),
					dataType: "Json",
					success: function (result) {
						var jsonData = eval(result);
						if (jsonData!=null){
							var appendString ="<option value='-1'>--县/区--</option>";
							$.each(jsonData,function(i,item){
								if(oldConntyId!=null&&oldConntyId!=""&&oldConntyId==jsonData[i].id){
									appendString +='<option selected value='+jsonData[i].id+'>' +  jsonData[i].name + '</option>';
								}else{
									appendString +='<option value='+jsonData[i].id+'>' +  jsonData[i].name + '</option>';
								}
							})
							$("#s_county").empty();
							$("#s_county").html(appendString);
						}
					}
				})
			}
		}
})();