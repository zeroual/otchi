angular.module('helpers', []);
angular.module('publisher', ['ui.router', 'ngResource', 'ngFileUpload', 'toaster', 'ngTagsInput', 'LocalStorageModule']);
angular.module('stream', ['ngResource', 'helpers', 'authentication', 'ui.bootstrap']);
angular.module('profile', ['ngResource']);
var app = angular.module("otchi", ['publisher', 'stream', 'authentication', 'ui.router',
    'ui.bootstrap', 'socialAuthentication', 'profile', 'pascalprecht.translate', 'ngCookies','angular-loading-bar']);
