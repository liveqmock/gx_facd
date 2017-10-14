<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ckplayer.js"></script>
<script type="text/javascript">
	$(function() {
		$('#positionMarkEditForm').form({
			url : '${path}/positionMark/change',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					var form = $('#positionMarkEditForm');
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

		$("#editStatus").val('${positionMark.infoStatus}');

	});
	function sendinfoFileseeFun(urlimg,type) {
		if(type==1){
			 $('#sendinfoimg').html("<img alt='' src='"+urlimg+"' width='394px' height='357px'> ");
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
                    text: '关闭',  
                 //   iconCls: 'icon-ok',  
                    handler: function () {  
                        $('#sendinfoimg').dialog('close');  
                            }  
                }]
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
					<td>新经纬度</td>
					<td>${positionMark.longitude}</td>
					<td>${positionMark.latitude}</td>
					<!-- 
					<td><input type="button" value="替换" onclick="replacecoordinate(${positionMark.id})" /> </td>
				-->
				</tr>
				 
				<c:if test="${positionMark.type==0 }">
				
				<tr>
					<td>${mapMark.name}经纬度</td>
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
								<a href="javascript:void(0)"  onclick="sendinfoFileseeFun('${file.fileurl}','${file.filetype }');" >${file.filename}</a>
							</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="2"><textarea rows="2" cols="30" name="remark"></textarea>
					</td>
					<td><select id="infoStatus" name="infoStatus"
						class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1">请选择</option>
							<option value="2">审核通过</option>
							<option value="3">审核未通过</option>
					</select></td>
				</tr>
			</table>
			<div id="sendinfoimg" style="overflow: hidden">
            </div>
		</form>
	</div>
</div>