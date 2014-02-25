package com.lexemus.task.client;
 
import com.google.gwt.core.client.EntryPoint;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class Main implements EntryPoint {

    public static Panel mainTabSet;
    public static Container mainBox;
    public static NavigationPanel sideNav;
    public static String fileName=null;
    public static String pathName=null;
    public static Main project=null;
    public static ProjectData projectData=null;
    public static String appUrl=null;
    public static int id=0;
	
    public static Panel panel;
	public static Viewport viewport;
	public static TeamBoardPanel teamBoard;

	public void onModuleLoad() {
    	project = new Main();
        viewport = new Viewport(create(new NavigationPanel(new ProjectData(ProjectData.untitled).getNodeData())));
        viewport.doLayout();
	}

    public Panel create(NavigationPanel sideNav) {
		Main.sideNav=sideNav;
        panel = new Panel();
        panel.setHideBorders(true);
        panel.setLayout(new FitLayout());
        Panel borderPanel = new Panel();
        borderPanel.setLayout(new BorderLayout());
        
        mainTabSet = new Panel();
        mainTabSet.setBorder(false);
        mainTabSet.setLayout(new FitLayout());
        mainTabSet.setWidth("100%");
        mainTabSet.setHeight("100%");
        
        teamBoard=new TeamBoardPanel();
        mainTabSet.add(teamBoard);
        borderPanel.add(mainTabSet, new BorderLayoutData(RegionPosition.CENTER));
        panel.add(borderPanel);
        return panel;
    }
    
 }
