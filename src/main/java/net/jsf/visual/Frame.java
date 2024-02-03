package net.jsf.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import net.jsf.payroll.EmployeeNotFound;
import net.jsf.payroll.PayrollSystem;
import net.miginfocom.swing.MigLayout;


class RoundedPanel extends JPanel {
    private int radius = 10;
    private Color backgroundColor = null;

    public RoundedPanel(LayoutManager layoutManager, Color backgroundColor) {
        super(layoutManager);

        this.backgroundColor = backgroundColor;
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);
        Dimension corners = new Dimension(radius, radius);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setColor(this.backgroundColor);

        graphics2D.fillRoundRect(0, 0, panelWidth - 2, panelHeight - 2, corners.width, corners.height);
        graphics2D.setColor(getForeground());
        graphics2D.drawRoundRect(0, 0, panelWidth - 2, panelHeight - 2, corners.width, corners.height);
    }
}

class RoundedTextField extends JTextField {
    private int radius = 15;
    private Color backgroundColor = null;
    private Color borderColor = null;
    private Dimension corners = new Dimension(radius, radius);
    private Shape shape;

    public RoundedTextField(Color backgroundColor, Color borderColor) {
        setOpaque(false);
        setHorizontalAlignment(JTextField.CENTER);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(this.backgroundColor);
        graphics.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, corners.width, corners.height);
        super.paintComponent(graphics);
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        graphics.setColor(this.borderColor);
        graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, corners.width, corners.height);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, corners.width, corners.height);
        }
        return shape.contains(x, y);
    }
}

public class Frame extends JFrame {
    private final Dimension windowSize = new Dimension(500, 350);
    private final Image windowIcon = new ImageIcon("Payroll System.png").getImage();
    private Dimension verificationTextFieldSize = new Dimension(200, 20);
    private Dimension verificationContainerSize = new Dimension(350, (int) windowSize.getHeight());
    private Dimension verificationPanelSize = new Dimension((int) windowSize.getWidth() - 170, (int) windowSize.getHeight() - 20);

    private Color backgroundGreenColor = new Color(0, 173, 91);
    private Color backgroundGrayColor = new Color(18, 18, 18);
    private Color backgroundBlackColor = new Color(0, 0, 0);
    private Color textWhiteForeground = new Color(230, 230, 230);

    private Font employeeIDLabelFont = new Font("Arial", 1, 28);
    private Font textFieldFont = new Font("Helvetica", 0, 22);
    private Font verificationTextFieldLabelFont = new Font("Arial", 1, 14);

    private BorderLayout windowLayout = new BorderLayout();
    private MigLayout verificationLayout = new MigLayout("wrap, align 50% 50%", "[]", "[]20[]5[]");

    private RoundedPanel verificationPanel;
    private RoundedPanel verificationAuthButton;
    private RoundedPanel verificationEnterButton;
    private RoundedPanel employeeDetailPanel;
    
    private RoundedTextField verificationDaysWorkedTextField;
    private RoundedTextField verificationTextField;
    private JPanel verificationContainerPanel;
    private JPanel verificationBlankPanel;
    private JPanel invisibleFieldContainer;

    private JLabel verificationDaysWorkedTextFieldLabel;
    private JLabel verificationLabel;
    private JLabel verificationTextFieldLabel;
    private JLabel verificationAuthButtonLabel;
    private JLabel verificationEnterButtonLabel;
    private JLabel employeeIDLabel;
    private JLabel employeeDaysWorkedLabel;
    private JLabel employeeGrossPayLabel;
    private JLabel employeeTaxLabel;
    private JLabel employeeNetPayLabel;
    private JLabel employeeDailyRateLabel;

    private PayrollSystem payrollSystem;

    public Frame() {
        setSize(windowSize);
        setIconImage(windowIcon);
        setLayout(windowLayout);
        setResizable(false);
        setTitle("Java Payroll System");
        initVerificationContainer();
        initVerificationBlankPanel();
        pack();
        setVisible(true);
    }

    private void initVerificationContainer() {
        verificationContainerPanel = new JPanel(new MigLayout());
        verificationContainerPanel.setPreferredSize(verificationContainerSize);
        verificationContainerPanel.setBackground(backgroundBlackColor);

        initVerificationPanel();

        verificationContainerPanel.add(verificationPanel, "dock center");

        add(verificationContainerPanel, BorderLayout.WEST);

    }

    private void initVerificationPanel() {
        verificationPanel = new RoundedPanel(verificationLayout, backgroundBlackColor);
        verificationPanel.setLayout(verificationLayout);
        verificationPanel.setPreferredSize(verificationPanelSize);

        initVerificationLabel(verificationPanel);
        initVerificationField(verificationPanel);
    }

    private void initVerificationField(RoundedPanel panel) {
        invisibleFieldContainer = new JPanel(new MigLayout("wrap", "[]10[]"));
        invisibleFieldContainer.setBackground(backgroundBlackColor);

        verificationTextField = new RoundedTextField(backgroundBlackColor, backgroundGreenColor);
        verificationTextField.setPreferredSize(verificationTextFieldSize);
        verificationTextField.setFont(textFieldFont);
        verificationTextField.setForeground(textWhiteForeground);
        verificationTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                onVerificationTextFieldType(evt);
            }
        });

        verificationTextFieldLabel = new JLabel("Employee ID: ");
        verificationTextFieldLabel.setFont(verificationTextFieldLabelFont);
        verificationTextFieldLabel.setForeground(textWhiteForeground);

        verificationAuthButton = new RoundedPanel(new MigLayout("align 50% 50%"), backgroundGreenColor);
        verificationAuthButton.setPreferredSize(new Dimension(50, verificationTextField.getHeight()));
        verificationAuthButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                onVerificationAuthButtonEnter(evt);
            }
            public void mouseExited(MouseEvent evt) {
                onVerificationAuthButtonExit(evt);
            }
            public void mouseClicked(MouseEvent evt) {
                onVerificationAuthButtonClick(evt);
            }
        });

        verificationAuthButtonLabel = new JLabel("Auth");
        verificationAuthButtonLabel.setFont(verificationTextFieldLabelFont);
        verificationAuthButtonLabel.setForeground(backgroundBlackColor);
        verificationAuthButton.add(verificationAuthButtonLabel);

        invisibleFieldContainer.add(verificationTextFieldLabel);
        invisibleFieldContainer.add(verificationTextField);

        panel.add(invisibleFieldContainer, "align center");
        panel.add(verificationAuthButton, "align center");
    }

    private void initVerificationLabel(RoundedPanel panel) {
        verificationLabel = new JLabel("Employee Verification");
        verificationLabel.setFont(employeeIDLabelFont);
        verificationLabel.setForeground(textWhiteForeground);

        panel.add(verificationLabel, "align center");
    }  

    private void initVerificationBlankPanel() {
        verificationBlankPanel = new JPanel(new MigLayout("align 50% 50%"));
        verificationBlankPanel.setPreferredSize(new Dimension((int) (windowSize.getWidth() - verificationContainerPanel.getWidth()), (int) windowSize.getHeight()));
        verificationBlankPanel.setBackground(backgroundGrayColor);

        add(verificationBlankPanel, BorderLayout.CENTER);
    }

    private void onVerificationTextFieldType(KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            RoundedTextField textField = (RoundedTextField) evt.getSource();

            String textFieldText = textField.getText().replaceAll(" ", "");
    
            if (textFieldText.length() == 3 || textFieldText.length() == 6) {
                textField.setText(textField.getText() + " ");
            }
            else if (textFieldText.length() == 9) {
                textField.setEditable(false);
            }
        }
    }

    private void onVerificationAuthButtonEnter(MouseEvent evt) {
        String textFieldText = verificationTextField.getText().replaceAll(" ", "");

        if (textFieldText.length() == 9) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
    }

    private void onVerificationAuthButtonExit(MouseEvent evt) {
        String textFieldText = verificationTextField.getText().replaceAll(" ", "");

        if (textFieldText.length() >= 9) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void onVerificationAuthButtonClick(MouseEvent evt) {
        String idTextFieldText = verificationTextField.getText().replaceAll(" ", "");
        int employeeID = 0;

        if (idTextFieldText.length() == 9) {
            try {
                employeeID = Integer.parseInt(idTextFieldText);

                try {
                    PayrollSystem.accessEmployee(employeeID);
                    
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    invisibleFieldContainer.removeAll();
                    verificationPanel.remove(verificationAuthButton);

                    verificationDaysWorkedTextField = new RoundedTextField(backgroundBlackColor, backgroundGreenColor);
                    verificationDaysWorkedTextField.setPreferredSize(verificationTextFieldSize);
                    verificationDaysWorkedTextField.setFont(textFieldFont);
                    verificationDaysWorkedTextField.setForeground(textWhiteForeground);
            
                    verificationDaysWorkedTextFieldLabel = new JLabel("Days Worked: ");
                    verificationDaysWorkedTextFieldLabel.setFont(verificationTextFieldLabelFont);
                    verificationDaysWorkedTextFieldLabel.setForeground(textWhiteForeground);
                    verificationDaysWorkedTextField.requestFocusInWindow();
                    
                    verificationEnterButton = new RoundedPanel(new MigLayout("align 50% 50%"), backgroundGreenColor);
                    verificationEnterButton.setPreferredSize(new Dimension(50, verificationTextField.getHeight()));
                    verificationEnterButton.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent evt) {
                            onVerificationEnterButtonEnter(evt);
                        }
                        public void mouseExited(MouseEvent evt) {
                            onVerificationEnterButtonExit(evt);
                        }
                        public void mouseClicked(MouseEvent evt) {
                            onVerificationEnterButtonClick(evt);
                        }
                    });

                    verificationEnterButtonLabel = new JLabel("Enter");
                    verificationEnterButtonLabel.setFont(verificationTextFieldLabelFont);
                    verificationEnterButtonLabel.setForeground(backgroundBlackColor);
                    verificationEnterButton.add(verificationEnterButtonLabel);

                    invisibleFieldContainer.add(verificationDaysWorkedTextFieldLabel);
                    invisibleFieldContainer.add(verificationDaysWorkedTextField);
                    verificationPanel.add(verificationEnterButton, "align center");

                    invisibleFieldContainer.repaint();
                    invisibleFieldContainer.revalidate();

                    verificationPanel.repaint();
                    verificationPanel.revalidate();
                }
                
                catch (EmployeeNotFound error) {
                    SwingUtilities.invokeLater(() -> {
                        verificationTextField.setText(error.getMessage());
                        verificationTextField.setFont(new Font(textFieldFont.getFontName(), 1, 16));
                        verificationTextField.setForeground(textWhiteForeground);
                        verificationTextField.setEditable(false);
                        verificationTextField.requestFocusInWindow();
                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                            }
                            catch (InterruptedException err) {
                                Thread.currentThread().interrupt();
                            }
                            SwingUtilities.invokeLater(() -> {
                                verificationTextField.setText("");
                                verificationTextField.setForeground(textWhiteForeground);
                                verificationTextField.setFont(textFieldFont);

                                verificationTextField.setEditable(true);
                                verificationTextField.requestFocusInWindow();

                            });
                        }).start();   
                    });
                }
            }
            catch (NumberFormatException err) {
                SwingUtilities.invokeLater(()-> {
                    verificationTextField.setText("Invalid Employee ID");
                    verificationTextField.setFont(new Font(textFieldFont.getFontName(), 1, 16));
                    verificationTextField.setForeground(textWhiteForeground);
                    verificationTextField.setEditable(false);
                    verificationTextField.requestFocusInWindow();

                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException error) {
                            Thread.currentThread().interrupt();
                        }
    
                        SwingUtilities.invokeLater(() -> {
                            verificationTextField.setText("");
                            verificationTextField.setForeground(textWhiteForeground);
                            verificationTextField.setFont(textFieldFont);
                            
                            verificationTextField.setEditable(true);
                            verificationTextField.requestFocusInWindow();
                        });
                    }).start(); 
                });
            }
        }
    }

    private void onVerificationEnterButtonEnter(MouseEvent evt) {
        if (verificationDaysWorkedTextField.isEditable()) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private void onVerificationEnterButtonExit(MouseEvent evt) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    private void onVerificationEnterButtonClick(MouseEvent evt) {
        if (employeeDetailPanel == null) {
            int days = 0;

            try {
                days = Integer.parseInt(verificationDaysWorkedTextField.getText());

                payrollSystem = new PayrollSystem();
                payrollSystem.setDaysWorked(days);
                payrollSystem.calculate();

                verificationDaysWorkedTextField.setText("");
                verificationDaysWorkedTextField.setEditable(false);

                employeeDetailPanel = new RoundedPanel(new MigLayout("wrap, fill, align 50% 50%", "", "[][]30[][][]20[]"), backgroundGreenColor);
                employeeDetailPanel.setPreferredSize(new Dimension(verificationBlankPanel.getWidth() - 50, verificationBlankPanel.getHeight() - 100));

                employeeIDLabel = new JLabel("Employee " + PayrollSystem.formatEmployeeID(payrollSystem.getEmployeeID()));
                employeeIDLabel.setFont(new Font(verificationDaysWorkedTextFieldLabel.getFont().getName(), 1, 32));
                employeeIDLabel.setForeground(backgroundGrayColor);
    
                employeeDaysWorkedLabel = new JLabel("Worked a total of " + payrollSystem.getemployeeDaysWorked() + " day(s)");
                employeeDaysWorkedLabel.setFont(new Font(verificationDaysWorkedTextFieldLabel.getFont().getName(), 1, 16));
                employeeDaysWorkedLabel.setForeground(backgroundGrayColor);
                
                employeeDailyRateLabel = new JLabel("Daily Wage: $ " + String.format("%,.2f", payrollSystem.getEmployeeDailyRate()));
                employeeDailyRateLabel.setFont(new Font(textFieldFont.getFontName(), 0, 22));
                employeeDailyRateLabel.setForeground(backgroundGrayColor);

                employeeGrossPayLabel = new JLabel("Gross Pay: $ " + String.format("%,.2f", payrollSystem.getEmployeeGrossSalary()));
                employeeGrossPayLabel.setFont(new Font(textFieldFont.getFontName(), 0, 22));
                employeeGrossPayLabel.setForeground(backgroundGrayColor);
    
                employeeTaxLabel = new JLabel("Income Tax: $ " + String.format("%,.2f", payrollSystem.getEmployeeTax()));
                employeeTaxLabel.setFont(new Font(textFieldFont.getFontName(), 0, 22));
                employeeTaxLabel.setForeground(backgroundGrayColor);
    
                employeeNetPayLabel = new JLabel("Net Pay: $ " + String.format("%,.2f", payrollSystem.getEmployeeNetPay()));
                employeeNetPayLabel.setFont(new Font(textFieldFont.getFontName(), 0, 22));
                employeeNetPayLabel.setForeground(backgroundGrayColor);

                employeeDetailPanel.add(employeeIDLabel);
                employeeDetailPanel.add(employeeDaysWorkedLabel);
                employeeDetailPanel.add(employeeDailyRateLabel);
                employeeDetailPanel.add(employeeGrossPayLabel);
                employeeDetailPanel.add(employeeTaxLabel);
                employeeDetailPanel.add(employeeNetPayLabel);

                verificationBlankPanel.add(employeeDetailPanel, "align center");
                verificationBlankPanel.revalidate();
            }
            
            catch (NumberFormatException error) {
                SwingUtilities.invokeLater(() -> {
                    verificationDaysWorkedTextField.setText("Invalid Days Worked");
                    verificationDaysWorkedTextField.setFont(new Font(textFieldFont.getFontName(), 1, 16));
                    verificationDaysWorkedTextField.setForeground(textWhiteForeground);
                    verificationDaysWorkedTextField.setEditable(false);
                    verificationDaysWorkedTextField.requestFocusInWindow();
    
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException err) {
                            Thread.currentThread().interrupt();
                        }
        
                        SwingUtilities.invokeLater(() -> {
                            verificationDaysWorkedTextField.setText("");
                            verificationDaysWorkedTextField.setForeground(textWhiteForeground);
                            verificationDaysWorkedTextField.setFont(textFieldFont);
    
                            verificationDaysWorkedTextField.setEditable(true);
                            verificationDaysWorkedTextField.requestFocusInWindow();
                        });
                    }).start(); 
                });
            }
        }
    }
}
