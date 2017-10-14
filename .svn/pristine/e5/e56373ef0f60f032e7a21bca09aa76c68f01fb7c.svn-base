<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#mapMarkEditForm').form({
            url : '${path}/mapMark/edit',
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
                    var form = $('#mapMarkEditForm');
                    parent.$.messager.alert('错误',result.msg, 'error');
                }
            }
        });
        
        $("#selid").val('${mapMark.type}'); 
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="mapMarkEditForm" method="post" enctype="multipart/form-data">
        <input name="id" type="hidden" value="${mapMark.id}"/>
            <table class="grid">
                <tr>
                    <td>名称</td>
                    <td><input name="name" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value="${mapMark.name }"></td>
                    <td>类型</td>
                    <td><select name="type" class="easyui-validatebox span2" id="selid" data-options="required:true">
                        <option value="">请选择</option>
                        <option value="0">界碑</option>
                        <option value="1">巡防路</option>
                        <option value="2">邻国边境部署</option>
                    </select></td>
                </tr> 
                <tr>
                    <td>经度</td>
                    <td><input name="longitude" type="text" placeholder="请输入经度" class="easyui-validatebox span2" data-options="required:true" value="${mapMark.longitude }"></td>
                    <td>纬度</td>
                    <td><input name="latitude" type="text" placeholder="请输入纬度" class="easyui-validatebox span2" data-options="required:true" value="${mapMark.latitude }"></td>
                </tr>
                 <tr>
					<td>图标</td>
					<td><input name="file" type="file" ></td>
				 <td>图标</td>
				 <td><img alt="${mapMark.name}图标" src="${mapMark.icon}" style="width: 150px;height: 150px"></td>
				</tr>
                <tr>
                    <td>内容</td>
                    <td colspan="3" rowspan="2">
                    <textarea  id="info" name="info"  placeholder="请输入内容" class="easyui-validatebox span2" data-options="required:true" style="width: 450px;height: 120px;">${mapMark.info }</textarea>
                    </td>
                </tr>
                
            </table>
        </form>
    </div>
</div>