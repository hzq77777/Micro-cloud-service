'use strict';
/* angular.module('app')*/
app.config(['$urlRouterProvider',function($urlRouterProvider){
    $urlRouterProvider
        .when('/', ['$state', function ($state) {
            $state.go('app.file.lists');
        }]).when('/index', ['$state', function ($state) {
        $state.go('app.file.lists');
    }]).when('', ['$state', function ($state) {
        $state.go('app.file.lists');
    }]);
    $urlRouterProvider.otherwise("/404");
}]);

app.config(['$routeProvider', '$stateProvider', '$urlRouterProvider','$controllerProvider',
    '$compileProvider', '$filterProvider', '$provide',
    '$locationProvider', '$httpProvider',function($routeProvider,$stateProvider,$urlRouterProvider,$controllerProvider,
                                                  $compileProvider, $filterProvider, $provide,
                                                  $locationProvider, $httpProvider) {
        app.controller = $controllerProvider.register;
        app.directive = $compileProvider.directive;
        app.filter = $filterProvider.register;
        app.factory = $provide.factory;
        app.service = $provide.service;
        app.constant = $provide.constant;
        app.value = $provide.value;

        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }
        // Disable IE ajax request caching
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
        $stateProvider
            .state('app', {
                url: "/app",
                abstract: true,
                templateUrl: "template/home/home.html"
            })
            .state('signin',{
                url:'/signin',
                templateUrl: "template/signin/signin.html",
                controller: 'signinController'
            })
            .state('app.file',{
                url:'/file',
                template: '<div ui-view class="fade-in-down"></div>'
            })

            .state('app.file.fileAdds',{
                url:'/fileAdds',
                templateUrl: "template/file/file_edit.html",
                controller: 'fileAddControllers'

            })
            .state('app.file.fileshows',{
                url:'/fileshows',
                params:{messageId:null},
                templateUrl: "template/file/file_shows.html",
                controller: 'fileshowControllers'
            })
            .state('app.file.lists',{
                url:'/lists',
                params:{messageId:null},
                templateUrl: "template/file/file_tables.html",
                controller: 'filelistControllers'
            })
            .state('app.file.maclist',{
                 url:'/maclists',
                 params:{userid:null},
                 templateUrl: "template/file/mac_tables.html",
                 controller: 'filemacControllers'
             })
    }]
);
app.run(
    ['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }
    ]
);

