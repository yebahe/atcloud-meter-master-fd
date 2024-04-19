package net.lishen.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import org.apache.ibatis.type.JdbcType;

import java.util.Collections;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.db
 * @Project：atcloud-meter
 * @name：MyBatisPlusGenerator
 * @Date：2024-01-06 22:02
 * @Filename：MyBatisPlusGenerator
 */

public class MyBatisPlusGenerator {

    public static void main(String[] args) {

        String userName = "root";
        String password = "xdclass.net180";
        String serverInfo = "43.138.11.85:3306";
        String targetModuleNamePath = "/atcloud-data";
        String dbName = "test_data";
        String[] tables = {
//                "report", "report_detail_stress"
                "report_detail_api"
        };

        // 数据源配置
        FastAutoGenerator.create("jdbc:mysql://"+serverInfo+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&tinyInt1isBit=true", userName, password)
                .globalConfig(builder -> {
                    builder.author("joseph,")        // 设置作者
                            .commentDate("yyyy-MM-dd")
                            .enableSpringdoc()
                            .disableOpenDir() //禁止打开输出目录
                            .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期类型 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .outputDir(System.getProperty("user.dir") + targetModuleNamePath + "/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> {//Mysql下tinyint字段转换
                    builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                            return DbColumnType.BOOLEAN;
                        }
                        return typeRegistry.getColumnType(metaInfo);
                    });
                })
                .templateConfig(builder -> {
                    //设置不生成controller和service
                    builder.disable(TemplateType.CONTROLLER,TemplateType.SERVICE,TemplateType.SERVICE_IMPL);
                })
                .packageConfig(builder -> {
                    builder.parent("net.joseph") // 父包模块名
                            .entity("model")      //Entity 包名 默认值:entity
                            .mapper("mapper")     //Mapper 包名 默认值:mapper
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + targetModuleNamePath + "/src/main/resources/mapper")); // 设置mapperXml生成路,默认存放在mapper的xml下
                })

                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名 可变参数
                            .entityBuilder()// Entity策略配置
                            .enableFileOverride() // 开启生成Entity层文件覆盖
                            .idType(IdType.ASSIGN_ID)//主键策略  雪花算法自动生成的id
                            .enableLombok() //开启lombok
                            .logicDeleteColumnName("deleted")// 说明逻辑删除是哪个字段
                            .enableTableFieldAnnotation()// 属性加上注解说明
                            .formatFileName("%sDO")

                            .controllerBuilder()// Controller策略配置
                            .enableFileOverride() // 开启生成Controller层文件覆盖

                            .serviceBuilder()// Service策略配置
                            .enableFileOverride() // 开启生成Service层文件覆盖
                            .superServiceClass("")
                            .superServiceImplClass("")
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")

                            .mapperBuilder()// Mapper策略配置
                            .enableFileOverride() // 开启生成Mapper层文件覆盖
                            .formatMapperFileName("%sMapper")
                            .superClass(BaseMapper.class)
                            .enableBaseResultMap()
                            .enableBaseColumnList()

                            .formatXmlFileName("%sMapper");
                })
                .execute();

    }


}