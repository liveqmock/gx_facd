<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var ue;
    $(function() {
    	ue=UE.getEditor('editor');
    	$('#userEditorganizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${notice.receiver}'
        });
        $('#noticeEditForm').form({
            url : '${path}/notice/edit',
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
                    var form = $('#noticeEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
            $("#showtime").val('${notice.showTime }');
            $("#typeid").val('${notice.type }');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;">
        <form id="noticeEditForm" method="post">
        <input name="id" value="${notice.id }" type="hidden" >
        <input name="info" id="infoid" type="hidden" >
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td><input name="title" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value="${notice.title }"></td>
                    <td>发布人</td>
                    <td><input name="publisher" type="text" placeholder="请输入发布人" class="easyui-validatebox span2" data-options="required:true" value="${notice.publisher }"></td>
                </tr> 
                 <tr>
                    <td>公告类型</td>
                    <td><select name="type" id="typeid" > 
                    	<option value="1" >公告</option>
                    	<option value="0" >通知</option>
                    </select> </td>
                    <td colspan="2" >*请选择发布的公告类型</td>
                </tr> 
                <tr>
                    <td>展示时间</td>
                    <td><input name="showTime" id="showtime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
                    <td>公告范围</td>
					<td><select id="userEditorganizationId" name="receiver"
						style="width: 140px; height: 29px;" class="easyui-validatebox"
						data-options="required:true"></select></td>
                </tr> 
                <tr>
                	<td colspan="4">
                	<script id="editor" type="text/plain" style="width:100%;height:100%;min-height: 200px;">${notice.info }</script>
                	</td>
                </tr> 
            </table>
        </form>
    </div>
</div>