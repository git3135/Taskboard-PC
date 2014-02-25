package com.lexemus.task.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lexemus.task.client.GreetingService;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	private String[] fileNames;
	private String[] stories;

    public static String readFile(String fileName) {
    	FileReader f=null;
		StringBuffer result = new StringBuffer();
    	try {
    		f=new FileReader(fileName);
    		BufferedReader br = new BufferedReader(f);
    		String line;
    		while ((line = br.readLine()) != null) {
    			result.append(line+'\n');
    		}
    		f.close();
    	} catch (Throwable t) {
    		System.out.println("file not found.");
    	}
    	return result.toString();
    }

	public String greetServer(String input) {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		String root = getServletContext().getContextPath();
		if (root.length() > 1)
			root += "/";
		String host = "http://" + getThreadLocalRequest().getHeader("Host")
				+ root;
		// return "Hello, " + input + "!<br><br>I am running " + serverInfo +
		// ".<br><br>It looks like you are using:<br>" + userAgent;
		System.out.println("context path: "+root+"\nhost: "+host+"\nserver info: "+serverInfo+"\nuser agent: "+userAgent);
		String curDir = getServletContext().getRealPath("/");
		int pos = input.indexOf(".");
		if (pos > 0) {
			input = input.substring(0, pos) + ".html";
		}
		String srcFile = curDir + "outputs/" + input;
		String dstFile = curDir + input;
		return (host + input);
	}

	@Override
	public String[] getSampleFiles(String directory) {
		//getDoc();
	    Stack<String> list=new Stack<String>();
		String curDir = getServletContext().getRealPath("/");
	    String srcDir = curDir+"samples";;
	    File dir =new File(srcDir);
	    System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	if(dir.listFiles()[i].getName().endsWith(".eng")) {
	    		String text= "stories/"+dir.listFiles()[i].getName()+":"+readFile(dir.listFiles()[i].getAbsolutePath());
	    		list.add(text);
	    		//System.out.println(text);
	    	}
	    }
	    srcDir = curDir+"outputs";;
	    dir =new File(srcDir);
	    //System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	//if(dir.listFiles()[i].getName().endsWith(".eng")) {
	    		String text= "code/src/"+dir.listFiles()[i].getName()+":"+readFile(dir.listFiles()[i].getAbsolutePath());
	    		list.add(text);
	    		//System.out.println(text);
	    	//}
	    }
	    srcDir = curDir+"lib";;
	    dir =new File(srcDir);
	    //System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	//if(dir.listFiles()[i].getName().endsWith(".eng")) {
	    		String text= "code/lib/"+dir.listFiles()[i].getName()+":"+readFile(dir.listFiles()[i].getAbsolutePath());
	    		list.add(text);
		    	System.out.println("lib: "+dir.listFiles()[i].getName()+", size: "+text.length());
	    		//System.out.println(text);
	    	//}
	    }
	    stories=list.toArray(new String[list.size()]);
	    return stories;
	}
	
	@Override
	public String[] getSampleStories() {
		  return stories;
	}
  
	private byte[] readBytes(String fileName,String path) {
		int off = path.length();
		File file = new File(fileName);
		int len = (int)file.length();
		byte[] fileData = new byte[len+off];
		byte[] pathData = path.getBytes();
		System.arraycopy(pathData, 0, fileData, 0, off);
		DataInputStream dis;
		try {
			dis = new DataInputStream((new FileInputStream(file)));
			dis.read(fileData, off, len);
			dis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileData;
	}
	
	@Override
	public String[] getDirList(String homePath) {
		//getDoc();
	    Stack<String> list=new Stack<String>();
		String curDir = getServletContext().getRealPath("/")+homePath;
	    String srcDir = curDir+"samples";;
	    File dir =new File(srcDir);
	    System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	String fileName=dir.listFiles()[i].getName();
	    	list.add(fileName);
	    }
	    srcDir = curDir+"outputs";;
	    dir =new File(srcDir);
	    //System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	String fileName=dir.listFiles()[i].getName();
    		list.add(fileName);
	    }
	    srcDir = curDir+"lib";;
	    dir =new File(srcDir);
	    //System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	String fileName=dir.listFiles()[i].getName();
    		list.add(fileName);
	    }
	    return list.toArray(new String[list.size()]);
	}
	
	@Override
	public String[] getSampleList(String homePath) {
		//getDoc();
	    Stack<String> list=new Stack<String>();
		String curDir = getServletContext().getRealPath("/");
	    String srcDir = curDir+"samples";;
	    File dir =new File(srcDir);
	    System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	String fileName=dir.listFiles()[i].getName();
	    	list.add("stories/"+fileName);
	    }
	    srcDir = curDir+"outputs";;
	    dir =new File(srcDir);
	    //System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	String fileName=dir.listFiles()[i].getName();
    		list.add("code/src/"+fileName);
	    }
	    srcDir = curDir+"lib";;
	    dir =new File(srcDir);
	    //System.out.println("curDir: "+curDir+", srcDir: "+srcDir);
	    for(int i=0;i<dir.listFiles().length;i++) {
	    	String fileName=dir.listFiles()[i].getName();
    		list.add("code/lib/"+fileName);
	    }
	    return list.toArray(new String[list.size()]);
	}
	
	@Override
	public byte[] getData(String fileName) {
		String curDir = getServletContext().getRealPath("/");
		File file = new File(curDir+fileName);
		int len = (int)file.length();
		byte[] fileData = new byte[len];
		DataInputStream dis;
		try {
			dis = new DataInputStream((new FileInputStream(file)));
			dis.readFully(fileData);
			dis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileData;
	}
	
}
