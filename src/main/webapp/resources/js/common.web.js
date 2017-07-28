/**
 * 数字变为中文函数
 * 
 * @param num
 * @returns {String}
 */
function num2Chinese(num){
	var units = new Array("","十","百","千","万","十万","百万","千万","亿","十亿","百亿","千亿","万亿");
	var numArray = new Array('零','一','二','三','四','五','六','七','八','九');

	var result = "";
	
	numStr = num.toString();
	for(var i = 0; i < numStr.length; i++){
		m = numStr[i];
		n = parseInt(m);
		isZero = n == 0;
		unit = units[(numStr.length - 1) - i];
		if(isZero) {
			if('0' == numStr[i - 1]) {
				continue;
			}else{
				result += numArray[n];
			}
		}else{
			result += numArray[n];
			result += unit;
		}
	}
	if(result[result.length - 1] ==  '零')
		result = result.substr(0,result.length - 1);
	if((result.length ==  2 || result.length == 3) && (result[0] == '一' && result[1] == '十')){
		result = result.substr(1, result.length - 1);
	}
	return result;
}

/**
 * 转换表单信息为 zzrw 表单信息
 * @param forms
 */
function transFormLines(forms){
	var formLines = [];
	
	for(i= 0; i< forms.length; i++){
		var formItem = forms[i];
		var id = "form"+i;
		
		var items = [];
		if("02" == formItem.eleType || "03" == formItem.eleType || "04" == formItem.eleType){
			for(j=0;j< formItem.loanValues.length; j++){
				var item = {id: j}
				item.name = formItem.loanValues[j].valueDesc;
				item.value = formItem.loanValues[j].value;
				
				items.push(item);
			}
		}
		
		if("01" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			var tip = formItem.eleDesc + "不可为空";
			var placeholder = formItem.eleDesc;
			
			var regex = null;
			var maxlength = "30";
			for(j=0;j< formItem.loanValues.length; j++){
				var item = {};
				item.name = formItem.loanValues[j].valueDesc;
				item.value = formItem.loanValues[j].value;
				
				if("placeholder" == item.value){
					placeholder = item.name;
				}
				
				if("regex" == item.value){
					regex = {
						regExp: item.name,
						param: '',
						dataType: '',
						onError: '请输入正确的'+label
					}
				}
				
				if("maxlength" == item.value){
					maxlength = item.value;
				}
			}
			
			var formLine = {	
				id: id,													// 输入input 的 id
				name: name,												// 输入input 的 name
				label: label,											// 描述数据的值
				tag: 'input',											// 输入的类型
				type: 'text',											// 子类型 text | password | email | phone
				placeholder: placeholder,								// 输入 input 的 placeholder
				cssclass: '',											// 输入内容添加的 CSS class
				validate:{},											// formValidator 的 formValidator配置
				input: {												// formValidator 的 inputValidator配置
					type: 'size',
					min: '1',
					onError: tip,
					onErrorMin: tip,
					empty: false
				},
				regex: regex,
				exts:[
					{"name": "maxlength", "value": maxlength}
				]
			}
			formLines.push(formLine);
			
		}else if("02" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			
			var formLine = {	
				id: id,
				name: name,
				label: label,
				tag: 'select',											// 输入类型为选择项，使用 tag = select | checkbox | radio
				type: '',												// type 值置空，不使用
				placeholder: '',										// placeholder 值置空，不使用
				cssclass: '',
				items:items,											// 可选择项列表
				validate:{},											// formValidator 的 formValidator配置
				input: {												// formValidator 的 inputValidator配置
					empty: false
				}
			}
			formLines.push(formLine);
			
		}else if("03" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			
			var formLine = {	
				id: id+"radio",											// 输入input 的 id
				name: name,												// 
				label: label,											// 描述数据的值
				tag: 'radio',											// 输入的类型
				value: '',
				type: '',												// 
				placeholder: name,										// 显示display 的值
				cssclass: '',											// 输入内容添加的 CSS class
				validate:{},											// formValidator 的 formValidator配置
				input: null,
				items:items
			}
			formLines.push(formLine);
			
		}else if("04" == formItem.eleType){
			var name = formItem.eleName;
			var label = formItem.eleDesc;
			
			var formLine = {	
				id: id,													// 输入input 的 id
				name: name,												// 
				label: label,											// 描述数据的值
				tag: 'checkbox',										// 输入的类型
				value: '',
				type: '',												// 
				placeholder: '',										// 显示display 的值
				cssclass: '',											// 输入内容添加的 CSS class
				validate:{},											// formValidator 的 formValidator配置
				input: null,
				hide:true,
				items:checkboxItems 
			}
			formLines.push(formLine);
			
		}else if("05" == formItem.eleType){
			
		}else{
			
		}
	}
	
	return formLines;
}

/**
 * 显示LOADING框(不可点击销毁)
 */
function showPersistLoading(){
	var mask = $('<div class="mask loading"></div>');
	
	var loadingImg = $('<img src="'+webRoot+'/resources/images/loading.gif"></img>');
	mask.append(loadingImg);
	$('body').append(mask);
	
	mask.css({
		"width" : $(window).width(),
		"height" : $(window).height(),
		"position" : 'absolute',
		"top" : '0',
		"left" : '0',
		'background' : '#333',
		'opacity': '0.5',
		"z-index" : "99998"
	});
	
	loadingImg.css({
		"position" : 'absolute',
		"top" : '50%',
		"left" : '50%',
		"margin-top" : "-50px",
		"margin-left" : "-50px"
	});
	
	mask.show();
}

/**
 * 显示LOADING框(可点击销毁)
 */
function showLoading(){
	var mask = $('<div class="mask loading"></div>');
	
	var loadingImg = $('<img src="'+webRoot+'/resources/images/loading.gif"></img>');
	mask.append(loadingImg);
	$('body').append(mask);
	
	mask.css({
		"width" : $(window).width(),
		"height" : $(window).height(),
		"position" : 'absolute',
		"top" : '0',
		"left" : '0',
		'background' : '#333',
		'opacity': '0.5',
		"z-index" : "99998"
	});
	
	loadingImg.css({
		"position" : 'absolute',
		"top" : '50%',
		"left" : '50%',
		"margin-top" : "-50px",
		"margin-left" : "-50px"
	});
	
	mask.show();
	
	mask.click(function(){
		$(".loading").remove();
	});
}

function removeLoading(){
	$(".loading").remove();
}

function showUploadTypeModal(){
	$("#uploadTypeModal").modal('show');
}

function hideUploadTypeModal(){
	$("#uploadTypeModal").modal('hide');
}

function showIsbnUploadModal(){
	hideUploadTypeModal();
	$("#isbnModal #isbnInput").val('');
	$("#isbnModal").modal("show");
}

/**
 * TIMESTAMP 转 yyyy-MM-dd hh:mm:ss
 */
function convertTimeToDate(unixTime) {
    
	unixTime = parseInt(unixTime) + parseInt(8) * 60 * 60 * 1000;

    var time = new Date(unixTime);
    var ymdhis = "";
    ymdhis += time.getUTCFullYear() + "-";
    if(time.getUTCMonth()+1 < 10){
    	ymdhis = ymdhis + "0" + (time.getUTCMonth()+1) + "-";
    }else{
    	ymdhis += (time.getUTCMonth()+1) + "-";
    }
    if(time.getUTCDate() < 10){
    	ymdhis = ymdhis + "0" + time.getUTCDate();
    }else{
    	ymdhis += time.getUTCDate();
    }
    if(time.getUTCHours() < 10){
    	ymdhis += " 0" + time.getUTCHours() + ":";
    }else{
    	ymdhis += " " + time.getUTCHours() + ":";
    }
    if(time.getUTCMinutes() < 10){
    	ymdhis += "0" + time.getUTCMinutes() + ":";
    }else{
    	ymdhis += time.getUTCMinutes() + ":";
    }
    if(time.getUTCSeconds() < 10){
    	ymdhis += "0" + time.getUTCSeconds();
    }else{
    	ymdhis += time.getUTCSeconds();
    }
    return ymdhis;
}
