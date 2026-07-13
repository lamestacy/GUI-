import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessGame extends JFrame {
    private Consumer consumer = new Consumer();
    private Timer animationTimer;
    private int animationStep = 0;

    private JButton bigButton;
    private JButton smallButton;

    public GuessGame() {
        setTitle("猜大小游戏");
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); 

        // 按钮界面
        JPanel panel1 = new JPanel();
        panel1.setBounds(10, 10, 930, 270);
        panel1.setBackground(new Color(215, 228, 239));
        panel1.setLayout(new BorderLayout());

        JLabel label1 = new JLabel("请按下大或小按钮", JLabel.CENTER);
        label1.setFont(new Font("宋体", Font.BOLD, 24));
        panel1.add(label1, BorderLayout.NORTH);

        // 按钮设置
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1, 2, 20, 20));
        buttonContainer.setBackground(new Color(215, 228, 239));

        bigButton = new JButton(new ImageIcon("images/b1.png")); // 红色大按钮
        smallButton = new JButton(new ImageIcon("images/s1.png")); // 红色小按钮

        buttonContainer.add(bigButton);
        buttonContainer.add(smallButton);
        panel1.add(buttonContainer, BorderLayout.CENTER);

        add(panel1);

        // 骰子初始界面
        JPanel panel2 = new JPanel();
        panel2.setBounds(10, 300, 930, 730);
        panel2.setBackground(new Color(194, 213, 232));
        panel2.setLayout(new GridLayout(1, 3, 20, 20));
        JLabel dice1 = new JLabel(new ImageIcon("images/t1.png"));
        JLabel dice2 = new JLabel(new ImageIcon("images/t2.png"));
        JLabel dice3 = new JLabel(new ImageIcon("images/t3.png"));

        panel2.add(dice1);
        panel2.add(dice2);
        panel2.add(dice3);
        add(panel2);

        // 背景1
        JPanel panel3 = new JPanel();
        panel3.setBounds(10, 1040, 540, 160);
        panel3.setBackground(new Color(154, 204, 202));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel3.add(resultArea);
        add(panel3);

        // 背景2
        JPanel panel4 = new JPanel();
        panel4.setBounds(960, 10, 970, 340);
        panel4.setBackground(new Color(240, 228, 212));
        panel4.setBorder(BorderFactory.createLineBorder(new Color(239, 179, 177), 2));
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        panel4.setLayout(new BorderLayout());
        panel4.add(new JScrollPane(historyArea), BorderLayout.CENTER);
        add(panel4);

        // 背景3
        JPanel panel5 = new JPanel();
        panel5.setBounds(960, 360, 810, 1015);
        panel5.setBackground(new Color(240, 228, 212));
        add(panel5);

        // “大”按钮变色设置
        bigButton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
        bigButton.setIcon(new ImageIcon("images/b2.png")); // 鼠标悬停时，变为黄色
        }
            
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
        if (bigButton.isEnabled()) { // 仅在按钮未禁用时恢复颜色
        bigButton.setIcon(new ImageIcon("images/b1.png")); // 恢复红色
        }
        }
            });

            // 执行游戏
            bigButton.addActionListener(_ -> {
            bigButton.setEnabled(false); // 禁用“大”按钮
            smallButton.setEnabled(false); // 禁用“小”按钮
            Touzi("大", resultArea, historyArea, dice1, dice2, dice3, bigButton, smallButton);
        });


            // “小”按钮变色设置
        smallButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                smallButton.setIcon(new ImageIcon("images/s2.png")); // 鼠标悬停时，变为黄色
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (smallButton.isEnabled()) { // 仅在按钮未禁用时恢复颜色
                    smallButton.setIcon(new ImageIcon("images/s1.png")); // 恢复红色
                }
            }
        });

        // 执行游戏
        smallButton.addActionListener(_ -> {
            bigButton.setEnabled(false); // 禁用“大”按钮
            smallButton.setEnabled(false); // 禁用“小”按钮
            Touzi("小", resultArea, historyArea, dice1, dice2, dice3, bigButton, smallButton);
        });

        setVisible(true);
    }
    
    private void Touzi(String guess, JTextArea resultArea, JTextArea historyArea, JLabel dice1, JLabel dice2, JLabel dice3, JButton bigButton, JButton smallButton) {
        Random random = new Random();
        int[] diceValues = new int[3];
        for (int i = 0; i < 3; i++) {
            diceValues[i] = random.nextInt(6) + 1;
        }

        // 骰子动画
        animationStep = 0;
        animationTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                dice1.setIcon(new ImageIcon("images/t" + (random.nextInt(6) + 1) + ".png"));
                dice2.setIcon(new ImageIcon("images/t" + (random.nextInt(6) + 1) + ".png"));
                dice3.setIcon(new ImageIcon("images/t" + (random.nextInt(6) + 1) + ".png"));

                if (animationStep >= 10) { // 动画播放10次后停止
                    animationTimer.stop();

                    // 显示最终结果
                    dice1.setIcon(new ImageIcon("images/t" + diceValues[0] + ".png"));
                    dice2.setIcon(new ImageIcon("images/t" + diceValues[1] + ".png"));
                    dice3.setIcon(new ImageIcon("images/t" + diceValues[2] + ".png"));

                    int sum = diceValues[0] + diceValues[1] + diceValues[2];
                    String result = sum > 10 ? "大" : "小";

                    // 更新结果显示
                    resultArea.setText("三个骰子：" + diceValues[0] + " " + diceValues[1] + " " + diceValues[2] + "\n" +
                            "三个骰子和：" + sum + " " + result + "\n" +
                            "--------------------\n" +
                            "你猜：" + guess + " 骰子结果：" + result + "\n" +
                            "本局结果：" + (guess.equals(result) ? "你赢" : "你输"));

                    // 更新游戏记录
                    consumer.updateGame(guess, diceValues, sum, result, guess.equals(result));
                    historyArea.setText(consumer.getHistory());
                    // 游戏结束后恢复按钮状态
                    bigButton.setIcon(new ImageIcon("images/b1.png")); // 恢复红色
                    smallButton.setIcon(new ImageIcon("images/s1.png")); // 恢复红色
                    bigButton.setEnabled(true); // 启用“大”按钮
                    smallButton.setEnabled(true); // 启用“小”按钮

                }
            }
        });
        animationTimer.start();
    }

    public static void main(String[] args) {
        new GuessGame();
    }
}

class Consumer {
    private int gameCount = 0;
    private int winCount = 0;
    private StringBuilder history = new StringBuilder();

    public void updateGame(String guess, int[] diceValues, int sum, String result, boolean win) {
        gameCount++;
        if (win) winCount++;

        history.append(gameCount).append("\t")
                .append(guess).append("\t")
                .append(diceValues[0]).append(" ")
                .append(diceValues[1]).append(" ")
                .append(diceValues[2]).append("\t")
                .append(sum).append("\t")
                .append(result).append("\t")
                .append(win ? "赢" : "输").append("\n");
    }

    public String getHistory() {
        return "游戏结果：\n序号\t你猜\t三个骰子\t骰子和\t骰子结果\t输赢\n" +
                history.toString() +
                "\n玩了：" + gameCount + "次\t赢了：" + winCount + "次\t成功率：" +
                (gameCount > 0 ? String.format("%.2f", (winCount * 100.0 / gameCount)) : "0") + "%";
    }
}
