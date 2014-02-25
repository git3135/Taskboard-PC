package com.lexemus.task.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback);
  void getSampleFiles(String dir, AsyncCallback<String[]> callback);
  void getDirList(String dir, AsyncCallback<String[]> callback);
  void getSampleList(String dir, AsyncCallback<String[]> callback);
  void getSampleStories(AsyncCallback<String[]> callback);
  void getData(String dir, AsyncCallback<byte[]> callback);
}
