package com.transaction.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyBatisPlusGeneration {
    public static void main(String[] args) {
        String output_dir = "D:\\Android_Project\\group_project\\COMP-7506-Secondary-Market\\MyBatisPlus";
        String database_dir = "jdbc:mysql://localhost:3306/comp_7506";
        String database_username = "root";
        String database_pwd = "youpassword";
        FastAutoGenerator.create(database_dir, database_username, database_pwd)
                .globalConfig(builder -> {
                    builder.author("weiyanhu") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(output_dir); // 指定输出目录
                }).strategyConfig(builder -> {
            builder.mapperBuilder().enableBaseResultMap().enableMapperAnnotation().enableBaseColumnList();// 设置过滤表前
        }).templateConfig(templateConfig -> {
            templateConfig.controller("").service("").serviceImpl("");
        })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
