package org.example.libmgmt.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * return Object T from ResultSet.
 * @param <T> target Object.
 */
public interface Extractor<T> {
  T extract(ResultSet rs) throws SQLException;
}
