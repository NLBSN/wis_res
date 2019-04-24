package com.wis.jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

//鼠标及按键
public class MouseControl implements MouseMotionListener, KeyListener {
    private JFrame frame;
    private JLabel tf;
    String ch = ""; //放置待显示的字符

    public static void main(String[] args) {
        MouseControl two = new MouseControl();
        two.go();
    }


    private void go() {
        frame = new JFrame("鼠标按键测试");
        Container contentPane = frame.getContentPane();
        contentPane.add(new JLabel("get mouse and keyboard event"), BorderLayout.NORTH);
        tf = new JLabel();
        contentPane.add(tf, BorderLayout.SOUTH);
        //注册监听程序
        frame.addMouseMotionListener(this);
        frame.addKeyListener(this);

        frame.setSize(300, 300);
        frame.setVisible(true);

    }


    //接下来为实现接口中的方法
    @Override
    public void keyTyped(KeyEvent e) { //顺序 keypress keytype keyrelease
        // TODO Auto-generated method stub
        System.out.println("2");
        int charcode = e.getKeyCode();
        ch = "有键按下2";
        if (charcode == KeyEvent.VK_SHIFT) {
            ch = "shift2";
        }
        if (charcode == KeyEvent.VK_CONTROL) ch = "control2";
    }

    @Override
    public void keyPressed(KeyEvent e) { //看不到显示，因为keytype覆盖了
        // TODO Auto-generated method stub
        System.out.println("1");
        int charcode = e.getKeyCode();
        ch = "有键按下1";
        if (charcode == KeyEvent.VK_SHIFT) ch = "shift1";
        if (charcode == KeyEvent.VK_CONTROL) ch = "control1";
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        //ch = "有键松开";
        System.out.println("3");
    }

    @Override
    public void mouseDragged(MouseEvent e) { //按住鼠标拖动才会调用
        // TODO Auto-generated method stub
        String s = "鼠标拖动的坐标: X =" + e.getX() + " Y =" + e.getY() + " KEY：" + ch;
        tf.setText(s);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        String s = "鼠标移动时的坐标: X =" + e.getX() + " Y =" + e.getY() + " KEY：" + ch;
        tf.setText(s);
    }

}
