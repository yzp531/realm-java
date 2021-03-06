package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import some.test.Booleans;

public class BooleansRealmProxy extends Booleans {

    private static long INDEX_DONE;
    private static long INDEX_ISREADY;
    private static long INDEX_MCOMPLETED;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("done");
        fieldNames.add("isReady");
        fieldNames.add("mCompleted");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public boolean isDone() {
        realm.checkIfValid();
        return (boolean) row.getBoolean(INDEX_DONE);
    }

    @Override
    public void setDone(boolean value) {
        realm.checkIfValid();
        row.setBoolean(INDEX_DONE, (boolean) value);
    }

    @Override
    public boolean isReady() {
        realm.checkIfValid();
        return (boolean) row.getBoolean(INDEX_ISREADY);
    }

    @Override
    public void setReady(boolean value) {
        realm.checkIfValid();
        row.setBoolean(INDEX_ISREADY, (boolean) value);
    }

    @Override
    public boolean ismCompleted() {
        realm.checkIfValid();
        return (boolean) row.getBoolean(INDEX_MCOMPLETED);
    }

    @Override
    public void setmCompleted(boolean value) {
        realm.checkIfValid();
        row.setBoolean(INDEX_MCOMPLETED, (boolean) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_Booleans")) {
            Table table = transaction.getTable("class_Booleans");
            table.addColumn(ColumnType.BOOLEAN, "done");
            table.addColumn(ColumnType.BOOLEAN, "isReady");
            table.addColumn(ColumnType.BOOLEAN, "mCompleted");
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_Booleans");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_Booleans")) {
            Table table = transaction.getTable("class_Booleans");
            if(table.getColumnCount() != 3) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }
            if (!columnTypes.containsKey("done")) {
                throw new IllegalStateException("Missing column 'done'");
            }
            if (columnTypes.get("done") != ColumnType.BOOLEAN) {
                throw new IllegalStateException("Invalid type 'boolean' for column 'done'");
            }
            if (!columnTypes.containsKey("isReady")) {
                throw new IllegalStateException("Missing column 'isReady'");
            }
            if (columnTypes.get("isReady") != ColumnType.BOOLEAN) {
                throw new IllegalStateException("Invalid type 'boolean' for column 'isReady'");
            }
            if (!columnTypes.containsKey("mCompleted")) {
                throw new IllegalStateException("Missing column 'mCompleted'");
            }
            if (columnTypes.get("mCompleted") != ColumnType.BOOLEAN) {
                throw new IllegalStateException("Invalid type 'boolean' for column 'mCompleted'");
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type Booleans");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_DONE = table.getColumnIndex("done");
            INDEX_ISREADY = table.getColumnIndex("isReady");
            INDEX_MCOMPLETED = table.getColumnIndex("mCompleted");
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Booleans class is missing from the schema for this Realm.");
        }
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static Booleans createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
            throws JSONException {
        Booleans obj = realm.createObject(Booleans.class);
        if (!json.isNull("done")) {
            obj.setDone((boolean) json.getBoolean("done"));
        }
        if (!json.isNull("isReady")) {
            obj.setReady((boolean) json.getBoolean("isReady"));
        }
        if (!json.isNull("mCompleted")) {
            obj.setmCompleted((boolean) json.getBoolean("mCompleted"));
        }
        return obj;
    }

    public static Booleans createUsingJsonStream(Realm realm, JsonReader reader)
            throws IOException {
        Booleans obj = realm.createObject(Booleans.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("done") && reader.peek() != JsonToken.NULL) {
                obj.setDone((boolean) reader.nextBoolean());
            } else if (name.equals("isReady")  && reader.peek() != JsonToken.NULL) {
                obj.setReady((boolean) reader.nextBoolean());
            } else if (name.equals("mCompleted")  && reader.peek() != JsonToken.NULL) {
                obj.setmCompleted((boolean) reader.nextBoolean());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Booleans copyOrUpdate(Realm realm, Booleans object, boolean update, Map<RealmObject,RealmObject> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static Booleans copy(Realm realm, Booleans newObject, boolean update, Map<RealmObject,RealmObject> cache) {
        Booleans realmObject = realm.createObject(Booleans.class);
        cache.put(newObject, realmObject);
        realmObject.setDone(newObject.isDone());
        realmObject.setReady(newObject.isReady());
        realmObject.setmCompleted(newObject.ismCompleted());
        return realmObject;
    }

    static Booleans update(Realm realm, Booleans realmObject, Booleans newObject, Map<RealmObject, RealmObject> cache) {
        realmObject.setDone(newObject.isDone());
        realmObject.setReady(newObject.isReady());
        realmObject.setmCompleted(newObject.ismCompleted());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Booleans = [");
        stringBuilder.append("{done:");
        stringBuilder.append(isDone());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isReady:");
        stringBuilder.append(isReady());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{mCompleted:");
        stringBuilder.append(ismCompleted());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleansRealmProxy aBooleans = (BooleansRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aBooleans.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aBooleans.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aBooleans.row.getIndex()) return false;

        return true;
    }

}
