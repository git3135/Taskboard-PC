package com.lexemus.task.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.Node;
import com.gwtext.client.data.NodeTraversalCallback;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;

public class NavigationPanel extends TreePanel {
    private TreeNode root=null,parent=null;
	private String parentId="";
    private static GreetingServiceAsync greetingService = (GreetingServiceAsync) GWT.create(GreetingService.class);

	public NavigationPanel() {
    	this(new ProjectData(ProjectData.untitled).getNodeData());
	}

    public NavigationPanel(String[] treeData) {
        root = new TreeNode("Explorer");
        setData(treeData);
		setTitle("Project Explorer");
		setWidth(150);
		setHeight("100%");
		setEnableDD(true);
        setRootVisible(false);
        setAutoScroll(true);
        setContainerScroll(true);
		setUseArrows(true);
		root.setId("root");
		root.setExpandable(true);
		root.setExpanded(true);
		setRootNode(root);
		initData();
    }
    
    public void setData(String[] nodeData) {
    	Node[] childs=root.getChildNodes();
    	for(int i=0;i<childs.length;i++) {
    		childs[i].remove();
    	}
    	int pos=0;
    	String drive="local";
    	if(Main.pathName!=null) {
    		pos=Main.pathName.indexOf("/");
    		drive=Main.pathName.substring(0,pos);
    	}
        for (int i = 0; i < nodeData.length; i++) {
        	String icon="images/icons/16/text.gif";
        	parentId="root";
        	String id=nodeData[i];
        	int len=id.length();
        	String factory=null;
        	if(id.endsWith("/")) {
        		len--;
        		id=id.substring(0,len);
        		icon="images/icons/16/folder.gif";
        		//isFolder=true;
        	} else {
        		factory="leaf"; 
        	}
        	String name=id;
        	pos=id.lastIndexOf("/");
        	if(pos>0) {
        		name=id.substring(pos+1);
        		parentId=drive+"/"+id.substring(0,pos);
        	} else {
        		icon="images/icons/16/prj_obj.gif";
        	}
    		id=drive+"/"+id;
        	//Console.log("# "+i+", name: "+name+", id: "+id+", parent id: "+parentId);
        	final ExplorerTreeNode node = new ExplorerTreeNode(name, id, parentId, icon, factory, true, "");
        	node.setAttribute("data", "");
            if (parentId.equalsIgnoreCase("root")) {
                root.appendChild(node);
                parent=node;
            } else {
            	root.cascade(new NodeTraversalCallback() {
            		public boolean execute(Node parent) {
            			//Console.log("entry parent id: "+parentId+", cascade node id: "+parent.getId());
            			if(parent.getId().equalsIgnoreCase(parentId)) {
                    		parent.appendChild(node);
            				return false;
            			}
            			return true;
            		}
            	});
            }
        }
    }

    public void initData() {
        greetingService.getData("data/teamboard.dat", new AsyncCallback<byte[]>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(final byte[] data) {
				String text=new String(data);
		    	Main.projectData=new ProjectData(text);
		    	Main.pathName=Main.projectData.getPathName();
		    	Main.teamBoard.clearData();
		    	setData(Main.projectData.getNodeData());
		    	String id=root.getFirstChild().getId();
	            Console.log("home id: "+id);
	            TeamBoardPanel.setColumnData(Main.sideNav.getNodeById(id+"/stories"));
	            TeamBoardPanel.setColumnData(Main.sideNav.getNodeById(id+"/stories/in progress"));
	            TeamBoardPanel.setColumnData(Main.sideNav.getNodeById(id+"/stories/in test"));
	            TeamBoardPanel.setColumnData(Main.sideNav.getNodeById(id+"/stories/done"));
	            TeamBoardPanel.setColumnData(Main.sideNav.getNodeById(id+"/stories/accepted"));
			}
        });
    }
}