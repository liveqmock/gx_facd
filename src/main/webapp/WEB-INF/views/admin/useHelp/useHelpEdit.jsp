<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var ue;
    $(function() {
    	ue=UE.getEditor('editor');
        $('#useHelpEditForm').form({
            url : '${path}/useHelp/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
            	$("#infoid").val(ue.getContent());
            	console.info(ue.getContent());
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#useHelpEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
          
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;">
        <form id="useHelpEditForm" method="post">
        <input name="id" value="${useHelp.id }" type="hidden" >
        <input name="info" id="infoid" type="hidden" >
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td><input name="title" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value="${useHelp.title }"></td>
             
                   
                </tr> 
                <tr>
                	<td colspan="4">
                	<script id="editor" type="text/plain" style="width:100%;height:100%;min-height: 200px;">${useHelp.info }</script>
                	</td>
                </tr> 
            </table>
        </form>
    </div>
</div>