angular.module('helpers', []);
angular.module('stream', ['ngResource', 'helpers', 'authentication', 'ui.bootstrap']);
angular.module('publisher', ['ngResource', 'ngFileUpload', 'kitchen', 'toaster', 'ngTagsInput']);
angular.module('profile', ['ngResource']);
var app = angular.module("otchi", ['publisher', 'stream', 'authentication', 'ui.router',
    'ui.bootstrap', 'socialAuthentication', 'profile', 'pascalprecht.translate']);
