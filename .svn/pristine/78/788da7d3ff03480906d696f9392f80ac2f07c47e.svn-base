<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var userDataGrid2;
var organizationTree2;
var uids ="0,";
$(function() {
	 $(document).on("change",'select#type',function(){
		     var type = $(this).val();
		     if(type==1){
		    	 $("#random").css('display','block');
		    	 $("#random2").css('display','block');
		     }else{
		    	 $("#random").hide();
		    	 $("#random2").hide();
		     }
		 });
	
	 
    organizationTree2 = $('#organizationTree2').tree({
        url : '${path }/organization/tree2',
        parentField : 'pid',
        lines : true,
        onClick : function(node) {
            userDataGrid.datagrid('load', {
                organizationId: node.id
            });
        }
    });

    userDataGrid2 = $('#userDataGrid2').datagrid({
        url : '${path }/user/dataGridEx',
        fit : true,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : false,
        idField : 'id',
        sortName : 'id',
        sortOrder : 'asc',
        pageSize : 20,
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
        columns : [ [ {
            width : '10%',
            title : '选择',
            field : 'cb',checkbox:true,width:100,align:'center'
        }, {
            width : '6%',
            title : '编号',
            field : 'id',
            sortable : true
        },{
            width : '12%',
            title : '姓名',
            field : 'name',
            sortable : true
        }
        /*
        , {
            field : 'action',
            title : '操作',
            width : '20%',
            formatter : function(value, row, index) {
                var str = '';
                        str += $.formatString('<input type="button"  class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" > value="确定"/> ', row.id);
                return str;
            }
        }*/
        
        ] ],
        onLoadSuccess:function(data){
            $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.user-easyui-linkbutton-del').linkbutton({text:'删除'});
        },
        toolbar : '#userToolbar'
    });
});
/**
 * 全选
 */
function allchoose(){
	var rows = $("#userDataGrid").datagrid("getSelections");
	if(rows.length==0){
		$.messager.alert("提示","请选择要参加考试的用户！");
	}else{
		$.messager.confirm("警告","你确定要这"+rows.length+"个用户参加考试吗?",function(r){
			if(r){

				for(var i=0;i<rows.length;i++){
					uids += rows[i].id+",";
				}
				uids = uids.substring(0,uids.length-1);
			/*	
				$.ajax({
					url:"StudentServlet",
					type:"post",
					data:"opType=deleteMore&ids="+ids,
					success:function(){
						
						$("#dg").datagrid("reload");
					}
				});
			*/
			}
		});
	}
}

   var i = 3;
   var ids ="0,";
   var j = 0;
    $(function() {
    	$('#userEditorganizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${user.organizationId}'
        });
        $('#examinationAddForm').form({
            url : '${path}/examination/add',
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
                     parent.$.modalDialog.openner_dataGrid('reload');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#examinationAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
    
    function nextOne(){
    	var title = $('#title').val();
    	var btime = $('#beginTime').val();
    	var etime = $('#endTime').val();
    	var info = $('#info').val();
    	
         if(title==null||title==''&&btime==null||btime==''&&etime==null||etime==''&&info==null||info==''){
        	 $.messager.alert("提示","请输入完整的内容！");
    			 return;
    		 }
     
    	
     
    	var select = $('select#type').val();
    	 if(select==1){
    		 var b = $('#num').val();
    		 if(b==null||b==''){
        		 $.messager.alert("提示","请输入随机的题目数量");
        		 return;
        	 }
    		 nextThree();
    	 }
    	 if(select==2){
    		 $("#tab").hide();
    		 $("#tab5").css('display','block');
    		 nextfive(j);
		       
    	 }
    	
        
    }
    
   
    function nextThree(){
    	
    	var a = $('select#type').val();
    	 var b = $('#num').val();
    	var title = $('#title').val();
    	var btime = $('#beginTime').val();
    	var etime = $('#endTime').val();
    	
         if(title==null||title==''&&btime==null||btime==''&&etime==null||etime==''){
        	 $.messager.alert("提示","请输入完整的内容！");
    			 return;
    		 }
    	
    	
    	 if(a==0){
    		 $.messager.alert("警告","你还没有选择！")
    		 return;
    	 }
    
    	 if(b==null||b==''){
    		 $.messager.alert("提示","请输入随机的题目数量");
    		 return;
    	 }
    	 if(a==1){
        	 $.ajax({
    				url:"${path}/examination/selectRandom?num="+b,
    				type:"post",
    				dataType:"json",
    				success:function(result){
    					var html  =  "";
    					 $("#tab").hide();
    				        $("#tab4").css('display','block');
    				           var arr = result.obj;
    				        for(var i=0;i<arr.length;i++){
    				        	var v = arr[i];
    							var g = v.answer;
    									var ans_html = "";
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

    				html +="<tr><td> <table> <tr><td><input  type='hidden' name='ids' value='"+v.id+"'/>"+v.num+"."+v.title+"</td></tr><tr><td>"+ans_html+"</td></tr> </table></td><td>"+v.level+"</td></tr>";
    							 
    //$tr.after("<tr><td>"+v.title+"</td><td>"+v.level+"</td></tr>");
    							// var g = v.answer;
    							// $tr.after("<tr></tr>");
    							// var $th= $("#tab4 tr:last");
    							// for(var j=0;j<g.length;j++){
    							//	   var h = g[j];
    							//	 $th.after("<td>"+h.answerContent+"<input type='hidden' name='id' value='"+h.id+"'/></td>");
    							// } 
    				        }
    						console.log(html);
    						html += "<input type='hidden' id='is' value='1'/>";
    						$("#tab2").html(html);
    				}
    			});
    	 }
    	
    	 
       
    }
    function nextsix(){
    	 $("#one").hide();
	        $("#tow").css('display','block');
    }
    function nextseven(){
    	var arr = document.getElementsByName("ids");
    	
    	var number = 0;
    	for(var i=0;i<arr.length;i+=1){
    		if(arr[i].checked){
    			number++;
    		}
    	}
		if(number==0){
			$.messager.alert("提示","您还没有选择题目！");
			return;
		}
    	 $("#one").hide();
	        $("#tow").css('display','block');
    }
    function nextfinish(){
        var rows = $("#userDataGrid2").datagrid("getSelections");
        var arr = document.getElementsByName("ids");
        var is = $("#is").val();
     	if(rows.length==0){
     		$.messager.alert("提示","请选择要参加考试的用户！");
     		return;
     	}else{
     		for(var i=0;i<rows.length;i++){
				uids += rows[i].id+",";
			}
			uids = uids.substring(0,uids.length-1);
     	}
     	
    if(is==1){
    	for(var i=0;i<arr.length;i+=1){
			ids+= arr[i].value+",";
	}
    	
    }else{
    	for(var i=0;i<arr.length;i+=1){
    		if(arr[i].checked){
    			ids+= arr[i].value+",";
    		}
    	}
    	
    }
     	
     	ids = ids.substring(0, ids.length-1);
		var data=$('#title').val();
		var da = encodeURI(encodeURI(data));
		var info=$('#info').val();
		var info2 = encodeURI(encodeURI(info));
		
		 $.ajax({
				url:"${path}/examination/add?ids="+ids+"&titl="+da+"&uids="+uids+"&"+$('#examinationAddForm').serialize()+"&info2="+info2,
				type:"post",
				success:function(result){
					   progressClose();
		                result = $.parseJSON(result);
		                if (result.success) {
		                	  parent.$.modalDialog.openner_dataGrid = examinationDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
		                      var f = parent.$.modalDialog.handler.find('#examinationAddForm');
		                      $("#userDataGrid").datagrid("clearSelections");
		                    //之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
		                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
		                    parent.$.modalDialog.handler.dialog('close');
		                    parent.$.messager.alert('成功', result.msg, 'check');
		                } else {
		                    var form = $('#examinationAddForm');
		                    parent.$.messager.alert('错误', result.msg, 'error');
		                }
				}
			});
		 }
   
    function nextagain(){
    	//$("#tab4").html("<table id='tab2'><tr></tr><table/><br/> <br/><center> <input type='button' onclick='nextfinish()' value='完成'></center><center> <input type='button' onclick='nextagain()' value='重新生成'></center>");
    	 //var $tr= $("#tab4 tr:last");
    	 var b = $('#num').val();
    	 $.ajax({
				url:"${path}/examination/selectRandom?num="+b,
				type:"post",
				dataType:"json",
				success:function(result){
					var html  =  "";
					 $("#tab3").hide();
				        $("#tab4").css('display','block');
				           var arr = result.obj;
				        for(var i=0;i<arr.length;i++){
				        	var v = arr[i];
					
							
							var g = v.answer;
									var ans_html = "";
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
	    							 
//$tr.after("<tr><td>"+v.title+"</td><td>"+v.level+"</td></tr>");
							// var g = v.answer;
							// $tr.after("<tr></tr>");
							// var $th= $("#tab4 tr:last");
							// for(var j=0;j<g.length;j++){
							//	   var h = g[j];
							//	 $th.after("<td>"+h.answerContent+"<input type='hidden' name='id' value='"+h.id+"'/></td>");
							// } 
				        }
						console.log(html);
						$("#tab2").html(html);
				}
			});
    }
    function nextfive(page){
   	 if(page=1){
   		 j=j-1;
   	 }else if(page==2){
   		 j=j+1;
   	 }else if(page==3){
   		 j=1;
   	 }else if(page=0){
   		 j=1;
   	 } else{
   		 j=max;
   	 }
   	 
   	$("#tab6").html("<tr id='firstid'><tr/>"); 
   
	var arr = document.getElementsByName("ids");
	
	
	for(var i=0;i<arr.length;i+=1){
		if(arr[i].checked){
			ids+= arr[i].value+",";
		}
	}
	ids = ids.substring(0, ids.length-1);
		 $.ajax({
				url:"${path}/examination/selectPage?page="+j+"&ids="+ids,
				type:"post",
				dataType:"json",
				success:function(result){
					var html  =  "";
				           var arr = result.obj;
				        for(var i=0;i<arr.length;i++){
	                    var v = arr[i];
							var g = v.answer;
									var ans_html = "";
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

	    				html +="<tr><td> <table> <tr><td><input  type='checkbox' name='ids' value='"+v.id+"'/>"+v.num+"."+v.title+"</td></tr><tr><td>"+ans_html+"</td></tr> </table></td><td>"+v.level+"</td></tr>";
	    							 
				        }
				        $("#tab6").html(html); 
				}
			});
	 
    }
  
</script>
<div  id="one" class="easyui-layout" data-options="fit:true,border:false" >
    <div  id="a" data-options="region:'center',border:false" style="overflow-y:scroll; overflow-x:scroll;padding: 3px;" >
        <form id="examinationAddForm" method="post" enctype="multipart/form-data" accept-charset="utf-8">
           <div id="tab" >
            <table class="grid" >
            
                <tr >
                   <td >  标题：</td>
                    <td ><input id="title" name="title" type="text" placeholder="请输入标题" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                 <td>类型</td>
                  
                    <td><select id="type" name="type" 
                                data-options="width:140,height:29,editable:false,panelHeight:'auto'"
                                 >
	                     <option value="0" >请选择</option>
	                     <option value="1" >随机选题</option>
                         <option value="2" >手动选题</option>
                       </select>
                    </td>
                </tr> 
                 <tr>
                  <td>考试开始日期</td>
					<td>
                        <input id="beginTime" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="beginTime" placeholder="点击选择时间" type="text" class="easyui-validatebox span2" data-options="required:true" id="datetime" />
                        </td>
               
                    <td>考试截止日期</td>
					<td>
                        <input id="endTime" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endTime" placeholder="点击选择时间" type="text" class="easyui-validatebox span2" data-options="required:true" id="datetime" />
                        </td>
                 </tr>
                 
                   <tr>
                
                        <td >  内容：</td>
                    <td >
                      <textarea  id="info" name="info"  placeholder="请输入内容" class="easyui-validatebox span2" data-options="required:true" style="width: 250px;height: 120px;"></textarea>
                    </td>
                 
                   <!--  
                   <td>考试范围</td>
					<td><select id="userEditorganizationId" name="receiver"
						style="width: 140px; height: 29px;" class="easyui-validatebox"
						data-options="required:true"></select></td>
						-->
                 </tr>
                 
            </table  >
            <table id="random" style="display: none;">
            <tr>
                 <td > 
                                                                              请输入随机选题的数量: </td>
                   <td  >
                       <input type="text" id="num" name="num" />
                    </td>
            </tr>
            </table>
            <br/>
            <br/>
                        <center>  <input type="button" onclick="nextOne()" value="下一步"></center>
                    
                        </div>
                       
          
           
             <div id="tab4"  style="display: none;" align="center">
                 <table id="tab2" width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                      </tr>
                 </table>
                  <br/>
                <br/>
                
                <center> <input type="button" onclick="nextagain()" value="重新生成"></center>
                
                  <br/>
                <br/>
                <center> <input type="button" onclick="nextsix()" value="下一步"></center>
             </div>
             <div id="tab5"  style="display: none;" align="center">
                 <table id="tab6" width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr id="firstid">
                      
                        
                      </tr>
                      
                 </table>
                 <br/><br/> 
                <a href='javascript:void(0);' onclick="nextfive(3)">首页</a>
                 <a href='javascript:void(0);' onclick="nextfive(1)">上一页</a>
                 <a href='javascript:void(0);' onclick='nextfive(2)'>下一页</a> 
                 <a href='javascript:void(0);' onclick="nextfive(4)">尾页</a>
                 <input type='button' onclick='nextseven()' value='下一步'></center>
             </div>
        </form>
          </div> 
        
   
    
  
    
</div>
<div id="tow" class="easyui-layout" data-options="fit:true,border:false" >
 <div  id="a" data-options="region:'north',border:false" style="height:70px;overflow: hidden; ">
      <h3>请在下列中选择考试用户：</h3>
 </div>

    <div id="c" data-options="region:'center',border:true,title:'用户列表'" >
        <table id="userDataGrid2" data-options="fit:true,border:false"></table>
    </div>
    <div id="w" data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:240px;overflow: hidden; ">
        <ul id="organizationTree2" style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
     <div id="s" data-options="region:'south',border:true,split:false,title:'选项'"  style="height:70px;overflow: hidden; ">
           <table  width="100%" >
            
                <tr >
                <!-- 
                    <td align="right"><input type="button" value="确定" onclick="allchoose()" /></td>
                   -->
                  <center> <input type="button" onclick="nextfinish()" value="完成"></center>
                </tr> 
                </table>
    </div>
</div>
