'use strict';

angular.module('fakeModal', [])
    .factory('$uibModal', function () {
        return {
            result: {
                then: function (confirmCallback, cancelCallback) {
                    //Store the callbacks for later when the user clicks on the OK or Cancel button of the dialog
                    this.confirmCallBack = confirmCallback;
                    this.cancelCallback = cancelCallback;
                }
            },
            open: function (data) {
                this.openData = data;
            },
            close: function (item) {
                //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
                this.result.confirmCallBack(item);
            },
            dismiss: function (type) {
                //The user clicked cancel on the modal dialog, call the stored cancel callback
                this.result.cancelCallback(type);
            }
        };
    })
    .factory('$uibModalInstance', function () {
        return {
            result: {},
            close: function (item) {
                //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
                this.result = {'type': 'close', 'value': item};
            },
            dismiss: function () {
                //The user clicked cancel on the modal dialog, call the stored cancel callback
                this.result = {'type': 'dismiss'};
            }
        };
    })
;
