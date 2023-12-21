
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBManager {

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/super_market", "root", "root1234");
            if (con != null) {
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return con;
    }

    public void updateProduct(int id, int quantity) {
        try {
            Connection con = getConnection();
            PreparedStatement prest = con.prepareStatement("UPDATE tbl_product SET quantity=?  where id= " + id);
            prest.setInt(1, quantity);
            prest.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("update product error");
        }
    }

    public ArrayList getProducts() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM tbl_product";
            PreparedStatement p = null;
            ResultSet r = null;
            p = con.prepareStatement(query);
            p.clearParameters();
            r = p.executeQuery();
            String name;
            int id, quantity;
            double price, discount;
            while (r.next()) {
                id = r.getInt(1);
                name = r.getString(2);
                price = r.getDouble(3);
                quantity = r.getInt(4);
                discount = r.getDouble(5);
                products.add(new Product(id, name, price, quantity, discount));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Get products errors");
        }
        return products;
    }

    public ArrayList getDiscountedProduct() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM tbl_product where discount != " + 0.0;
            PreparedStatement p = null;
            ResultSet r = null;
            p = con.prepareStatement(query);
            p.clearParameters();
            r = p.executeQuery();
            String name;
            int id, quantity;
            double price, discount;
            while (r.next()) {
                id = r.getInt(1);
                name = r.getString(2);
                price = r.getDouble(3);
                quantity = r.getInt(4);
                discount = r.getDouble(5);
                products.add(new Product(id, name, price, quantity, discount));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Get products errors");
        }
        return products;
    }

    public void addBill(String name, String phone, double bill) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO tbl_bill (name, phone, bill) VALUES(?,?,?)";
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setString(1, name);
            prest.setString(2, phone);
            prest.setDouble(3, bill);
            prest.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
