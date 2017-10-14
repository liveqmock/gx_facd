<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
$(function() {
	 var id= $("#examinationid").val();
	 $.ajax({
			url:"${path}/examination/selectEdit?id="+id,
			type:"post",
			dataType:"json",
			success:function(result){
				var html  =  "";
			           var arr = result.obj;
			        for(var i=0;i<arr.length;i++){
			        	var ans_html = "";
			        		var v = arr[i];
			        		var g = v.answer;
			        		var number ="";
 							for(var j=0;j<g.length;j++){
 								var nu = j+1;
 							   switch (nu) {
 				                case 1:
 				                	number = "A";
 				                    break;
 				               case 2:
				                	number = "B";
				                	break;
 				              case 3:
				                	number = "C";
				                	break;
 				             case 4:
				                	number = "D";
				                	break;
 				            case 5:
				                	number = "E";
				                	break;
 				           case 6:
			                	number = "F";
			                	break;
 				                    
 				                }
								   var h = g[j];
								 //$th.after("<td>"+h.answerContent+"<input type='hidden' name='id' value='"+h.id+"'/></td>");
							ans_html += number+"、"+h.answerContent+"&nbsp;&nbsp;&nbsp;&nbsp;<br/>"		
							} 

				html +="<tr><td> <table> <tr><td>"+v.num+"."+v.title+"</td></tr><tr><td>"+ans_html+"</td></tr> </table></td><td>"+v.level+"</td></tr>";
							  }
			        $("#tab2").html(html);
			}
		});
});
</script>


<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;" >
      <input type="hidden" value="${ex.id }" id="examinationid" name="id"/>
       
       <table id="tab2" width="100%" border="0" cellspacing="0" cellpadding="0">
                    
      </table>
    	
    </div>
</div>