<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var ue ;
    $(function() {
    	ue=UE.getEditor('editor');
		$('#type').combobox({
			url : '${path }/borderKnowledge/tree',
			multiple : false,
			required : true,
			panelHeight : 'auto',
			 value : '${KnowledgeType.id}'
		});
        $('#borderKnowledgeAddForm').form({
        	url : '${path}/borderKnowledge/add',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
            	$("#infoid").val(ue.getContent());
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
                    var form = $('#borderKnowledgeAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;" >
        <form id="borderKnowledgeAddForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="content" id="infoid" >
            <table class="grid">
                <tr>
                    <td width="150px">标题</td>
                    <td><input name="title" type="text" placeholder="请输入标题" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                     <td rowspan="2" width="150px">上传附件</td>
                    <td rowspan="2">
                    <input type="file" name="file" />
                    </td>
                </tr> 
               <tr>
		     <td width="150px">类型名称</td>
					<td>
					<select name="id" id="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,valueField:'id', textField:'text'"></select>
					</td>
                </tr> 
                <tr>
                	<td colspan="4" rowspan="2">
                	<script id="editor" type="text/plain" style="width:100%;height:100%;min-height: 200px;"></script>
                	</td>
                </tr> 
            </table>
        </form>
    </div>
</div>