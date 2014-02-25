package com.lexemus.task.client;

public class Console {
	public static boolean hasApplet=false;

    /**
     * Should be instantiated by {@link #requestFileSystem()}.
     */
	protected Console() {
	}

	/**
	 * This is the name of the file system. The specifics of naming filesystems
	 * is unspecified, but a name must be unique across the list of exposed file systems.
	 * @return
	 */
    public static native final String log(String text) /*-{
		console.log(text);
	}-*/;

    public static native final String callback(String text) /*-{
		console.log(text);
	}-*/;

    public static native final String execute(String text) /*-{
		@com.lexemus.task.client.Console::init(*)();
		$wnd.callback[$wnd.callindex]=$wnd.callindex;
		$wnd.callindex++;
	   	$wnd.callback = function(someString) {
	   		//console.log("callback #: "+someString);
	     	@com.lexemus.task.client.Console::callback(Ljava/lang/String;)(someString);
		}
		return $doc.runcmd.executecmd(text,"callback");
	}-*/;

    public static native final String run(String text, Callback<String, FileError> callback) /*-{
		@com.lexemus.task.client.Console::init(*)();
	   	$wnd.runback = function(result) {
	   		console.log("run res #0: "+result[0]+", res #1: "+result[1])
	     	@com.lexemus.task.client.Console::handleSuccess(*)(callback,result);
		}
		return $doc.runcmd.runcmd(text,"runback");
	}-*/;

    public static native final void stopRun() /*-{
		@com.lexemus.task.client.Console::init(*)();
		$doc.runcmd.stoprun();
	}-*/;

    public static native final String compile(String text, Callback<String, FileError> callback) /*-{
		@com.lexemus.task.client.Console::init(*)();
	   	$wnd.compileback = function(result) {
	   		console.log("compile res #0: "+result[0]+", res #1: "+result[1])
	     	@com.lexemus.task.client.Console::handleSuccess(*)(callback,result);
		}
		return $doc.runcmd.compilecmd(text,"compileback");
	}-*/;

    public static native final String build(String text, Callback<String, FileError> callback) /*-{
		@com.lexemus.task.client.Console::init(*)();
	   	$wnd.buildback = function(result) {
	     	@com.lexemus.task.client.Console::handleSuccess(*)(callback,result);
		}
		return $doc.runcmd.buildcmd(text,"buildback");
	}-*/;

    public static native final void stopBuild() /*-{
		@com.lexemus.task.client.Console::init(*)();
		$doc.runcmd.stopbuild();
	}-*/;

    public static native final String getOutput() /*-{
		@com.lexemus.task.client.Console::init(*)();
		return $doc.runcmd.getOutput();
	}-*/;

    public static native final void printOutput() /*-{
		@com.lexemus.task.client.Console::init(*)();
    	var line="";
		while ((line=$doc.runcmd.getOutput())!=null) {
			console.log(line+"\n");
		}
	}-*/;

    public static native final void init() /*-{
    	if(!@com.lexemus.task.client.Console::hasApplet) {
			var applet = "<applet id='runcmd' style='visibility: hidden' name='runcmd' code='Runcmd.class' archive='Lexemus.jar' width='0' height='0' MAYSCRIPT >Sorry, you need a Java-enabled browser.</applet>";
			//var applet = "<applet id='runcmd' style='visibility: hidden' name='runcmd' code='Runcmd.class' width='0' height='0' MAYSCRIPT >Sorry, you need a Java-enabled browser.</applet>";
			var body = $doc.getElementsByTagName("body")[0];
			var div = $doc.createElement("div");
			div.innerHTML = applet;
			body.appendChild(div);
			@com.lexemus.task.client.Console::hasApplet=true;
        	$wnd.logger = function(someString) {
            	@com.lexemus.task.client.Console::log(Ljava/lang/String;)(someString);
            }
            $wnd.callback={};
            $wnd.callindex=0;
		}
    }-*/;

    public static native final String writeData(String dstPath,byte[] data) /*-{
		@com.lexemus.task.client.Console::init(*)();
		return $doc.runcmd.writeData(dstPath,data);
	}-*/;

    public static native final String writeFile(String dstPath,String data) /*-{
		@com.lexemus.task.client.Console::init(*)();
		//var base64Data = $wnd.btoa(unescape(encodeURIComponent( data )));
		return $doc.runcmd.writeFile(dstPath,data);
	}-*/;

    public static native final String writeFile(String dstPath,byte[] data,Callback<String, FileError> callback) /*-{
		@com.lexemus.task.client.Console::init(*)();
	   	$wnd.writeback = function(result) {
	     	@com.lexemus.task.client.Console::handleSuccess(*)(callback,result);
		}
		return $doc.runcmd.writeFile(dstPath,data,"writeback");
		//return $doc.runcmd.buildcmd(text,"buildback");
	}-*/;

    public static native final String checkFile(String dstPath,String date) /*-{
		@com.lexemus.task.client.Console::init(*)();
		return $doc.runcmd.checkFile(dstPath,date);
	}-*/;

    public static native final String openFile(String dstPath) /*-{
		@com.lexemus.task.client.Console::init(*)();
		return $doc.runcmd.openFile(dstPath);
	}-*/;

    public static native final String writeFile(String data) /*-{
		return $doc.runcmd.writeFile(data);
	}-*/;

    public static native final String closeFile() /*-{
		return $doc.runcmd.closeFile();
	}-*/;

    static final void handleSuccess(Callback<String,FileError> callback, String result) {
    	callback.onSuccess(result);
    }

    static final void handleFailure(Callback<String,FileError> callback, FileError err) {
    	callback.onFailure(err);
    }

}
