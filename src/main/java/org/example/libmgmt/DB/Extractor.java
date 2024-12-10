package org.example.libmgmt.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * return full Object T from sql.ResultSet.
 * @param <T> target Object.
 */
public interface Extractor<T> {
  T extract(ResultSet rs) throws SQLException;
}
