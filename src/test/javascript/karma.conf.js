// Karma configuration
// Generated on Sat Oct 31 2015 12:48:35 GMT+0100 (CET)

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '../../',

        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            'main/webapp/bower_components/angular/angular.js',
            'main/webapp/bower_components/angular-mocks/angular-mocks.js',
            'main/webapp/bower_components/angular-resource/angular-resource.js',
            'main/webapp/bower_components/angular-cookies/angular-cookies.min.js',
            'main/webapp/bower_components/moment/moment.js',
            'main/webapp/bower_components/moment/locale/fr.js',
            'main/webapp/bower_components/moment/locale/fr.js',
            'main/webapp/bower_components/angular-local-storage/dist/angular-local-storage.js',
            'main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js',
            'main/webapp/bower_components/ng-file-upload/ng-file-upload.min.js',
            'main/webapp/bower_components/angular-sanitize/angular-sanitize.js',
            'main/webapp/bower_components/ngToast/dist/ngToast.js',
            'main/webapp/bower_components/sockjs-client/dist/sockjs.min.js',
            'main/webapp/bower_components/stomp-websocket/lib/stomp.min.js',
            'main/webapp/bower_components/ng-tags-input/ng-tags-input.js',
            'main/webapp/bower_components/angular-translate/angular-translate.js',
            'main/webapp/bower_components/angular-translate-loader-partial/angular-translate-loader-partial.min.js',
            'main/webapp/bower_components/angular-translate-storage-cookie/angular-translate-storage-cookie.min.js',
            'main/webapp/bower_components/angular-loading-bar/build/loading-bar.min.js',
            'main/webapp/bower_components/ng-material-floating-button/src/mfb-directive.js',
            'main/webapp/app/components/toaster/toaster.js',
            'main/webapp/app/social-authentication/social-authentication.js',
            'main/webapp/app/app.js',
            'main/webapp/app/**/*.js',
            'main/webapp/app/**/*.html',
            'test/javascript/**/!(karma.conf).js'
        ],

        // list of files to exclude
        exclude: [],

        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
            'main/webapp/app/**/*.html': ['ng-html2js']
        },

        ngHtml2JsPreprocessor: {
            stripPrefix: 'main/webapp/',
            moduleName: 'directives.templates'
        },
        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher',
            'karma-ng-html2js-preprocessor'
        ],
        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress'],

        // web server port
        port: 9876,

        // enable / disable colors in the output (reporters and logs)
        colors: true,

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,

        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false,

        // Concurrency level
        // how many browser should be started simultanous
        concurrency: Infinity
    })
};
