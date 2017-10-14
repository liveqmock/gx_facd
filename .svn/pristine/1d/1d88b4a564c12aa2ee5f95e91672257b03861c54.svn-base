<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ckplayer.js"></script>
<script type="text/javascript">
	$(function() {

		$("#editStatus").val('${positionMark.infoStatus}');

	});
	function replacecoordinate(id){
		parent.$.messager.confirm('询问', '您是否要替换原有坐标？', function(b) {
	         if (b) {
	             progressLoad();
	             $.post('${path}/positionMark/replacemm', {
	                 id : id
	             }, function(result) {
	                 if (result.success) {
	                     parent.$.messager.alert('提示', result.msg, 'info');
	                     positionMarkDataGrid.datagrid('reload');
	                 }
	                 progressClose();
	             }, 'JSON');
	         }
	     });
	}
	
	function sendinfoFileseeFun(urlimg,type,id) {
		if(type==1){
			 $('#sendinfoimg').html("<img alt='' src='"+urlimg+"' width='394px' height='357px'>");
		}else{
			 var flashvars={
				        f:urlimg,
				        c:0
				    };
				    var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
				    CKobject.embedSWF('${staticPath }/static/ckplayer.swf','sendinfoimg','ckplayer_a1','394','357',flashvars,params);
		}
   	 $('#sendinfoimg').dialog({  
            content: "",  
            title: '${file.filename}',  
            //iconCls: "icon-edit",  
            collapsible: false,  
            minimizable: false,  
            maximizable: false,  
            closable: false,  
            resizable: false,  
            top: 200,  
            width:400,  
            //height: 485,  
            height:400,  
            //left: auto,  
            modal: true,  
            buttons: [  
               {
                	text:'设为标准图标',
                	iconCls:'fi-pencil',
                	handler: function () {  
                	//	window.location.href="${path}/mapMark/change?url="+urlimg;
                		  $.ajax({
                	            //提交数据的类型 POST GET
                	            type:"POST",
                	            //提交的网址
                	            url:"${path}/mapMark/change?&id="+id+"&url="+urlimg,
                	            //提交的数据
                	           // data:{Name:"sanmao",Password:"sanmaoword"},
                	            //返回数据的格式
                	          //  datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
                	            //在请求之前调用的函数
                	           // beforeSend:function(){$("#msg").html("logining");},
                	            //成功返回之后调用的函数             
                	            success:function(result){
                	            	 result = $.parseJSON(result);
                	            	 if (result.success) {
                	            		 parent.$.messager.alert('成功',result.msg, 'info'); 
                	            		 $('#sendinfoimg').dialog('close'); 
                	            	 }else{
                	            		 parent.$.messager.alert('错误',result.msg, 'error'); 
                	            		 $('#sendinfoimg').dialog('close'); 
                	            	 }
                	            	 
                	            }                 	        
                	           });
                            } 
                },
                {  
                    text: '关闭',  
                  //  iconCls: 'icon-ok',  
                    handler: function () {  
                        $('#sendinfoimg').dialog('close');  
                            }  
                }
                ]
   	 })
   }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="positionMarkEditForm" method="post">
			<input name="id" type="hidden" value="${positionMark.id}" />
			<table class="grid">
				<tr>
					<td width="20%;">信息名称</td>
					<td width="30%;">${positionMark.name}</td>
					<td width="20%;">信息类型</td>
					<td width="30%;"><label> <c:if
								test="${positionMark.type==0}">界碑</c:if> <c:if
								test="${positionMark.type==1}">巡防路</c:if> <c:if
								test="${positionMark.type==2}">邻国边境部署<</c:if>
					</label></td>
				</tr>
				<tr>
					<td>标记人</td>
					<td><p>${username}</p></td>
					<td>报送信息时间</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${positionMark.createTime}" /></td>
				</tr>
				<tr>
					<td>新经，纬度</td>
					<td>${positionMark.longitude}</td>
					<td>${positionMark.latitude}</td>
					<td><input type="button" value="替换" onclick="replacecoordinate(${positionMark.id})" /> </td>
				</tr>
				<c:if test="${positionMark.type==0 }">
				
				<tr>
					<td>${mapMark.name}经，纬度</td>
					<td>${mapMark.longitude}</td>
					<td>${mapMark.latitude}</td>
					<td>*界碑经纬度信息</td>
				</tr>
				<tr>
				<!--  
					<td>区域归属</td>
					<td>${road.territorial}</td>
					-->
					<td>路段名称</td>
					<td colspan="3">${road.name}</td>
				</tr>
				</c:if>
				<tr>
					<td>信息描述</td>
					<td colspan="3">
						<p>${positionMark.info}</p>
					</td>
				</tr>
				<tr>
					<td>附件列表</td>
					<td colspan="3">
						<ul>
							
							<c:forEach items="${SendFile}" var="file">
							<li>
								<a  href="javascript:void(0)"  onclick="sendinfoFileseeFun('${file.fileurl}','${file.filetype }',${positionMark.mmid});" >${file.filename}</a><br/>
							</li>
							</c:forEach>
							
						</ul>
					</td>
				</tr>
			</table>
			<div id="sendinfoimg" style="overflow: hidden">
			
            </div>
		</form>
	</div>
	
</div>