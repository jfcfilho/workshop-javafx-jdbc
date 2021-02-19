package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO{

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dep) {
		PreparedStatement st = null;

		try {
			String sql = "INSERT INTO department (Name) VALUES (?)";

			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dep.getName());

			int rowsAffecteds = st.executeUpdate();

			if(rowsAffecteds > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Unexpected error! No rows affected!");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new DBException(t.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department dep) {
		PreparedStatement st = null;

		try {
			String sql = "UPDATE department SET Name = ? WHERE Id = ?";

			st = conn.prepareStatement(sql);

			st.setString(1, dep.getName());
			st.setInt(2, dep.getId());

			st.executeUpdate();

		} catch (Throwable t) {
			t.printStackTrace();
			throw new DBException(t.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			String sql = "DELETE FROM department WHERE Id = ?";

			st = conn.prepareStatement(sql);

			st.setInt(1, id);

			st.executeUpdate();

		} catch (Throwable t) {
			t.printStackTrace();
			throw new DBException(t.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM department WHERE Id = ?";

			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				return instanciateDepartment(rs);
			}
			return null;
			
		} catch (Throwable t) {
			t.printStackTrace();
			throw new DBException(t.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM department ORDER BY Name";

			st = conn.prepareStatement(sql);
			
			rs = st.executeQuery();
			
			List<Department> departments = new ArrayList<>();
			
			while(rs.next()) {
				departments.add(instanciateDepartment(rs));
			}
			return departments;
			
		} catch (Throwable t) {
			t.printStackTrace();
			throw new DBException(t.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
