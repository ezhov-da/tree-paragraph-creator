package ru.ezhov.tree.paragraph.creator;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create a tree from items
 * <p>
 *
 * @author ezhov_da
 */
public class TreeCreator {
    private static final Logger LOG = Logger.getLogger(TreeCreator.class.getName());

    public DefaultMutableTreeNode createTree(String textParagraph) {
        if (textParagraph == null || "".equals(textParagraph)) {
            return new DefaultMutableTreeNode("Text must be specified");
        }

        String[] textArray = textParagraph.split("\\n");

        List<PreparedTreeObject> preparedTreeObjects = new ArrayList();

        for (int i = 0; i < textArray.length; i++) {
            PreparedTreeObject preparedTreeObject = new PreparedTreeObject();

            String row = textArray[i];
            row = row.trim();

            //looking for items
            Pattern pattern = Pattern.compile("^\\d*\\.(\\d*\\.?){0,100}");
            Matcher matcher = pattern.matcher(row);
            boolean find = matcher.find();

            if (find) {
                String group = matcher.group();
                String[] arrayNumber = group.split("\\.");

                LOG.log(
                        Level.CONFIG,
                        "line:[{0}]\tmatch found:{1}\tnumber of points=number of levels: {2}",
                        new Object[]{row, find, arrayNumber.length}
                );
                preparedTreeObject.setLvl(arrayNumber.length);
                preparedTreeObject.setParagraph(group);
            } else {
                LOG.log(
                        Level.CONFIG,
                        "line:[{0}]\tmatch found:{1}",
                        new Object[]{row, find}
                );
            }
            preparedTreeObject.setFullText(row);
            preparedTreeObject.setFindParagraph(find);

            preparedTreeObjects.add(preparedTreeObject);
        }

        DefaultMutableTreeNode defaultMutableTreeNodeRoot =
                new DefaultMutableTreeNode("Test tree by points");
        Map<String, DefaultMutableTreeNode> map = new HashMap<>();

        DefaultMutableTreeNode treeNodeLastAdd = null;

        for (PreparedTreeObject preparedTreeObject : preparedTreeObjects) {
            if (preparedTreeObject.isFindParagraph()) {

                DefaultMutableTreeNode mutableTreeNodeParenFromMap =
                        map.get(preparedTreeObject.getParentParagraph());
                String fullText = preparedTreeObject.getFullText();
                treeNodeLastAdd = new DefaultMutableTreeNode(fullText);
                // if the parent is not found, then create a new one and add it to the root
                if (mutableTreeNodeParenFromMap == null) {
                    defaultMutableTreeNodeRoot.add(treeNodeLastAdd);
                } else {
                    mutableTreeNodeParenFromMap.add(treeNodeLastAdd);
                }
                map.put(preparedTreeObject.getParagraph(), treeNodeLastAdd);

            } else {
                String fullText = preparedTreeObject.getFullText();
                if (treeNodeLastAdd == null) {
                    treeNodeLastAdd = new DefaultMutableTreeNode(fullText);
                    defaultMutableTreeNodeRoot.add(treeNodeLastAdd);
                } else {
                    String string = (String) treeNodeLastAdd.getUserObject();
                    string = string + "\n" + fullText;
                    treeNodeLastAdd.setUserObject(string);
                }

            }
        }
        return defaultMutableTreeNodeRoot;
    }

}
