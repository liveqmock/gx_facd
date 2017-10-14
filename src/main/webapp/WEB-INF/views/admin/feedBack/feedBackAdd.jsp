<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#feedBackAddForm').form({
            url : '${path}/feedBack/add',
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
                    var form = $('#feedBackAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="feedBackAddForm" method="post" >
        		<div align="left">
        			<hr style="height:1px;border:none;border-top:1px solid #555555;" />
        			<span style="width: 100%;color: black;font-style: italic; font-size: 20px;" >${feedBack.question}</span><br/>
        			<hr style="height:1px;border:none;border-top:1px solid #555555;" />
        			<span style="width: 100%;color: black; font-size: 20px;">问题反馈</span>
        		</div>
	               <textarea id="1" name="answer" cols="70" rows="25" ></textarea>
        </form>
    </div>
</div>