package view;

import model.Point;
import model.QuickHull;
import model.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


@SuppressWarnings("serial")
public class GUI extends JFrame implements UpdateListener {
    private JButton calculate;
    private JButton paintPoints;
    private PointPanel pointPanel;

    public GUI() {
        prepareFrame();
    }

    private void prepareFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        calculate = new JButton();
        pointPanel = new PointPanel();
        paintPoints = new JButton();

        calculate.setText("Calculate Hull");
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCalculate(e);
            }
        });

        paintPoints.setText("Reset Points");
        paintPoints.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPaintPoints(e);
            }
        });


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(paintPoints)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(calculate, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(pointPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pointPanel, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(calculate)
                                        .addComponent(paintPoints)
                                )
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(null);
    }

    private void onCalculate(ActionEvent e) {
        calculate.setEnabled(false);
        paintPoints.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QuickHull qh = new QuickHull();
                qh.setListener(GUI.this);
                try {
                    qh.executeQuickHull(pointPanel.getPoints());
                    calculate.setEnabled(true);
                    paintPoints.setEnabled(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void onPaintPoints(ActionEvent e) {
        ArrayList<model.Point> points = new ArrayList<>();
        points.add(new model.Point(0.12, 0.26));
        points.add(new model.Point(0.18, 0.13));
        points.add(new model.Point(0.2, 0.13));
        points.add(new model.Point(0.53, 0));
        points.add(new model.Point(0.8, 0));
        points.add(new model.Point(0.9, 0));
        points.add(new model.Point(0.98, 0));
        points.add(new model.Point(0.95, 0.04));
        points.add(new model.Point(0.55, 1));
        points.add(new model.Point(0.65, 0.95));
        points.add(new model.Point(0.6, 0.55));
        points.add(new model.Point(0.5, 0.55));
        points.add(new model.Point(0.42, 0.5));
        points.add(new model.Point(0, 0.87));
        points.add(new model.Point(0.13, 0.13));
        points.add(new model.Point(0.02, 0.05));
        points.add(new model.Point(0.04, 0.16));
        points.add(new model.Point(0.2, 0.6));
        points.add(new model.Point(0.1, 0.04));
        points.add(new model.Point(0.15, 0.91));
        points.add(new model.Point(0.15, 1));
        points.add(new model.Point(0.2, 0.91));
        points.add(new Point(0.22, 0.91));
        points.add(new model.Point(0.1, 0.62));
        points.add(new model.Point(0.13, 0.57));
        points.add(new model.Point(0.2, 0));
        this.pointPanel.clearPoints();
        this.pointPanel.addPoints(points);
        this.pointPanel.savePoints(points);
    }

    public void updatePoints(ArrayList<Point> points) {
        pointPanel.clearPoints();
        pointPanel.addPoints(points);
        pointPanel.drawLines();
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.paintPoints.doClick();
                gui.setVisible(true);

            }
        });
    }
}
