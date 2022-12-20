package controller;

import model.Project;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {

    public void save(Project project) {

        String sql = "INSERT INTO projects(name, " +
                "description, " +
                "createdAt, " +
                "updatedAt) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            //Executando a query
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o projeto.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Project project) {

        String sql = "UPDATE projects SET name = ?, " +
                "description = ?, " +
                "createdAt = ?, " +
                "updatedAt = ?" +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //Executando a query
            statement.execute();

        } catch(Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int idProject) {

        String sql = "DELETE from projects WHERE ID = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, idProject);

            //Executando a query
            statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o projeto.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Project> getAll() {

        String sql = "SELECT * FROM projects";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Project> projects = new ArrayList<>();

        try {
            //Criação da conexão
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houver valores a serem percorridos no resultSet
            while (resultSet.next()) {
                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir o projeto.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }

        //Lista de tarefas que foi criada e carregada do banco de dados
        return projects;
    }
}
