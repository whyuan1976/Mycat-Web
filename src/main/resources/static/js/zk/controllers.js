'use strict';

/* Controllers */
var zkControllers = angular.module('zkControllers', ['ui.bootstrap']);

var ModalInstanceCtrl = function ($scope, $modalInstance, client) {


};



// ----- Menu页面 ------
zkControllers.controller('ConnectionsCtrl', function ($scope,$modal,$http,$location) {

	//取得所有zk配置信息
    $http.get("/zks").success(function(json){    	
        $scope.zks = json;
    });
   
    //按照index，计算背景色
    $scope.getBackground= function(index){
    	switch(index%4){
    	case 0:  return "small-box bg-aqua";	
        case 1 : return "small-box bg-green"; 	
	    case 2 : return "small-box bg-yellow"; 
	    case 3 : return "small-box bg-red"; 
       }
    };
    $scope.delete = function(zk,index){
    	 if (confirm("确定删除吗？")) {
    	        // todo code for deletion
    		 $scope.zks.splice(index,1);
    	 }
    }
    
    
    $scope.open = function(zk,size){  //打开模态 
    	var tmp;
    	if (zk != undefined)
    		tmp = zk;
        var modalInstance = $modal.open({
            templateUrl : 'myModelContent.html',  //指向上面创建的视图
            controller : 'ModalInstanceCtrl',// 初始化模态范围
            size : size, //大小配置
            resolve : {
                data : function(){
                    return tmp;
                }
            }
        });
        modalInstance.result.then(function(result){  
        	$scope.zks.splice(0,0,result);
        },function(){
        });
        
    } 


});

//----- Zookeeper配置页面 ------
zkControllers.controller('ModalInstanceCtrl', function($scope,$modalInstance,data){ //依赖于modalInstance
	if (data !=undefined)
		$scope.zk = {name:data.name,port:data.port,host:data.host};
	else
		$scope.zk = {port:2181};
    $scope.ok = function(){
    	if (data !=undefined)
    		data.name = $scope.zk.name;

        $modalInstance.close($scope.zk); //关闭并返回当前选项
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    };
});




//----- Zookeeper配置页面 ------
zkControllers.controller('ZkConfigCtrl',  ['$scope', '$routeParams',
  function($scope,$routeParams) {
	//初始值设置
    //$scope.zk_ip='192.168.0.1';
    $scope.zk_port=2181;
    $http.get("/zkconfig").success(function(json){
        $scope.zk_ip = json.ip;
        $scope.zk_port=json.port;
    });
  }

]);