package com.lexemus.task.client;

import com.gwtext.client.widgets.tree.TreeNode;

public class ProjectData {

	public static String untitled="(untitled)/\n(untitled)/stories/\n(untitled)/resources/\n(untitled)/outputs/\n";		//"<project>(untitled></project>";
    private String xmlData;
    private String[] nodeData;
    private String[][] nodeRecord;
    public String name="(untitled)";
    public String pathName="";
    public final static int PATHNAME_COL=0,ASSIGN_COL=1,POINT_COL=2;

    public ProjectData() {
    }

    public ProjectData(String text) {
    	this.xmlData = text;
        int pos=xmlData.indexOf("\n");
        if(pos>0) {
        	this.pathName=text.substring(0,pos-1);
        	this.name=pathName;
        	pos=name.lastIndexOf("/");
        	if(pos>0) {
        		name=name.substring(pos+1);
        	}
        }
        String[] nodeLines=xmlData.split("\n");
        nodeData=new String[nodeLines.length];
        nodeRecord=new String[nodeLines.length][8];
        for(int i=0;i<nodeLines.length;i++) {
        	//System.out.println("nodeline #"+i+", data: "+nodeLines[i]);
        	String[] record=nodeLines[i].split(",");
        	nodeRecord[i]=record;
        	nodeData[i]=record[0].trim();
        	//System.out.println("nodedata #"+i+", data: "+nodeData[i]);
        	String str="record:\n";
        	for(int j=0;j<record.length;j++) {
        		str+=" col"+j+": "+record[j];
        	}
        	//System.out.println(str);
        }
    }

    public String[] getNodeData() {
    	return nodeData;
    }
    
    public String[][] getNodeRecord() {
    	return nodeRecord;
    }
    
    public String getFileName() {
    	TreeNode node=(TreeNode)Main.sideNav.getRootNode().getFirstChild();
    	return node.getText()+".npj";
    }

    public String getPathName() {
    	TreeNode node=(TreeNode)Main.sideNav.getRootNode().getFirstChild();
    	String path=node.getId();
    	//Console.log("prj path: "+path);
    	if(path.startsWith("local/")) {
    		path=path+"/"+node.getText()+".npj";
    	}
    	return path;
    }

    public String getHomePath() {
    	TreeNode node=(TreeNode)Main.sideNav.getRootNode().getFirstChild();
    	return node.getId();
    }

	public String getName() {
    	TreeNode node=(TreeNode)Main.sideNav.getRootNode().getFirstChild();
		return node.getText();
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
