/* 由于spring security跳转登录页面原因，不走angularjs框架 */
$(function () {
    'use strict';

    function loginSuccess(response) {
        $state.go("app.file.lists");
        //window.location.replace('/index.html');
    };

    function loginError(response, status) {

    };

    $('#signInForm').submit(function() {
        var data = {
            username : $('#username').val().trim(),
            password : $('#password').val().trim()
        };
        ajaxFormPost('login', data, loginSuccess, loginError);

        return false;
    });
});

/*
app.controller('signinController', ['$scope', '$http', '$state',function($scope, $http, $state) {
    $scope.loginSuccess = function (response) {
        $state.go('app.home');
    };

    $scope.loginError = function (response, status) {

    };

    $scope.login = function() {
        var data = {
            j_username : $scope.username,
            j_password : $scope.password
        };
        ajaxFormPost($http, 'j_spring_security_check', data, $scope.loginSuccess, $scope.loginError);
        return false;
    };
}]);
*/
