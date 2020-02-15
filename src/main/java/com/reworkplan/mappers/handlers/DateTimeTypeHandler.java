package com.reworkplan.mappers.handlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DateTimeTypeHandler implements TypeHandler<DateTime> {

    @Override
    public void setParameter(PreparedStatement ps, int i, DateTime parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setTimestamp(i, new Timestamp(parameter.getMillis()));
        } else {
            ps.setTimestamp(i, null);
        }
    }

    @Override
    public DateTime getResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnName);
        if (ts != null) {
            return new DateTime(ts.getTime(), DateTimeZone.UTC);
        } else {
            return null;
        }
    }

    @Override
    public DateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnIndex);
        if (ts != null) {
            return new DateTime(ts.getTime(), DateTimeZone.UTC);
        } else {
            return null;
        }
    }

    @Override
    public DateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (timestamp != null) {
            return new DateTime(timestamp.getTime(), DateTimeZone.UTC);
        } else {
            return null;
        }
    }


    /**
     * Returns the Date object for the first dat of the current month
     *
     * @return
     */
    public static Date startOfMonth() {
        DateTime currentDate = new DateTime(DateTimeZone.UTC);
        currentDate.withDayOfMonth(1);
        return currentDate.withDayOfMonth(1).toDate();
    }

    public static Date currentDate() {
        return new DateTime(DateTimeZone.UTC).toDate();
    }
}
