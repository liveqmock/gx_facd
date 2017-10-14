<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
   var i = 3;
    $(function() {
        $('#subjectAddForm').form({
            url : '${path}/subject/add',
            onSubmit : function() {
            	
                 var isValid = $(this).form('validate');
            	var a1 = $("select#a1").val();
            	var a2 = $("select#a2").val();
            	var a3 = $("#a3").val();
            	var a4 = $("#a4").val();
            	var a5 = $("#a5").val();
            	var a6 = $("#a6").val();
            	if(a1==1&&a2==1){
            		$.messager.alert("提示","不能全部为正确答案！");
        			return false;
            	}else if(a1==0&&a2==0){
            		$.messager.alert("提示","请选择一个正确答案！");
        			return false;
            	}
            	if(a3!=undefined){
            		var b3 = $("select#b3").val();
            		if(b3==2){
            			$.messager.alert("提示","请选择答案3正确与否！");
            			return false;
            		}
            		if(a1==1&&a2==1&&a3==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(a1==0&&a2==0&&a3==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a3==""||a3==null){
            			$.messager.alert("提示","请输入答案3的内容！");
            			return false;
            		}
            	}
            	if(a4!=undefined){
            		var b4 = $("select#b4").val();
            		if(b4==2){
            			$.messager.alert("提示","请选择答案4正确与否！");
            		}
            		if(a1==1&&a2==1&&a3==1&&a4==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(a1==0&&a2==0&&a3==0&&a4==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a4==""||a4==null){
            			$.messager.alert("提示","请输入答案4的内容！");
            			return false;
            		}
            	}
            	if(a5!=undefined){
            		var b5 = $("select#b5").val();
            		if(b5==2){
            			$.messager.alert("提示","请选择答案5正确与否！");
            		}
            		if(a1==1&&a2==1&&a3==1&&a4==1&&a5==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(a1==0&&a2==0&&a3==0&&a4==0&&a5==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a5==""||a5==null){
            			$.messager.alert("提示","请输入答案5的内容！");
            			return false;
            		}
            	}
            	if(a6!=undefined){
            		var b6 = $("select#b6").val();
            		if(b6==2){
            			$.messager.alert("提示","请选择答案6正确与否！");
            			return false;
            		}
            		if(a1==1&&a2==1&&a3==1&&a4==1&&a6==1&&a5==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(a1==0&&a2==0&&a3==0&&a4==0&&a6==0&&a5==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a6==""||a6==null){
            			$.messager.alert("提示","请输入答案6的内容！");
            			return false;
            		}
            	}
            	 progressLoad();
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
                	
                    var form = $('#subjectAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
    
    function addTr2(){
    	 if(i>6){
	    	 alert("最多增加6个答案!")
	    	 return;
	     }
    	 var $tr= $("#tab tr:last");
    	 if($tr.size()==0){
    	        alert("指定的table id或行数不存在！");
    	        return;
    	     }
    	     $tr.after(" <tr><th colspan='4'> <center><span>答案"+i+"</span></center></th></tr>"
    	     	    +"<tr><td>答案</td><td colspan='3'><input id ='a"+i+"' name='answerContent"+i+"' type='text' placeholder='请输入答案' class='easyui-validatebox span2' data-options='required:true' value=''>"
    	     	    +"<select id='b"+i+"' name='isAnswer"+i+"' class='easyui-combobox' "
    	     	   +"ata-options='width:140,height:29,editable:false,panelHeight:'auto''>"
    	    	    +"<option value='2'>请选择</option>"
    	    	    +"<option value='0'>错误</option>"
    	    	    +"<option value='1'>正确</option>"
    	    	    +"</select></td></tr>");
    	     i++;
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow-y:scroll; overflow-x:scroll;padding: 3px;" >
        <form id="subjectAddForm" method="post" enctype="multipart/form-data">
            <table class="grid" id="tab">
                <tr>
                    <td>标题</td>
                    <td><input name="title" type="text" placeholder="请输入标题" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                    <td>类型</td>
                    <td><select name="level" class="easyui-validatebox span2" data-options="required:true"
                                data-options="width:140,height:29,editable:false,panelHeight:'auto'">
	                     <option value="">请选择</option>
	                     <option value="1">简单</option>
                         <option value="2">一般</option>
                         <option value="3">困难</option>
                       </select>
                    </td>
                </tr> 
                 <tr>
                   <th colspan="4"> <center><span>答案1</span></center></th>
                 </tr>
                <tr>
                    <td>答案</td>
                    <td><input name="answerContent" type="text" placeholder="请输入答案" class="easyui-validatebox span2" data-options="required:true" value="">
                        <select id="a1" name="isAnswer" class="easyui-validatebox span2" data-options="required:true"
                                >
                                 <option value="">请选择</option>
	                     <option value="0">错误</option>
                         <option value="1">正确</option>
                                </select>
                    </td>
                   </tr>
                <tr>
                   <th colspan="4"> <center><span>答案2</span></center></th>
                 </tr>
                <tr>
                    <td >答案</td>
                    <td colspan="3"><input name="answerContent2" type="text" placeholder="请输入答案" class="easyui-validatebox span2" data-options="required:true" value="">
                        <select id="a2" name="isAnswer2" class="easyui-validatebox span2" data-options="required:true"
                               >
                                 <option value="">请选择</option>
	                     <option value="0">错误</option>
                         <option value="1">正确</option>
                                </select>
                    </td>
                   </tr>
                
            </table>
            <center> <input type="button" onclick="addTr2()" value="添加答案"></center>
            
            <br/>
            <br/>
             <table >
             <tr>
             <td > 解析： </td>
               </tr>
               <tr>
                   <td align="center"><textarea style="width: 600px;height: 120px;" id="analysis" name="analysis"  data-options="required:true" ></textarea></td>
                </tr>
                   </table>
        </form>
    </div>
</div>
