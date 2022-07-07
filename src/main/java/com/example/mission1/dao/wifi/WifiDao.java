package com.example.mission1.dao.wifi;

import com.example.mission1.domain.wifi.PublicWifi;
import com.example.mission1.domain.wifi.SearchWifi;
import com.example.mission1.dto.wifi.PublicWifiReponseDto;
import com.example.mission1.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiDao {

    public int PublicWifiInsert(List<PublicWifi> publicWifiList) {
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO PUBLICWIFI "
                    + " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, "
                    + " X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
                    + " X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    + " ON DUPLICATE KEY UPDATE"
                    + " X_SWIFI_MGR_NO = values(X_SWIFI_MGR_NO) ,"
                    + " X_SWIFI_WRDOFC = values(X_SWIFI_WRDOFC) ,"
                    + " X_SWIFI_MAIN_NM = values(X_SWIFI_MAIN_NM) ,"
                    + " X_SWIFI_ADRES1 = values(X_SWIFI_ADRES1) ,"
                    + " X_SWIFI_ADRES2 = values(X_SWIFI_ADRES2) ,"
                    + " X_SWIFI_INSTL_FLOOR = values(X_SWIFI_INSTL_FLOOR) ,"
                    + " X_SWIFI_INSTL_TY = values(X_SWIFI_INSTL_TY) ,"
                    + " X_SWIFI_INSTL_MBY = values(X_SWIFI_INSTL_MBY) ,"
                    + " X_SWIFI_SVC_SE = values(X_SWIFI_SVC_SE) ,"
                    + " X_SWIFI_CMCWR = values(X_SWIFI_CMCWR) ,"
                    + " X_SWIFI_CNSTC_YEAR = values(X_SWIFI_CNSTC_YEAR) ,"
                    + " X_SWIFI_INOUT_DOOR = values(X_SWIFI_INOUT_DOOR) ,"
                    + " X_SWIFI_REMARS3 = values(X_SWIFI_REMARS3) ,"
                    + " LAT = values(LAT) ,"
                    + " LNT = values(LNT) ,"
                    + " WORK_DTTM = values(WORK_DTTM);";

            preparedStatement = connection.prepareStatement(sql);

            for(int i=0; i < publicWifiList.size(); i++){
                PublicWifi publicWifi = publicWifiList.get(i);

                preparedStatement.setString(1, publicWifi.getX_swifi_mgr_no());
                preparedStatement.setString(2, publicWifi.getX_swifi_wrdofc());
                preparedStatement.setString(3, publicWifi.getX_swifi_main_nm());
                preparedStatement.setString(4, publicWifi.getX_swifi_adres1());
                preparedStatement.setString(5, publicWifi.getX_swifi_adres2());
                preparedStatement.setString(6, publicWifi.getX_swifi_instl_floor());
                preparedStatement.setString(7, publicWifi.getX_swifi_instl_ty());
                preparedStatement.setString(8, publicWifi.getX_swifi_instl_mby());
                preparedStatement.setString(9, publicWifi.getX_swifi_svc_se());
                preparedStatement.setString(10, publicWifi.getX_swifi_cmcwr());
                preparedStatement.setString(11, publicWifi.getX_swifi_cnstc_year());
                preparedStatement.setString(12, publicWifi.getX_swifi_inout_door());
                preparedStatement.setString(13, publicWifi.getX_swifi_remars3());
                preparedStatement.setBigDecimal(14, publicWifi.getLnt());
                preparedStatement.setBigDecimal(15, publicWifi.getLat());
                preparedStatement.setTimestamp(16, Timestamp.valueOf(publicWifi.getWork_dttm()));

                // addBatch에 담기
                preparedStatement.addBatch();

                // 파라미터 Clear
                preparedStatement.clearParameters() ;
            }

            result = preparedStatement.executeBatch().length;
            System.out.println("result = " + result);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return result;
    }

    public List<PublicWifiReponseDto> getNearPublicWifiList(BigDecimal x, BigDecimal y) {
        List<PublicWifiReponseDto> result = null;
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT "
                    + " ROUND((6371 * acos ("
                    + "      cos (radians( " + x + "))"
                    + "      * cos(radians( p.LAT  ))"
                    + "      * cos(radians( p.LNT ) - radians(" + y + "))"
                    + "      + sin(radians(" + x + "))"
                    + "      * sin(radians( p.LAT))"
                    + "    )), 4) AS distance ,"
                    + " p.X_SWIFI_MGR_NO, p.X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, p.X_SWIFI_ADRES1, p.X_SWIFI_ADRES2, p.X_SWIFI_INSTL_FLOOR ,"
                    + " p.X_SWIFI_INSTL_TY, p.X_SWIFI_INSTL_MBY, p.X_SWIFI_SVC_SE, p.X_SWIFI_CMCWR, p.X_SWIFI_CNSTC_YEAR ,"
                    + " p.X_SWIFI_INOUT_DOOR, p.X_SWIFI_REMARS3, "
                    + " ROUND(p.LAT, 6) AS lat , "
                    + " ROUND(p.LNT, 6) AS lnt , "
                    + " p.WORK_DTTM "
                            + " FROM PUBLICWIFI p"
                    + " ORDER BY distance ASC limit 20";

            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();
            result = new ArrayList<>();
            while (resultSet.next()) {
                PublicWifiReponseDto publicWifi = PublicWifiReponseDto.builder()
                        .distance(resultSet.getString("distance"))
                        .x_swifi_mgr_no(resultSet.getString("X_SWIFI_MGR_NO"))
                        .x_swifi_wrdofc(resultSet.getString("X_SWIFI_WRDOFC"))
                        .x_swifi_main_nm(resultSet.getString("X_SWIFI_MAIN_NM"))
                        .x_swifi_adres1(resultSet.getString("X_SWIFI_ADRES1"))
                        .x_swifi_adres2(resultSet.getString("X_SWIFI_ADRES2"))
                        .x_swifi_instl_floor(resultSet.getString("X_SWIFI_INSTL_FLOOR"))
                        .x_swifi_instl_ty(resultSet.getString("X_SWIFI_INSTL_TY"))
                        .x_swifi_instl_mby(resultSet.getString("X_SWIFI_INSTL_MBY"))
                        .x_swifi_svc_se(resultSet.getString("X_SWIFI_SVC_SE"))
                        .x_swifi_cmcwr(resultSet.getString("X_SWIFI_CMCWR"))
                        .x_swifi_cnstc_year(resultSet.getString("X_SWIFI_CNSTC_YEAR"))
                        .x_swifi_inout_door(resultSet.getString("X_SWIFI_INOUT_DOOR"))
                        .x_swifi_remars3(resultSet.getString("X_SWIFI_REMARS3"))
                        .lat(resultSet.getBigDecimal("LAT").toString())
                        .lnt(resultSet.getBigDecimal("LNT").toString())
                        .work_dttm(resultSet.getTimestamp("WORK_DTTM").toString())
                        .build();
                result.add(publicWifi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return result;
    }

    public int historyWifiInsert(SearchWifi searchWifi) {
        int result = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO SEARCHWIFI "
                    + " (LAT, LNT, SEARCH_DTTM)"
                    + " VALUES(?, ?, now());";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, searchWifi.getLat());
            preparedStatement.setBigDecimal(2, searchWifi.getLnt());
            int affectdRow = preparedStatement.executeUpdate();

            if (affectdRow > 0) {
                result = affectdRow;
            }

            connection.commit() ;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return result;
    }

    public List<SearchWifi> histoyList() {
        List<SearchWifi> history_list = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT s.ID, s.LAT, s.LNT, s.SEARCH_DTTM"
                    + " FROM SEARCHWIFI s"
                    + " WHERE s.DELETE_YN = FALSE"
                    + " ORDER BY s.ID DESC";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SearchWifi searchWifi = SearchWifi.builder()
                        .id(resultSet.getInt("ID"))
                        .lat(resultSet.getBigDecimal("LAT"))
                        .lnt(resultSet.getBigDecimal("LNT"))
                        .search_dttm(new Timestamp(resultSet.getTimestamp("SEARCH_DTTM").getTime()).toLocalDateTime())
                        .build();

//                SearchWifiResponseDto searchWifiResponseDto = SearchWifiResponseDto.builder()
//                        .id(resultSet.getInt("ID"))
//                        .lat(resultSet.getBigDecimal("LAT").stripTrailingZeros().toString())
//                        .lnt(resultSet.getBigDecimal("LNT").stripTrailingZeros().toString())
//                        .search_dttm(new Timestamp(resultSet.getTimestamp("SEARCH_DTTM").getTime()).toLocalDateTime())
//                        .build();
                history_list.add(searchWifi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }

        return history_list;
    }

    public int selectOneHistory(int id) {
        int result = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT ID"
                    + " FROM SEARCHWIFI s"
                    + " WHERE s.ID = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            result = resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }
        return result;
    }

    public void deleteHistory(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();

            String sql = "UPDATE SEARCHWIFI s"
                    + " SET s.DELETE_YN = TRUE"
                    + " WHERE s.ID = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, preparedStatement, resultSet);
        }
    }
}
