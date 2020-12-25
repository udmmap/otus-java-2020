package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private final EntityClassMetaData ecmd;

    public EntitySQLMetaDataImpl(EntityClassMetaData ecmd) {
        this.ecmd = ecmd;
    }

    @Override
    public String getSelectAllSql() {
        List<Field> af = ecmd.getAllFields();
        return "select "
                + af.stream()
                .map(f->f.getName()).collect(Collectors.joining(","))
                + " from " + ecmd.getName();
    }

    @Override
    public String getSelectByIdSql() {
        List<Field> af = ecmd.getAllFields();
        return "select "
                + af.stream().map(f->f.getName()).collect(Collectors.joining(","))
                + " from " + ecmd.getName()
                + " where " + ecmd.getIdField().getName() + "=?";
    }

    @Override
    public String getInsertSql() {
        List<Field> af = ecmd.getAllFields();
        return "insert into "+ecmd.getName()+"("
                + af.stream().map(f->f.getName()).collect(Collectors.joining(","))
                + ") values ("
                + af.stream().map(f->"?").collect(Collectors.joining(","))
                + ")";
    }

    @Override
    public String getUpdateSql() {
        List<Field> af = ecmd.getAllFields();
        return "update "+ecmd.getName()
                + af.stream().map(f->" set "+f.getName()+"=?").collect(Collectors.joining(","))
                + " where " + ecmd.getIdField().getName() + "=?";
    }
}
