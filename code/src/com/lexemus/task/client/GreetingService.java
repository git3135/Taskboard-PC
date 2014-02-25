package com.lexemus.task.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name);
	String[] getSampleFiles(String dir);
	String[] getSampleStories();
	String[] getDirList(String directory);
	String[] getSampleList(String directory);
	byte[] getData(String directory);
}
