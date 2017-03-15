
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：TODO(描述这个类的作用) <br/>
 * 日期：2016年5月20日 下午4:14:41 <br/>
 *
 * @since JDK1.7
 * @author gebin
 * @version 
 */
public class MybatisUtil {
    
    public static void main(String[] args) {
        String packageName = "com.yonyou.boss.entity";
        String module = "productmodule";
        String entityClassName = "SysOrg";
        String mapperClassName = "SysOrg";
        
        try {
            Class class1 = Class.forName(entityClassName);
            System.out.println(class1.getPackage().getName());
        }
        catch (ClassNotFoundException e) {
            
        }
        
        //参数表明，有些连表查询的表前缀,没前缀就为空串
//        getColumnName("sys_org", "");
    }

    /**
     * TODO(这里描述这个方法的作用). <br/>
     *
     * @param string 字段
     * @param prefix 字段前缀
     */
    public static void whereWrite(List<String> list, String prefix){
        
        System.out.println("<!-- 模糊搜索and  -->");
        System.out.println("<if test=\"search != null\">");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("<if test=\"search." + list.get(i) + "!=null and search." + list.get(i) + "!='' \"> and  "+ prefix +"" + list.get(i) + " LIKE CONCAT(CONCAT('%', #{search." + list.get(i) + "}),'%')</if>");
        }
        System.out.println("</if>");
        System.out.println("<!-- 模糊搜索or  -->");
        System.out.println("<trim prefix=\" and (\" prefixix=\")\" prefixOverrides=\"AND|OR\" prefixixOverrides=\"AND|OR\">");
        System.out.println("<if test=\"searchor != null\">");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("<if test=\"searchor." + list.get(i) + "!=null and searchor." + list.get(i) + "!='' \"> or  "+ prefix +"" + list.get(i) + " LIKE CONCAT(CONCAT('%', #{searchor." + list.get(i) + "}),'%')</if>");
        }
        System.out.println("</if>");
        System.out.println("</trim>");
        
        System.out.println("<!-- 精确搜索  -->");
        System.out.println("<if test=\"searchconfirm != null\">");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("<if test=\"searchconfirm." + list.get(i) + "!=null and searchconfirm." + list.get(i) + "!='' \"> and  "+ prefix +"" + list.get(i) + " = #{searchconfirm." + list.get(i) + "}</if>");
        }
        System.out.println("</if>");
        System.out.println("<!-- 排序  -->");
        System.out.println("<if test=\"order != null\">");
        System.out.println("<trim prefix=\" order by \" prefixixOverrides=\",\">");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("<if test=\"order." + list.get(i) + "!=null and order." + list.get(i) + "!='' \"> "+ prefix +"" + list.get(i) + " ${order." + list.get(i) + "},</if>");
        }
        System.out.println("</trim>");
        System.out.println("</if>");
        System.out.println("<if test=\"start != null and length!=null\">limit #{start},#{length}</if>");
        
    }
    
    public static Connection getConn(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://10.10.5.24:3306/boss?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "group", "cluster@yonyou.com");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * TODO(获取表所有字段). <br/>
     *
     * @param tableName
     * @return
     */
    public static List<String> getColumnName(String tableName, String prefix){
        List<String> list = new ArrayList<String>();
        Connection conn = getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select COLUMN_NAME from information_schema.COLUMNS where table_name = '" + tableName + "' and table_schema = 'boss'");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("COLUMN_NAME"));
            }
        }
        catch (SQLException e) {
            
        }finally{
            try {
                rs.close();
                ps.close();
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            
        }
        whereWrite(list, prefix);
        
        
        
        return list;
    }
}
