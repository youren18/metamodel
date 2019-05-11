package executor;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SqlUtil {
    private String sql;
    private List<String> tables;
    private List<String> whereColumnNames;
    private List<String> setColumnNames;
    private List<String> operator;

    public SqlUtil(@NotNull String s){
        tables = new LinkedList<>();
        whereColumnNames = new LinkedList<>();
        setColumnNames = new LinkedList<>();
        operator = new LinkedList<>();
        sql = s;
        String[] tokens = s.split("[\\s|,]+");
//        System.out.println("tokens");
//        for (String s1 : tokens){
//            System.out.println(s1);
//        }

        if (tokens[0].equals("select")){
            getSelectField(tokens);
        } else if (tokens[0].equals("insert")){
            tables.add(tokens[2]);
        } else if (tokens[0].equals("update")){
            getUpdateField(tokens);
        } else if (tokens[0].equals("delete")){
            getDeleteField(tokens);
        }
    }

    private void getDeleteField(@NotNull String[] tokens){
        tables.add(tokens[2]);
        int indexOfWhere = 0;
        for (int i = 1; i < tokens.length; ++i){
            if (tokens[i].equals("Where")){
                indexOfWhere = i;
                break;
            }
        }
        getWhere(indexOfWhere,tokens);

    }

    private void getUpdateField(@NotNull String[] tokens){
        tables.add(tokens[1]);
        int indexOfSet = 0, indexOfWhere = 0;
        for (int i = 2; i < tokens.length; ++i){
            if (tokens[i].equals("set")){
                indexOfSet = i;
                break;
            }
        }
        for (int i = 1; i < tokens.length; ++i){
            if (tokens[i].equals("where")){
                indexOfWhere = i;
                break;
            }
        }
        for (int i = indexOfSet; i < indexOfWhere; ++i){
            if (tokens[i].equals("=")){
                setColumnNames.add(tokens[i - 1]);
            }
        }
        getWhere(indexOfWhere,tokens);

    }

    private void getSelectField(@NotNull String [] tokens){
        int indexOfFrom = 0, indexOfWhere = 0;
        for (int i = 1; i < tokens.length; ++i){
            if (tokens[i].equals("from")){
                indexOfFrom = i;
                break;
            }
        }
        for (int i = 1; i < tokens.length; ++i){
            if (tokens[i].equals("where")){
                indexOfWhere = i;
                break;
            }
        }

        tables.addAll(Arrays.asList(tokens).subList(indexOfFrom + 1, indexOfWhere));
        getWhere(indexOfWhere,tokens);
    }

    private void getWhere(int indexOfWhere, String[] tokens){
        for (int i = indexOfWhere; i < tokens.length; ++i){
            if (tokens[i].equals("where") || tokens[i].equals("and")){
                whereColumnNames.add(tokens[i + 1]);
                operator.add(tokens[i + 2]);
            }
        }
    }

    public List<String> getOperator(){
        return operator;
    }

    public List<String> getSetColumnNames() {
        return setColumnNames;
    }

    public List<String> getWhereColumnNames() {
        return whereColumnNames;
    }

    public List<String> getTables() {
        return tables;
    }
}
