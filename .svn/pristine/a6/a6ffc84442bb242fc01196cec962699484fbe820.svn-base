<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#appVersionAddForm').form({
            url : '${path}/appVersion/add',
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
                    //之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#appVersionAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="appVersionAddForm" method="post" enctype="multipart/form-data" >
            <table class="grid">
                <tr>
                    <td>名称</td>
                    <td><input name="name" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                    <td>版本号</td>
                    <td><input name="version" type="text" placeholder="请输入版本号" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr> 
                <tr>
                    <td>安装包</td>
                    <td><input name="file" type="file"  class="easyui-validatebox span2" data-options="required:true"></td>
                    <td>*apk包</td>
                    <td>
	                    <input type="radio" name="type" checked value="1" />移动端
	                    <input type="radio" name="type" value="2" />电视端
	                    <input type="radio" name="type" value="3" />移动端插件
	                    <input type="radio" name="type" value="4" />电视端插件
                    </td>
                </tr> 
                <tr>
                    <td>更新原因</td>
                    <td colspan="3" ><textarea name="raeson" cols="50" rows="5" class="easyui-validatebox span2" ></textarea></td>
                </tr> 
            </table>
        </form>
    </div>
</div>