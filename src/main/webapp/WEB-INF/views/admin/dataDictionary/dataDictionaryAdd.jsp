<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	   $('#dataDictionaryAddPid').combotree({
               url : '${path }/dataDictionary/allTree',
               parentField : 'pid',
               lines : true,
               panelHeight : 'auto',
               onLoadSuccess:function(data){
            	 $(".tree-icon,.tree-file").removeClass("tree-icon tree-file");
                 $(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed"); 
               }
           });
        $('#dataDictionaryAddForm').form({
            url : '${path}/dataDictionary/add',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#dataDictionaryAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
    function cleanC(){
    	$('select#dataDictionaryAddPid').combotree('clear');
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="dataDictionaryAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>资源名称</td>
                    <td><input name="name" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                    <td>类型编码</td>
                    <td><input maxlength="10"  name="code" type="text" placeholder="请输入编码" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                 <td>备注</td>
                    <td>
                      <textarea  id="remake" name="remake"  placeholder="请输入备注" class="easyui-validatebox span2" data-options="required:true" ></textarea>
                    </td>
                     <td>排序</td>
                <td><input name="seq" value="0" maxlength="3"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                </tr> 
                 <tr>
                <td>上级资源</td>
                <td colspan="3">
                    <select id="dataDictionaryAddPid" name="pid" style="width: 200px; height: 29px;"></select>
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="cleanC()" >清空</a>
                </td>
            </tr>
            </table>
        </form>
    </div>
</div>