<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#videoFileAddForm').form({
            url : '${path}/videoFile/add',
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
                    var form = $('#videoFileAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="videoFileAddForm" method="post">
           <table class="grid">
                
                <tr>
                    <td>文件名称</td>
                    <td><input name="fileName" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""  >
                    </td>
                </tr>
                <tr>
                    <td>文件路径</td>
                    <td><input name="fileUrl" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""  >
                    </td>
                </tr>
                <tr>
                    <td>直播人id</td>
                    <td><input name="userId" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""  >
                    </td>
                </tr>
                 <tr>
                    <td>备注</td>
                    <td><input name="remarks" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""    style="width: 350px;">
                    </td>
                </tr>
                
                
            </table>
        </form>
    </div>
</div>