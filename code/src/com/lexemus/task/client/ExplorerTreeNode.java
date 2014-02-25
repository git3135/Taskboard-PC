package com.lexemus.task.client;


import com.gwtext.client.widgets.tree.TreeNode;

public class ExplorerTreeNode extends TreeNode {

    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String factory, boolean enabled, String idSuffix) {
    	super(name);
        //if (enabled) {
          //  setName(name);
        //} else {
          //  setName("<span style='color:808080'>" + name + "</span>");
        //}
        //setNodeID(nodeID.replace("-", "_") + idSuffix);
        //setThumbnail("thumbnails/" + nodeID.replace("-", "_") + ".gif");
        //setParentNodeID(parentNodeID.replace("-", "_") + idSuffix);
        setParentNodeID(parentNodeID);
        setIcon(icon);
        setId(nodeID);
        //setUserObject(parentNodeID);
        //setText(getName());

        setFactory(factory);

//        if (ShowcaseConfiguration.getSingleton().isOpenForTesting() && null != factory) {
//            String className = factory.getClass().getName().replaceFirst("\\$.*$","");
//            setSampleClassName(className);
//        }
        
        //if(nodeID.equals("project-category") || nodeID.equals("new-category")) {
            //setIsOpen(true);
        //}
    }
    /*
    public void setSampleClassName(String name) {
        setTreeAttribute("sampleClassName",name);
    }
    public String getSampleClassName() {
        return getAttribute("sampleClassName");
    }
*/
    public void setFactory(String factory) {
        setTreeAttribute("factory", factory);
    }
    
    public String getFactory() {
        return (String) getAttributeAsObject("factory");
    }
/*
    public void setNodeID(String value) {
        setTreeAttribute("nodeID", value);
    }

    public String getNodeID() {
        return getAttribute("nodeID");
    }
*/
    public void setParentNodeID(String value) {
        setTreeAttribute("parentNodeID", value);
    }

    public String getParentNodeID() {
        return getAttribute("parentNodeID");
    }
/*
    public void setName(String name) {
        setTreeAttribute("nodeTitle", name);
    }

    public String getName() {
        return getAttribute("nodeTitle");
    }

    public void setIcon(String icon) {
        setAttribute("icon", icon);
    }

    public String getIcon() {
        return getAttribute("icon");
    }

    public void setThumbnail(String thumbnail) {
        setTreeAttribute("thumbnail", thumbnail);
    }

    public String getThumbnail() {
        return getAttribute("thumbnail");
    }

    public void setIsOpen(boolean isOpen) {
        setTreeAttribute("isOpen", isOpen);
    }

    public void setIconSrc(String iconSrc) {
        setTreeAttribute("iconSrc", iconSrc);
    }

    public String getIconSrc() {
        return getAttribute("iconSrc");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplorerTreeNode that = (ExplorerTreeNode) o;

        if (!getName().equals(that.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    */
}
