package website.liujie.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description :数据库处理工具类
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : 深圳市华磊移动设备科技有限公司
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2016/4/19 0019
 */
public class SqlUtil {

    /**
     * 返回对象转为javabean
     *
     * @param rs
     * @param row
     * @return
     */
    public static Object resultSetToEntity(ResultSet rs, int row, Class cls) throws Exception {

        //取得Method
        Method[] methods = cls.getDeclaredMethods();
        // 用于获取列数、或者列类型
        ResultSetMetaData meta = rs.getMetaData();

        // 获取formbean实例对象
        Object obj = cls.newInstance();
        // 循环获取指定行的每一列的信息
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            // 当前列名
            String colName = meta.getColumnName(i);
            // 设置方法名
            String setMethodName = "set" + colName;
            //遍历Method
            for (int j = 0; j < methods.length; j++) {
                if (methods[j].getName().equalsIgnoreCase(setMethodName)) {
                    setMethodName = methods[j].getName();

                    // 获取当前位置的值，返回Object类型
                    Object value = rs.getObject(colName);
                    if (value == null) {
                        continue;
                    }

                    //实行Set方法
                    try {
                        //// 利用反射获取对象
                        //JavaBean内部属性和ResultSet中一致时候
                        Method setMethod = obj.getClass().getMethod(
                                setMethodName, value.getClass());
                        setMethod.invoke(obj, value);
                    } catch (Exception e) {
                        //JavaBean内部属性和ResultSet中不一致时候，自己到外部处理。
                        //e.printStackTrace();
                        continue;
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 返回对象转为javabean的新方法(老方法多表查询有问题)
     *
     * @param rs
     * @param row
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object resultSetToEntity2(ResultSet rs, int row, Class clazz) throws Exception {
        Object entity = null;

        entity = clazz.newInstance();
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnCount = rsMetaData.getColumnCount();
        List<String> columnNameList = new ArrayList<String>();
        for (int i = 0; i < columnCount; i++) {
            columnNameList.add(rsMetaData.getColumnLabel(i + 1).toLowerCase());
        }

        Class<?> fieldTypeClazz = null;
        Field[] fieldArr = getWholeFields(clazz);
        for (Field field : fieldArr) {
            field.setAccessible(true);
            if (columnNameList.contains(field.getName().toLowerCase())) {
                Object value = rs.getObject(field.getName());
                fieldTypeClazz = field.getType();
                if (value != null) {
                    Class<?> dbType = value.getClass();
                    if ("java.lang.String".equals(dbType.getName())) {//对应oracle的char、LONG、nvarchar2、varchar2
                    } else if ("java.lang.Double".equals(dbType.getName())) {//对应oracle的binary_double
                        value = Double.valueOf(value.toString());
                    } else if ("java.lang.Float".equals(dbType.getName())) {//对应oracle的binary_float
                        value = Float.valueOf(value.toString());
                    } else if ("java.math.BigDecimal".equals(dbType.getName())) {//对应oracel的number
                        if ("java.lang.Integer".equalsIgnoreCase(fieldTypeClazz.getName())) {
                            value = Integer.valueOf(value.toString());
                        } else if ("java.lang.Long".equalsIgnoreCase(fieldTypeClazz.getName())) {
                            value = Long.valueOf(value.toString());
                        } else if ("java.lang.Double".equalsIgnoreCase(fieldTypeClazz.getName())) {
                            value = Double.valueOf(value.toString());
                        }
                    } else if ("oracle.sql.BLOB".equals(dbType.getName())) {//对应oracle的BLOB
                        if ("java.io.InputStream".equalsIgnoreCase(fieldTypeClazz.getName())) {
                            value = rs.getBlob(field.getName()).getBinaryStream();
                        }
                    } else if ("oracle.sql.CLOB".equals(dbType.getName())) {//对应oracle的CLOB
                        if ("java.lang.String".equalsIgnoreCase(fieldTypeClazz.getName())) {
                            value = rs.getClob(field.getName()).toString();
                        } else if ("java.io.Reader".equalsIgnoreCase(fieldTypeClazz.getName())) {
                            value = rs.getClob(field.getName()).getCharacterStream();
                        }
                    } else if ("java.sql.Timestamp".equals(dbType.getName())) {//对应oralce date
                    } else if ("[B".equals(dbType.getName())) {
                    } else if ("oracle.sql.TIMESTAMPTZ".equals(dbType.getName())//对应oralce的timestamp_with_tim
                            || "oracle.sql.TIMESTAMPLTZ".equals(dbType.getName())//对应oralce的timestamp_with_local
                            || "oracle.sql.TIMESTAMP".equals(dbType.getName())) {//对应oracel的timestamp

                        if ("java.sql.Timestamp".equals(fieldTypeClazz.getName())) {
                            value = rs.getTimestamp(field.getName());
                        }
                    }
                } else {
                    if ("java.lang.Double".equals(fieldTypeClazz.getName())) {
                        value = 0.0d;
                    } else if ("java.lang.Float".equals(fieldTypeClazz.getName())) {
                        value = 0.0f;
                    }
                }
                try {
                    field.set(entity, value);
                } catch (Exception e) {
                    continue;
                }
            }
        }

        return entity;
    }

    /**
     * 获取所以的类变量，包括该类的父类的类变量
     *
     * @param clazz
     * @return
     */
    private static Field[] getWholeFields(Class<?> clazz) {
        Field[] result = clazz.getDeclaredFields();
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            Field[] tempField = superClass.getDeclaredFields();
            Field[] tempResult = new Field[result.length + tempField.length];
            for (int i = 0; i < result.length; i++) {
                tempResult[i] = result[i];
            }
            for (int i = 0; i < tempField.length; i++) {
                tempResult[result.length + i] = tempField[i];
            }
            result = tempResult;
            superClass = superClass.getSuperclass();
        }
        return result;
    }

    /**
     * 反射构建动态插入sql
     *
     * @param classitem
     * @return
     * @example (AAA, BBB) value(:aaa,:bbb)
     */
    public static String buildInsertSubSql(Class classitem) {
        Field[] fields = classitem.getDeclaredFields();
        StringBuilder sb1 = new StringBuilder("(");
        StringBuilder sb2 = new StringBuilder(" values (");
        for (int i = 0; i < fields.length; i++) {

            String name = fields[i].getName();
            if (!name.equals("serialVersionUID")) {
                sb2.append(":");
                sb1.append(name.toUpperCase());
                sb2.append(name);
                if (i != fields.length - 1) {
                    sb1.append(",");
                    sb2.append(",");
                }
            }
        }
        sb1.append(")");
        sb2.append(")");
        return sb1.append(sb2).toString();
    }

    /**
     * 反射构建动态更新sql
     *
     * @param classitem
     * @return
     * @example AAA=:AAA,BBB=:bbb
     */
    public static String buildUpdateSubSql(Class classitem) {
        Field[] fields = classitem.getDeclaredFields();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if (!name.equals("serialVersionUID")) {
                sb.append(name.toUpperCase() + "=:" + name);
                if (i != fields.length - 1) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 构建IN子句
     *
     * @param param (比如字符串：1,2)
     * @return
     */
    public static String buildInSubSql(String param) {
        //  LIKE in('1','2')
        StringBuilder sql = new StringBuilder();
        String[] strs = param.trim().split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append("'" + strs[i] + "'");
            if (i != strs.length - 1) {
                sb.append(",");
            }
        }
        sql.append("(" + sb.toString() + ")");
        return sql.toString();
    }

    /**
     * 构建IN子句
     *
     * @return
     */
    public static String buildInSubSql(List<String> params) {
        //  LIKE in('1','2')
        StringBuilder sql = new StringBuilder();
        Object[] strs = params.toArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append("'" + strs[i] + "'");
            if (i != strs.length - 1) {
                sb.append(",");
            }
        }
        sql.append("(" + sb.toString() + ")");
        return sql.toString();
    }

    public static void main(String[] args) {
        //buildDelSubSql(CosTollEquationRelation.class);
        System.out.print(SqlUtil.buildInSubSql("1,2"));
    }


    /**
     * 查询日期子句
     *
     * @param beginDate
     * @param endDate
     * @param type 1:yyyy-MM-dd
     * @return
     */
    public static final int DATE_FORMAT_1 = 1;//格式类型

    public static String buildDateSubSql(Date beginDate, Date endDate, String dbColName, int type) {

        StringBuilder sql = new StringBuilder();
        if (ObjectUtil.isNotEmpty(beginDate)) {
            if (type == DATE_FORMAT_1) {
                sql.append(" AND " + dbColName + ">='" + DateUtil.DateToStr(beginDate, DateUtil.FORMAT_YYYY_MM_DD) + " 00:00:00'");
            } else {
                sql.append(" AND " + dbColName + ">='" + DateUtil.DateToStr(beginDate, DateUtil.FORMAT_YYYY_MM_DD + "'"));
            }
        }

        if (ObjectUtil.isNotEmpty(endDate)) {
            if (type == DATE_FORMAT_1) {
                sql.append(" AND " + dbColName + "<='" + DateUtil.DateToStr(endDate, DateUtil.FORMAT_YYYY_MM_DD) + " 23:59:59'");
            } else {
                sql.append(" AND " + dbColName + "<='" + DateUtil.DateToStr(endDate, DateUtil.FORMAT_YYYY_MM_DD + "'"));
            }
        }
        return sql.toString();
    }

    private static int indexOfByRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return m.start();
        }
        return -1;
    }

    /**
     * 去除select 子句，未考虑union的情况
     *
     * @param sql
     * @return
     */
    public static String removeSelect(String sql) {
        int beginPos = indexOfByRegex(sql.toLowerCase(), "from");
        return sql.substring(beginPos);
    }

    /**
     * 去除orderby 子句
     *
     * @param sql
     * @return
     */
    public static String removeOrders(String sql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String removeFetchKeyword(String sql) {
        return sql.replaceAll("(?i)fetch", "");
    }

    public static String removeXsqlBuilderOrders(String string) {
        Pattern p = Pattern
                .compile("/~.*order\\s*by[\\w|\\W|\\s|\\S]*~/", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return removeOrders(sb.toString());
    }
}
