app.controller('filelistControllers', ['$scope', "$stateParams", '$controller', '$http', '$state', '$translate',
    function ($scope, $stateParams, $controller, $http, $state, $translate) {
        $.ajax({
            type: "GET",
            async: false,
            dataType: "json",
            url: "/file/messages",
            success: (function (data) {
                $scope.fileTable = data;
            }),
            error: $scope.getServicePluginsError
        });
        $scope.pageSize = 25;
        $scope.page = 0;
        $scope.add = function () {
            $state.go("app.file.fileAdds");
        };
        $scope.selectmac = function (id) {
               $state.go("app.file.maclist",{userid: id});
        };
        $scope.ralate = function (id) {
            bootbox.confirm($translate.instant('CERTIFICATE.RELATION') + "?", function (result) {
                if (!result) {
                    return;
                }
                $.ajax({
                    type: "PUT",
                    dataType: "json",
                    url: "/file/messages/r/" + id,
                    success: $scope.putmessageSuccess,
                    error: $scope.putmessageError
                });
            });
        };
        $scope.access = function (id) {
            bootbox.confirm($translate.instant('CERTIFICATE.ACCESS') + "?", function (result) {
                if (!result) {
                    return;
                }
                $.ajax({
                    type: "PUT",
                    dataType: "json",
                    url: "/file/messages/a/" + id,
                    success: $scope.putmessageSuccess,
                    error: $scope.putmessageError
                });
            });
        };

        $scope.uaccess = function (id) {
            bootbox.confirm($translate.instant('CERTIFICATE.UACCESS') + "?", function (result) {
                if (!result) {
                    return;
                }
                $.ajax({
                    type: "PUT",
                    dataType: "json",
                    url: "/file/messages/ap/" + id,
                    success: $scope.putmessageSuccess,
                    error: $scope.putmessageError
                });
            });
        };
        $scope.uralate = function (id) {
            bootbox.confirm($translate.instant('CERTIFICATE.URELATION') + "?", function (result) {
                if (!result) {
                    return;
                }
                $.ajax({
                    type: "PUT",
                    dataType: "json",
                    url: "/file/messages/rp/" + id,
                    success: $scope.putmessageSuccess,
                    error: $scope.putmessageError
                });
            });
        };
        $scope.putmessageSuccess = function (response) {
            $scope.fileTable = response;
            $scope.$digest();
        };
        $scope.putmessageError = function (response) {
        };
        $scope.searchByUserid= function () {
            console.log($scope.bbbb);
            var t = document.getElementById("keyid");
            console.log(t);
            console.log(t.value);
            if(t.value == null || t.value.trim()==""){
                bootbox.alert("用户ID不能为空");
                return;
            }
            $.ajax({
                type: "Get",
                dataType: "json",
                async: false,
                url: "/file/messages/userid/" + t.value,
                success: (function (data) {
                    $scope.fileTable = data;
                }),
                error: $scope.getmessageError
            });
        };
        $scope.searchByDeviceMac= function () {
            var t = document.getElementById("keyid");
            if(t.value == null || t.value.trim()==""){
                bootbox.alert("设备MAC不能为空");
                return;
            }
            $.ajax({
                type: "Get",
                dataType: "json",
                async: false,
                url: "/file/messages/device/" + t.value,
                success: (function (data) {
                    $scope.fileTable = data;
                }),
                error: $scope.getmessageError
            });
        };
        $scope.getmessageSuccess = function (response) {
            $scope.fileTable = response;
            $scope.$digest();
        }
        $scope.getmessageError = function (response) {
            $scope.fileTable = null;
        }
    }

]);
