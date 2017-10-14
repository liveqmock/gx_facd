<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var ue;
    $(function() {
    	ue=UE.getEditor('editor');
    
    	
        $('#borderKnowledgeEditForm').form({
            url : '${path}/borderKnowledge/edit',
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
                    var form = $('#borderKnowledgeEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;">
        <form id="borderKnowledgeEditForm" method="post" enctype="multipart/form-data">
        <input name="id" value="${b.id }" type="hidden" >
         <input name="kid" value="${k2.id }" type="hidden" >
         <input type="hidden" name="content" id="infoid" >
            <table class="grid">
                <tr>
                    <td width="150px">标题</td>
                    <td><input name="title" type="text" placeholder="请输入标题" class="easyui-validatebox span2" data-options="required:true" value="${b.title }"></td>
                     <td rowspan="2" width="150px">修改附件</td>
                    <td rowspan="2">
                    <input type="file" name="file" />
                    </td>
                </tr> 
               <tr>
                    <td width="150px">类别名称</td>
                    <td >
                   <select name="typeId" id="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,valueField:'id', textField:'text'">
                   <c:forEach items="${KnowledgeType1 }" var="k">
                      <option value="${k.id }" <c:if test="${k.id==k2.id }">selected="selected"</c:if> >${k.name }</option>
                  </c:forEach> </select>
                    <!-- 
                   <input id="roadIds" name = "name" class="easyui-combotree" 
					data-options="url:'${path }/knowledgeType/tree',
					method:'post',
					 parentField: 'pid',
            		lines : true,
            		panelHeight: 'auto',
            		multiple: false,
            		required: true,
            		cascadeCheck: false," style="width: 140px; height: 29px;" value='${k.name }'>
            		 -->
            		</td>
					</tr> 
                <tr>
                	<td colspan="4" rowspan="2">
                	<script id="editor" type="text/plain" style="width:100%;height:100%;min-height: 200px;">${b.content }</script>
                	</td>
                </tr> 
            </table>
        </form>
    </div>
</div>