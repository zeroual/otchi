angular.module("publisher")
    .service("ImageBase64Encoder", function (Upload) {

        this.encode = function (files) {
            return Upload.base64DataUrl(files);
        };

        this.decode = function (imagesBase64) {
            return Upload.dataUrltoBlob(imagesBase64, name);
        };

        this.resize = function (image) {
         var tempImage = new Image();
                        tempImage.src = image;
                        var MAX_WEIGHT = 400;
                        var MAX_HEIGHT = 300;
                        var tempW = tempImage.width;
                        var tempH = temp.height;
                        if (tempW > tempH) {
                        	if (tempW > MAX_WIDTH) {
                        		tempH *= MAX_WIDTH / tempW;
                        		tempW = MAX_WIDTH;
                        	}
                        } else {
                        	if (tempH > MAX_HEIGHT) {
                        		tempW *= MAX_HEIGHT / tempH;
                        		tempH = MAX_HEIGHT;
                        	}
                        }
                        var canvas = document.createElement('canvas');
                        canvas.width = tempW;
                        canvas.height = tempH;
                        var ctx = canvas.getContext("2d");
                        ctx.drawImage(this, 0, 0, tempW, tempH);
                        var dataURL = canvas.toDataURL("image/jpeg");
                        return dataURL;
        };
    });
