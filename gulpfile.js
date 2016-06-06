    var gulp = require('gulp');
    var Server = require('karma').Server;
    var browserSync = require('browser-sync').create();
    var usemin = require('gulp-usemin'),
        cssnano = require('gulp-cssnano'),
        rev = require('gulp-rev'),
        htmlmin = require('gulp-htmlmin'),
        sourcemaps = require('gulp-sourcemaps'),
        ngAnnotate = require('gulp-ng-annotate'),
        uglify = require('gulp-uglify'),
        prefix = require('gulp-autoprefixer');

    var config = {
        app: 'src/main/webapp/',
        dist: 'src/main/webapp/dist/'
    };

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
            configFile: __dirname +'/src/test/javascript/karma.conf.js',
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


    gulp.task('build', ['usemin']);

    gulp.task('default', function () {
        // place code for your default task here
    });

    gulp.task('styles', [], function () {
        return gulp.src(config.app + 'assest/styles')
            .pipe(browserSync.reload({stream: true}));
    });

    gulp.task('usemin', ['styles'], function () {
        return gulp.src([config.app + '**/*.html', '!' + config.app + '@(dist|bower_components)/**/*.html'])
            .pipe(usemin({
                css: [
                    prefix,
                    'concat',
                    cssnano,
                    rev
                ],
                html: [
                    htmlmin.bind(htmlmin, {collapseWhitespace: true})
                ],
                js: [
                    sourcemaps.init,
                    ngAnnotate,
                    'concat',
                    uglify.bind(uglify, {mangle: false}),
                    rev,
                    sourcemaps.write.bind(sourcemaps.write, '.')
                ]
            }))
            .pipe(gulp.dest(config.dist));
    });