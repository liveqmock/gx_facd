<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#sysConfigEditForm').form({
            url : '${path}/sysConfig/edit',
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
                    var form = $('#sysConfigEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        

        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="sysConfigEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>编号</td>
                    <td><input name="id" type="hidden"  value="${sysConfig.id}">
                    ${sysConfig.selkey}</td>
                </tr>
                <tr>
                    <td>设定</td>
                    <td>
                    <input name="value" type="text" placeholder="请输入名称" class="easyui-validatebox" data-options="required:true" value="${sysConfig.value}"></td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>
                    ${sysConfig.info}</td>
                </tr>
            </table>
        </form>
    </div>
</div>