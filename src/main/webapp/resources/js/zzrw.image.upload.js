	/**
	 * 图片压缩上传
	 * @param form 表单对象，如 var form = document.getElementById("logoUploadForm");
	 * @param id <input type=file>标签的id
	 * @param url 上传地址，如 var url ="${rc.contextPath}/mchnt/picUpload";
	 * @param height 等比例压缩至指定高度，如
	 * @returns
	 */
	function imageUpload(form,id,url,height){
		var e = document.getElementById(id);
		if(e.files.length=="0"){
			return zzrw.alert("未选择图片！");
		}
		var count = 0;
		var successCount = 0;
		var failCount = 0;
		var size = 480;
		if(height!=""){
			size = height;
		}
		zzrw.load('上传中...');
		for(var i= 0;i<e.files.length;i++){
			var file = e.files[i];
			var FR=new FileReader();
			
			var Orientation = null;  
			//Orientation = getOrientation(file);
			//获取照片方向角属性，用户旋转控制  - 针对苹果手机
			if (navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)) {
				EXIF.getData(file, function() {  
					Orientation = EXIF.getTag(this, 'Orientation');  
					//alert("Orientation="+Orientation);
				});  
			}
			
			FR.readAsDataURL(file);
			//alert(i);   onloadend
			FR.onload=function(file){
				compressImg(this.result,size,Orientation,function(data){
					var blob = dataURLtoBlob(data);
			
    				var fd = new FormData(form);//普通form转为formdata
    				fd.append("newImg", blob, "image.png");//压缩后的blob追加到formdata
					
					$.ajax({
                        type:'POST',
                        url:url,
                        data : fd,
        				processData : false,
        				contentType : false,
        				success : function(data){
							count++;
							if(data=="success"){
								successCount++;
							}else{
								failCount++;
							}
							if(count==e.files.length){
								 if(count==successCount){
								 	$("#loadModal").modal('hide');
								 	zzrw.alert("图片上传成功！图片通过审核前仅对自己可见！",function(){
										window.location.reload();
									});
								 }else{
								 	$("#loadModal").modal('hide');
								 	zzrw.alert(successCount+"张图片上传成功，"+failCount+"张图片上传失败！图片通过审核前仅对自己可见！");
								 }
							}
                         } 
         			})	
					
				})
			};
		}
	 	
	}

	// 图片压缩函数，参数:源dataurl，压缩后高度，输出dataurl
	function compressImg(imgData, maxHeight, Orientation,onCompress) {
		if (!imgData)
			return false;
		var canvas = document.createElement('canvas');
		var img = new Image();
		img.src = imgData;
		img.onload = function() {
			if (img.height > maxHeight) {
				img.width *= maxHeight / img.height;
				img.height = maxHeight;
			}
			var ctx = canvas.getContext("2d");
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			canvas.width = img.width;
			canvas.height = img.height;
			ctx.drawImage(img, 0, 0, img.width, img.height);
			
			// 修复iphone拍照上传旋转问题:开始
//			var base64 = null;
			if (navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)) {
				//alert("iphone，Orientation="+Orientation);
				// 如果方向角不为1，都需要进行旋转
				if (Orientation != "" && Orientation != 1 && Orientation !=null ) {
					switch (Orientation) {
					case 6:// 需要顺时针（向左）90度旋转
//						alert('需要顺时针（向左）90度旋转');
						rotateImg(this, 'left', canvas);
						break;
					case 8:// 需要逆时针（向右）90度旋转
//						alert('需要顺时针（向右）90度旋转');
						rotateImg(this, 'right', canvas);
						break;
					case 3:// 需要180度旋转
						//alert('需要180度旋转');
//						rotateImg(this, 'right', canvas);// 转两次
						rotateImg(this, '180', canvas);
						break;
					}
				}
//				base64 = canvas.toDataURL("image/jpeg");
			}
			//结束
			
//			onCompress(base64);
			onCompress(canvas.toDataURL("image/jpeg"));
		};
	}

    //dataURL转为Blob二进制大文件
    function dataURLtoBlob(dataurl) {
        var arr = dataurl.split(','), 
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), 
        n = bstr.length, 
        u8arr = new Uint8Array(n);
        while(n--){
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type:mime});
    }

     //图片上传前预览 - 单张图片
    //<input name="mchntLogo"  type="file"  id="mchntLogo"  onchange="preview(this,'mchntLogo','preview')"/>
    //<img src="../resources/images/moreShopImg.png" style="margin-left:15px;" alt="店铺门面修改" onclick="$('#mchntLogo').click()" />
    //<div id="preview"></div>
     function previewOne(file,id,previewId){
	 		var input = document.getElementById(id);
	 		if (!input['value'].match(/.jpg|.gif|.png|.bmp/i)){
    			return zzrw.alert("上传的图片格式不正确，请重新选择")
    		}
        	var prevDiv = document.getElementById(previewId); 
        	 if (file.files && file.files[0])  { 
        		 var reader = new FileReader();  
        		 reader.onload = function(evt){ 
        			 prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';  
        		 }    
        		 reader.readAsDataURL(file.files[0]);  
        	 }  else { 
        		prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
        	 }  
      }
	
	
	
	//图片预览 - 多张连续排列（目前限制5张，可根据需求修改）
     //<input name="mchntEnv"  type="file" id="mchntEnv" multiple onchange="previewMore(this,'mchntEnv','previewMore')"/>
	//<img src="../resources/images/moreShopImg.png" style="margin-left:8px;" onclick="$('#mchntEnv').click()" />
	//<div id="previewMore"></div>
        function previewMore(file,inputTagID,previewId){
        		var input = document.getElementById(inputTagID);
        		var result,div;
				if(file.files.length>5){
					return zzrw.alert("您最多可上传五张店铺环境图片！");
				}
			
				$("#"+previewId).empty();
						
                for(var i=0;i<file.files.length;i++){
                    if (!input['value'].match(/.jpg|.gif|.png|.bmp/i)){
    						 return zzrw.alert("上传的图片格式不正确，请重新选择");
    				}
                    var reader = new FileReader();
                    reader.readAsDataURL(file.files[i]);
                    reader.onload = function(e){
                        result = '<div id="result"><img src="'+this.result+'" alt=""/></div>';
                        div = document.createElement('div');
                        div.innerHTML = result;
                        document.getElementById(previewId).appendChild(div);     //插入dom树       　　
    				}
                }
        }
 
     // 对图片旋转处理 added by lzk
        function rotateImg(img, direction, canvas) {
        	// alert(img);
        	// 最小与最大旋转方向，图片旋转4次后回到原方向
        	var min_step = 0;
        	var max_step = 3;
        	// var img = document.getElementById(pid);
        	if (img == null)
        		return;
        	// img的高度和宽度不能在img元素隐藏后获取，否则会出错
        	var height = img.height;
        	var width = img.width;
        	// var step = img.getAttribute('step');
        	var step = 2;
        	if (step == null) {
        		step = min_step;
        	}
        	if (direction == 'right') {
        		step++;
        		// 旋转到原位置，即超过最大值
        		step > max_step && (step = min_step);
        	}else if(direction == '180'){
        		step =2;
        	} else {
        		step--;
        		step < min_step && (step = max_step);
        	}
        	// img.setAttribute('step', step);
        	/*
        	 * var canvas = document.getElementById('pic_' + pid); if (canvas == null) {
        	 * img.style.display = 'none'; canvas = document.createElement('canvas');
        	 * canvas.setAttribute('id', 'pic_' + pid);
        	 * img.parentNode.appendChild(canvas); }
        	 */
        	// 旋转角度以弧度值为参数
        	var degree = step * 90 * Math.PI / 180;
        	var ctx = canvas.getContext('2d');
        	switch (step) {
        	case 0:
        		canvas.width = width;
        		canvas.height = height;
        		ctx.drawImage(img, 0, 0,width,height);
        		break;
        	case 1:
        		canvas.width = height;
        		canvas.height = width;
        		ctx.rotate(degree);
//        		ctx.drawImage(img, 0, -height);
        		ctx.drawImage(img, 0, -height,width,height);
        		break;
        	case 2:
        		canvas.width = width;
        		canvas.height = height;
        		ctx.rotate(degree);
        		ctx.drawImage(img, -width, -height,width,height);
        		break;
        	case 3:
        		canvas.width = height;
        		canvas.height = width;
        		ctx.rotate(degree);
        		ctx.drawImage(img, -width, 0,width,height);
        		break;
        	}
        }
	