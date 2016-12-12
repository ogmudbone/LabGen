var utils = Java.type('renderer.js.JsUtil');

function echo(code, bundle) {

    if(bundle != undefined)
        utils.echo(code, bundle);
    else
        utils.echo(code);

}

function include(filename, bundle) {

    if(bundle != undefined)
        utils.include(filename, bundle);
    else
        utils.include(filename);

}

function includeScript(filename){
    utils.includeScript(filename);
}

function bufferStart(){
    utils.bufferStart();
}

function bufferFlush(){
    return utils.bufferFlush();
}