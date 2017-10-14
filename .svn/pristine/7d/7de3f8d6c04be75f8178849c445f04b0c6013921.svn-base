
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var positionMarkDataGrid;
    var sendInfoDataGrid1;
    var stype1;
    function position(stype){
    	stype1 = stype;
    	$("#sendinfoid").css("height","0px");
    	$("#positionmark").css("height","100%");
    	positionMarkDataGrid = $('#positionMarkDataGrid').datagrid({
            url : '${path}/positionMark/dataGrid?stype='+stype,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'desc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            frozenColumns : [ [ {
                width : '60',
                title : '编号',
                field : 'id',
                sortable : true
            }, {
                width : '120',
                title : '名称',
                field : 'name',
                sortable : true
            }, {
                width : '90',
                title : '标记人',
                field : 'username',
                sortable : true
            }, {
                width : '120',
                title : '类型',
                field : 'type',
                sortable : true,
                formatter : function(value, row, index) {
                    switch(value){
                    case 0: return "界碑";
                    case 1: return "巡防路";
                    case 2: return "邻国边境部署";
                    }
                }
            }, {
                width : '90',
                title : '经度',
                field : 'longitude',
                sortable : true
            }, {
                width : '90',
                title : '纬度',
                field : 'latitude',
                sortable : true
            }, {
                width : '120',
                title : '状态',
                field : 'infoStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '未查看';
                    case 1:
                        return '信息已查看';
                    case 2:
                        return '审核已通过';
                    case 3:
                        return '审核未通过';
                    }
                }
            }, {
                width : '140',
                title : '创建时间',
                field : 'createTime',
                sortable : true
            }, {
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str =""; 
                    if (stype==2) {
    				  str = $.formatString('<a href="javascript:void(0)" class="positionMark-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-check icon-blue\'" onclick="see(\'{0}\');" >查看</a>', row.id);
    				  if(row.mmid==null){
    				  	str += $.formatString('<a href="javascript:void(0)" class="positionMark-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-print icon-blue\'" onclick="setcriterion(\'{0}\');" >设置标准库</a>', row.id);
    				  }
                    }else{
    	              str = $.formatString('<a href="javascript:void(0)" class="positionMark-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="positionMarkEditFun(\'{0}\');" >审核</a>', row.id);
                    }
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.positionMark-easyui-linkbutton-edit').linkbutton({text:'审核'});
                $('.positionMark-easyui-linkbutton-check').linkbutton({text:'设置标准库'});
                $('.positionMark-easyui-linkbutton-check').linkbutton({text:'查看'});
            },
            toolbar : '#positionMarkToolbar'
        });
    }
/**
 * 审核
 */
function positionMarkEditFun(id) {
    if (id == undefined) {
        var rows = positionMarkDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        positionMarkDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '审核',
        width : 700,
        height : 600,
        href :  '${path}/positionMark/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = positionMarkDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#positionMarkEditForm');
                f.submit();
            }
        } ]
    });
}
function see(id) {
    if (id == undefined) {
        var rows = positionMarkDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        positionMarkDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '查看',
        width : 700,
        height : 600,
        href :  '${path}/positionMark/see?id=' + id,
        buttons : [ {
            text : '关闭',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = positionMarkDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        } ]
    });
}
/**
 * 设为标准库
 */
 function setcriterion(id){
	 if (id == undefined) {//点击右键菜单才会触发这个
         var rows = positionMarkDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
    	 positionMarkDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要将当前信息设为标准标注？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/positionMark/setcriterion', {
                 uuid : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     positionMarkDataGrid.datagrid('reload');
                 }else{
                	 parent.$.messager.alert('提示', result.msg, 'error');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}
/**
 * 清除
 */
function positionMarkCleanFun() {
    $('#positionMarkSearchForm input').val('');
    $('#positionMarkSearchForm select').val('');
    positionMarkDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function positionMarkSearchFun() {
     positionMarkDataGrid.datagrid('load', $.serializeObject($('#positionMarkSearchForm')));
}
//=====================报送信息===========
    function sendinfo(stype) {
    	stype1 = stype;
    	$("#sendinfoid").css("height","100%");
    	$("#positionmark").css("height","0px");
//     	$("#positionmark").css("display","none");
//     	$("#sendinfoid").css("display","block");
        sendInfoDataGrid1 = $('#sendInfoDataGrid1').datagrid({
            url: '${path}/sendInfo/dataGrid?stype='+stype1,
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            frozenColumns: [[{
                width: '4%',
                title: '编号',
                field: 'id',
                sortable: true
            }, {
                width: '6%',
                title: '报送人',
                field: 'sendUserName',
                sortable: true
            }, {
                width: '10%',
                title: '报送标题',
                field: 'sendInfoName',
                sortable: true
            }, 
            {
                width: '12%',
                title: '报送时间',
                field: 'receiveTime',
                sortable: true
            },
            {
                width: '28%',
                title: '报送内容',
                field: 'sendInfo',
                sortable: true
            },
            {
                width: '10%',
                title: '报送信息类型',
                field: 'sendInfoType',
                sortable: true,
                formatter: function (value, row, index) {
                    //报送信息类型：0-基础设施；1-涉外时间；2-走私事件；3-突发事件
                    switch (value) {
                        case 0:
                            return '基础设施';
                        case 1:
                            return '涉外事件';
                        case 2:
                            return '走私事件';
                        case 3:
                            return '突发事件';
                    }
                }
            },
            {
                width: '10%',
                title: '紧急程度',
                field: 'urgencyLevel',
                sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '一般';
                        case 1:
                            return '紧急';
                        case 2:
                            return '特急';
                       
                    }
                }
            }
            , {
                width: '10%',
                title: '报送信息状态',
                field: 'infoStatus',
                sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '未查看';
                        case 1:
                            return '已查看';
                        case 2:
                            return '已审核';
                        case 3:
                            return '审核未通过';
                    }
                }
            }, {
                field: 'action',
                title: '操作',
                width: '10%',
                formatter: function (value, row, index) {
                    var str =""; 
                    if (stype==2) {
    				 str = $.formatString('<a href="javascript:void(0)" class="sendInfo-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-check icon-blue\'" onclick="sendInfoseeFun(\'{0}\');" >查看</a>', row.id);
                    }else{
                     str = $.formatString('<a href="javascript:void(0)" class="sendInfo-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="sendInfoEditFun(\'{0}\');" >审核</a>', row.id);
                    }
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.sendInfo-easyui-linkbutton-edit').linkbutton({text: '审核'});
                $('.sendInfo-easyui-linkbutton-check').linkbutton({text:'查看'});
            },
            toolbar: '#sendInfoToolbar'
        });
    }
    /**
     * 审核
     */
    function sendInfoEditFun(id) {
        if (id == undefined) {
            var rows = sendInfoDataGrid1.datagrid('getSelections');
            id = rows[0].id;
        } else {
            sendInfoDataGrid1.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title: '审核',
            width: 700,
            height: 600,
            href: '${path}/sendInfo/verify?id=' + id,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = sendInfoDataGrid1;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#sendInfoEditForm');
                    f.submit();
                }
            }]
    	});
	}
    function sendInfoseeFun(id) {
        if (id == undefined) {
            var rows = sendInfoDataGrid1.datagrid('getSelections');
            id = rows[0].id;
        } else {
            sendInfoDataGrid1.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title: '查看',
            width: 700,
            height: 600,
            href: '${path}/sendInfo/see?id=' + id,
            buttons: [{
                text: '关闭',
                handler: function () {
                	parent.$.modalDialog.openner_dataGrid = sendInfoDataGrid1;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }
            }]
    	});
	}


/**
 * 清除
 */
function sendInfoCleanFun() {
    $('#sendInfoSearchForm1 input').val('');
    $('#sendInfoSearchForm1 select').val('');
    sendInfoDataGrid1.datagrid('load', {});
}
/**
 * 搜索
 */
function sendInfoSearchFun() {
     sendInfoDataGrid1.datagrid('load', $.serializeObject($('#sendInfoSearchForm1')));
}


function exportSendInfo(){
	window.location.href="${path}/sendInfo/exportSendInFo?stype="+stype1+"&"+$("#sendInfoSearchForm1").serialize();
}

function exportpositionMark(){
	window.location.href="${path}/positionMark/exportpositionMark?stype="+stype1+"&"+$("#positionMarkSearchForm").serialize();
}

$(function() {
	$("#sendinfoid").css("height","100%");
	$("#positionmark").css("height","0px");
	sendinfo(1);
});
</script>

<div id="sendinfoid" class="easyui-layout" data-options="fit:true,border:false"  style="height: 95%;">
    <div data-options="region:'north',border:false" style="height:92px;overflow: hidden; background-color: #fff">
        <div>
		    <a href="javascript:sendinfo(1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">待审报送</a>
		    <a href="javascript:sendinfo(2)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">已审报送</a>
		    <a href="javascript:position(1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">待审标记</a>
		    <a href="javascript:position(2)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">已审标记</a>
		</div>
        <form id="sendInfoSearchForm1"  >
            <table>
                <tr>
                	<th>报送人:</th>
                    <td><input name="sendUserName" id="sendUserName" placeholder="输入上报人"/></td>
                    <th>报送标题:</th>
                    <td><input name="sendInfoName" id="sendInfoName" placeholder="输入报送标题"/></td>
                    <th>报送时间:</th>
                    <td colspan="2" >
                        <input name="recvDateStart" id="recvDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" /><span style="float: left;">至</span>
                        <input  name="recvDateEnd" id = "recvDateEnd"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                </tr>
                
                <tr>
                	<th>报送信息类型:</th>
                    <td>
	                    <select name="sendInfoType"  id="sendInfoType" >
						    <!-- ：0-基础设施；1-涉外时间；2-走私事件；3-突发事件 -->
						    <option value="" selected="selected">全部</option>
						    <option value="0">基础设施</option>
						    <option value="1">涉外事件</option>
						    <option value="2">走私事件</option>
						    <option value="3">突发事件</option>
						</select>
                    </td>
                    <th>紧急程度:</th>
                    <td>
	                    <select name="urgencyLevel" id="urgencyLevel" >
						    <option value="" selected="selected">全部</option>
						    <option value="0">一般</option>
						    <option value="1">紧急</option>
						    <option value="2">特急</option>
						</select>
                    </td>
                    <th>报送信息状态:</th>
                    <td>
	                    <select name="infoStatus"  id="infoStatus" >
						    <!-- 0-未查看；1-信息已查看；2-审核已通过；3-审核未通过 -->
						    <option value="" selected="selected">全部</option>
						    <option value="0">未查看</option>
						    <option value="1">已查看</option>
						    <option value="2">已审核</option>
						    <option value="3">未审核</option>
						</select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="sendInfoSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="sendInfoCleanFun();">清空</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-download',plain:true" onclick="exportSendInfo();">导出</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
    <div data-options="region:'center',border:false">
        <table id="sendInfoDataGrid1" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="positionmark" class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	    <div>
		   <a href="javascript:sendinfo(1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">待审报送</a>
		    <a href="javascript:sendinfo(2)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">已审报送</a>
		    <a href="javascript:position(1)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">待审标记</a>
		    <a href="javascript:position(2)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">已审标记</a>
		</div>
        <form id="positionMarkSearchForm">
            <table>
                <tr>
                    <th>名称:</th>
                    <td><input name="name" placeholder="名称"/></td>
                    <th>类型:</th>
                    <td><select name="type" >
                    <option value="">请选择</option>
                    <option value="0">界碑</option>
                    <option value="1">巡防路</option>
                    <option value="2">邻国边境部署</option></select>
                   </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="positionMarkSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="positionMarkCleanFun();">清空</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-download',plain:true" onclick="exportpositionMark();">导出</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="positionMarkDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
