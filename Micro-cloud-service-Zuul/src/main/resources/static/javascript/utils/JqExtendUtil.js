/**
 * Created by pengyan on 2017-06-20.
 */

(function ($) {

    $.extend({
        /*
         * 根据参数名获取参数 @name String 参数名
         */
        queryString: function (name) {
            name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
            var regexS = "[\\?&]" + name + "=([^&#]*)";
            var regex = new RegExp(regexS);
            var results = regex.exec(window.location.search);
            if (results == null) {
                return "";
            }
            else {
                return decodeURIComponent(results[1].replace(/\+/g, " "));
            }
        },
        funDownload :function (content, filename) {
            var eleLink = document.createElement('a');
            eleLink.download = filename;
            eleLink.style.display = 'none';
            // 字符内容转变成blob地址
            var blob = new Blob([content]);
            eleLink.href = URL.createObjectURL(blob);
            // 触发点击
            document.body.appendChild(eleLink);
            eleLink.click();
            // 然后移除
            document.body.removeChild(eleLink);
        },
        stringArrToNumberArr:function(arr){
            if(arr){
                for(i in arr){
                    arr[i] = Number(arr[i])
                }
                return arr;
            }else{
               alert('string arr to number arr error！');
            }
        },
        /*
         * 根据URL地址后退 @URL String 需返回的后退URL地址
         */
        goBack: function (url) {
            if (url !== "" && typeof url !== "undefined") {
                var parameters = window.location.search;
                url = url + parameters;
                window.location.href = url;
            }
            else {
                window.history.back();
            }
        },

        /*
         * 数组对象转换成Json对象 @obj object form对象
         */
        serializeJson: function (obj) {
            var self = $(obj);
            var serializeObj = {};
            var array = self.serializeArray();
            var str = self.serialize();
            var objArr = [];
            $(array).each(function () {
                this.value = filterKeyWord(this.value);
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    }
                    else {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                }
                else {
                    serializeObj[this.name] = this.value;
                }
            });

            return serializeObj;
        },

        /*
         * Json对象转换成json字符串 @obj object form对象
         */
        jsonToString: function (obj) {
            var parameters = $(obj)[0];
            var arrResult = [];
            for (var key in parameters) {
                var parValue = parameters[key];

                if (parValue === null || parValue === undefined) {
                    continue;
                }
                parValue = filterKeyWord(parValue); // 过滤"\"以免json序列化问题

                arrResult.push('"' + key + '":"' + parValue + '"');
            }

            var json = '{' + arrResult.join(',') + '}';
            return json;
        }
        // 转义"\"特殊字符
    });
    // 转义"\"特殊字符
    function filterKeyWord ( strKeyWord ){
        if (strKeyWord.indexOf ("\\" ) === -1) { return strKeyWord; }

        var str = strKeyWord.split ("\\" );
        var len = str.length;
        var arr = [];
        for (var i = 0; i < len; i++) {
            var keyValue = str[i];
            arr.push (keyValue );
        }

        return arr.join ("\\\\" );
    }
})(jQuery);

