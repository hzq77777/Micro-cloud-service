app.controller('filemacControllers', ['$scope', '$controller', '$http', '$state', '$translate','$stateParams', function ($scope, $controller, $http, $state, $translate,$stateParams) {
    // app.file.lists
    //
     var id  = $stateParams.userid;
      $.ajax({
           type: "Get",
           async: false,
           dataType: "json",
           url: "/file/messages/mac/" + id,
           success: (function (data) {
                          $scope.fileTable = data;
                      }),
           error: $scope.putmessageError
           });
     $scope.goBack = function () {
        $state.go("app.file.lists");
     };



        $scope.accecemac = function (id,mac) {
               var d = {};
                d.userId = id;
                d.mac =mac;
             $.ajax({
                     type: "POST",
                     dataType: "json",
                     url: "/file/mac/a/"+JSON.stringify(d),
                     success: $scope.postbangdingSuccess,
                     error: $scope.postbangdingError
               });
            }
           $scope.postbangdingSuccess = function (data) {
                $scope.formData = angular.fromJson(data) ;
               if("1"== data)
                 bootbox.alert("证书信息保存成功");
               else
                bootbox.alert("设备或用户信息已经关联，请先解除关联");
               $state.go("app.file.lists");
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
}
]);
