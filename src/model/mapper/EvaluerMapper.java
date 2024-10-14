package model.mapper;

import model.Evaluation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EvaluerMapper implements Mapper<Evaluation>{

    @Override
    public Evaluation map(ResultSet rs) throws SQLException {
        int note = rs.getInt("note");
        String date = rs.getString("dateEval");
        return new Evaluation(note, date);
    }
}
