    var gulp = require('gulp');
    var Server = require('karma').Server;
    var browserSync = require('browser-sync').create();
    /**
     * Run test once and exit
     */
    //TODO make browserSyn use server proxy
    gulp.task('test', function (done) {
        new Server({
            configFile: __dirname + '/src/test/javascript/karma.conf.js',
            singleRun: true
        }, done).start();
    });

    gulp.task('test-watch', function (done) {
        new Server({
            configFile: __dirname + '/src/test/javascript/karma.conf.js',
            singleRun: false
        }, done).start();
    });

    // Static Server + watching js/css/html files
    gulp.task('serve', function () {
        browserSync.init({
            server: __dirname + '/src/main/webapp'
        });
        gulp.watch(__dirname + '/src/main/webapp/*').on('change', browserSync.reload);
    });

    //TODO minify the css
    //build the front project
    gulp.task('build', []);

    gulp.task('default', function () {
        // place code for your default task here
    });