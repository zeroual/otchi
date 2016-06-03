angular.module('helpers', []);
angular.module('stream', ['ngResource', 'helpers', 'authentication', 'ui.bootstrap']);
angular.module('publisher', ['ngResource', 'kitchen']);
var app = angular.module("otchi", ['publisher', 'stream', 'authentication', 'ui.router',
    'ui.bootstrap', 'pascalprecht.translate', 'toaster', 'socialAuthentication']);