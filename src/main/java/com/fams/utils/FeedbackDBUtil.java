package com.fams.utils;

import com.fams.App;
import com.fams.model.Fragment;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public enum FeedbackDBUtil {
    INSTANCE;

    private Connection conn = null;
/*
    FeedbackDBUtil() {
        //initializeDB("jdbc:sqlite:feedback.db");
        String basePath = AppConfig.getBasePathForJar(App.class);
        initializeDB("jdbc:sqlite:" + Paths.get(basePath, "feedback.db").toString());
    }
*/

    FeedbackDBUtil() {
        try {
            // Get the base path where the jar or working directory is
            String basePath = AppConfig.getBasePathForJar(App.class);
            // Remove any leading forward slash if present (Windows compatibility)
            if (basePath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("win")) {
                basePath = basePath.substring(1);
            }
            // Build the DB path safely
            java.nio.file.Path dbPath = Paths.get(basePath, "feedback.db").toAbsolutePath().normalize();
            // Initialize the SQLite DB
            initializeDB("jdbc:sqlite:" + dbPath.toString());
            System.out.println("Feedback DB initialized at: " + dbPath);
        } catch (Exception e) {
            System.err.println("Failed to initialize FeedbackDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeDB(String url) {
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");

            // create a database connection
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // Create Fragments table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Fragments " +
                    "(FragmentIdentifier TEXT PRIMARY KEY, " +
                    "Content TEXT, " +
                    "Course TEXT, " +
                    "Assignment TEXT)");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private Fragment buildFragmentFromResultSet(ResultSet resultSet) throws SQLException {
        String fragmentIdentifier = resultSet.getString("FragmentIdentifier");
        String content = resultSet.getString("Content");
        String course = resultSet.getString("Course");
        String assignment = resultSet.getString("Assignment");

        return new Fragment(fragmentIdentifier, content, course, assignment);
    }


    public Fragment getFragment(String fragmentIdentifier) {
        Fragment fragment = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Fragments WHERE FragmentIdentifier = ?");
            statement.setString(1, fragmentIdentifier);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                fragment = buildFragmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return fragment;
    }

    public List<Fragment> getAllFragments() {
        List<Fragment> fragments = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Fragments");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Fragment fragment = buildFragmentFromResultSet(resultSet);
                fragments.add(fragment);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return fragments;
    }

    public boolean updateFragment(Fragment fragment) {
        boolean isSuccess = false;
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE Fragments SET Content = ?, Course = ?, Assignment = ? WHERE FragmentIdentifier = ?");
            statement.setString(1, fragment.getContent());
            statement.setString(2, fragment.getCourse());
            statement.setString(3, fragment.getAssignment());
            statement.setString(4, fragment.getFragmentIdentifier());

            int updatedRows = statement.executeUpdate();
            isSuccess = updatedRows > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return isSuccess;
    }
    
    public boolean insertNewFragment(String fragmentIdentifier, String course, String assignment) {
        String sql = "INSERT INTO Fragments(FragmentIdentifier, Content, Course, Assignment) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,  fragmentIdentifier);
            pstmt.setString(2, "");
            pstmt.setString(3, course);
            pstmt.setString(4, assignment);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean removeFragment(String fragmentIdentifier) {
        boolean isRemoved = false;
        String sql = "DELETE FROM Fragments WHERE FragmentIdentifier = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fragmentIdentifier);
            int deletedRows = pstmt.executeUpdate();
            isRemoved = deletedRows > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return isRemoved;
    }


    public void closeDB() {
        try {
            if(conn != null)
                conn.close();
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}

