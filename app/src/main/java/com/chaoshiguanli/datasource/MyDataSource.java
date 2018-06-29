package com.chaoshiguanli.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/6/13.
 */

public class MyDataSource extends SQLiteOpenHelper {
    public MyDataSource(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "chaoshiguanli.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        进货记录表
        db.beginTransaction();
        try {

            db.execSQL("CREATE TABLE `jinhuojilu` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `barcode` varchar(40) DEFAULT NULL,\n" +
                    "  `commodityname` varchar(40) DEFAULT NULL,\n" +
                    "  `quantityofgoods` int(6) DEFAULT '0',\n" +
                    "  `purchaseprice` double(7,2) DEFAULT '0.00',\n" +
                    "  `commoditytype` varchar(10) DEFAULT NULL,\n" +
                    "  `noteinformation` varchar(200) DEFAULT NULL,\n" +
                    "  `vendorname` varchar(40) DEFAULT NULL,\n" +
                    "  `contactinformation` varchar(11) DEFAULT NULL,\n" +
                    "  `adddate` varchar(10) DEFAULT NULL,\n" +
                    "  `isupdate` int(1) DEFAULT '0'\n" +
                    ")");
//        库存表
            db.execSQL("CREATE TABLE `kucunbiao` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `barcode` varchar(40) DEFAULT NULL,\n" +
                    "  `commodityname` varchar(40) DEFAULT NULL,\n" +
                    "  `quantityofgoods` int(6) DEFAULT '0',\n" +
                    "  `commoditytype` varchar(10) DEFAULT NULL,\n" +
                    "  `saleprice` double(7,2) DEFAULT '0.00',\n" +
                    "  `dateofmanufacture` varchar(10) DEFAULT NULL,\n" +
                    "  `expirationdate` varchar(10) DEFAULT NULL,\n" +
                    "  `noteinformation` varchar(200) DEFAULT NULL,\n" +
                    "  `whetherithasexpired` tinyint(1) DEFAULT NULL\n" +
                    ")");
//        修改记录表
            db.execSQL("CREATE TABLE `xiugaijilu` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `barcode` varchar(40) DEFAULT NULL,\n" +
                    "  `commodityname` varchar(40) DEFAULT NULL,\n" +
                    "  `preinformation` varchar(200) DEFAULT NULL,\n" +
                    "  `alteredinformation` varchar(200) DEFAULT NULL,\n" +
                    "  `dateandtime` varchar(30) DEFAULT NULL,\n" +
                    "  `isupdate` int(1) DEFAULT '0'\n" +
                    ")");
//         商品类型表
            db.execSQL("CREATE TABLE `shangpingleixing` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `commoditytype` varchar(10) DEFAULT NULL,\n" +
                    "dateandtime varchar(30) DEFAULT NULL\n"+
                    ")");

//            商品信息表
            db.execSQL("CREATE TABLE `shangpingxingxi` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `barcode` varchar(40) DEFAULT NULL,\n" +
                    "  `commodityname` varchar(40) DEFAULT NULL,\n" +
                    "  `quantityofgoods` int(6) DEFAULT '0',\n" +
                    "  `commoditytype` varchar(10) DEFAULT NULL,\n" +
                    "  `saleprice` double(7,2) DEFAULT '0.00',\n" +
                    "  `adddate` varchar(30) DEFAULT NULL\n" +
                    ")");
//             销售记录
            db.execSQL("CREATE TABLE `xiaoshoujilu` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `saleno` varchar(32) DEFAULT NULL,\n" +
                    "  `barcode` varchar(40) DEFAULT NULL,\n" +
                    "  `commodityname` varchar(40) DEFAULT NULL,\n" +
                    "  `quantityofgoods` int(6) DEFAULT '0',\n" +
                    "  `commoditytype` varchar(10) DEFAULT NULL,\n" +
                    "  `saleprice` double(7,2) DEFAULT '0.00',\n" +
                    "  `dateofsale` varchar(30) DEFAULT NULL,\n" +
                    "  `isupdate` int(1) DEFAULT '0'\n" +
                    ")");
//             单号记录表
            db.execSQL("CREATE TABLE `danhaojilu` (\n" +
                    "  `usernumber` varchar(20) DEFAULT NULL,\n" +
                    "  `totalvalue` double(7,2) DEFAULT NULL,\n" +
                    "  `Saleno` varchar(32) DEFAULT NULL,\n" +
                    "  `dateofsale` varchar(30) DEFAULT NULL,\n" +
                    "  `isupdate` int(1) DEFAULT '0'\n" +
                    ")");

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
