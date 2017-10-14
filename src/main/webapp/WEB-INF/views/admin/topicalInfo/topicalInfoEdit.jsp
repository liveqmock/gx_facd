<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
     var ue ;
    $(function() {
    	ue=UE.getEditor('editor');
        $('#topicalInfoEditForm').form({
            url : '${path}/topicalInfo/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                $("#infoid").val(ue.getContent());//*************
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#topicalInfoEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
        $("#editStatus").val('${topicalInfo.status}'); 
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;padding: 3px;">
        <form id="topicalInfoEditForm" method="post">
            <input name="id" value="${topicalInfo.id }" type="hidden" >
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td><input name="title" type="text" placeholder="请输入标题" class="easyui-validatebox" data-options="required:true" value="${topicalInfo.title}"></td>
                </tr>
                <tr>
                    <td>正文</td>
                </tr>
                <tr>
                	<td colspan="4">
                	<script id="editor" type="text/plain" style="width:100%;height:100%;min-height: 200px;">${topicalInfo.content}</script>
                	</td>
                	<td><input type="hidden" name="info" id="infoid"></td>
                </tr> 
            </table>
        </form>
    </div>
</div>