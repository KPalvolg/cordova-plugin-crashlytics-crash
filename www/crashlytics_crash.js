var exec = require('cordova/exec');

exports.crash = function (success, error) {
    exec(success, error, 'CrashlyticsCrash', 'crash', []);
};
