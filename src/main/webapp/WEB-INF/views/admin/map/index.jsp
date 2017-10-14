<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/Cesium/Cesium.js"></script>
<style>
    @import url(${staticPath }/static/Cesium/Widgets/widgets.css);
    html, body, #cesiumContainer {
        width: 100%; height: 100%; margin: 0; padding: 0; overflow: hidden;
    }
    .margin-0px{ margin: 0;}

    .maps-tips{
        display: block;
        box-sizing: content-box;
        position: relative;
    }
    .maps-tips img{
        max-width: 100%;
    }
    .maps-tips p{
        margin: 0;
        font-size: 15px;
        line-height: 30px;
    }
    .maps-tips .btn{
        width: 160px;
    }
    .align-center{ text-align: center; }
    .align-right{ text-align: right; }
    .align-left{ text-align: left; }
    .public-title{
        position: absolute;
        box-sizing: border-box;
        width: 100%;
        height: 64px;
        padding: 0 20px;
        z-index: 2;
    }
</style>
<div class="public-title" style="width: 10%;top: 50px;color: wheat;">
    <input  id="showMapLine" name="showMapLine" type="checkbox" value="0" onclick="showMapLine()" checked/>显示边界<br>
    <input  id="showMapMark" name="showMapMark" type="checkbox" value="1" onclick="showMapMark()"/>显示界碑<br>
    <input  id="autoFresh" name="autoFresh" type="checkbox" value="1" onclick="autoFresh()"/>自动刷新<br>

</div>
<div id="lineColor" class="public-title" style="height: 164px;padding: 0 0px;width: 12%;top: 80px;color: wheat;right: 0%;overflow-y: scroll;">
    <!-- <p >张三：<span style="background-color: rgb(209,238,238);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p> -->
   </div>
<div id="cesiumContainer"></div>

<script>
    var viewer; //地图实体
    var lineEntity; //龙州边境线实体
    var mapMarkEntity = new Array(); //所有界碑
    var personEntity = new Array(); //所有巡防人员
    var roadEntity = new Array();  //所有巡防路线
    var had_roadEntity = new Array();//已巡防的路线
    var patrol_data = new Array(); //巡防人员基础数据
    var data_uid = new Array(); //当前所有巡防人员ID

    var uid_now = new Array(); //正在巡防的巡防人员ID
    var  uid_add = new Array(); //新开始巡防的巡防人员ID
    var uid_del = new Array(); //结束巡防的巡防人员ID

    var peopleMap_now = new Array();//正在巡防的巡防人员
    var peopleMap_add = new Array();//新增的巡防人员
    var peopleMap_del = new Array();//结束巡防的巡防人员

    var lineColorMap = {};//装载  巡防人员：巡防路线颜色

    $(function(){

        initMap();//先加载地图
        initStaticEntity(); //加载静态数据，边界、界碑等
        getPeoples(); //获取巡防实时数据


    });

    function initMap(){
        var mylongitude = 106.85417175292945;
        var mylatitude = 22.34499936506692;
        var myheight = 100000;

        //BingMapApi key
        Cesium.BingMapsApi.defaultKey = "J6oHjfVD1h4EB0imMAEt~2ojF_8N_GXKSntrzgp70uA~AtCDJ0Zg6BNbhbUPyWmFEwBFraxXY24rXtQ63kVAesKlmcGOMUBgDXSmIS8LIZKv";

        //全球影像地图服务
        viewer = new Cesium.Viewer("cesiumContainer", {
            animation: false,  //是否显示动画控件
            baseLayerPicker: false, //是否显示图层选择控件
            //geocoder: true, //是否显示地名查找控件,默认true
            timeline: false, //是否显示时间线控件
            sceneModePicker: true, //是否显示投影方式控件
            navigationHelpButton: false, //是否显示帮助信息控件
            infoBox: true,  //是否显示点击要素之后显示的信息
            fullscreenButton: false,
            sceneMode: Cesium.SceneMode.SCENE2D,

        });
        viewer.imageryLayers.addImageryProvider(new Cesium.WebMapTileServiceImageryProvider({
            url: "http://t0.tianditu.com/img_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=img&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default&format=tiles",
            //url: "http://www.mapgx.com/ime-server/rest/tdtgx_img/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=img&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default&format=tiles",
            layer: "tdtBasicLayer",
            style: "default",
            format: "image/jpeg",
            tileMatrixSetID: "GoogleMapsCompatible",
            show: false
        }));

        //默认cesium地图
        // viewer = new Cesium.Viewer('cesiumContainer',{
        //   animation:false, //动画控制，默认true
        //   timeline:false,//时间线,默认true
        //   sceneMode:Cesium.SceneMode.SCENE2D,
        // } );

        //全球影像中文注记服务
        viewer.imageryLayers.addImageryProvider(new Cesium.WebMapTileServiceImageryProvider({
            url: "http://t0.tianditu.com/cia_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=cia&tileMatrixSet=w&TileMatrix={TileMatrix}&TileRow={TileRow}&TileCol={TileCol}&style=default.jpg",
            layer: "tdtAnnoLayer",
            style: "default",
            format: "image/jpeg",
            tileMatrixSetID: "GoogleMapsCompatible",
            show: false

        }));

        viewer.camera.setView({
            destination: Cesium.Cartesian3.fromDegrees(mylongitude, mylatitude, myheight),
            orientation: {
                heading: 0.0,
                pitch: -Cesium.Math.PI_OVER_TWO,
                roll: 0.0
            }
        });
        //设置HOME按钮定位到中国
        //Cesium.Rectangle.MAX_VALUE = Cesium.Rectangle.fromDegrees(65, -10, 145, 70);
        //Cesium.Camera.DEFAULT_VIEW_RECTANGLE = Cesium.Rectangle.fromDegrees(65, -10, 145, 70);

        Cesium.Rectangle.MAX_VALUE = Cesium.Cartesian3.fromDegrees(mylongitude, mylatitude, myheight);
        Cesium.Camera.DEFAULT_VIEW_RECTANGLE = Cesium.Cartesian3.fromDegrees(mylongitude, mylatitude, myheight);

        //取消双击事件
        viewer.cesiumWidget.screenSpaceEventHandler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
        //隐藏Cesium信息
        viewer.bottomContainer.style.display = "none";

        //初始化map的时候,给entity添加点击事件的监听
        //调用接口-气泡窗口
        var handler3D = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
        var setViewer = viewer;
        handler3D.setInputAction(function (movement) {
            //点击弹出气泡窗口
            var pick = setViewer.scene.pick(movement.position);
            if (pick && pick.id) {//选中某模型
                //判断点击的元素是不是巡防人员
                console.log(pick.id);
                //点击的是巡防人员
                if (pick.id._type.lastIndexOf("person") > -1) {
                    console.log("点击巡防人员：" + pick.id._id);
                    var cartographic = Cesium.Cartographic.fromCartesian(pick.id._position._value);//世界坐标转地理坐标（弧度）
                    var point = [cartographic.longitude / Math.PI * 180, cartographic.latitude / Math.PI * 180];//地理坐标（弧度）转经纬度坐标
                    var destination = Cesium.Cartesian3.fromDegrees(point[0], point[1]+0.0055, 4000.0);
                    var content = pick.id._description._value;
                    var css = {'width': '400px'};
                    var obj = {position: movement.position, destination: destination, content: content, css: css};
                    setViewer.infoWindow(setViewer, obj);
                } else if (pick.id._type.lastIndexOf("mapMark") > -1) {
                    //点击的是界碑
                    console.log("点击界碑：" + pick.id._id);
                    var cartographic = Cesium.Cartographic.fromCartesian(pick.id._position._value);//世界坐标转地理坐标（弧度）
                    var point = [cartographic.longitude / Math.PI * 180, cartographic.latitude / Math.PI * 180];//地理坐标（弧度）转经纬度坐标
                    var destination = Cesium.Cartesian3.fromDegrees(point[0], point[1] + 0.0055, 4000.0);
                    var content = pick.id._description._value;
                    var css = {'width': '400px'};
                    var obj = {position: movement.position, destination: destination, content: content, css: css};
                    setViewer.infoWindow(setViewer, obj);
                } else {
                    //其他，不显示冒泡
                    $(".cesium-selection-wrapper").hide();
                    $(".infowin3D").hide();
                }
            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    }

    //静态数据
   function initStaticEntity() {
       //先加载边界线
        $.ajax({
            type: "GET",
            url: "${staticPath }/map/QueryConfig?selKey=map_line",
            data: "",
            dataType: "json",
            success: function(data){
                if(data.success){
                    var lineStr = data.obj.value;
                    var mapLine_str = lineStr.split(",");
                    var mapLine_int = [];
                    for (var i = 0;i < mapLine_str.length;i++){
                        mapLine_int.push(Number(mapLine_str[i]));
                    }
                    console.log(mapLine_int);
                    //加载龙州边境线
                    lineEntity = viewer.entities.add({
                        type: "line",
                        polyline: {
                            positions: Cesium.Cartesian3.fromDegreesArray(mapLine_int),
                            width: 3,
                            material: Cesium.Color.RED
                        }
                    });
                }
            }
        });

        //加载界碑
        $.ajax({
            type: "GET",
            url: "${staticPath }/map/mapmark",
            data: "",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    var mapMarks = data.obj;
                    for (var j = 0; j < mapMarks.length; j++) {
                        var pinBuilder = new Cesium.PinBuilder();
                        var mapMark = viewer.entities.add({
                            id: "mapMark-" + mapMarks[j].id,
                            type: "mapMark",
                            position: Cesium.Cartesian3.fromDegrees(mapMarks[j].longitude, mapMarks[j].latitude),
                            show: false,
                            label: {
                                text: mapMarks[j].name,
                                font: '14pt monospace',

                                verticalOrigin: Cesium.VerticalOrigin.TOP
                            },
                            billboard: {
                                image: pinBuilder.fromColor(Cesium.Color.SALMON, 48).toDataURL(),
                                verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
                            }

                        });
                        mapMark.description =
                                '<div style="border-radius:10px ;padding:10px 10px;background-color: white;display: block;box-sizing: content-box;position: relative;" ><div class="maps-tips"> ' +
                                '<div class="row"> <div class="col-xs-5" style="float:left;width:30%"> ' +
                                '<h6 class="margin-0px align-center">' +
                                '<img alt="" src="' + mapMarks[j].icon + '" onerror="mapMarkImgOnError(this)" width="100px" height="100px" />' +
                                '</h6> <h5 class="align-center">' + mapMarks[j].name + '</h5> ' +
                                '</div> <div class="col-xs-7" style="float:rigth;height: 85px;">' +
                                '<p>界碑位置：<br />' + mapMarks[j].longitude + '，' + mapMarks[j].latitude + '</p> ' +
                                '</div> </div> <hr /> ' +
                                '</div></div>';

                        //存所有界碑
                        mapMarkEntity.push(mapMark);
                    }
                }
            }
        });
}


        //地图初始化后，获取巡防人员数据
    function getPeoples() {
        $.ajax({
            type: "GET",
            url: "${staticPath }/map/watchman",
            data: "",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    //先存储旧数据
                    var old_map = {};
                    console.log("巡防人员旧数据",patrol_data);
                    for (var i = 0; i < patrol_data.length; i++) {
                        old_map[patrol_data[i].uid] = patrol_data[i];
                    }
                }

                //获取新数据
                patrol_data = data.obj;
                console.log("巡防人员新数据",patrol_data);
                    var uid = [];//新获取到的uid
                    //var map_uid_patrolid = {};
                    var new_map = {};
                    //新数据处理
                    for (var i = 0; i < patrol_data.length; i++) {
                        console.log(patrol_data[i].uid);
                        uid.push(patrol_data[i].uid);
                        new_map[patrol_data[i].uid] = patrol_data[i];
                    }

                    //先去重
                    // uid = commonAction.unique(uid);
                    // data_uid = commonAction.unique(data_uid);
                    console.log("data_uid:" + data_uid);
                    console.log("uid:" + uid);
                    var unionArray = union_array(data_uid, uid);//求并集
                    uid_now = intersect_array(data_uid, uid);//求交集-即正在巡防的人员
                    uid_add = minus_array(unionArray, data_uid);//求差集-即新增巡防的人员
                    uid_del = minus_array(unionArray, uid);//求差集-即退出巡防的人员
                    //执行完后，更新data_uid为最新的数据
                    data_uid = uid;

                    console.log("并集：" + unionArray);

                    //得到交集，装载正在巡防人员entity
                    console.log("交集：" + uid_now);
                    peopleMap_now = [];
                    for (var i = 0; i < uid_now.length; i++) {
                        peopleMap_now.push(new_map[uid_now[i]]);
                    }
                    console.log("正在巡防map_now：" , peopleMap_now);

                    //得到差集，装载新增巡防人员entity
                    console.log("新增：" + uid_add);
                    peopleMap_add = [];
                    for (var i = 0; i < uid_add.length; i++) {
                        peopleMap_add.push(new_map[uid_add[i]]);
                    }
                    console.log("新增map_add：" + peopleMap_add);

                    console.log("删除：" + uid_del);
                    peopleMap_del = [];
                    for (var i = 0; i < uid_del.length; i++) {
                        peopleMap_del.push(old_map[uid_del[i]]);
                    }
                    console.log("删除map_del：" + peopleMap_del);
                    //执行 刷新地图实体
                    doUpdateEntity();

            }
    });
    }

    //刷新 地图实体
    function doUpdateEntity() {
        //新增巡防人员
        initDynamicEntity_add(peopleMap_add);
        //移除巡防人员
        initDynamicEntity_del(peopleMap_del);
        //更新巡防人员
        initDynamicEntity_update(peopleMap_now);
        //显示路线颜色标识
        showLineColor();
    }

    //动态数据渲染到地图上-add
    function initDynamicEntity_add(data) {
        console.log("新增巡防人员begin...",data)
        var d = data;

        //for循环开始
        for (let i = 0; i < d.length; i++) {
            //alert(d[i].name+"="+d[i].longitude+","+d[i].latitude);

            //显示巡防人员位置
            var person = viewer.entities.add({
                id: "person-" + d[i].uid + "-" + d[i].patrolid,
                type: "person",
                position: Cesium.Cartesian3.fromDegrees(d[i].longitude, d[i].latitude),
                billboard: {
                    image: '${staticPath }/static/style/images/u406.png',
                    width: 33,
                    height: 50,
                    pixelOffset: new Cesium.Cartesian2(0, -25)
                },
                label: {
                    text: d[i].name,
                    font: '14pt monospace',
                    style: Cesium.LabelStyle.FILL_AND_OUTLINE,
                    outlineWidth: 2,
                    verticalOrigin: Cesium.VerticalOrigin.TOP,
                    pixelOffset: new Cesium.Cartesian2(0, 10)
                }
            });

            person.description =
                    '<div style="border-radius:10px ;padding:10px 10px;background-color: white;display: block;box-sizing: content-box;position: relative;" ><div class="maps-tips"> ' +
                    '<div class="row" > <div class="col-xs-5" style="float:left;width:30%">  ' +
                    '<h6 class="margin-0px align-center">' +
                    '<img alt="" src="' + d[i].icon + '" onerror="personImgOnError(this)" width="100px" height="100px"/>' +
                    '</h6> <h5 class="align-center">' + d[i].name + '</h5> ' +
                    '</div> <div class="col-xs-7" style="float:rigth"><input type="hidden" id="entityId" value="' + d[i].uid + '"> <p>巡防路段：' + d[i].roadname + '</p> ' +
                    '<p>巡防距离：' + d[i].totaldistance + 'KM</p> <p>已巡防　：' + d[i].patroldistance + 'KM</p> ' +
                    '<p>实时位置：<br />' + d[i].longitude + '，' + d[i].latitude + '</p> ' +
                    '</div> </div> <hr /> ' +
                    //'<h6 class="margin-0px align-center"> ' +
                    //'<a class="btn btn-success" id="reportbtn"><i class="glyphicon glyphicon-comment"></i>信息上报(' + d[i].reportcount + ')</a> ' +
                    //'<a class="btn btn-danger" id="videobtn"><i class="glyphicon glyphicon-facetime-video"></i> 视频回传</a> </h6> ' +
                    '</div></div>';

            //存所有巡防人员
            personEntity.push(person);

            //显示个人巡防路线
            var roadinfo = d[i].roadinfo;
            var roadinfo_positionsArray = [];
            for (var a = 0; a < roadinfo.length; a++) {
                roadinfo_positionsArray.push(roadinfo[a].longitude);
                roadinfo_positionsArray.push(roadinfo[a].latitude);
            }
            if (roadinfo_positionsArray.length > 0) {
                var raod = viewer.entities.add({
                    id: "road-" + d[i].uid + "-" + d[i].patrolid,
                    type: "road",
                    polyline: {
                        positions: Cesium.Cartesian3.fromDegreesArray(roadinfo_positionsArray),
                        width: 5,
                        material: Cesium.Color.BLUE
                    }
                });
                //存个人巡防路线
                roadEntity.push(raod);
            }

            //显示个人已经巡防的路线
            var patrolinfo = d[i].patrolinfo;
            var patrolinfo_positionsArray = [];
            for (var b = 0; b < patrolinfo.length; b++) {
                patrolinfo_positionsArray.push(patrolinfo[b].longitude);
                patrolinfo_positionsArray.push(patrolinfo[b].latitude);
            }

            var myColor = new Object();
            if (patrolinfo_positionsArray.length > 0) {
                var myMaterial = Cesium.Color.fromRandom({alpha: 0.9});
                var red = Cesium.Color.floatToByte(myMaterial.red);
                var green = Cesium.Color.floatToByte(myMaterial.green);
                var blue = Cesium.Color.floatToByte(myMaterial.blue);

                myColor.red = red;
                myColor.green = green;
                myColor.blue = blue;

//                var color1 = Cesium.Color.fromBytes(myMaterial.red,myMaterial.green,myMaterial.blue,myMaterial.alpha, color1);
//                console.log("转换颜色1-----"+color1);
//                var cartesian = new Cesium.Cartesian4(Number(myMaterial.red), Number(myMaterial.green), Number(myMaterial.blue), Number(myMaterial.alpha));
//                var color2 = Cesium.Color.fromCartesian4(cartesian, color2);
//                console.log("转换颜色2-----"+color2);

                var patrolinfo = viewer.entities.add({
                    id: "had_road-" + d[i].uid + "-" + d[i].patrolid,
                    type: "had_road",
                    polyline: {
                        positions: Cesium.Cartesian3.fromDegreesArray(patrolinfo_positionsArray),
                        width: 5,
                        // material : Cesium.Color.AQUA
                        material: myMaterial
                    }
                });
                //存个人已经巡防的路线
                had_roadEntity.push(patrolinfo);
            }
            //装载 巡防人实体ID：路线颜色map
            var tempMap = {};
            tempMap[d[i].name]=myColor;
            lineColorMap["person-" + d[i].uid + "-" + d[i].patrolid]=tempMap;
        }
        console.log("颜色",lineColorMap);
        console.log("新增巡防人员end...");
        //for循环结束
    }

    //动态数据渲染到地图上-update
    function initDynamicEntity_update(data) {
        console.log("更新巡防人员begin...",data);
        var d = data;
        //for循环开始

        for (let i = 0; i < d.length; i++) {
            //更新巡防人员坐标
            var id = 'person-' + d[i].uid + '-' + d[i].patrolid;
            var person = viewer.entities.getById(id);
            person.position = Cesium.Cartesian3.fromDegrees(d[i].longitude, d[i].latitude);

            //更新已巡防路线
            var roadid = "had_road-" + d[i].uid + '-' + d[i].patrolid;
            var patrolinfo = d[i].patrolinfo;
            var patrolinfo_positionsArray = [];
            for (var b = 0; b < patrolinfo.length; b++) {
                patrolinfo_positionsArray.push(patrolinfo[b].longitude);
                patrolinfo_positionsArray.push(patrolinfo[b].latitude);
            }

            if (patrolinfo_positionsArray.length > 0) {
                var patrolinfo = viewer.entities.getById(roadid);
                console.log(patrolinfo);
                //console.log(patrolinfo_positionsArray);
                patrolinfo.polyline.positions = Cesium.Cartesian3.fromDegreesArray(patrolinfo_positionsArray);
            }

        }
        console.log("更新巡防人员end...");
    }

    //动态数据渲染到地图上-del
    function initDynamicEntity_del(data) {
        console.log("删除巡防人员begin...",data);
        var d = data;
        //for循环开始
        for (let i = 0; i < d.length; i++) {
            //alert(d[i].name+"="+d[i].longitude+","+d[i].latitude);
            //移除巡防人员
            var id = 'person-' + d[i].uid + '-' + d[i].patrolid;
            var person = viewer.entities.getById(id);
            viewer.entities.remove(person);

            //移除个人巡防路线
            var roadid = "road-" + d[i].uid + '-' + d[i].patrolid;
            var road = viewer.entities.getById(roadid);
            viewer.entities.remove(road);

            //移除已巡防路线
            var had_roadid = 'had_road-' + d[i].uid + '-' + d[i].patrolid;
            var had_road = viewer.entities.getById(had_roadid);
            viewer.entities.remove(had_road);

            //移除颜色标识
            //lineColorMap.remove(id);
            delete lineColorMap[id];
        }
        //for循环结束
        console.log("删除巡防人员end...");
    }




    //控制界碑显示
    function showMapMark() {
        var mapMarks = mapMarkEntity;
        var checkbox_showMapMark = $('#showMapMark');
        if (checkbox_showMapMark.val() == '1') {
            for (var k = 0; k < mapMarks.length; k++) {
                mapMarks[k].show = true;
            }
            checkbox_showMapMark.val('0');
        } else {
            for (var k = 0; k < mapMarks.length; k++) {
                mapMarks[k].show = false;
            }
            checkbox_showMapMark.val('1');
        }

    }

    //控制边界显示
    function showMapLine() {
        var mapLine = lineEntity;
        var checkbox_showMapLine = $('#showMapLine');
        if (checkbox_showMapLine.val() == '1') {
            mapLine.show = true;
            checkbox_showMapLine.val('0');
        } else {
            mapLine.show = false;
            checkbox_showMapLine.val('1')
        }
    }

    //控制实时刷新
    var timer;
    function autoFresh() {
        var checkbox_autoFresh = $('#autoFresh');

        if (checkbox_autoFresh.val() == '1') {
            timer = setInterval(function () {  //5秒刷新一次，获取巡防实时信息
                console.log("开启实时刷新，5s刷新一次");
                getPeoples();
        }, 1000 * 5);
            checkbox_autoFresh.val('0')
        } else {
            clearInterval(timer);
            console.log("关闭实时刷新");
            checkbox_autoFresh.val('1')
        }

    }
    function showLineColor(){
        var lineColorHtml = "<table>";
        for(var ckey in lineColorMap){  //通过定义一个局部变量k遍历获取到了map中所有的key值
            var map=lineColorMap[ckey]; //获取到了key所对应的value的值！
            for(var key in map){  //通过定义一个局部变量k遍历获取到了map中所有的key值
                var value=map[key]; //获取到了key所对应的value的值！
                console.log(key+","+value);
				lineColorHtml+='<tr><td>'+key+':</td><td><hr style="width: 39px;height: 8px;border: none;margin-top: 0;margin-bottom: 0px;background-color:rgb('+value.red+','+value.green+','+value.blue+')"/></td></tr>';
            }
        }
		lineColorHtml+="</table>"
        console.log("颜色Html----"+lineColorHtml);
        $("#lineColor").html(lineColorHtml);
    }

    //求数组并集
    function union_array(a,b) {
        for (var i = 0, j = 0, ci, r = {}, c = []; ci = a[i++] || b[j++]; ) {
            if (r[ci]) continue;
            r[ci] = 1;
            c.push(ci);
        }
        return c;
    }
    //求数组差集
    function minus_array(arr1,arr2){
        var arr3 = [];
        for (var i = 0; i < arr1.length; i++) {
            var flag = true;
            for (var j = 0; j < arr2.length; j++) {
                if (arr2[j] == arr1[i]) {
                    flag = false;
                }
            }
            if (flag) {
                arr3.push(arr1[i]);
            }
        }
        return arr3;
    }
    //求数组交集
    function intersect_array(arr1,arr2){
        var ai=0, bi=0;
        var arr3 = new Array();
        while ( ai < arr1.length && bi < arr2.length )
        {
            if      ( arr1[ai] < arr2[bi] ) { ai++; }
            else if ( arr1[ai] > arr2[bi] ) { bi++; }
            else /* they're equal */
            {
                arr3.push ( arr1[ai] );
                ai++;
                bi++;
            }
        }
        return arr3;
    }
    //头像加载失败时显示默认图片
    function personImgOnError(obj){
        console.log(obj);
        obj.src="${staticPath }/static/style/images/p1.jpg";
    }

    //界碑图片不存在时显示默认图片
    function mapMarkImgOnError(obj){
        obj.src='"${staticPath }/static/style/images/logo.png';
    }

</script>
