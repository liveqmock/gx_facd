<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#subjectEditForm').form({
            url : '${path}/subject/edit',
            onSubmit : function() {
            	var a1 = $("#a1").val();
            	var a2 = $("#a2").val();
            	var a3 = $("#a3").val();
            	var a4 = $("#a4").val();
            	var a5 = $("#a5").val();
            	var a6 = $("#a6").val();
            	var b1 = $("select#b1").val();
            	var b2 = $("select#b2").val();
            	var b3 = $("select#b3").val();
            	var b4 = $("select#b4").val();
            	var b5 = $("select#b5").val();
            	var b6 = $("select#b6").val();
            	if(a6!=undefined){
            		if(b6==2){
            			$.messager.alert("提示","请选择答案6正确与否！");
            			return false;
            		}
            		if(b1==1&&b2==1&&b3==1&&b4==1&&b6==1&&b5==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(b1==0&&b2==0&&b3==0&&b4==0&&b6==0&&b5==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a6==""||a6==null){
            			$.messager.alert("提示","请输入答案6的内容！");
            			return false;
            		}
            	}else if(a5!=undefined){
            		if(b5==2){
            			$.messager.alert("提示","请选择答案5正确与否！");
            		}
            		if(b1==1&&b2==1&&b3==1&&b4==1&&b5==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(b1==0&&b2==0&&b3==0&&b4==0&&b5==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a5==""||a5==null){
            			$.messager.alert("提示","请输入答案5的内容！");
            			return false;
            		}
            	}else if(a4!=undefined){
            		if(b4==2){
            			$.messager.alert("提示","请选择答案4正确与否！");
            		}
            		if(b1==1&&b2==1&&b3==1&&b4==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(b1==0&&b2==0&&b3==0&&b4==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a4==""||a4==null){
            			$.messager.alert("提示","请输入答案4的内容！");
            			return false;
            		}
            	}else if(a3!=undefined){
            		if(b3==2){
            			$.messager.alert("提示","请选择答案3正确与否！");
            			return false;
            		}
            		if(b1==1&&b2==1&&b3==1){
            			$.messager.alert("提示","不能全部为正确答案！");
            			return false;
            		}else if(b1==0&&b2==0&&b3==0){
            			$.messager.alert("提示","请选择一个正确答案！");
            			return false;
            		}else if(a3==""||a3==null){
            			$.messager.alert("提示","请输入答案3的内容！");
            			return false;
            		}
            	}else	if(b1==1&&b2==1){
            		$.messager.alert("提示","不能全部为正确答案！");
        			return false;
            	}else if(b1==0&&b2==0){
            		$.messager.alert("提示","请选择一个正确答案！");
        			return false;
            	}
            
           
            	
            
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
                    var form = $('#subjectEditForm');
                    parent.$.messager.alert('错误',result.msg, 'error');
                }
            }
        });
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="subjectEditForm" method="post" enctype="multipart/form-data">
        <input name="id" type="hidden" value="${subject.id}"/>
            <table class="grid">
                <tr>
                    <td>标题</td>
                    <td><input name="title" type="text" placeholder="请输入标题" class="easyui-validatebox span2" data-options="required:true" value="${subject.title}"></td>
                    <td>类型</td>
                    <td><select name="level" class="easyui-combobox"
                                data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <c:if test="${subject.level==1}"><option value="1" selected="selected">简单</option>
                             <option value="2" >一般</option>
                            <option value="3" >困难</option>
                        </c:if>
                        <c:if test="${subject.level==2}"><option value="2" selected="selected">一般</option> 
		                    <option value="1" >简单</option>
		                    <option value="3" >困难</option>
                        </c:if>
                        <c:if test="${subject.level==3}"><option value="3" selected="selected">困难</option>
		                      <option value="1" >简单</option>
		                      <option value="2" >一般</option>
                       </c:if>
                       </select>
                    </td>
                </tr> 
                <c:forEach items="${Answer}" var="answer">
                 <tr>
                   <th colspan="4"> <center><span>答案${answer.seq}<input  name="id${answer.seq}" type="hidden" value="${answer.id}"/></span></center></th>
                 </tr>
                <tr>
                    <td>答案</td>
                    <td colspan="3"><input id="a${answer.seq}" name="answerContent${answer.seq}" type="text" placeholder="请输入答案" class="easyui-validatebox span2" data-options="required:true" value="${answer.answerContent }">
                        <select name="isAnswer${answer.seq}" id="b${answer.seq}">
                                <c:if test="${answer.isAnswer==1}"><option value="1" selected="selected">正确</option>
                                <option value="0">错误</option>
                         </c:if>
                               <c:if test="${answer.isAnswer==0}"><option value="0" selected="selected">错误</option>
                                <option value="1">正确</option>
                         </c:if>
                                </select>
                    </td>
                   </tr>
               </c:forEach>
                
            </table>
            <br/>
            <br/>
              <table >
             <tr >
             <td >  解析：</td>
               </tr>
               <tr align="center">
                   <td align="center"><textarea id="analysis" style="width: 600px;height: 120px;" name="analysis" placeholder="请输入解析" class="easyui-validatebox span2" data-options="required:true" >${subject.analysis }</textarea></td>
                </tr>
                   </table>
        </form>
    </div>
</div>