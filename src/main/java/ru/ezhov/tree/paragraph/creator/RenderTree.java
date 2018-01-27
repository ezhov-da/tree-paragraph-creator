package ru.ezhov.tree.paragraph.creator;

import java.awt.Component;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author ezhov_da
 */
public class RenderTree extends DefaultTreeCellRenderer
{
    private static final Logger LOG = Logger.getLogger(RenderTree.class.getName());

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) value;
        String string = (String) defaultMutableTreeNode.getUserObject();
        string = string.replaceAll("\n", "<br>");

        string = "<html>" + string;

        JLabel label = new JLabel(string);

        return label;
    }


}
