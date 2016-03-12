/**
 *
 */
(function() {
	window.comAreaJS = window.comAreaJS || {
			queryComArea: function () {
				alert("aaaaa");
				alert(${comAreas});
				var categories = window.eval('(' + '${comAreas}' + ')');
				var c1 = {};//一级类别
				var c2 = {};//二级类别
				var c3 = {};//三级类别
				alert("aaaa");
				c1['sub'+0] = [];
				for(var i in categories){
					if(categories[i].level == 1){

						c1['sub'+0].push(categories[i]);

						c2['sub'+categories[i].id] = [];
						for(var sub in categories){
							if(categories[sub].pid == categories[i].id) c2['sub'+categories[i].id].push(categories[sub]);
						}

						for(var sub in c2['sub'+categories[i].id]){
							c3['sub'+c2['sub'+categories[i].id][sub].id] = [];
							for(var ss in categories){
								if(categories[ss].pid == c2['sub'+categories[i].id][sub].id) c3['sub'+c2['sub'+categories[i].id][sub].id].push(categories[ss]);
							}
						}

					}
				}
				var $skuC1 = $('#s_province');
				var $skuC2 = $('#s_city');
				var $skuC3 = $('#s_county');

				$skuC1.html('<option value="-1">请选择</option>');
				for(var sub in c1['sub'+0]){
					$skuC1.append('<option value="' + c1['sub'+0][sub].id + '">' + c1['sub'+0][sub].name + '</option>');
				}

				$skuC1.change(function(){
					$skuC2.empty().html('<option value="-1">请选择</option>');
					$skuC3.empty().html('<option value="-1">请选择</option>');

					for(var sub in c2['sub'+$(this).val()]){
						$skuC2.append('<option value="'+ c2['sub'+$(this).val()][sub].id +'">'+ c2['sub'+$(this).val()][sub].name+'</option>');
					}
				});

				$skuC2.change(function(){
					$skuC3.empty().html('<option value="-1">请选择</option>');

					for(var sub in c3['sub'+$(this).val()]){
						$skuC3.append('<option value="'+ c3['sub'+$(this).val()][sub].id +'">'+ c3['sub'+$(this).val()][sub].name+'</option>');
					}
				});
			}
		}
})();