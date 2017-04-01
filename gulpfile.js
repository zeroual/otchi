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
        prefix = require('gulp-autoprefixer'),
        flatten = require('gulp-flatten'),
        del = require('del'),
        runSequence = require('run-sequence');

    var config = {
        app: 'src/main/webapp/',
        dist: 'src/main/webapp/dist/'
    };

    /**
     * Run test once and exit
     */
    gulp.task('test', function (done) {
        new Server({
            configFile: __dirname + '/src/test/javascript/karma.conf.js',
            singleRun: true
        }, done).start();
    });

    /**
     * Run test once and watch changing
     */
    gulp.task('test-watch', function (done) {
        new Server({
            configFile: __dirname +'/src/test/javascript/karma.conf.js',
            singleRun: false
        }, done).start();
    });

    /**
     * Run a static server and reload changing file
     */
    gulp.task('serve', function () {
        browserSync.init({
            server: __dirname + '/src/main/webapp'
        });
        gulp.watch(__dirname + '/src/main/webapp/*').on('change', browserSync.reload);
    });


    /**
     * build the front app for prod env
     */
    gulp.task('build', function (cb) {
        runSequence('clean', 'usemin', 'copy-bs-fonts', cb);
    });

    gulp.task('styles', [], function () {
        return gulp.src(config.app + 'assets/css/**/*.css')
            .pipe(browserSync.reload({stream: true}));
    });

    /**
     *  copy bootstrap font into assets/fonts
     */
    gulp.task('copy-bs-fonts', function () {
        return gulp.src([
                config.app + 'bower_components/bootstrap/fonts/*.*'])
            .pipe(flatten())
            .pipe(gulp.dest(config.dist + 'assets/fonts/'));
    });

    /**
     * remove the old dist directory
     */
    gulp.task('clean', function () {
        return del([config.dist]);
    });

    /**
     *  minify css and js
     */
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
