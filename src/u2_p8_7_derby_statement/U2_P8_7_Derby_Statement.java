/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2_p8_7_derby_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author mdfda
 */
public class U2_P8_7_Derby_Statement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nombre = args[0], apellidos = args[1] + " " + args[2], email = args[3], dept_no = args[4];
        Date fecha_antes = new Date();
        java.sql.Date fecha_alta = new java.sql.Date(fecha_antes.getTime());
        Float salario = Float.parseFloat(args[5]);
        Pattern patron = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        Boolean emailValido = patron.matcher(email).matches();

        if (salario <= 0.0 || !emailValido) {
            if (salario <= 0.0) {
                System.out.println("El salario no puede ser menor que 0: " + salario);
            }

            if (!emailValido) {
                System.out.println("El email introducido tiene un formato erroneo: " + email);
            }

            System.out.println("El resgistro no se ha podido introducir");
        } else {
            try {
                
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

                Connection con = DriverManager.getConnection("jdbc:derby:C:\\Users\\mdfda\\Desktop\\DAM\\Acceso a Datos (AD)\\Tema 2\\Ejercicios\\clase\\bases\\derby\\ejemplo");

                Statement s = con.createStatement();

                s.executeUpdate("INSERT INTO PROFESORES VALUES((SELECT MAX(NRP) FROM PROFESORES) + 1, '" + nombre + "' , '" + apellidos + "', '" + email + "', '" + fecha_alta + "', " + dept_no + ", " + salario + ")");

                s.close();
                con.close();

            } catch (SQLException ex) {

                System.out.println("Código de error: " + ex.getErrorCode());
                System.out.println("Mensaje de error: " + ex.getMessage());
                System.out.println("Estado SQL: " + ex.getSQLState());

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(U2_P8_7_Derby_Statement.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                System.out.println("El registro se ha introducido correctamente");
            }
        }
    }

}
