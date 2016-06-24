angular.module("publisher")
    .component('imagesUploadPreview', {
        bindings: {
            images: '<',
            onDelete: '&'
        },
        templateUrl: 'app/social/publisher/shared/images-upload-preview/images-upload-preview.template.html'
    });