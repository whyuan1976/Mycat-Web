'use strict';

/* App Module */
var zkApp = angular.module('zkApp', [
                                                 'ngRoute',
                                                 //'mycateyeAnimations',
                                                 'zkControllers',
                                                 //'mycateyeFilters',
                                                 //'mycateyeServices'
]);


zkApp.config(['$routeProvider',
  function($routeProvider) {	
    $routeProvider.
      when('/mysql_info', {
        templateUrl: 'pages/manager/mysql.html',
        controller: 'PhoneListCtrl'
      }).
      when('/zkconfig', {
        templateUrl: 'pages/zk/zkconfig.html',
        controller: 'ZkConfigCtrl'
      }).
      when('/zkread', {
          templateUrl: 'pages/zk/zkread.html',
          controller: 'ZkReadCtrl'
        }).
      otherwise({
        redirectTo: '/'
        
      });
  }]);

