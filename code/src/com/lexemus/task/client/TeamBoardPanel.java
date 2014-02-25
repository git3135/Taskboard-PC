package com.lexemus.task.client;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.Node;
import com.gwtext.client.data.NodeTraversalCallback;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Tool;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.portal.Portal;
import com.gwtext.client.widgets.portal.PortalColumn;
import com.gwtext.client.widgets.portal.Portlet;
import com.gwtext.client.widgets.tree.TreeNode;

public class TeamBoardPanel extends Panel {
	//private final static DocumentServiceAsync docService = GWT.create(DocumentService.class);
    private TreeNode root=null,parent=null;
	private String parentId="";
	private static Tool[] tools;
	public static Portal portal;
	public static PortalColumn backlog,inprogress,intest,done,accepted;
	private static String path;
	private static TreeNode backLogNode,inProgressNode,inTestNode,doneNode,acceptedNode;
	public static boolean isRendered=false;
	public static Map<String,String> idMap;
	public static Map<String,String> dataMap;
	public static Set<String> fileSet; 

	public TeamBoardPanel() {
    	this(new ProjectData(ProjectData.untitled).getNodeData());
	}

    public TeamBoardPanel(String[] treeData) {
        setLayout(new BorderLayout());
        add(getHeaderPanel(), new BorderLayoutData(RegionPosition.NORTH));
        
        idMap=new TreeMap<String,String>();
        dataMap=new TreeMap<String,String>();
        fileSet=new TreeSet<String>();
        isRendered=false;
        backLogNode=null;
        inProgressNode=null;
        inTestNode=null;
        doneNode=null;
        acceptedNode=null;

		//tools = new Tool[]{gear};
		portal = new Portal();
		//portal.
		path=Main.projectData.getHomePath()+"/stories";
		//backlog column
		backlog = new PortalColumn();
		setColumn(path,"Product Backlog",backlog);

		//inporgress column
		inprogress = new PortalColumn();
		setColumn(path+"/inprogress","In Progress",inprogress);

		//intest column
		intest = new PortalColumn();
		setColumn(path+"/intest","In Test",intest);

		done = new PortalColumn();
		setColumn(path+"/completed","Completed",done);

		accepted = new PortalColumn();
		setColumn(path+"/accepted","Accepted",accepted);
		add(portal, new BorderLayoutData(RegionPosition.CENTER));
    }
    
    public Panel getHeaderPanel() {
        Panel panel = new Panel();
        panel.setBorder(false);
        panel.setBodyBorder(false);
        panel.setWidth("100%");
        panel.setHeight(25);
        panel.setHeader(false);
        //panel.setBodyStyle("border-style:dotted;border-color:blue;");

        panel.setLayout(new ColumnLayout());
        
        Panel backlog=new Panel("<center>Product Backlog</center>");
        //Console.log("style name: "+backlog.getStyleName()+", primary: "+backlog.getStylePrimaryName());
        //backlog.setHtml("<div align='center'>Product Backlog</div><td style='border-left: 1px solid gray; padding: 0px;'/>");
        backlog.setBorder(false);
        panel.add(backlog, new ColumnLayoutData(.2));
        Panel divider = new Panel("1");
        divider.setWidth(1);
        panel.add(divider);
        
        Panel inprogress=new Panel("<center>In Progress</center>");
        inprogress.setBorder(false);
        panel.add(inprogress, new ColumnLayoutData(.2));
        Panel divider1 = new Panel("1");
        divider1.setWidth(1);
        panel.add(divider1);

        Panel intest=new Panel("<center>In Test</center>");
        intest.setBorder(false);
        panel.add(intest, new ColumnLayoutData(.2));
        Panel divider2 = new Panel("1");
        divider2.setWidth(1);
        panel.add(divider2);

        Panel done=new Panel("<center>COMPLETED</center>");
        done.setBorder(false);
        panel.add(done, new ColumnLayoutData(.2));
        Panel divider3 = new Panel("1");
        divider3.setWidth(1);
        panel.add(divider3);

        Panel accepted=new Panel("<center>Accepted</center>");
        accepted.setBorder(false);
        panel.add(accepted, new ColumnLayoutData(.2));
        return panel;
    }

    private void setColumn(String id,String columnName,PortalColumn column) {
		column.setPaddings(5, 5, 5, 0);
		//column.setTitle(columnName);
		portal.add(column, new ColumnLayoutData(.2));
    }

    private static String data="";
    public static void saveTreeData() {
    	data="";
    	Main.sideNav.getRootNode().getFirstChild().cascade(new NodeTraversalCallback() {
    		public boolean execute(Node parent) {
    			String id=parent.getId();
    			int pos=id.indexOf("/");
    			if(parent.getAttribute("factory")==null) id+="/";
    			data+=id.substring(pos+1)+"\n";
    			return true;
    		}
    	});
    	Console.log("dir data: "+data);
    }
    
    public void clearData() {
        backLogNode=null;
        inProgressNode=null;
        inTestNode=null;
        doneNode=null;
        acceptedNode=null;
		backlog.clear();
		inprogress.clear();
		intest.clear();
		done.clear();
		accepted.clear();
		isRendered=false;
		idMap.clear();
		dataMap.clear();
		fileSet.clear();
		//enableSave(false);
    }
    
    public static void setColumnData(TreeNode node) {
    	if(node==null) return;
    	String id=node.getId().toLowerCase();
    	//Console.log("node: "+id);
    	final PortalColumn column;
    	if(id.endsWith("stories")) {
    		column=backlog;
    		backLogNode=node;
    	} else if(id.endsWith("in progress")||id.endsWith("inprogress")) {
    		column=inprogress;
    		inProgressNode=node;
    	} else if(id.endsWith("in test")||id.endsWith("intest")) {
    		column=intest;
    		inTestNode=node;
    	} else if(id.endsWith("completed")) {
    		column=done;
    		doneNode=node;
    	} else if(id.endsWith("accepted")) {
    		column=accepted;
    		acceptedNode=node;
    	} else {
    		return;
    	}
    	//Console.log("done xtype: "+done.getXType()+", backlog xtype: "+backlog.getXType());
        Node[] childs=node.getChildNodes();
        if(childs.length>0) createPortlets(0,childs,column);
    }
    
    private static void createPortlets(final int i,final Node[] childs,final PortalColumn column) {
    	if(i<childs.length) {
	    	final TreeNode tnode=(TreeNode) childs[i];
	    	//Console.log("add portlet: "+tnode.getId());
	    	if(tnode.getAttribute("factory")!=null) {
		        final String pathName = tnode.getId();
        		String str=tnode.getAttribute("data");
        		String[] dat=str.split(",");
        		String owner="",point="";
        		if(dat.length>1) owner=dat[1];
        		if(dat.length>2) point=dat[2];
        		String name=tnode.getText();
        		if(owner.length()>0||point.length()>0) name=name+" - [Owner: "+owner+"][Point: "+point+"]";
				//final Portlet portlet = new Portlet(name, e.getResult().substring(0,128));
				final Portlet portlet = new Portlet(name,null);
				//Tool gear = new Tool(Tool.GEAR, new Function() {
				//	public void execute() {
				//		new CardInfo(portlet,tnode).show();
				//	}
				//});
				//tools = new Tool[]{gear};
				//portlet.setTools(tools);
				portlet.setCollapsed(true);
				portlet.setHtml(null);
				//portlet.setCollapseFirst(false);
        		column.add(portlet);
        		idMap.put(portlet.getId(), pathName);
        		int pos = str.indexOf(",");
        		if(pos>0) {
        			str=str.substring(pos+1);
        		} else {
        			str="";
        		}
        		dataMap.put(portlet.getId(), str);
        		portlet.addListener(new PanelListenerAdapter() {
					//public boolean doBeforeExpand(Panel panel,boolean animate) {
						// TODO Auto-generated method stub
						//return false;
					//}

					//public void onCollapse(Panel panel) {
					//}

					public void onExpand(final Panel panel) {
						//Console.log("html len: "+panel.getHtml().length()+", pathname: "+pathName);
				    	if(!fileSet.contains(panel.getId())) {
				    		/*
					        FileIO.readFile(pathName,false,new FileReaderCallback() {
								@Override
								public void onLoad(ProgressEvent e) {
									String text=e.getResult().substring(0,128);
									panel.setHtml(text);
									String[] lines=text.split("\n");
									int n=lines.length>10?10:lines.length;
									panel.setHeight(n*10);
									fileSet.add(panel.getId());
								}
								@Override
								public void onError(ProgressEvent e) {
									Console.log("create portlet error: "+e.getErrorCode());
								}
					        });
					        */
				    	}
					}
        		});
        		nextPortlet(i,childs,column);
	    	} else {
	    		nextPortlet(i,childs,column);
	    	}
    	}
    }
    
    private static void nextPortlet(int i,Node[] childs,PortalColumn column) {
		if(i+1<childs.length) {
			createPortlets(i+1,childs,column);
		} else {
	        Main.viewport.doLayout();
		}
    }
}