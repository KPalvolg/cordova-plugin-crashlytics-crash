exports.crash = function(success, error) {
    //Does not crash Interner Explorer, instead crashes Windows
    var overflow = "text";
    if(typeof success == "function") {
        success("Crash method started");
    }
    while(true) {
        overflow =  overflow += "ℒ";
    }
}