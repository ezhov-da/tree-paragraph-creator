package ru.ezhov.tree.paragraph.creator;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author ezhov_da
 */
public class TreeParagraphRunner {
    private static final Logger LOG = Logger.getLogger(TreeParagraphRunner.class.getName());

    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            properties.load(TreeParagraphRunner.class.getResourceAsStream("/tree-source.properties"));
            String text = properties.getProperty("tree.source");
            LOG.config("Load text: " + text);
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Throwable ex) {
                        //
                    }

                    final JTree tree = new JTree();
                    //tree.setCellRenderer(new RenderTree());

                    final JTextPane textPane = new JTextPane();
                    textPane.setText(text);

                    textPane.addCaretListener(new CaretListener() {

                        @Override
                        public void caretUpdate(CaretEvent e) {
                            TreeCreator treeCreator = new TreeCreator();
                            DefaultMutableTreeNode mutableTreeNode = treeCreator.createTree(textPane.getText());
                            tree.setModel(new DefaultTreeModel(mutableTreeNode));
                            expandAllNodes(tree, 0, tree.getRowCount());
                        }
                    });

                    TreeCreator treeCreator = new TreeCreator();
                    DefaultMutableTreeNode mutableTreeNode = treeCreator.createTree(text);
                    tree.setModel(new DefaultTreeModel(mutableTreeNode));
                    expandAllNodes(tree, 0, tree.getRowCount());

                    JSplitPane splitPane = new JSplitPane();
                    splitPane.setLeftComponent(new JScrollPane(tree));
                    splitPane.setRightComponent(new JScrollPane(textPane));
                    splitPane.setDividerLocation(0.5);
                    splitPane.setResizeWeight(0.5);

                    JFrame frame = new JFrame("ПОСТРОЕНИЕ ДЕРЕВА ИЗ ПУНКТОВ");
                    frame.add(splitPane);
                    frame.setSize(1000, 600);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }

            });
        } catch (IOException e) {
            LOG.severe("File [tree-source.properties] not fount in classpath");
            e.printStackTrace();
        }
    }


    private static void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
}
