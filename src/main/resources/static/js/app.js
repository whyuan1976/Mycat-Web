'use strict';

/* App Module */
var conApp = angular.module('conApp', [
                                                 'ngRoute',
                                                 //'mycateyeAnimations',
                                                 'conControllers',
                                                 //'mycateyeFilters',
                                                 //'mycateyeServices'
]);











var mycateyeApp = angular.module('mycateyeApp', [
  'ngRoute',
  //'mycateyeAnimations',
  'mycateyeControllers',
  //'mycateyeFilters',
  //'mycateyeServices'
]);

mycateyeApp.config(['$routeProvider',
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

