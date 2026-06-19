//25%
package vhg;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import javax.swing.event.*;

public class VHG extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VHG().setVisible(true));
    }

    // Database variables
    private Connection con;
    static final String URL_DB = "jdbc:mysql://localhost:3306/virtualherbalgarden";
    static final String USER = "root";
    static final String PASS = "yuva07@sql";

    // Panels
    private JPanel mainPanel;
    private JPanel welcomePanel;
    private CardLayout cardLayout;
    private JPanel containerPanel;

    // Components for main page
    private JTextField inputField;
    private JButton searchPartialBtn, getBotanicalBtn, getMedicinalBtn, clearBtn, backBtn;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;
    private JPopupMenu suggestionPopup;

    // Panels for authentication
    private JPanel loginPanel;
    private JPanel signupPanel;

    public VHG() {
        setTitle("----------VIRTUAL HERBAL GARDEN----------");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initDB();

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        createWelcomePanel();
        createLoginPanel();
        createSignupPanel();
        createMainPanel();

        containerPanel.add(welcomePanel, "welcome");
        containerPanel.add(loginPanel, "login");
        containerPanel.add(signupPanel, "signup");
        containerPanel.add(mainPanel, "main");

        add(containerPanel);

        cardLayout.show(containerPanel, "welcome");
    }

    private void initDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL_DB, USER, PASS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Styled Welcome Panel
    private void createWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(230, 245, 230));
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("WELCOME TO THE VIRTUAL HERBAL GARDEN");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(0, 100, 0));
        welcomePanel.add(title);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Image container with border and padding
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imageLabel = new JLabel();
        URL imgURL = getClass().getResource("newpic2.png");
        if (imgURL == null) {
            System.out.println("Image not found!");
           
            imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            imageLabel.setForeground(Color.BLACK);
            imageLabel.setPreferredSize(new Dimension(1000, 1000));
        } else {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(1126, 500, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        }
        imagePanel.add(imageLabel);
        welcomePanel.add(imagePanel);
        welcomePanel.add(Box.createRigidArea(new Dimension(100,35)));

        JButton enterBtn = new JButton("Explore");
        styleButton(enterBtn);
        enterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterBtn.setMaximumSize(new Dimension(200, 45));
        enterBtn.addActionListener(e -> cardLayout.show(containerPanel, "login"));

        welcomePanel.add(enterBtn);
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        loginPanel.setBackground(new Color(230, 245, 230));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(0, 100, 0));
        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLabel.setForeground(new Color(0, 70, 0));
        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        userField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLabel.setForeground(new Color(0, 70, 0));
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton toSignupBtn = new JButton("Create Account");
        styleButtonLink(toSignupBtn);
        toSignupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // New Back button
        JButton backToWelcomeBtn = new JButton("Back");
        styleButtonLink(backToWelcomeBtn);
        backToWelcomeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel.add(loginBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(toSignupBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(backToWelcomeBtn);

        loginPanel.add(buttonsPanel);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginPanel, "Please enter username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (authenticateUser(username, password)) {
                JOptionPane optionPane = new JOptionPane("Login successful!", JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = optionPane.createDialog(loginPanel, "Success");

                new Timer(700, esu -> dialog.dispose()).start();

                dialog.setVisible(true);
                inputField.setText("");
                userField.setText("");
                passField.setText("");
                cardLayout.show(containerPanel, "main");
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        toSignupBtn.addActionListener(e -> {
            userField.setText("");
            passField.setText("");
            cardLayout.show(containerPanel, "signup");
        });

        backToWelcomeBtn.addActionListener(e -> {
            userField.setText("");
            passField.setText("");
            cardLayout.show(containerPanel, "welcome");
        });
    }
    // Styled Signup Panel
    private void createSignupPanel() {
        signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        signupPanel.setBackground(new Color(230, 245, 230));

        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(0, 100, 0));
        signupPanel.add(title);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel userLabel = new JLabel("Choose Username:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLabel.setForeground(new Color(0, 70, 0));
        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        userField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        signupPanel.add(userLabel);
        signupPanel.add(userField);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel passLabel = new JLabel("Choose Password:");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLabel.setForeground(new Color(0, 70, 0));
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        signupPanel.add(passLabel);
        signupPanel.add(passField);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        JButton signupBtn = new JButton("Sign Up");
        styleButton(signupBtn);
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backToLoginBtn = new JButton("Back to Login");
        styleButtonLink(backToLoginBtn);
        backToLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel.add(signupBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(backToLoginBtn);

        signupPanel.add(buttonsPanel);

        signupBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(signupPanel, "Please enter username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(signupPanel, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                userField.setText("");
                passField.setText("");
                cardLayout.show(containerPanel, "login");
            } else {
                JOptionPane.showMessageDialog(signupPanel, "Username already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backToLoginBtn.addActionListener(e -> {
            userField.setText("");
            passField.setText("");
            cardLayout.show(containerPanel, "login");
        });
    }

    // Styled Main Search Panel
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(230, 245, 230));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(new Color(230, 245, 230));
        JLabel inputLabel = new JLabel("Enter Name or Common Name: ");
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        inputLabel.setForeground(new Color(0, 100, 0));
        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 0), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBackground(new Color(230, 245, 230));
        searchPartialBtn = new JButton("Get Details");
        getBotanicalBtn = new JButton("Get Botanical Name");
        getMedicinalBtn = new JButton("Get Medicinal Use");
        clearBtn = new JButton("Clear Output");
        backBtn = new JButton("LOGOUT");

        styleButton(searchPartialBtn);
        styleButton(getBotanicalBtn);
        styleButton(getMedicinalBtn);
        styleButton(clearBtn);
        styleButton(backBtn);

        buttonPanel.add(searchPartialBtn);
        buttonPanel.add(getBotanicalBtn);
        buttonPanel.add(getMedicinalBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(backBtn);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 0), 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        suggestionPopup = new JPopupMenu();

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                String text = inputField.getText().trim();
                if (text.isEmpty()) {
                    suggestionPopup.setVisible(false);
                    return;
                }
                showSuggestions(text);
            }

            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });

        inputField.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke("ENTER"), "none");

        searchPartialBtn.addActionListener(e -> searchPartial());
        getBotanicalBtn.addActionListener(e -> getBotanical());
        getMedicinalBtn.addActionListener(e -> getMedicinal());
        clearBtn.addActionListener(e -> {
            resultsPanel.removeAll();
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });

        backBtn.addActionListener(e -> cardLayout.show(containerPanel, "welcome"));
    }

    // Button styling helper
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 120, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 150, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 0));
            }
        });
    }

    // Link-style button for "create account" and "back to login"
    private void styleButtonLink(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setForeground(new Color(0, 100, 0));
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(0, 150, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(0, 100, 0));
            }
        });
    }

    // Suggestion popup
    private void showSuggestions(String text) {
        suggestionPopup.removeAll();
        String query = "SELECT Name, Common_name FROM plants_info WHERE Name LIKE ? OR Common_name LIKE ? LIMIT 10";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, text + "%");
            pstmt.setString(2, text + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasResults = false;
                while (rs.next()) {
                    hasResults = true;
                    String name = rs.getString("Name");
                    String common = rs.getString("Common_name");

                    String display = name;
                    if (!name.equalsIgnoreCase(common)) {
                        display += " (" + common + ")";
                    }

                    JMenuItem item = new JMenuItem(display);
                    item.setFont(new Font("SansSerif", Font.PLAIN, 13));

                    item.addActionListener(ev -> {
                        inputField.setText(name);
                        suggestionPopup.setVisible(false);
                    });

                    suggestionPopup.add(item);
                }

                if (hasResults) {
                    suggestionPopup.show(inputField, 0, inputField.getHeight());
                    inputField.requestFocusInWindow();
                } else {
                    suggestionPopup.setVisible(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search partial
    private void searchPartial() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT * FROM plants_info WHERE Name LIKE ? OR Common_name LIKE ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, "%" + input + "%");
            pstmt.setString(2, "%" + input + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                resultsPanel.removeAll();
                boolean found = false;
                while (rs.next()) {
                    addPlantCard(rs);
                    found = true;
                }
                if (!found) {
                    resultsPanel.add(new JLabel("PLANT NOT FOUND FOR THE SEARCHED ITEM !"));
                }
                resultsPanel.revalidate();
                resultsPanel.repaint();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void getBotanical() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT * FROM plants_info WHERE Name = ? OR Common_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, input);
            pstmt.setString(2, input);
            try (ResultSet rs = pstmt.executeQuery()) {
                resultsPanel.removeAll();
                if (rs.next()) addPlantCard(rs, "botanical");
                else resultsPanel.add(new JLabel("PLANT NOT FOUND , CANT GET BOTANICAL NAME !"));
                resultsPanel.revalidate();
                resultsPanel.repaint();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void getMedicinal() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT * FROM plants_info WHERE Name = ? OR Common_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, input);
            pstmt.setString(2, input);
            try (ResultSet rs = pstmt.executeQuery()) {
                resultsPanel.removeAll();
                if (rs.next()) addPlantCard(rs, "medicinal");
                else resultsPanel.add(new JLabel("PLANT NOT FOUND , CANT GET MEDICINAL USES !"));
                resultsPanel.revalidate();
                resultsPanel.repaint();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addPlantCard(ResultSet rs) throws SQLException {
        addPlantCard(rs, "full");
    }

    private void addPlantCard(ResultSet rs, String mode) throws SQLException {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        JTextArea plantText = new JTextArea();
        plantText.setEditable(false);
        plantText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        plantText.setBackground(Color.WHITE);
        switch (mode) {
            case "botanical":
                plantText.setText("Botanical Name: " + rs.getString("Botanical_name") + "\n");
                break;
            case "medicinal":
                plantText.setText("-------------------------------------------------------------\n" +
                        "Name: " + rs.getString("Name") + "\n" +
                        "Common Name: " + rs.getString("Common_name") + "\n" +
                        "Medicinal Use: " + rs.getString("Medicinal_use") + "\n");
                break;
            default:
                plantText.setText(getPlantDetails(rs));
                break;
        }
        card.add(plantText, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 3));
        resultsPanel.add(card);
    }

    private String getPlantDetails(ResultSet rs) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("\n---------------------------------------------------------------\n");
        sb.append("Name: ").append(rs.getString("Name")).append("\n");
        sb.append("Botanical Name: ").append(rs.getString("Botanical_name")).append("\n");
        sb.append("Common Name: ").append(rs.getString("Common_name")).append("\n");
        sb.append("Family: ").append(rs.getString("Family")).append("\n");
        sb.append("Category: ").append(rs.getString("Category")).append("\n");
        sb.append("Climate: ").append(rs.getString("Climate")).append("\n");
        sb.append("Soil Type: ").append(rs.getString("Soil_type")).append("\n");
        sb.append("Soil pH: ").append(rs.getString("Soil_pH")).append("\n");
        sb.append("Medicinal Use: ").append(rs.getString("Medicinal_use")).append("\n");
        sb.append("Image URL: ").append(rs.getString("image_url")).append("\n");
        return sb.toString();
    }

    // Authentication helper methods
    private boolean authenticateUser(String username, String password) {
        String query = "SELECT password FROM users_info WHERE Username = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    return password.equals(storedHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        String checkQuery = "SELECT User_id FROM users_info WHERE Username = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        String insertQuery = "INSERT INTO users_info (Username, password) VALUES (?, ?)";
        try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}