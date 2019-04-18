package proxy;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SqlUtil {
    private String sql;
    private List<String> tables;
    private List<String> whereColumnNames;
    private List<String> setColumnNames;

    public SqlUtil(@NotNull String s){
        sql = s;
        String[] tokens = s.split("[\\s|,]+");

        if (tokens[0].equals("select")){
            getSelectField(tokens);
        } else if (tokens[0].equals("insert")){
            tables.add(tokens[2]);
        } else if (tokens[0].equals("update")){
            getInsertField(tokens);
        } else if (tokens[0].equals("delete")){

        }
    }

    private void getDeleteField(@NotNull String[] tokens){

    }

    private void getInsertField(@NotNull String[] tokens){
        int indexOfSet = 0, indexOfWhere = 0;
        for (int i = 1; i < tokens.length; ++i){
            if (tokens[i].equals("set")){
                indexOfSet = i;
                break;
            }
        }
        for (int i = 1; i < tokens.length; ++i){
            if (tokens[i].equals("Where")){
                indexOfWhere = i;
                break;
            }
        }
        for (int i = indexOfSet; i < indexOfWhere; ++i){
            if (tokens[i].equals("=")){
                setColumnNames.add(tokens[i - 1]);
            }
        }
        for (int i = indexOfWhere; i < tokens.length; ++i){
            if (tokens[i].equals("where") || tokens[i].equals("and")){
                whereColumnNames.add(tokens[i + 1]);
            }
        }


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
        for (int i = indexOfWhere; i < tokens.length; ++i){
            if (tokens[i].equals("where") || tokens[i].equals("and")){
                whereColumnNames.add(tokens[i + 1]);
            }
        }
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
