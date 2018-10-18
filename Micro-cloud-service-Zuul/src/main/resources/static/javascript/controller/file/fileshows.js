app.controller('fileshowControllers', ['$scope',"$stateParams", '$controller', '$http', '$state', '$translate', function ($scope, $stateParams, $controller, $http, $state, $translate) {
    $scope.formData = angular.fromJson($stateParams.messageId) ;
    $scope.id=$scope.formData.certid ;
  //  $scope.device
    $scope.submitServiceForm = function () {
        var headers =  {
            'Content-Type': 'application/json; charset=UTF-8',
            'cache-control': 'no-cache'
        }
        var id= $stateParams.id ;
        var  messageid=$scope.id;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/file/savefiles",
            data:{
              "messageid":  messageid
            },
            success:$scope.addPolicySuccess,
            error:$scope.addPolicyError
        });
    }

    $scope.addPolicySuccess = function (date) {
        bootbox.alert("证书信息保存成功");
        $state.go("app.file.lists");
    };

    $scope.addPolicyError = function () {

    };
}
]);
