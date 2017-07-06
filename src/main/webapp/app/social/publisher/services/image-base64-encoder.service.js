angular.module("publisher")
    .service("ImageBase64Encoder", function (Upload) {

        this.encode = function (files) {
            return Upload.base64DataUrl(files);
        };

        this.decode = function (imagesBase64) {
            return Upload.dataUrltoBlob(imagesBase64, name);
        };
    });
