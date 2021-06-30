package bcd;

import java.sql.*;
import java.util.concurrent.ExecutionException;

public class Principal {

    private final String DB_URI = "jdbc:sqlite:src/main/resources/banco.sqlite";
    public void listarPessoas(){
        String sql = "SELECT * FROM Pessoa";

        try(Connection conexao = DriverManager.getConnection(DB_URI); // conecta com o db
            Statement statement = conexao.createStatement(); // analogo terminal
            ResultSet rs = statement.executeQuery(sql) //tabela em memoria
        ){
            if(!rs.next()){
                System.out.println("Tabela vazia");
            } else {
                do{
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Email: " + rs.getString("email"));
                }while (rs.next());

            }
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }

//    public void listarDadosDePessoa(String nome){
//        String sql = "SELECT * FROM Pessoa WHERE nome = '" + nome + "' ";
//        System.out.println("Query: " + sql);
//
//        try(Connection conexao = DriverManager.getConnection(DB_URI); // conecta com o db
//            Statement statement = conexao.createStatement(); // analogo terminal
//            ResultSet rs = statement.executeQuery(sql) //tabela em memoria
//        ){
//            if(!rs.next()){
//                System.out.println("Tabela vazia");
//            } else {
//                do{
//                    System.out.println("Nome: " + rs.getString("nome"));
//                    System.out.println("Email: " + rs.getString("email"));
//                }while (rs.next());
//            }
//        }catch (Exception e){
//            System.err.println(e.toString());
//        }
//    }

    public void listarDadosDePessoa(String nome){
        String sql = "SELECT * FROM Pessoa WHERE nome = ?";
        System.out.println("Query: " + sql);

        try(Connection conexao = DriverManager.getConnection(DB_URI); // conecta com o db
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ){
            stmt.setString(1,nome);
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()){
                System.out.println("Tabela vazia");
            } else {
                do{
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Email: " + rs.getString("email"));
                }while (rs.next());
            }
            rs.close();
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }
    public static void main(String[] args) {

        Principal p = new Principal();
        //p.listarPessoas();
        p.listarDadosDePessoa("Joao' OR '1'='1");
        //p.listarDadosDePessoa("Joao';DROP Table Teste;--"); //apaga tudo
    }
}
