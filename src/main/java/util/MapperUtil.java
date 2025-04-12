package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MapperUtil {
    private static final SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载 MyBatis 配置文件
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getMapper(Class<T> clazz, SqlSession session) {
        return session.getMapper(clazz);
    }

    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }
}
