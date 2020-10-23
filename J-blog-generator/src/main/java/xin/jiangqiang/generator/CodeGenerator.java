package xin.jiangqiang.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang.StringUtils;

import java.util.Scanner;

/**
 * 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 *
 * @author JiangQiang
 * @date 2020/10/12 11:18
 */
public class CodeGenerator {
    // 生成的代码放到哪个工程中
    private static String PROJECT_NAME = "J-blog-system";
    // 数据库名称
    private static String DATABASE_NAME = "J_blog";
    // 子包名
    private static String MODULE_NAME = "admin1";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode = true & useSSL = false & characterEncoding = utf8 & serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("suixin");
        mpg.setDataSource(dsc);
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/";
        gc.setOutputDir(projectPath + PROJECT_NAME + "/src/main/java");
        gc.setIdType(IdType.ASSIGN_ID); // 分布式id
        gc.setAuthor("jiangqiang--blog.jiangqiang.xin");
        gc.setFileOverride(true); //覆盖现有的
        gc.setOpen(false); //是否生成后打开
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(MODULE_NAME +"xin.jiangqiang.blog"); //父包名
        pc.setController(MODULE_NAME + ".controller");
        pc.setService(MODULE_NAME +".service");
        pc.setServiceImpl(MODULE_NAME +".service.impl");
        pc.setMapper(MODULE_NAME +".mapper");
        pc.setXml(MODULE_NAME +".mapper.xml");
        pc.setEntity(MODULE_NAME +".entities");//实体类存储包名
        mpg.setPackageInfo(pc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); //使用lombok
        strategy.setEntitySerialVersionUID(true);// 实体类的实现接口Serializable
        strategy.setRestControllerStyle(true); // @RestController
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("j_blog_"); // 去掉表前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
