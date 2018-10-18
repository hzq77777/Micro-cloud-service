app.controller('fileAddControllers', ['$scope', '$controller', '$http', '$state', '$translate', function ($scope, $controller, $http, $state, $translate) {
    $scope.submitPolicyForm = function () {
        var formFile = new FormData();
        var file = document.getElementById("fileUpload").files[0];
        formFile.append('file', file);
        var data = formFile;
        $.ajax({
            url: "/file/uploads",
            data: data,
            type: "POST",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: $scope.addPolicySuccess,
            error: $scope.addPolicyError
        })
    }

    $scope.addPolicySuccess = function (data) {
        $scope.formData = angular.fromJson(data) ;
        if("true"== $scope.formData.ZXSJJRAQ_ZDGL_ZDFW_DRU_RES)
          bootbox.alert("证书信息保存成功");
        else
         bootbox.alert("证书信息保存失败、用户ID或设备名称已存在");
        $state.go("app.file.lists");
    };

    $scope.addPolicyError = function () {

    };
    // app.file.lists
    //
    $scope.goBack = function () {
        $state.go("app.file.lists");
    };
}
]);
