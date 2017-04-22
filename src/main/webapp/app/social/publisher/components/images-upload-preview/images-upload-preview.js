angular.module("publisher")
    .component('imagesUploadPreview', {
        bindings: {
            images: '<',
            onDelete: '&'
        },
        templateUrl: 'app/social/publisher/components/images-upload-preview/images-upload-preview.html'
    });
