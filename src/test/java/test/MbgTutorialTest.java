package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;

import net.harawata.domain.Person;
import net.harawata.domain.PersonExample;
import net.harawata.mapper.PersonMapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class MbgTutorialTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setUp() throws Exception {
        // create a SqlSessionFactory
        Reader reader = Resources.getResourceAsReader("MyBatisConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        reader.close();

        // populate in-memory database
        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        reader = Resources.getResourceAsReader("CreateDB.sql");
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setLogWriter(null);
        runner.runScript(reader);
        reader.close();
        session.close();
    }

    @Test
    public void getAPersonByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PersonMapper personMapper = sqlSession
                    .getMapper(PersonMapper.class);
            Person person = personMapper.selectByPrimaryKey(1);
            assertEquals("yamada tarou", person.getName());
            assertEquals("male", person.getGender());
            assertEquals("tarou@example.com", person.getEmail());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getAPersonByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PersonMapper personMapper = sqlSession
                    .getMapper(PersonMapper.class);
            PersonExample example = new PersonExample();
            example.createCriteria().andNameLike("%hanako")
                    .andGenderEqualTo("female");
            List<Person> persons = personMapper.selectByExample(example);
            assertEquals(1, persons.size());
            assertEquals(Integer.valueOf(2), persons.get(0).getId());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getAPersonWithPetsByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PersonMapper personMapper = sqlSession
                    .getMapper(PersonMapper.class);
            Person person = personMapper.selectPersonAndPetsByPrimaryKey(1);
            assertEquals("yamada tarou", person.getName());
            assertEquals(2, person.getPets().size());
            assertEquals("tarou@example.com", person.getEmail());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getAPersonWithPetsByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            PersonMapper personMapper = sqlSession
                    .getMapper(PersonMapper.class);
            PersonExample example = new PersonExample();
            example.createCriteria().andNameLike("yamada%");
            example.setOrderByClause("id");
            List<Person> persons = personMapper.selectPersonAndPetsByExample(example);
            assertEquals(2, persons.size());
            Person person1 = persons.get(0);
            assertEquals("yamada tarou", person1.getName());
            assertEquals(2, person1.getPets().size());
            Person person2 = persons.get(1);
            assertEquals("yamada hanako", person2.getName());
            assertEquals("hanako@example.com", person2.getEmail());
            assertEquals(3, person2.getPets().size());
        } finally {
            sqlSession.close();
        }
    }
}
