<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#sendFileAddForm').form({
            url : '${path}/sendFile/add',
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
                    var form = $('#sendFileAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="sendFileAddForm" method="post" enctype="multipart/form-data" >
        <input type="hidden" value="${owenid }" name="owenid">
            <table class="grid">
                <tr>
                    <th width="20%" >附件</th>
                    <td colspan="2"><input type="radio" name="filetype" value="1" checked >图片
                    <input type="radio" name="filetype" value="2" >音频 
                    <input type="radio" name="filetype" value="3" >视频 </td>
                    <td><input class="filePrew"  tabIndex="3" type="file" size="9" name="graphTheories" multiple/>  </td> 
                </tr>
            </table>
        </form>
    </div>
</div>