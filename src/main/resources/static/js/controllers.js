'use strict';

/* Controllers */
//var mycateyeControllers = angular.module('mycateyeApp', []);
var mycateyeControllers = angular.module('mycateyeControllers', []);

// ----- Menu页面 ------
mycateyeControllers.controller('MenuCtrl', function ($scope,$http,$location) {
	$scope.zone_connected = false;
	
    $http.get("/zones").success(function(json){
    	$scope.zone_connected = true;
        $scope.zones = json;
        $scope.val = $scope.zones[0].guid;
        $scope.getZoneInfo($scope.val);
    });
        
    $scope.getZoneInfo= function(id){
    	$http.get("/menus/zones/"+id).success(function(json){    		
            $scope.menus = json;
        });
    }
        
    $scope.getMenuIcon= function(type){
    	switch(type){
    	case "1":  return "fa fa-cloud";	//Zone
        case "2" : return "fa fa-cubes"; 	//clust group
	    case "3" : return "fa fa-cogs"; //clust node
	    case "4" : return "fa fa-codepen"; //host group
		case "5" : return "fa fa-circle";//host node
	     case "6" : return "fa fa-th";  // project group
	     case "7" : return "fa fa-file"; //project node
	     case "8" : return"fa fa-circle-o"; //node
	     case "9" : return"fa fa-circle-o"; //LBS
	     case "10" : return"fa fa-circle-o"; //LB
       }
    }
});


//----- Zookeeper配置页面 ------
mycateyeControllers.controller('ZkConfigCtrl',  ['$scope', '$routeParams',
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