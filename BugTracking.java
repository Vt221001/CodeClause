package com.company;
public class Main {
public static void main(String[] args) {
new Bug();
}
}
package com.company;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
public class Bug {
private JTextField idData;
private JTable table1;
private JButton ADDRECORDButton;
private JButton UPDATERECORDButton;
private JPanel bugPanel;
private JTextArea description;
private JComboBox product;
private JComboBox environment;
private JComboBox type;
private JComboBox status;
JFrame bugF = new JFrame();
public Bug(){
bugF.setContentPane(bugPanel);
bugF.pack();
bugF.setLocationRelativeTo(null);
bugF.setVisible(true);
tableData();
ADDRECORDButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
if(idData.getText().equals("")||description.getText().equals("")){
JOptionPane.showMessageDialog(null,"Please Fill All Fields to add Bug.");
}else{
//
try {
String sql = "insert into bug"+"(Bug_ID,Product,Environment,Type,Description,Status)"+"values (?,?,?,?,?,?)";
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
PreparedStatement statement = connection.prepareStatement(sql);
statement.setInt(1,Integer.parseInt(idData.getText()));
statement.setString(2, ""+product.getSelectedItem());
statement.setString(3, ""+environment.getSelectedItem());
statement.setString(4,""+type.getSelectedItem());
statement.setString(5,description.getText());
statement.setString(6,""+status.getSelectedItem());
statement.executeUpdate();
JOptionPane.showMessageDialog(null,"ITEM ADDED SUCCESSFULLY");
idData.setText("");
description.setText("");
}catch (Exception ex){
JOptionPane.showMessageDialog(null,ex.getMessage());
}
tableData();
}
}
});
UPDATERECORDButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
try{
String sql = "UPDATE bug " +
"SET Product = '"+ product.getSelectedItem()+"',Environment='"+ environment.getSelectedItem()+
"',Description='"+description.getText()+"',Status='"+status.getSelectedItem()+"'" +
" WHERE Bug_ID="+Integer.parseInt(idData.getText());
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
PreparedStatement statement = connection.prepareStatement(sql);
statement.executeUpdate();
JOptionPane.showMessageDialog(null,"Updated successfully");
}catch (Exception e2){
System.out.println(e2);
}
tableData();
}
});
table1.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent e) {
DefaultTableModel dm = (DefaultTableModel)table1.getModel();
int selectedRow = table1.getSelectedRow();
idData.setText(dm.getValueAt(selectedRow,0).toString());
description.setText(dm.getValueAt(selectedRow,4).toString());
}
});
}
public void tableData() {
try{
String a= "Select* from bug";
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
Statement statement = connection.createStatement();
ResultSet rs = statement.executeQuery(a);
// table1.setModel(new DefaultTableModel(null, new String[]{"ID", "ITEM NAME", "QUANTITY", "PRICE"}));
table1.setModel(buildTableModel(rs));
}catch (Exception ex1){
JOptionPane.showMessageDialog(null,ex1.getMessage());
}
}
public static DefaultTableModel buildTableModel(ResultSet rs)
throws SQLException {
ResultSetMetaData metaData = rs.getMetaData();
// names of columns
Vector<String> columnNames = new Vector<String>();
int columnCount = metaData.getColumnCount();
for (int column = 1; column <= columnCount; column++) {
columnNames.add(metaData.getColumnName(column));
}
// data of the table
Vector<Vector<Object>> data = new Vector<Vector<Object>>();
while (rs.next()) {
Vector<Object> vector = new Vector<Object>();
for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
vector.add(rs.getObject(columnIndex));
}
data.add(vector);
}
return new DefaultTableModel(data, columnNames);
}
}