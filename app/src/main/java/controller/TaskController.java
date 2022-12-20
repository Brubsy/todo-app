package controller;

import model.Task;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionFactory;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject, " +
                "name, " +
                "description, " +
                "completed, " +
                "notes, " +
                "deadline, " +
                "createdAt, " +
                "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));

            //Executando a query
            statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a tarefa.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET " +
                "idProject = ?, " +
                "name = ?, " +
                "description = ?, " +
                "notes = ?, " +
                "deadline = ?, " +
                "completed = ?, " +
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
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setDate(5, new Date(task.getDeadline().getTime()));
            statement.setBoolean(6, task.isCompleted());
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());

            //Executando a query
            statement.execute();

        } catch(Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void removeById(int taskId) {

        String sql = "DELETE FROM tasks WHERE ID = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a query
            statement = conn.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, taskId);

            //Executando a query
            statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a tarefa.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<>();

        try {
            //Criação da conexão
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houver valores a serem percorridos no resultSet
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir a tarefa.");

        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }

        //Lista de tarefas que foi criada e carregada do banco de dados
        return tasks;
    }
}
